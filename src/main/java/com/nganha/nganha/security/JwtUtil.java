package com.nganha.nganha.security;

import com.nganha.nganha.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long accessTokenExpiration; // Expiration for access token (e.g., 1 day)

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // 🔹 Generate Access Token (Short-Lived)
    public String generateAccessToken(User user) {
        return Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(new Date())
                .claim("role", user.getRole().name())
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .signWith(getSigningKey())
                .compact();
    }

    // ✅ Validate Access & Refresh Tokens
    public boolean validateToken(String token) {
        return !isTokenExpired(token) && extractUsername(token) != null;
    }

    // ✅ Extract Username
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    // ✅ Extract Expiration
    public Date extractExpiration(String token) {
        return extractClaims(token).getExpiration();
    }

    // ✅ Extract Role
    public String extractRole(String token) {
        return extractClaims(token).get("role", String.class);
    }

    // 🔹 Check if Token is Expired
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // 🔹 Extract Claims from Token
    private Claims extractClaims(String token) {
        JwtParser parser = Jwts.parser()
                .verifyWith(getSigningKey())
                .build();

        return parser.parseSignedClaims(token).getPayload();
    }
}
