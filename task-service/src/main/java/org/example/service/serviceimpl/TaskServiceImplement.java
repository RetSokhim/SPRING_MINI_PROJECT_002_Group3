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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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
        return getTaskResponse(task, taskRequest.getCreateBy(), taskRequest.getAssignedTo(), taskRequest.getGroupId());
    }

    @Override
    public TaskResponse getTaskById(String taskId) {
        Task task = taskRepository.findById(UUID.fromString(taskId)).orElseThrow();
        return getTaskResponse(task, task.getCreateBy(), task.getAssignedTo(), task.getGroupId());
    }

    @Override
    public List<TaskResponse> getAllTask() {
        return taskRepository.findAll().stream()
                .map(task -> getTaskResponse(task, task.getCreateBy(), task.getAssignedTo(), task.getGroupId())).toList();
    }

    @Override
    public void deleteTaskById(String taskId) {
        taskRepository.deleteById(UUID.fromString(taskId));
    }

    @Override
    public TaskResponse updateTaskById(TaskRequest taskRequest, String taskId) {
        Task task = taskRepository.findById(UUID.fromString(taskId)).orElseThrow();
        task.setTaskName(taskRequest.getTaskName());
        task.setDescription(taskRequest.getDescription());
        task.setAssignedTo(taskRequest.getAssignedTo());
        task.setCreateBy(taskRequest.getCreateBy());
        task.setGroupId(taskRequest.getGroupId());
        task.setLastModified(LocalDateTime.now());
        taskRepository.save(task);
        return getTaskResponse(task, task.getCreateBy(), task.getAssignedTo(), task.getGroupId());
    }

    private TaskResponse getTaskResponse(Task task, String createBy2, String assignedTo2, String groupId) {
        TaskResponse taskResponse = task.toResponse();
        UserResponse createBy = Objects.requireNonNull(userServiceClient.getUserById(createBy2).getBody()).getPayload();
        UserResponse assignedTo = Objects.requireNonNull(userServiceClient.getUserById(assignedTo2).getBody()).getPayload();
        GroupResponse group = Objects.requireNonNull(groupServiceClient.getGroupById(groupId).getBody()).getPayload();
        taskResponse.setCreateBy(createBy);
        taskResponse.setAssignedTo(assignedTo);
        taskResponse.setGroup(group);
        return taskResponse;
    }
}
