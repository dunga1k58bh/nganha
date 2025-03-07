package com.nganha.nganha.resolver;

import com.nganha.nganha.dto.group.UpdateUserGroupDto;
import com.nganha.nganha.entity.Group;
import com.nganha.nganha.entity.User;
import com.nganha.nganha.entity.UserGroup;
import com.nganha.nganha.security.CurrentUser;
import com.nganha.nganha.service.GroupService;
import com.nganha.nganha.service.UserGroupService;
import com.nganha.nganha.service.UserService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
@PreAuthorize("isAuthenticated()")
public class UserGroupResolver {

    private final UserGroupService userGroupService;
    private final GroupService groupService;
    private final UserService userService;

    public UserGroupResolver(UserGroupService userGroupService, GroupService groupService, UserService userService){
        this.userGroupService = userGroupService;
        this.groupService = groupService;
        this.userService = userService;
    }

    @MutationMapping
    public UserGroup joinGroup(@Argument Long groupId, @CurrentUser User user) {
        Group group = groupService.getGroupById(groupId).orElse(null);
        return userGroupService.joinGroup(group, user);
    }

    @MutationMapping
    public Boolean leaveGroup(@Argument Long groupId, @CurrentUser User user) {
        Group group = groupService.getGroupById(groupId).orElse(null);
        return userGroupService.leaveGroup(group, user);
    }

    @MutationMapping
    public UserGroup updateUserGroupRole(@Argument("input") UpdateUserGroupDto input, @CurrentUser User assigner) {
        User assignee = userService.getUserById(input.userId()).orElse(null);
        Group group = groupService.getGroupById(input.groupId()).orElse(null);
        return userGroupService.assignRole(assigner, assignee, group, input.role());
    }
}
