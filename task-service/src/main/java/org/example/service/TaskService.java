package org.example.service;

import org.example.model.request.TaskRequest;
import org.example.model.response.TaskResponse;

public interface TaskService {
    TaskResponse createTask(TaskRequest taskRequest);
}
