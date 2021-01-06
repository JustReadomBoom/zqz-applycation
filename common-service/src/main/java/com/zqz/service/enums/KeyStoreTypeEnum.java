package com.zqz.service.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zqz
 * @Description:
 * @Date: Created in 10:01 2020/12/29
 */
public enum KeyStoreTypeEnum {
    PLAINTEXT("PLAINTEXT", "", "密钥原文"),
    PKCS12("PKCS12", "certificate.p12", "PKCS12格式"),
    JKS("JKS", "certificate.jks", "JKS格式");

    private static final Map<String, KeyStoreTypeEnum> VALUE_MAP = new HashMap();
    private String value;
    private String filename;
    private String displayName;

    private KeyStoreTypeEnum(String value, String filename, String displayName) {
        this.value = value;
        this.filename = filename;
        this.displayName = displayName;
    }

    public static KeyStoreTypeEnum parse(String value) {
        return (KeyStoreTypeEnum)VALUE_MAP.get(value);
    }

    public String getValue() {
        return this.value;
    }

    public String getFilename() {
        return this.filename;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public static Map<String, KeyStoreTypeEnum> getValueMap() {
        return VALUE_MAP;
    }

    static {
        KeyStoreTypeEnum[] arr$ = values();
        int len$ = arr$.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            KeyStoreTypeEnum item = arr$[i$];
            VALUE_MAP.put(item.value, item);
        }

    }
}
