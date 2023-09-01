package com.example.animalplatform.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Sha256 해시 알고리즘,
 * 비즈케어 API 통신에 필요한 고객사 사번에 사용
 */
public class SHA256 {

    public static String encrypt(String text){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md.update(text.getBytes());

        return bytesToHex(md.digest());
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

}