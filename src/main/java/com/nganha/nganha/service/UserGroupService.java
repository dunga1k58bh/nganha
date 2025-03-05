package com.nganha.nganha.service;

import com.nganha.nganha.entity.Group;
import com.nganha.nganha.entity.User;
import com.nganha.nganha.entity.UserGroup;
import com.nganha.nganha.entity.UserGroupId;
import com.nganha.nganha.enums.GroupRole;
import com.nganha.nganha.repository.UserGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserGroupService {

    private final UserGroupRepository userGroupRepository;

    // ==================== Membership Management ====================

    @Transactional
    public UserGroup joinGroup(Group group, User user) {
        if (userGroupRepository.existsByUserAndGroup(user, group)) {
            throw new IllegalArgumentException("User is already a member of this group.");
        }

        return addUserToGroup(user, group, GroupRole.MEMBER);
    }

    @Transactional
    public boolean leaveGroup(Group group, User user) {
        UserGroup userGroup = getUserGroup(user, group);
        userGroupRepository.delete(userGroup);
        return true;
    }

    @Transactional
    public UserGroup addUserToGroup(User user, Group group, GroupRole role) {
        if (userGroupRepository.existsByUserAndGroup(user, group)) {
            throw new IllegalArgumentException("User is already a member of this group.");
        }

        return saveUserGroup(user, group, role);
    }

    @Transactional
    public void removeUserFromGroup(User user, Group group) {
        UserGroup userGroup = getUserGroup(user, group);
        userGroupRepository.delete(userGroup);
    }

    // ==================== Role Management ====================

    @Transactional
    public UserGroup assignRole(User assigner, User assignee, Group group, GroupRole newRole) {
        if (!hasRole(assigner, group, GroupRole.ADMIN) || !hasRole(assigner, group, GroupRole.OWNER )) {
            throw new IllegalArgumentException("Assigner is not an admin or owner of this group.");
        }

        if (hasRole(assignee, group, GroupRole.ADMIN) || hasRole(assignee, group, GroupRole.OWNER)){
            throw new IllegalArgumentException("Assignee is an admin or owner of this group.");
        }

        UserGroup userGroup = getUserGroup(assignee, group);
        userGroup.setRole(newRole);
        return userGroupRepository.save(userGroup);
    }

    public boolean hasRole(User user, Group group, GroupRole role) {
        return userGroupRepository.findByUserAndGroup(user, group)
                .map(userGroup -> userGroup.getRole().ordinal() <= role.ordinal())
                .orElse(false);
    }

    // ==================== Query Methods ====================
    public boolean isMember(User user, Group group) {
        return userGroupRepository.existsByUserAndGroup(user, group);
    }


    // ==================== Utility Methods ====================

    private UserGroup getUserGroup(User user, Group group) {
        return userGroupRepository.findByUserAndGroup(user, group)
                .orElseThrow(() -> new IllegalArgumentException("User is not a member of this group."));
    }


    private UserGroup saveUserGroup(User user, Group group, GroupRole role) {
        UserGroup userGroup = UserGroup.builder()
                .id(new UserGroupId(user.getId(), group.getId()))
                .user(user)
                .group(group)
                .role(role)
                .build();

        return userGroupRepository.save(userGroup);
    }
}
