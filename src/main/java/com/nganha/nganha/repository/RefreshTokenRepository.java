package com.nganha.nganha.repository;

import com.nganha.nganha.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    // Find a refresh token by its token string
    RefreshToken findByToken(String token);

    // Delete a refresh token by its token string
    void deleteByToken(String token);

    // Method to delete expired tokens
    void deleteByExpiryDateBefore(Date date);
}
