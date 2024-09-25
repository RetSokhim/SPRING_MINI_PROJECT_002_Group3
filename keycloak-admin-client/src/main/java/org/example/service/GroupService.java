package org.example.service;

import org.example.model.Group;
import org.example.model.User;
import org.example.model.request.GroupRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface GroupService {
    Group createGroup(GroupRequest groupRequest);

    List<Group> getAllGroups();

    Group getGroupByGroupId(UUID groupId);

    Group updateGroupById(UUID groupId, GroupRequest groupRequest);

    String deleteGroupById(UUID groupId);

    String addUserToGroup(UUID groupId, UUID userId);

    List<User> getAllUsersByGroupId(String groupId);
}
