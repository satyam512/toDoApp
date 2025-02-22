package com.to_do.ToDoApp.schemaobjects.response;

import com.to_do.ToDoApp.entity.Task;
import com.to_do.ToDoApp.enums.TaskStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class TaskResponseSo {
    private Integer id;
    private Integer parentTaskId;
    private TaskStatus taskStatus;
    private String taskDescription;
    private LocalDate dueDate;
    private LocalDate createdAt;
}
