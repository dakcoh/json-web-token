package com.jwt.util;

import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

import static org.junit.jupiter.api.Assertions.*;

public class JwtKeyUtilTest {

    @Test
    public void testGenerateSecretKey() {
        // Secret Key 생성
        SecretKey secretKey = JwtKeyUtil.generateSecretKey();

        // Secret Key 검증
        assertNotNull(secretKey, "Secret Key should not be null");
        assertEquals(SignatureAlgorithm.HS256.getMinKeyLength(), secretKey.getEncoded().length * 8,
                "Secret Key length should match HS256 minimum key length");
    }

    @Test
    public void testEncodedSecretKey() {
        // Base64 인코딩된 Secret Key 생성
        String encodedKey = JwtKeyUtil.getEncodedSecretKey();

        // Secret Key 검증
        assertNotNull(encodedKey, "Encoded Secret Key should not be null");
        assertFalse(encodedKey.isEmpty(), "Encoded Secret Key should not be empty");
        System.out.println("Generated Secret Key: " + encodedKey);
    }
}
