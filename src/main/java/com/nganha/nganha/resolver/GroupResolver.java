package com.nganha.nganha.resolver;

import com.nganha.nganha.dto.group.CreateGroupDto;
import com.nganha.nganha.dto.group.GroupDto;
import com.nganha.nganha.dto.group.UpdateGroupConfigDto;
import com.nganha.nganha.entity.Group;
import com.nganha.nganha.entity.User;
import com.nganha.nganha.security.CurrentUser;
import com.nganha.nganha.service.GroupService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@PreAuthorize("isAuthenticated()")
public class GroupResolver {

    private final GroupService groupService;

    public GroupResolver(GroupService groupService) {
        this.groupService = groupService;
    }

    // ==================== Queries ====================

    @QueryMapping
    public List<GroupDto> groups() {
        return groupService.getAllGroups()
                .stream()
                .map(GroupDto::fromEntity)
                .toList();
    }

    @QueryMapping
    public GroupDto group(@Argument Long id) {
        return groupService.getGroupById(id)
                .map(GroupDto::fromEntity)
                .orElseThrow(() -> new IllegalArgumentException("Group not found with ID: " + id));
    }

    // ==================== Mutations ====================

    @MutationMapping
    public GroupDto createGroup(@Argument("input") CreateGroupDto input, @CurrentUser User user) {
        Group group = input.toEntity();
        group = groupService.createGroup(group, user);
        return GroupDto.fromEntity(group);
    }

    @MutationMapping
    public GroupDto updateGroupConfig(@Argument("input") UpdateGroupConfigDto input, @CurrentUser User user) {
        Group group = groupService.getGroupById(input.groupId()).orElse(null);
        group = groupService.updateGroupConfig(group, input.config(), user);
        return GroupDto.fromEntity(group);
    }
}
