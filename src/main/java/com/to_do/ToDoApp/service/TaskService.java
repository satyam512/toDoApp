package com.to_do.ToDoApp.service;

import com.to_do.ToDoApp.entity.Task;
import com.to_do.ToDoApp.enums.TaskStatus;
import com.to_do.ToDoApp.schemaobjects.request.CreateTaskRequestSo;
import com.to_do.ToDoApp.schemaobjects.request.SubCreateTaskRequestSo;
import com.to_do.ToDoApp.schemaobjects.request.UpdateTaskRequestSo;

import java.util.List;

public interface TaskService {
    List<Task> getTasks();
    Task getTaskById(Integer taskId);
    void createTask(CreateTaskRequestSo createTaskRequestSo);
    void createSubTask(SubCreateTaskRequestSo subCreateTaskRequestSo);
    void updateTask(UpdateTaskRequestSo updateTaskRequestSo);
    void updateTaskStatus(Integer taskId, Integer parentTaskId, TaskStatus taskStatus);
}
