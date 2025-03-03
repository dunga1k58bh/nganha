package com.nganha.nganha.service;

import com.nganha.nganha.entity.RefreshToken;
import com.nganha.nganha.entity.User;
import com.nganha.nganha.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.refreshExpiration}")
    private long refreshTokenExpiration; // Expiration for refresh token (e.g., 7 days)


    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    // Generate and store refresh token
    public String createRefreshToken(User user) {
        String token = UUID.randomUUID().toString();
        Date expiryDate = new Date(System.currentTimeMillis() + refreshTokenExpiration);

        RefreshToken refreshToken = RefreshToken.builder()
                .userId(user.getId())
                .token(token)
                .expiryDate(expiryDate)
                .build();

        refreshTokenRepository.save(refreshToken);

        return token;
    }

    // Validate refresh token
    public RefreshToken findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    // Check if refresh token is expired
    public boolean isTokenExpired(RefreshToken refreshToken) {
        return refreshToken.getExpiryDate().before(new Date());
    }

    // Delete refresh token (Logout)
    @Transactional
    public void revokeRefreshToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }

    @Scheduled(cron = "0 0 0 * * ?") // Run every day at midnight
    public void cleanUpExpiredTokens() {
        Date now = new Date();
        refreshTokenRepository.deleteByExpiryDateBefore(now);
    }
}
