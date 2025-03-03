package com.nganha.nganha.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnTransformer;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "groups")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Group extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false, updatable = false)
    private String name; // Unique identifier like a username (e.g., "tech-community")

    @Column(name = "display_name", nullable = false)
    private String displayName; // Readable name (e.g., "Tech Community")

    @Column(name = "description")
    private String description;

    @Builder.Default
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserGroup> members = new HashSet<>();

    @Lob  // Use CLOB for large JSON storage
    @Column(name = "config", columnDefinition = "CLOB")
    private String config;
}