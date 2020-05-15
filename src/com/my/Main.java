package com.my;

public class Main {

    public static void main(String[] args) {
        encryption();
        System.out.println("======================== 上面是加密，下面是解密 =========================");
        decrypt();
    }

    public static void encryption() {
        String plaintext = "1234";
        String ciphertext = MyUtil.AESEncode(plaintext);
        System.out.println("plaintext : " + plaintext);
        System.out.println("ciphertext : " + ciphertext);
    }

    public static void decrypt() {
        String ciphertext = "MLoqTCZvJWnsTFAUeoZGaw==";
        String plaintext = MyUtil.AESDncode(ciphertext);
        System.out.println("ciphertext : " + ciphertext);
        System.out.println("plaintext : " + plaintext);
    }

}
