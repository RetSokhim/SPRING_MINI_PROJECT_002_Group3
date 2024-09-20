package org.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/group")
public class GroupController {

    @GetMapping("/get/group")
    public String getGroup(){
        return "Hello from group";
    }
}
