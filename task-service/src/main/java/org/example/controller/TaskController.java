package org.example.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/task")
@CrossOrigin(origins = "http://localhost:9090")
public class TaskController {

    @GetMapping("get-task")
    public String getGroup(){
        return "Hello from Task";
    }
}
