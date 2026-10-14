package com.fashionstore.config;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    private final byte[] secret;

    public JwtService(@Value("${app.jwt-secret}") String secret) {
        this.secret = secret.getBytes(StandardCharsets.UTF_8);
        if (this.secret.length < 32) {
            throw new IllegalArgumentException("app.jwt-secret must contain at least 32 bytes");
        }
    }

    public String createToken(String subject) {
        long expiry = Instant.now().plusSeconds(86_400).getEpochSecond();
        String header = encode("{\"alg\":\"HS256\",\"typ\":\"JWT\"}");
        String payload = encode("{\"sub\":\"" + escape(subject) + "\",\"exp\":" + expiry + "}");
        String content = header + "." + payload;
        return content + "." + Base64.getUrlEncoder().withoutPadding().encodeToString(sign(content));
    }

    public String getSubject(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) return null;
            String content = parts[0] + "." + parts[1];
            if (!java.security.MessageDigest.isEqual(sign(content), Base64.getUrlDecoder().decode(parts[2]))) return null;
            String payload = new String(Base64.getUrlDecoder().decode(parts[1]), StandardCharsets.UTF_8);
            java.util.regex.Matcher subject = java.util.regex.Pattern.compile("\"sub\":\"((?:\\\\.|[^\"])*)\"").matcher(payload);
            java.util.regex.Matcher expiry = java.util.regex.Pattern.compile("\"exp\":(\\d+)").matcher(payload);
            if (!subject.find() || !expiry.find() || Long.parseLong(expiry.group(1)) <= Instant.now().getEpochSecond()) return null;
            return subject.group(1).replace("\\\"", "\"").replace("\\\\", "\\");
        } catch (IllegalArgumentException exception) {
            return null;
        }
    }

    private byte[] sign(String value) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret, "HmacSHA256"));
            return mac.doFinal(value.getBytes(StandardCharsets.UTF_8));
        } catch (java.security.GeneralSecurityException exception) {
            throw new IllegalStateException("Unable to sign authentication token", exception);
        }
    }

    private static String encode(String value) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

    private static String escape(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
