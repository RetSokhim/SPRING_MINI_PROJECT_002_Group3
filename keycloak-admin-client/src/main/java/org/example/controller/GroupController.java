package org.example.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.example.model.Group;
import org.example.model.User;
import org.example.model.request.GroupRequest;
import org.example.model.response.ApiResponse;
import org.example.service.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/group")
@CrossOrigin(origins = "http://localhost:9090")
@SecurityRequirement(name = "keycloak_auth")
@AllArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @GetMapping("/get/group")
    public String getGroup(){
        return "Hello from group";
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<Group>> createGroup(@RequestBody GroupRequest groupRequest){
        ApiResponse<Group> response = ApiResponse.<Group>builder()
                .status(HttpStatus.CREATED)
                .message("A group is created successfully!")
                .payload(groupService.createGroup(groupRequest))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<Group>>> getAllGroups(){
        ApiResponse<List<Group>> response = ApiResponse.<List<Group>>builder()
                .status(HttpStatus.OK)
                .message("All group are fetched successfully!")
                .payload(groupService.getAllGroups())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("{groupId}")
    public ResponseEntity<ApiResponse<Group>> getGroupByGroupId(@PathVariable UUID groupId){
        ApiResponse<Group> response = ApiResponse.<Group>builder()
                .status(HttpStatus.OK)
                .message("A group with ID " + groupId + " is fetched successfully!")
                .payload(groupService.getGroupByGroupId(groupId))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping({"{groupId}"})
    public ResponseEntity<ApiResponse<Group>> updateGroupById(@PathVariable UUID groupId, @RequestBody Group group){
        ApiResponse<Group> response = ApiResponse.<Group>builder()
                .status(HttpStatus.OK)
                .message("A group with ID " + groupId + " is updated successfully!")
                .payload(groupService.updateGroupById(groupId, group))
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping({"{groupId}"})
    public ResponseEntity<?> deleteGroupById(@PathVariable UUID groupId){
        groupService.deleteGroupById(groupId);
        ApiResponse<Group> response = ApiResponse.<Group>builder()
                .status(HttpStatus.OK)
                .message("A group with ID " + groupId + " is deleted successfully!")
                .payload(null)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("{groupId}/user/{userId}")
    public ResponseEntity<?> addUserToGroup(@PathVariable UUID groupId, @PathVariable UUID userId){

        return null;
    }

    @GetMapping("{groupId}/users")
    public ResponseEntity<?> getAllUsersByGroupId(@PathVariable String groupId){
        return null;
    }








}
