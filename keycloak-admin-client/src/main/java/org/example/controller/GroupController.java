package org.example.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/group")
@CrossOrigin(origins = "http://localhost:9090")
@SecurityRequirement(name = "keycloak_auth")
public class GroupController {

    @GetMapping("/get/group")
    public String getGroup(){
        return "Hello from group";
    }

    @PostMapping("{groupId}/user/{userId}")
    public ResponseEntity<?> addUserToGroup(@PathVariable UUID groupId, @PathVariable UUID userId){

        return null;
    }

    @GetMapping()
    public ResponseEntity<?> getAllGroups(){
        return null;
    }

    @GetMapping("{groupId}")
    public ResponseEntity<?> getGroupByGroupId(){
        return null;
    }

    @PutMapping()
    public ResponseEntity<?> updateGroup(@RequestBody Group group){
        return null;
    }




}
