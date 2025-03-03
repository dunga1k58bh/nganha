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

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserGroupService userGroupService;

    @Transactional
    public Group createGroup(CreateGroupDto dto, User creator) {
        if (groupRepository.existsByName(dto.name())) {
            throw new IllegalArgumentException("Group name is already taken.");
        }

        // Ensure the name follows Reddit-style rules (redundant if already validated in DTO)
        if (!dto.name().matches("^[a-z0-9_]+$")) {
            throw new IllegalArgumentException("Group name can only contain lowercase letters, numbers, and underscores.");
        }

        Group group = Group.builder()
                .name(dto.name())
                .displayName(dto.displayName())
                .description(dto.description())
                .config(dto.config()) // JSON string (optional)
                .build();

        group = groupRepository.save(group);

        // Assign the creator as the group owner
        userGroupService.addUserToGroup(creator, group, GroupRole.OWNER);

        return group;
    }

    public Group getGroupByName(String name) {
        return groupRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));
    }

    public boolean groupExists(String name) {
        return groupRepository.existsByName(name);
    }


    @Transactional(readOnly = true)
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }
}
