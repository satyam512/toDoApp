package com.to_do.ToDoApp.schemaobjects.request;

import lombok.Data;

@Data
public class SubCreateTaskRequestSo extends CreateTaskRequestSo {
    private Integer parentTaskId;
}
