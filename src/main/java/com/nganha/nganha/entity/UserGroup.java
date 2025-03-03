package com.nganha.nganha.entity;

import jakarta.persistence.*;
import lombok.*;
import com.nganha.nganha.enums.GroupRole;

@Entity
@Table(name = "user_group")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserGroup extends BaseEntity {

    @EmbeddedId
    private UserGroupId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @MapsId("groupId")
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private GroupRole role;
}
