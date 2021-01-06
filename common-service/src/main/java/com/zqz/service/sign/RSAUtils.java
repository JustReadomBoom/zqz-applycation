package com.zqz.service.sign;

import javax.crypto.Cipher;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * <br>
 *
 * @description: RSA signature utils
 * @author: Dev.Yi.Zeng
 * @version: v1.0
 * @time: 2019/11/22 12:06
 */
public class RSAUtils {
    private static final String RSA_ALGORITHM = "RSA";
    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
    private static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * 签名
     * @param privateKey 私钥
     * @param plainText  明文
     * @return
     */
    public static byte[] sign(String privateKey, String plainText) {
        RSAKey rsaKey = loadKey(privateKey, Cipher.PRIVATE_KEY);
        byte[] signed = null;
        try {
            Signature Sign = Signature.getInstance(SIGNATURE_ALGORITHM);
            Sign.initSign((PrivateKey) rsaKey);
            Sign.update(plainText.getBytes(DEFAULT_CHARSET));
            signed = Sign.sign();
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return signed;
    }

    /**
     * 验签
     * @param publicKey  公钥
     * @param plainText  明文
     * @param signed     签名
     */
    public static boolean verifySign(String publicKey, String plainText, byte[] signed) {
        RSAKey rsaKey = loadKey(publicKey, Cipher.PUBLIC_KEY);
        boolean result = false;
        try {
            Signature verifySign = Signature.getInstance(SIGNATURE_ALGORITHM);
            verifySign.initVerify((PublicKey) rsaKey);
            verifySign.update(plainText.getBytes(DEFAULT_CHARSET));
            result = verifySign.verify(signed);
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private static RSAKey loadKey(String key, int type) {
        RSAKey rsaKey = null;
        try {
            byte[] buffer = Base64Utils.decode(key);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
            if (Cipher.PRIVATE_KEY == type) {
                PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
                rsaKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
            } else {
                X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
                rsaKey = (RSAKey) keyFactory.generatePublic(keySpec);
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        return rsaKey;
    }
}