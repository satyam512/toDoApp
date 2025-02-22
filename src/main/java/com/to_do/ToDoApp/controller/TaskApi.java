package com.to_do.ToDoApp.controller;

import com.to_do.ToDoApp.entity.Task;
import com.to_do.ToDoApp.schemaobjects.request.SubCreateTaskRequestSo;
import com.to_do.ToDoApp.schemaobjects.request.CreateTaskRequestSo;
import com.to_do.ToDoApp.schemaobjects.request.UpdateTaskRequestSo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tasks")
public interface TaskApi {

    @GetMapping
    List<Task> getTask();

    @GetMapping("/{task_id}")
    Task getTask(Integer task_id);

    @PostMapping
    String createTask(CreateTaskRequestSo createTaskRequestSo);

    @PostMapping("/{task_id}/subtasks")
    String createSubTask(Integer task_id, SubCreateTaskRequestSo taskRequestSo);

    @PutMapping("/{task_id}")
    String updateTask(Integer task_id, @RequestBody UpdateTaskRequestSo updateTaskRequestSo);

    @PutMapping("/{task_id}/subtasks/{subtask_id}")
    String updateSubTask(Integer task_id, Integer subtask_id, @RequestBody UpdateTaskRequestSo updateTaskRequestSo);

    @PatchMapping("/{task_id}/status")
    String updateStatus(Integer task_id, @RequestBody Map<String, String> updates);

    @PatchMapping("/{task_id}/subtasks/{subtask_id}/status")
    String updateSubTaskStatus(Integer task_id, Integer subtask_id, @RequestBody Map<String, String> updates);

    @DeleteMapping("/{task_id}")
    String deleteTask(Integer task_id);
}
