package com.to_do.ToDoApp.schemaobjects.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class UpdateTaskRequestSo {
    private Integer taskId;
    private Integer parentTaskId;
    private String taskDescription;
    private LocalDate dueDate;
}
