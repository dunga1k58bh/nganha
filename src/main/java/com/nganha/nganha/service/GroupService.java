package com.nganha.nganha.service;

import com.nganha.nganha.dto.group.CreateGroupDto;
import com.nganha.nganha.entity.Group;
import com.nganha.nganha.entity.User;
import com.nganha.nganha.enums.GroupRole;
import com.nganha.nganha.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserGroupService userGroupService;

    @Transactional
    public Group createGroup(Group group, User creator) {
        if (groupRepository.existsByName(group.getName())) {
            throw new IllegalArgumentException("Group name is already taken.");
        }

        if (!group.getName().matches("^[a-z0-9_]+$")) {
            throw new IllegalArgumentException("Group name can only contain lowercase letters, numbers, and underscores.");
        }

        group = groupRepository.save(group);

        userGroupService.addUserToGroup(creator, group, GroupRole.OWNER);

        return group;
    }

    @Transactional(readOnly = true)
    public Group getGroupById(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));
    }

    @Transactional(readOnly = true)
    public Group getGroupByName(String name) {
        return groupRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));
    }

    @Transactional(readOnly = true)
    public boolean groupExists(String name) {
        return groupRepository.existsByName(name);
    }

    @Transactional(readOnly = true)
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    @Transactional
    public Group updateGroupConfig(Group group, Object config, User user) {
        if (!userGroupService.hasRole(user, group, GroupRole.OWNER) && !userGroupService.hasRole(user, group, GroupRole.ADMIN)) {
            throw new IllegalArgumentException("User is not an owner or admin of this group.");
        }

        group.setConfig(config);
        return groupRepository.save(group);
    }
}