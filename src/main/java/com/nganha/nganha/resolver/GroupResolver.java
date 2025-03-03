package com.nganha.nganha.resolver;

import com.nganha.nganha.dto.group.CreateGroupDto;
import com.nganha.nganha.entity.Group;
import com.nganha.nganha.entity.User;
import com.nganha.nganha.service.GroupService;
import com.nganha.nganha.service.UserGroupService;
import com.nganha.nganha.security.CurrentUser;
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
    private final UserGroupService userGroupService;

    public GroupResolver(GroupService groupService, UserGroupService userGroupService) {
        this.groupService = groupService;
        this.userGroupService = userGroupService;
    }

    @MutationMapping
    public Group createGroup(@Argument CreateGroupDto input, @CurrentUser User user) {
        return groupService.createGroup(input, user);
    }

    @QueryMapping
    public List<Group> getAllGroups() {
        return groupService.getAllGroups();
    }

    @QueryMapping
    public Group getGroupByName(@Argument String name) {
        return groupService.getGroupByName(name);
    }
}
