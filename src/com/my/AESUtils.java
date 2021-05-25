package com.my;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class AESUtils {
    // 密匙
    private static final String key = "thisisatestkey==";
    // 偏移量
    private static final String OFFSET = "1234567890abcdef";
    // 编码
    private static final String ENCODING = "UTF-8";
    //算法
    private static final String ALGORITHM = "AES";
    //AES是加密方式 CBC是工作模式 PKCS5Padding是填充模式
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";

    // 加密和解密 的key和iv(偏移)是一样的
    private static SecretKeySpec skeySpec;
    private static IvParameterSpec iv; // AES 为16位

    static {
        skeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.US_ASCII), ALGORITHM);
        iv = new IvParameterSpec(OFFSET.getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
    }

    public static String encrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(data.getBytes(ENCODING));
            return new BASE64Encoder().encode(encrypted);//此处使用BASE64做转码
        } catch (NoSuchAlgorithmException | BadPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | NoSuchPaddingException | IllegalBlockSizeException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] buffer = new BASE64Decoder().decodeBuffer(data);//此处使用BASE64做转码
            byte[] encrypted = cipher.doFinal(buffer);
            return new String(encrypted, ENCODING);
        } catch (NoSuchAlgorithmException | BadPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | NoSuchPaddingException | IOException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String plaintext = "1234";
        String ciphertext = encrypt(plaintext);
        System.out.println("ciphertext:\n" + ciphertext);
        String plaintext2 = decrypt(ciphertext);
        System.out.println("plaintext2:\n" + plaintext2);
    }
}
