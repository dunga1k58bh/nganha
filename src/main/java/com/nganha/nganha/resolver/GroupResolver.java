package com.nganha.nganha.resolver;

import com.nganha.nganha.dto.group.CreateGroupDto;
import com.nganha.nganha.dto.group.UpdateGroupConfigDto;
import com.nganha.nganha.dto.group.UpdateUserGroupDto;
import com.nganha.nganha.entity.Group;
import com.nganha.nganha.entity.User;
import com.nganha.nganha.entity.UserGroup;
import com.nganha.nganha.entity.UserGroupId;
import com.nganha.nganha.security.CurrentUser;
import com.nganha.nganha.service.GroupService;
import com.nganha.nganha.service.UserGroupService;
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
    public List<Group> groups() {
        return groupService.getAllGroups();
    }

    @QueryMapping
    public Group group(@Argument Long id) {
        return groupService.getGroupById(id);
    }

    // ==================== Mutations ====================

    @MutationMapping
    public Group createGroup(@Argument("input") CreateGroupDto input, @CurrentUser User user) {
        Group group = input.toEntity();
        return groupService.createGroup(group, user);
    }

    @MutationMapping
    public Group updateGroupConfig(@Argument("input") UpdateGroupConfigDto input, @CurrentUser User user) {
        Group group = groupService.getGroupById(input.groupId());
        return groupService.updateGroupConfig(group, input.config(), user);
    }
}
