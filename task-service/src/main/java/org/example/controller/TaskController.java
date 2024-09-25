package org.example.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.example.model.request.TaskRequest;
import org.example.model.response.ApiResponse;
import org.example.model.response.TaskResponse;
import org.example.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/task")
@CrossOrigin(origins = "http://localhost:9090")
@SecurityRequirement(name = "keycloak_auth")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody TaskRequest taskRequest) {
        ApiResponse<TaskResponse> response = ApiResponse.<TaskResponse>builder()
                .status(HttpStatus.CREATED)
                .message("Create task successfully!")
                .payload(taskService.createTask(taskRequest))
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
