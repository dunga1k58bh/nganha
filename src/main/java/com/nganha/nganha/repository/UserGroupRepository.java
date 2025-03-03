package com.nganha.nganha.repository;

import com.nganha.nganha.entity.UserGroup;
import com.nganha.nganha.entity.UserGroupId;
import com.nganha.nganha.entity.User;
import com.nganha.nganha.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, UserGroupId> {

    // Check if a user is already in a group
    boolean existsByUserAndGroup(User user, Group group);

    // Find a user's membership in a group
    Optional<UserGroup> findByUserAndGroup(User user, Group group);

    // Find all groups a user is a member of
    List<UserGroup> findByUser(User user);

    // Find all users in a group
    List<UserGroup> findByGroup(Group group);
}
