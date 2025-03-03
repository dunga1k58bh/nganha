package com.nganha.nganha.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Builder
@NoArgsConstructor // This annotation generates a default no-argument constructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "refresh_tokens")
public class RefreshToken extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @Column(name = "user_id", nullable = false)
    private Long userId; // Now mapped as `user_id` in DB

    @Column(name = "expiry_date", nullable = false)
    private Date expiryDate; // Now mapped as `expiry_date` in DB

    // Getters and Setters
    public Long getId() { return id; }

    public String getToken() { return token; }

    public Long getUserId() { return userId; }

    public Date getExpiryDate() { return expiryDate; }
}
