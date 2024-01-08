package com.halcyon.pastebin.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class HashGenerator {
    public static String generateShortURL(Long id) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        byte[] hash = messageDigest.digest(String.valueOf(id).getBytes(StandardCharsets.UTF_8));

        String base64Hash = Base64.getUrlEncoder().encodeToString(hash);
        return base64Hash.substring(0, 7);
    }
}
