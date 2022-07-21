package com.zqz.service.antcloud;

import cn.com.antcloud.api.AntFinTechApiClient;
import cn.com.antcloud.api.AntFinTechProfile;
import cn.com.antcloud.api.appex.v1_0_0.model.NameValuePair;
import cn.com.antcloud.api.appex.v1_0_0.request.SaveSolutionFastnotaryRequest;
import cn.com.antcloud.api.appex.v1_0_0.response.SaveSolutionFastnotaryResponse;
import com.alibaba.fastjson.JSON;

import java.util.Arrays;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: AntCloudApiTest
 * @Date: Created in 11:28 2022/1/19
 */

public class AntCloudApiTest {


    public static void main(String[] args) {
        String endpoint = "https://admin.antchain.antgroup.com/";
        String accessKey = "LTAI5tPBdNoyo644NarzApyb";
        String secretKey = "X74uDSPC1R5cPjXEGjW7sTbaz2Pj2C";
        String instanceId = "appex";
        String fastAppDid = "did:mychain:ad142b89241c8b4ebdf0b3b0dceb76b801d5c07a333dcdf14ff7c423d4f4f4a3";

        try {
            AntFinTechProfile antFinTechProfile = AntFinTechProfile.getProfile(endpoint, accessKey, secretKey);
            AntFinTechApiClient antFinTechApiClient = new AntFinTechApiClient(antFinTechProfile);
            //1.发起存证
            //schemaName为定义的数据表表名
            String schemaName = "apptest";
            SaveSolutionFastnotaryRequest saveSolutionFastnotaryRequest = new SaveSolutionFastnotaryRequest(instanceId);
            saveSolutionFastnotaryRequest.setAppDid(fastAppDid);
            saveSolutionFastnotaryRequest.setSchemaName(schemaName);
            //根据数据表key-value进行填写
            NameValuePair nameValuePair1 = new NameValuePair();
            nameValuePair1.setName("id");
            nameValuePair1.setValue("2");
            NameValuePair nameValuePair2 = new NameValuePair();
            nameValuePair2.setName("name");
            nameValuePair2.setValue("张无忌");
            NameValuePair nameValuePair3 = new NameValuePair();
            nameValuePair3.setName("age");
            nameValuePair3.setValue("37");
            NameValuePair nameValuePair4 = new NameValuePair();
            nameValuePair4.setName("address");
            nameValuePair4.setValue("中国北京");
            NameValuePair nameValuePair5 = new NameValuePair();
            nameValuePair5.setName("ctime");
            nameValuePair5.setValue("2022-01-19 11:42:49");
            saveSolutionFastnotaryRequest.setAttributes(Arrays.asList(nameValuePair1, nameValuePair2, nameValuePair3, nameValuePair4));
            SaveSolutionFastnotaryResponse saveSolutionFastnotaryResponse = antFinTechApiClient.execute(saveSolutionFastnotaryRequest);
            System.out.println(JSON.toJSONString(saveSolutionFastnotaryResponse));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
