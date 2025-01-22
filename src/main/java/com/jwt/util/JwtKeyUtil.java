package com.jwt.util;

import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class JwtKeyUtil {

    // Secret Key 생성
    public static SecretKey generateSecretKey() {
        return Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
    }

    // Base64 인코딩된 Secret Key 반환 (저장 또는 테스트 용도)
    public static String getEncodedSecretKey() {
        SecretKey key = generateSecretKey();
        return java.util.Base64.getEncoder().encodeToString(key.getEncoded());
    }
}
