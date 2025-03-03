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

@Service
@RequiredArgsConstructor
public class UserGroupService {

    private final UserGroupRepository userGroupRepository;

    @Transactional
    public void addUserToGroup(User user, Group group, GroupRole role) {
        if (userGroupRepository.existsByUserAndGroup(user, group)) {
            throw new IllegalArgumentException("User is already a member of this group.");
        }

        // Explicitly create and assign UserGroupId
        UserGroupId userGroupId = new UserGroupId(user.getId(), group.getId());

        UserGroup userGroup = UserGroup.builder()
                .id(userGroupId)  // Set the composite key
                .user(user)
                .group(group)
                .role(role)
                .build();

        userGroupRepository.save(userGroup);
    }


    @Transactional
    public void assignRole(User user, Group group, GroupRole newRole) {
        UserGroup userGroup = userGroupRepository.findByUserAndGroup(user, group)
                .orElseThrow(() -> new IllegalArgumentException("User is not a member of this group."));

        userGroup.setRole(newRole);
        userGroupRepository.save(userGroup);
    }

    @Transactional
    public void removeUserFromGroup(User user, Group group) {
        UserGroup userGroup = userGroupRepository.findByUserAndGroup(user, group)
                .orElseThrow(() -> new IllegalArgumentException("User is not a member of this group."));

        userGroupRepository.delete(userGroup);
    }
}
