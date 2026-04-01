package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {

    // 🔐 FIXED SECRET KEY (MUST BE LONG)
    private static final String SECRET = "mysecretkeymysecretkeymysecretkey12345";

    private static final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    // ⏳ 1 DAY
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24;

    // ✅ GENERATE TOKEN
    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    // ✅ EXTRACT USERNAME
    public static String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    // ✅ VALIDATE TOKEN
    public static boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 🔍 GET CLAIMS (FIXED)
    private static Claims getClaims(String token) {
        return Jwts.parserBuilder()   // ✅ IMPORTANT FIX
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}