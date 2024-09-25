package org.example.service.userserviceimpl;

import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import org.example.exception.AlreadyExistException;
import org.example.exception.NotFoundException;
import org.example.model.Group;
import org.example.model.User;
import org.example.model.mapper.GroupMapper;
import org.example.model.mapper.UserMapper;
import org.example.model.request.GroupRequest;
import org.example.model.request.UserRequest;
import org.example.service.GroupService;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.GroupResource;
import org.keycloak.admin.client.resource.GroupsResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService {

    private final Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;

    public GroupServiceImpl(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    @Override
    public Group createGroup(GroupRequest groupRequest) {
        GroupRepresentation group = new GroupRepresentation();
        group.setName(groupRequest.getGroupName());

        // Create the group in Keycloak
        GroupsResource groupResource = keycloak.realm(realm).groups();
        Response response = groupResource.add(group);

        // Handle response
        if (response.getStatus() == 201) {
            // Fetch the newly created user by ID
            String groupId = CreatedResponseUtil.getCreatedId(response);
            GroupRepresentation representation = groupResource.group(CreatedResponseUtil.getCreatedId(response)).toRepresentation();

            if (representation != null) {
                // Map and return as custom DTO
                return GroupMapper.toDto(representation);
            } else {
                throw new RuntimeException("Group created, but fetching failed.");
            }
        } else if (response.getStatus() == 409) {
            throw new AlreadyExistException("Group already exists.");
        } else {
            throw new RuntimeException("Failed to create group: " + response.getStatusInfo());
        }
    }

    @Override
    public List<Group> getAllGroups() {
        List<GroupRepresentation> groupRepresentations = keycloak.realm(realm).groups().groups();
        return groupRepresentations.stream()
                .map(GroupMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Group getGroupByGroupId(UUID groupId) {
        try {
            GroupsResource groupsResource = keycloak.realm(realm).groups();
            GroupRepresentation groupRepresentation = groupsResource.group(String.valueOf(groupId)).toRepresentation();
            return GroupMapper.toDto(groupRepresentation);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch group by ID", e);
        }
    }

    @Override
    public Group updateGroupById(UUID groupId, GroupRequest groupRequest) {
        GroupsResource groupsResource = keycloak.realm(realm).groups();
        try {
            // Fetch the existing user
            GroupRepresentation existingGroup = groupsResource.group(String.valueOf(groupId)).toRepresentation();
            if (existingGroup == null) {
                throw new NotFoundException("Group not found");
            }

            // Update user details based on the userUpdateRequest object
            existingGroup.setName(groupRequest.getGroupName());
            existingGroup.singleAttribute("lastModify", String.valueOf(LocalDateTime.now()));
            // Update the user in Keycloak
            groupsResource.group(String.valueOf(groupId)).update(existingGroup);

            // Fetch the updated user and map to DTO
            GroupRepresentation updatedGroup = groupsResource.group(String.valueOf(groupId)).toRepresentation();
            return GroupMapper.toDto(updatedGroup);  // Assuming UserMapper is implemented
        } catch (Exception e) {
            throw new RuntimeException("Failed to update group", e);
        }
    }

    @Override
    public String deleteGroupById(UUID groupId) {
        GroupsResource groupsResource = keycloak.realm(realm).groups();

        try {
            // Check if the user exists before deleting
            GroupRepresentation group = groupsResource.group(String.valueOf(groupId)).toRepresentation();
            if (group == null) {
                throw new NotFoundException("Group not found");
            }

            // Delete the user
            groupsResource.group(String.valueOf(groupId)).remove();;
            return "Group successfully deleted";
        } catch (NotFoundException e) {
            throw e;  // Rethrow if user not found
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete group", e);
        }
    }

    @Override
    public String addUserToGroup(UUID groupId, UUID userId) {
        UsersResource usersResource = keycloak.realm(realm).users();
        UserResource userResource = usersResource.get(String.valueOf(userId));
        GroupsResource groupsResource = keycloak.realm(realm).groups();
        GroupRepresentation groupRepresentation = groupsResource.group(String.valueOf(groupId)).toRepresentation();

        userResource.joinGroup(groupRepresentation.getId());
        return "user was added to group";
    }

    @Override
    public List<User> getAllUsersByGroupId(String groupId) {
        GroupResource groupResource = keycloak.realm(realm).groups().group(String.valueOf(groupId));
        List<UserRepresentation> userRepresentationList = groupResource.members();
        return userRepresentationList.stream().map(UserMapper::toDto).collect(Collectors.toList());
    }
}
