package com.zqz.service.sign;


import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;

public class Base64Utils {
    public static byte[] decode(String base64)  {
        return Base64.decodeBase64(base64);
    }

    public static String encode(byte[] bytes)   {
        try {
            return new String(Base64.encodeBase64(bytes), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}