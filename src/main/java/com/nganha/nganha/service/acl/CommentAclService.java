package com.nganha.nganha.service.acl;

import com.nganha.nganha.entity.Comment;
import com.nganha.nganha.entity.Group;
import com.nganha.nganha.entity.User;
import com.nganha.nganha.enums.GroupRole;
import com.nganha.nganha.enums.Role;
import com.nganha.nganha.service.UserGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentAclService {

    private final UserGroupService userGroupService;

    public boolean canDelete(Comment comment, User user){
        if (user.getRole() == Role.ADMIN) return true;
        Group group = comment.getPost().getGroup();
        if (userGroupService.hasRole(user, group, GroupRole.ADMIN) || userGroupService.hasRole(user, group, GroupRole.OWNER)){
            return true;
        }

        return comment.isAuthoredBy(user);
    }
}
