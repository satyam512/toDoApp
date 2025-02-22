package com.to_do.ToDoApp.schemaobjects.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateTaskRequestSo {
    private String taskDescription;
    private LocalDate dueDate;
}
