package org.example.model.mapper;

import org.example.model.User;
import org.keycloak.representations.idm.UserRepresentation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class UserMapper {

    public static User toDto(UserRepresentation userRepresentation) {
        User user = new User();
        user.setId(UUID.fromString(userRepresentation.getId()));
        user.setUsername(userRepresentation.getUsername());
        user.setEmail(userRepresentation.getEmail());
        user.setFirstName(userRepresentation.getFirstName());
        user.setLastName(userRepresentation.getLastName());

        // Handle createdDate and lastModified if they are stored in Keycloak attributes
        if (userRepresentation.getAttributes() != null) {
            List<String> createdDateList = userRepresentation.getAttributes().get("createdDate");
            if (createdDateList != null && !createdDateList.isEmpty()) {
                user.setCreatedDate(LocalDateTime.parse(createdDateList.get(0)));
            }
            List<String> lastModifiedList = userRepresentation.getAttributes().get("lastModified");
            if (lastModifiedList != null && !lastModifiedList.isEmpty()) {
                user.setLastModified(LocalDateTime.parse(lastModifiedList.get(0)));
            }
        }
        return user;
    }
}

