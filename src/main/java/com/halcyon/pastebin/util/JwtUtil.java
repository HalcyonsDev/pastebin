package com.halcyon.pastebin.util;

import com.halcyon.pastebin.security.JwtAuthentication;
import io.jsonwebtoken.Claims;

public class JwtUtil {
    public static JwtAuthentication getAuthentication(Claims claims) {
        JwtAuthentication jwtAuthInfo = new JwtAuthentication();
        jwtAuthInfo.setEmail(claims.getSubject());

        return jwtAuthInfo;
    }
}
