package com.my;

public class Main {

    public static void main(String[] args) {
        String ciphertext = encryption();
        System.out.println("------------- 上在加密,下面解密 -------------");
        decrypt(ciphertext);
    }

    public static String encryption() {
        String plaintext = "1qaz2wsx3edc";
        String ciphertext = MyUtil.AESEncode(plaintext);
        System.out.println("encryption() --->  plaintext : " + plaintext);
        System.out.println("encryption() --->  ciphertext : " + ciphertext);
        return ciphertext;
    }

    public static void decrypt(String ciphertext) {
        String plaintext = MyUtil.AESDncode(ciphertext);
        System.out.println("decrypt()  --->  ciphertext : " + ciphertext);
        System.out.println("decrypt()  --->  plaintext : " + plaintext);
    }

}
