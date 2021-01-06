package com.zqz;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zqz.service.enums.KeyStoreTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.Assert;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.*;
import java.util.function.Consumer;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 14:33 2020/10/23
 */
@Slf4j
public class CommonTest {


    @Test
    public void testMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("name", "zqz");
        map.put("age", 20);
        map.put("address", "GZ");

        Set<Map.Entry<String, Object>> entrySet = map.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = entrySet.iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Object> next = iterator.next();
            String key = next.getKey();
            Object value = next.getValue();
            System.out.println("key: " + key);
            System.out.println("value: " + value);
        }

        for(Map.Entry<String, Object> entry : map.entrySet()){
            String key = entry.getKey();
            Object value = entry.getValue();
            System.out.println("key: " + key);
            System.out.println("value: " + value);
        }

    }

    @Test
    public void test2(){
//        BigDecimal amt = new BigDecimal("20.75");
//        System.out.println(amt.intValue());
//        System.out.println(amt.setScale(0, BigDecimal.ROUND_HALF_UP));
        String json = "{\n" +
                "            \"creditResult\":{\n" +
                "                  \"value\":\"zhouqizhi\",\n" +
                "                  \"index\":23\n" +
                "             }\n" +
                "        }";
        JSONObject dataJson = JSON.parseObject(json);

//        String creditResult = dataJson.getJSONObject("creditResult").getString("value");
        String a = Optional.of(dataJson).map(Obj -> Obj.getJSONObject("creditResult")).map(S -> S.getString("value")).orElse(null);
        System.out.println(a);

        String filePath = "/Users/zhouqizhi/Desktop/index.jpg";
        int index = filePath.lastIndexOf(".");
        String substring = filePath.substring(filePath.lastIndexOf("."));
        System.out.println(substring);

        Hashtable<String, Object> hashtable = new Hashtable<>();
        hashtable.put("name", "zhouqizhi");
        Map<String, Object> map = new HashMap<>();
        map.put("age", 27);
        map.put("add", "GZ");
        hashtable.putAll(map);
        System.out.println(JSON.toJSONString(hashtable));


    }


    @Test
    public void test3(){
        String data = "123,345,789,222,908";
        String[] array = data.split(",");
        List<String> list = new ArrayList<>(data.length());
        Collections.addAll(list, array);
        System.out.println(JSON.toJSONString(list));
    }

    @Test
    public void test4(){
        System.out.println(KeyStoreTypeEnum.PKCS12.getValue());

    }

    private static void readLine(BufferedReader br, Consumer<String> handle, boolean close) {
        String s;
        try {
            while (((s = br.readLine()) != null)) {
                handle.accept(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (close && br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void testPubKey(){
        String key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0GQG3IH8i9xZmRhqBlkukXBg10ouZDZcAqKs4WlgTv8Z46JGmbkX9ZCTLIM347SVGBUXUE49d8P71djx2WL4jey8eWKkvyxy0/5gA6IJ6qLH5dpOxWyfxpJJGVYPlE2Xk3b2KB1UFE9XPABXym70ontDfTJJnD6MdR47bPIjLu3sK/F49ytX48arlUQd0W77JVb9Rkq9aYR7Jlag1I5db1FFUOFgO25QT5g8dWHjNdfUuEIqKzEJzMnZj3W6w+mHNSdrkXVFd8DHGZsL6l1gJ1sw+LgsiRSb0FPFLW+1nApma5j4NeBdAHhuqG4es/O2X++iUdI1VdwZUNYaVRa0bQIDAQAB";
        String path = "/Users/zhouqizhi/my_doc/仁东资料/易宝支付/商户10033597080证书/公钥/2020052602.p7b";
        PublicKey publicKey = readPublicKey(path);
        System.out.println(publicKey);
    }


    public PublicKey readPublicKey(String cerFileName) {

        CertificateFactory cf;
        FileInputStream in;
        Certificate c;
        PublicKey pk = null;
        try {
            cf = CertificateFactory.getInstance("X.509");
            in = new FileInputStream(cerFileName);
            c = cf.generateCertificate(in);
            pk = c.getPublicKey();

        } catch (CertificateException e) {

            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return pk;
    }

    @Test
    public void testQry(){
        Map<String, Object> result = new HashMap<>();
        result.put("accountAmount", 85.58);
        result.put("customerNumber", "10033597080");
        result.put("errorCode", "BAC001");
        result.put("rjtValidAmount", 0.00);
        result.put("wtjsValidAmount", 86.39);

        String errorcode = Optional.ofNullable(result.get("errorcode")).map(String::valueOf).orElse(null);
        String errormsg = String.valueOf(result.get("errormsg"));
        String errorCode = String.valueOf(result.get("errorCode"));
        String errorMsg = String.valueOf(result.get("errorMsg"));

        if (null != errorcode) {
            log.info("=====易宝可用余额查询返回公共错误:[{}][{}]", errorcode, errormsg);
        }

    }


}
