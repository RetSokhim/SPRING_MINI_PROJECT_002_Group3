package org.example.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/group")
@CrossOrigin(origins = "http://localhost:9090")
@SecurityRequirement(name = "keycloak_auth")
public class GroupController {

    @GetMapping("/get/group")
    public String getGroup(){
        return "Hello from group";
    }
}
