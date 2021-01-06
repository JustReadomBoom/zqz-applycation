package com.zqz;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zqz.service.sign.Base64Utils;
import com.zqz.service.sign.DigestUtils;
import com.zqz.service.sign.RSAUtils;
import org.junit.Test;

import java.util.Arrays;

/**
 * <br>
 *
 * @description:
 * @author: Dev.Yi.Zeng
 * @version: v1.0
 * @time: 2019/11/26 14:27
 */
public class RSATest {
    private static final String PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCgAp4h6btZRg61pWIVD6sIVSfSOub5e305fgUeWbgir0+Lxdfm1INuyJ0s3zHzHxnBmL+gznLv9aZJLMDaZdz4X5TpAJohfg3mLRe75BX+dzQfWzpYf45y49yfk6alPakHwhmhPf+kX+ln1tLVNk04SWidFSkMcx3FdSfI+u/1OnhVcV2vO0z9M+2a42Q1fRCNRNOCMTvIT8WWKXgcej9Fm3PSD2pr952TmeLcyJQY9cmLxo9yre5xw6Kt+crRiLsHTjpUQ+7kT7AJKmYBn1nJ+fkTkw+zkX5CMQJtagvpsWnUFulaJmzfEY4gBKuf+u8kYDliIjcZ4/06zOkRpfNPAgMBAAECggEASVsJ3ehGoJXSlon8XkVxln9bozCnxUZM0XeppxFgrl4KrLyFz2gr3TURgYnNX3UpnKyHZZRf8wx+MFvZCgba9+7x2KQTBTnB631OvS/CuYcymRF8vSe1SYyXD31kT6rl/A2LLHW85kGmQpmkzGR5oE1fLWyPBgscYfLky2nnalerTrq4vtC/n2vOV1WaX9fpzkxrBpAUmVr8zFHosgP36nMrxtWr9+jzfcSDfIPwwuOw22DM7L8ZhyKTomlatnbZqMbrg7NUqIEatggJnjdnP4WBl44hEHhl4aedpK8ridwmd2QGPPwClSgxbxLNh+merRwznHXI7RCxZXf63kIPeQKBgQDM4ByYjtAj0djq8rkM5zhL9S5p33UJE2jUbZblf/GhGZiY58MHk6PnGzC6ebr1cfgww8T/dllfFXTpfw9fXxSz9D1e8I/9VaFz8tutvJQoRBnS3nQNdmWCt1g9euhZ80tbOgAfJ0+5ZplbzZuycEXgXEdw0Cm5hffzQNjyX/p5WwKBgQDH8Gs1iKEXmTlxAWBFJQbVxQxw2k4nXLE+g5MVdCYsBuuie8qg2Bj+hcVdcbR1VD5hdxs7dzR6UBRilsgFbGyl1SRd16S0re1wNvgLD86gGnNXQFP5yjvwRxqn/ez+GhXb239DdFng71DrmVvSLoNaMwfhjirIuMswNOunOrTcHQKBgQC/Et1YowkX/ySxqwJPOG5W2Soab9+1QTbpt/Odwz/Voguhv7UzIc0hQ0ilAdcOBucUg+D86KHdfiN1iZ0Ks6CZXNbH7tA701sqynt7g38iNrFm6zWNQLjXuqs/W5vMGI5VXLi0VXOTVE7/QblOKEvjPe0bF+lyL7HaWsZ69myiNQKBgQCV6yMoaAOKw1y77HE4R5RBdFhownh5sVpB6ry+CHKa7rF/C12bj43mQ34DHEBY/cssn/eudirGUAeJCVCAKJWrHw/+wbSBuizgyIbjHH4ttMeps9WD5m1dNKO7NX6f5gVf7s0VUkgWjrvZfQvJpRDe8IbHTSetjErnIbdVlIKKzQKBgBaGZcOm+Wg+pc82CguWMPmbq/xEqADYxnXWxLkdpUKO3hlJQ0Ww0pU0Bpmc0UpMEhbqY36THqXvDPXNXBgGDHvFyD9NU6+/dKoutS2SLvQCzwv/8SNORg0aWNi3biV+bYAGu5G8geAxOmrjMhQqkgGLJ2Od7GwkWXXhM87h7La7";
    private static final String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoAKeIem7WUYOtaViFQ+rCFUn0jrm+Xt9OX4FHlm4Iq9Pi8XX5tSDbsidLN8x8x8ZwZi/oM5y7/WmSSzA2mXc+F+U6QCaIX4N5i0Xu+QV/nc0H1s6WH+OcuPcn5OmpT2pB8IZoT3/pF/pZ9bS1TZNOElonRUpDHMdxXUnyPrv9Tp4VXFdrztM/TPtmuNkNX0QjUTTgjE7yE/Flil4HHo/RZtz0g9qa/edk5ni3MiUGPXJi8aPcq3uccOirfnK0Yi7B046VEPu5E+wCSpmAZ9Zyfn5E5MPs5F+QjECbWoL6bFp1BbpWiZs3xGOIASrn/rvJGA5YiI3GeP9OszpEaXzTwIDAQAB";

    @Test
    public void testRSA() {
        String plain = "{\"journalNo\":\"201911270000000001\",\"requestTime\":\"20191127110900\",\"clientId\":\"10000000\",\"version\":\"1.0\",\"signType\":\"RSA\",\"appVersion\":\"1.0.0\",\"osVersion\":\"1.0.0\",\"terminalType\":\"IOS\",\"terminalId\":\"1\",\"deviceId\":\"abc-dbcd-sdc\",\"test\":\"测试\",\"object\":{\"key1\":\"val1\"},\"array\":[{\"key1\":\"val1\"}]}";
        plain = convert(JSONObject.parseObject(plain));
        System.out.println("排序后，待签名报文：" + plain);
		
        byte[] signBytes = RSAUtils.sign(PRIVATE_KEY, plain);
        String base64Encode = Base64Utils.encode(signBytes);
        System.out.println("Base64编码后报文：" + base64Encode);

        byte[] verifyBytes = Base64Utils.decode(base64Encode);
        boolean result = RSAUtils.verifySign(PUBLIC_KEY, plain, verifyBytes);
        System.out.println("验签结果：" + result);
    }
	
	@Test
    public void testMD5AndRSA() throws Exception{
        String plain = "{\"journalNo\":\"201911270000000001\",\"requestTime\":\"20191127110900\",\"clientId\":\"10000000\",\"version\":\"2.0\",\"signType\":\"RSA\",\"appVersion\":\"1.0.0\",\"osVersion\":\"1.0.0\",\"terminalType\":\"IOS\",\"terminalId\":\"1\",\"deviceId\":\"abc-dbcd-sdc\",\"test\":\"测试\",\"object\":{\"key1\":\"val1\"},\"array\":[{\"key1\":\"val1\"}]}";
        plain = convert(JSONObject.parseObject(plain));
        System.out.println("排序后，待签名报文：" + plain);
		
//		plain = DigestUtils.md5DigestAsHex(plain.getBytes("UTF-8"));
//        System.out.println("md5签名串:" + plain);

        byte[] signBytes = RSAUtils.sign(PRIVATE_KEY, plain);
        String base64Encode = Base64Utils.encode(signBytes);
        System.out.println("Base64编码后报文：" + base64Encode);

        byte[] verifyBytes = Base64Utils.decode(base64Encode);
        System.out.println("验签明文:" + plain);
        boolean result = RSAUtils.verifySign(PUBLIC_KEY, plain, verifyBytes);
        System.out.println("验签结果：" + result);
    }

    private String convert(JSONObject json) {
        String[] keys = json.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            Object object = json.get(key);
            if (object instanceof JSONObject) {
                sb.append(key).append(convert((JSONObject) object));
            } else if (object instanceof JSONArray) {
                sb.append(key).append(convert((JSONArray) object));
            } else {
                sb.append(key).append(json.get(key));
            }
        }
        return sb.toString();
    }

    private String convert(JSONArray json) {
        StringBuilder sb = new StringBuilder();
        for (Object o : json) {
            if (o instanceof JSONObject) {
                sb.append(convert((JSONObject) o));
            } else if (o instanceof JSONArray) {
                sb.append(convert((JSONArray) o));
            } else {
                sb.append(o);
            }
        }
        return sb.toString();
    }
}
