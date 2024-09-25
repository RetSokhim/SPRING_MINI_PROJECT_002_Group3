package org.example.service;

import org.example.model.Group;
import org.example.model.request.GroupRequest;

import java.util.List;
import java.util.UUID;

public interface GroupService {
    Group createGroup(GroupRequest groupRequest);

    List<Group> getAllGroups();

    Group getGroupByGroupId(UUID groupId);

    Group updateGroupById(UUID groupId, Group group);

    void deleteGroupById(UUID groupId);
}
