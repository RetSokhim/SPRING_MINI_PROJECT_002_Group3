package org.example.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.model.Task;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequest {
    private String taskName;
    private String description;
    private String createBy;
    private String assignedTo;
    private String groupId;

    public Task toEntity(){
        return new Task(null,this.taskName,this.description,this.createBy,this.assignedTo,this.groupId,LocalDateTime.now(),LocalDateTime.now());
    }
}
