package com.fashionstore.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

class JwtServiceTest {
    private final JwtService jwtService = new JwtService("this-is-a-test-secret-with-at-least-32-bytes");

    @Test
    void tokenRoundTripsEmailSubject() {
        String token = jwtService.createToken("customer@example.com");
        assertEquals("customer@example.com", jwtService.getSubject(token));
    }

    @Test
    void rejectsTokenWithChangedSignature() {
        String token = jwtService.createToken("customer@example.com");
        String tampered = token.substring(0, token.length() - 1) + (token.endsWith("a") ? "b" : "a");
        assertNull(jwtService.getSubject(tampered));
    }
}
