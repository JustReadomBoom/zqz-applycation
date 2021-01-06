package com.zqz.service.aliyun;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.zqz.service.utils.DateUtil;
import com.zqz.service.utils.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Base64;
import java.util.Date;


/**
 * @Author: zqz
 * @Description: 阿里云上传文件服务
 * @Date: Created in 5:46 PM 2019/12/25
 */
@Service
@Slf4j
public class AliYunService {
    @Autowired
    private AliYunProperties aliYunProperties;

    public String uploadBase64File(String base64File, String filePath) {
        String key = aliYunProperties.getParentPath() + "/" + System.nanoTime() + filePath;
        log.debug("key=[{}]", key);
        OSSClient ossClient = new OSSClient(aliYunProperties.getIntranet(), aliYunProperties.getSecretId(), aliYunProperties.getSecretKey());
        try {
            byte[] bytes = Base64.getDecoder().decode(base64File.trim());
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            PutObjectResult putObjectResult = ossClient.putObject(aliYunProperties.getBucket(), key, inputStream);
            log.debug("============文件上传阿里云结果:[{}]", putObjectResult.getETag());
        } catch (Exception e) {
            log.error(">>>>>>>>>>>>文件上传阿里云异常:[{}]", e.getMessage());
            return null;
        } finally {
            ossClient.shutdown();
        }
        String fileUrl = "https://" + aliYunProperties.getBucket() + "." + aliYunProperties.getExtranet() + "/" + key;
        log.info("============文件上传阿里云url:[{}]", fileUrl);
        return fileUrl;
    }

    public String uploadFile(String dstFile, String filePath) {
        log.info("<-----------合同上传阿里云开始,dstFile:[{}]", dstFile);
        try {
            // 创建两层目录结构，按时间创建文件夹
            String timeDir = DateUtil.getDateFormat4Str(new Date());
            // 生成20位随机字符串
            String randomString = RandomUtil.getRandomString(20);
            String key = aliYunProperties.getParentPath() + filePath + "/" + timeDir + "/" + randomString + dstFile.substring(dstFile.lastIndexOf("."));

            // 将合同PDF保存至阿里云OSS, 取得URL返回
            // 创建OSSClient实例。
            OSSClient ossClient = new OSSClient(aliYunProperties.getIntranet(), aliYunProperties.getSecretId(), aliYunProperties.getSecretKey());

            // 上传文件。<yourLocalFile>由本地文件路径加文件名包括后缀组成，例如/users/local/myfile.txt。
            PutObjectResult putObjectResult = ossClient.putObject(aliYunProperties.getBucket(), key, new File(dstFile));
            // putobjectResult会返回文件的etag
            String etag = putObjectResult.getETag();
            log.debug("<----------上传阿里云结果[{}]---------->", etag);
            // 关闭OSSClient。
            ossClient.shutdown();
            String url = "https://" + aliYunProperties.getBucket() + "." + aliYunProperties.getExtranet() + "/" + key;
            log.info("<------------合同上传阿里云结束,url=[{}]----------->", url);
            // 合同地址加上域名
            return url;
        } catch (Exception e) {
            log.error("<----------上传阿里云异常[{}]------------>", e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 上传文件流
     */
    public String upload(InputStream inputStream, String fileUrl) {
        if (inputStream == null) {
            return null;
        }

        OSSClient ossClient = new OSSClient(aliYunProperties.getIntranet(), aliYunProperties.getSecretId(), aliYunProperties.getSecretKey()); // 创建OSS客户端
        try {
            String bucket = aliYunProperties.getBucket();
            if (!ossClient.doesBucketExist(bucket)) {// 判断文件容器是否存在，不存在则创建
                log.debug("====> bucket[{}]不存在，开始创建", bucket);
                ossClient.createBucket(bucket);
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucket);
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                ossClient.createBucket(createBucketRequest);
            }

            fileUrl = aliYunProperties.getParentPath() + "/" + fileUrl;
            PutObjectResult result = ossClient.putObject(new PutObjectRequest(bucket, fileUrl, inputStream));// 上传文件
            if (null != result) {

                log.debug("====> 上传文件成功，eTag[{}]，文件地址：{}", result.getETag(), fileUrl);
                return "https://" + bucket + "." + aliYunProperties.getExtranet() + "/" + fileUrl;
            }
        } catch (OSSException oe) {
            log.error("====>>> 上传OSS失败：{}", oe.getMessage());
            log.debug("", oe);
        } catch (Exception e) {
            log.debug("====>>> 上传文件失败：{}", e.getMessage());
            log.debug("", e);
        } finally {
            ossClient.shutdown();// 关闭OSS服务，一定要关闭
        }

        return null;
    }
}
