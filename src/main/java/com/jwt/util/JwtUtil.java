package com.jwt.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtUtil {
    @Value("${jwt.secret_key}")
    private String SECRET_KEY;

    private SecretKey key;

    @PostConstruct
    public void init() {
        if (SECRET_KEY == null || SECRET_KEY.isBlank()) {
            throw new IllegalArgumentException("JWT secret key is not configured");
        }
        this.key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // 토큰 생성
    public String generateToken(String username, String userId, long expiration) {
        return Jwts.builder()
                .header().type("JWT").and()
                .issuer("server")
                .subject("subject")
                .audience().add(username).and()
                .claim("iat", System.currentTimeMillis()) // 발행 시간
                .claim("exp", expiration) // 만료 시간
                .claim("userId", userId)
                .signWith(key, SignatureAlgorithm.HS256) // 키로 서명
                .compact();
    }

//    // 토큰 검증
//    public static boolean validateToken(String token, Object expectedAudience) {
//        try {
//            // SignedClaims 파싱 및 Audience 검증
//            return Jwts.parser()
//                    .verifyWith(key) // 서명 키 설정
//                    .build()
//                    .parseSignedClaims(token) // 서명된 JWT 파싱
//                    .getPayload()
//                    .getAudience()
//                    .equals(expectedAudience); // Audience 비교
//        } catch (JwtException e) {
//            System.err.println("Invalid Token: " + e.getMessage());
//            return false; // 유효하지 않은 경우 false 반환
//        }
//    }
}
