package com.my;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/*
 * AES对称加密和解密
 */
public class MyUtil {

    // 加密密钥key
    private static String key = "thisisatestkey==";

    // 默认的加密算法
    private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    // 统一编码格式
    public static final String ENCODE_UTF_8 = "UTF-8";
    public static final String ENCODE_ISO_8859_1 = "ISO-8859-1";

    // 加密和解密 的key是一样的
    private static SecretKey secretKey;

    static {
        try {
            // 根据ecnodeRules规则初始化密钥生成器
            //AES算法要求有一个可信任的随机数源，可以通过SecureRandom类,内置两种随机数算法，NativePRNG和SHA1PRNG
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(key.getBytes());
            // 创建AES算法生成器
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            // 初始化算法生成器,传入一个128位的随机源
            keygen.init(128, random);
            // 产生原始对称密钥
            secretKey = keygen.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    // 参数和返回值 是字符串格式
    public static String AESEncode(String plaintext) {
        try {
            // 获取待加密字符串的二进制数据
            byte[] byte_encode = plaintext.getBytes(ENCODE_UTF_8);
            // 进行加密
            byte[] byte_AES = encrypt(byte_encode);
            // 将加密后的二进制数据 作base64转码,目的是获取到字符串
            return Base64.getEncoder().encodeToString(byte_AES);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 加密核心代码
    public static byte[] encrypt(byte[] data) {
        try {
            // 根据指定算法AES生成密码器
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            // 初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的 私钥
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            // 进行加密
            return cipher.doFinal(data);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String AESDncode(String ciphertext) {
        try {
            // 将加密并编码后的内容解码成二进制数据
            byte[] byte_content = Base64.getDecoder().decode(ciphertext);

            // 进行解密操作
            byte[] byte_decode = decrypt(byte_content);
            // 解密完成后把二进制数据转换成字符串
            return new String(byte_decode, ENCODE_UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 解密核心代码
    public static byte[] decrypt(byte[] data) {
        try {
            // 根据指定算法AES生成密码器
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            // 初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的 私钥
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            // 进行解密操作
            return cipher.doFinal(data);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
