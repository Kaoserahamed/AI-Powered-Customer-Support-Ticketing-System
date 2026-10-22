package com.support.auth.service;

import com.support.auth.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret:default-dev-secret-change-in-production-min-32-chars}")
    private String secret;

    @Value("${jwt.expiration:86400000}")
    private long expiration;

    private Key getSigningKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    private String base64Url(byte[] data) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(data);
    }

    private byte[] hmacSha256(byte[] data, Key key) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(key);
        return mac.doFinal(data);
    }

    public String generateToken(String email, String role) {
        try {
            Date now = new Date();
            Date exp = new Date(now.getTime() + expiration);

            String header = base64Url("{\"alg\":\"HS256\",\"typ\":\"JWT\"}".getBytes(StandardCharsets.UTF_8));
            String payload = base64Url(
                    String.format("{\"sub\":\"%s\",\"role\":\"%s\",\"iat\":%d,\"exp\":%d}",
                            email, role, now.getTime() / 1000, exp.getTime() / 1000)
                            .getBytes(StandardCharsets.UTF_8));

            String signingInput = header + "." + payload;
            byte[] signature = hmacSha256(signingInput.getBytes(StandardCharsets.UTF_8), getSigningKey());

            return signingInput + "." + base64Url(signature);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate JWT token", e);
        }
    }

    public String extractEmail(String token) {
        return extractClaim(token, "sub");
    }

    public String extractRole(String token) {
        return extractClaim(token, "role");
    }

    public Date extractExpiration(String token) {
        String expStr = extractClaim(token, "exp");
        return expStr != null ? new Date(Long.parseLong(expStr) * 1000) : null;
    }

    private String extractClaim(String token, String claim) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                return null;
            }
            String payload = new String(base64UrlDecode(parts[1]), StandardCharsets.UTF_8);
            String quotedPattern = "\"" + claim + "\":\"([^\"]*)\"";
            java.util.regex.Pattern regex = java.util.regex.Pattern.compile(quotedPattern);
            java.util.regex.Matcher matcher = regex.matcher(payload);
            if (matcher.find()) {
                return matcher.group(1);
            }
            String unquotedPattern = "\"" + claim + "\":([0-9]+)";
            java.util.regex.Pattern unquotedRegex = java.util.regex.Pattern.compile(unquotedPattern);
            java.util.regex.Matcher unquotedMatcher = unquotedRegex.matcher(payload);
            if (unquotedMatcher.find()) {
                return unquotedMatcher.group(1);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    private byte[] base64UrlDecode(String input) {
        return Base64.getUrlDecoder().decode(input);
    }

    private boolean isTokenExpired(String token) {
        Date expiration = extractExpiration(token);
        return expiration != null && expiration.before(new Date());
    }

    public boolean isTokenValid(String token, com.support.auth.entity.User user) {
        if (token == null || token.isBlank()) {
            return false;
        }
        final String email = extractEmail(token);
        return (email != null && email.equals(user.getEmail()) && !isTokenExpired(token));
    }
}
