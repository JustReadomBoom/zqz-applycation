package com.zqz.service.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author: zqz
 * @Description: 文件服务工具类
 * @Date: Created in 11:05 AM 2020/8/17
 */
@Slf4j
public class FileServiceUtil {


    /**
     * @Author: zqz
     * @Description: NIO方式读取文件至byte数组
     * @Param: [filePath]
     * @Return: byte[]
     * @Date: 11:18 AM 2020/8/17
     */
    public static byte[] readFile2ByteArrayNIO(String filePath) throws Exception {
        FileChannel channel = null;
        FileInputStream fs = null;
        File f = new File(filePath);
        if (!f.exists()) {
            throw new RuntimeException(String.format("模板文件(%s)不存在", filePath));
        }
        try {
            fs = new FileInputStream(f);
            channel = fs.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
            while ((channel.read(byteBuffer)) > 0) {
                // do nothing
                // System.out.println("reading");
            }
            return byteBuffer.array();
        } catch (Exception e) {
            log.error("Read file error:[{}]", e.getMessage());
            throw new RuntimeException(null == e.getMessage() ? "读取文件异常" : e.getMessage());
        } finally {
            try {
                if (null != channel) {
                    channel.close();
                }
                if (null != fs) {
                    fs.close();
                }
            } catch (Exception e) {
                log.error("关闭流异常:[{}]", e.getMessage(), e);
            }
        }
    }
}
