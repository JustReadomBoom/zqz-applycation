package com.zqz.service.sign;


import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * <tr>
 * AES utils
 *
 * @author: Dev.Yi.Zeng
 * @date: 2019/12/18 17:10
 * @since: v1.0
 */
public final class AESUtils {
    private static final int ALGORITHM_LENGTH = 128;
    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
    private static final String DEFAULT_CHARSET = "UTF-8";

    private AESUtils() {
    }

    /**
     * aes encryption
     * @param plain normal plain
     * @param key
     * @return
     */
    public static String encrypt(String plain, String key) {
        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(key));
            byte[] byteContent = plain.getBytes(DEFAULT_CHARSET);
            byte[] result = cipher.doFinal(byteContent);
            return Base64.getEncoder().encodeToString(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * aes decryption
     * @param cipherPlain encrypted plain
     * @param key
     * @return
     */
    public static String decrypt(String cipherPlain, String key) {
        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(key));
            byte[] plainByte = cipherPlain.getBytes(DEFAULT_CHARSET);
            byte[] result = cipher.doFinal(Base64.getDecoder().decode(plainByte));
            return new String(result, DEFAULT_CHARSET);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static SecretKeySpec getSecretKey(final String key) {
        KeyGenerator kg = null;
        try {
            kg = KeyGenerator.getInstance(KEY_ALGORITHM);
            kg.init(ALGORITHM_LENGTH, new SecureRandom(key.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("e" + e.getMessage());
        }
        return new SecretKeySpec(kg.generateKey().getEncoded(), KEY_ALGORITHM);
    }
}
