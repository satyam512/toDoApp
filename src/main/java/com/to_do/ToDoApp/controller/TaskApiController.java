package com.to_do.ToDoApp.controller;

import com.to_do.ToDoApp.constants.AppConstants;
import com.to_do.ToDoApp.entity.Task;
import com.to_do.ToDoApp.enums.TaskStatus;
import com.to_do.ToDoApp.schemaobjects.request.CreateTaskRequestSo;
import com.to_do.ToDoApp.schemaobjects.request.SubCreateTaskRequestSo;
import com.to_do.ToDoApp.schemaobjects.request.UpdateTaskRequestSo;
import com.to_do.ToDoApp.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TaskApiController implements TaskApi{
    private final TaskService taskService;
    @Override
    public List<Task> getTask() {
        // Create Task Response Mapping
        return taskService.getTasks();
    }

    @Override
    public Task getTask(Integer task_id) {
        return taskService.getTaskById(task_id);
    }

    @Override
    public String createTask(CreateTaskRequestSo createTaskRequestSo) {
        taskService.createTask(createTaskRequestSo);
        return "";
    }

    @Override
    public String createSubTask(Integer task_id, SubCreateTaskRequestSo taskRequestSo) {
        taskRequestSo.setParentTaskId(task_id);
        taskService.createSubTask(taskRequestSo);
        return "";
    }

    @Override
    public String updateTask(Integer task_id, UpdateTaskRequestSo updateTaskRequestSo) {
        updateTaskRequestSo.setTaskId(task_id);
        taskService.updateTask(updateTaskRequestSo);
        return "SUCCESS";
    }

    @Override
    public String updateSubTask(Integer task_id, Integer subtask_id, UpdateTaskRequestSo updateTaskRequestSo) {
        updateTaskRequestSo.setTaskId(subtask_id);
        updateTaskRequestSo.setParentTaskId(task_id);
        taskService.updateTask(updateTaskRequestSo);
        return "SUCCESS";
    }

    @Override
    public String updateStatus(Integer task_id, Map<String, String> updates) {
        if (updates.isEmpty() || ObjectUtils.isEmpty(updates.get(AppConstants.STATUS))) {
            throw new IllegalArgumentException("Status cannot be empty");
        }

        TaskStatus status = TaskStatus.valueOf(updates.get(AppConstants.STATUS));
        taskService.updateTaskStatus(task_id, null, status);
        return "SUCCESS";
    }

    @Override
    public String updateSubTaskStatus(Integer task_id, Integer subtask_id, Map<String, String> updates) {
        if (updates.isEmpty() || ObjectUtils.isEmpty(updates.get(AppConstants.STATUS))) {
            throw new IllegalArgumentException("Status cannot be empty");
        }

        TaskStatus status = TaskStatus.valueOf(updates.get(AppConstants.STATUS));
        taskService.updateTaskStatus(subtask_id, task_id, status);
        return "SUCCESS";
    }

    @Override
    public String deleteTask(Integer task_id) {
        taskService.updateTaskStatus(task_id, null, TaskStatus.DELETED);
        return "SUCCESS";
    }
}
