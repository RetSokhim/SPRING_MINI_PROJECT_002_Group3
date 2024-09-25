package org.example.service.serviceimpl;

import org.example.client.GroupServiceClient;
import org.example.client.UserServiceClient;
import org.example.model.Task;
import org.example.model.request.TaskRequest;
import org.example.model.response.GroupResponse;
import org.example.model.response.TaskResponse;
import org.example.model.response.UserResponse;
import org.example.repository.TaskRepository;
import org.example.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TaskServiceImplement implements TaskService {
    private final TaskRepository taskRepository;
    private final UserServiceClient userServiceClient;
    private final GroupServiceClient groupServiceClient;

    public TaskServiceImplement(TaskRepository taskRepository, UserServiceClient userServiceClient, GroupServiceClient groupServiceClient) {
        this.taskRepository = taskRepository;
        this.userServiceClient = userServiceClient;
        this.groupServiceClient = groupServiceClient;
    }

    @Override
    public TaskResponse createTask(TaskRequest taskRequest) {
        Task task = taskRepository.save(taskRequest.toEntity());
        TaskResponse taskResponse = task.toResponse();
        UserResponse createBy = Objects.requireNonNull(userServiceClient.getUserById(taskRequest.getCreateBy()).getBody()).getPayload();
        UserResponse assignedTo = Objects.requireNonNull(userServiceClient.getUserById(taskRequest.getAssignedTo()).getBody()).getPayload();
//        GroupResponse group = Objects.requireNonNull(groupServiceClient.getGroupById(taskRequest.getGroupId()).getBody()).getPayload();
        taskResponse.setCreateBy(createBy);
        taskResponse.setAssignedTo(assignedTo);
        return taskResponse;
    }
}
