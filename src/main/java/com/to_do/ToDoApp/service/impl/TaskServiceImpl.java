package com.to_do.ToDoApp.service.impl;

import com.to_do.ToDoApp.configs.UserContext;
import com.to_do.ToDoApp.entity.Task;
import com.to_do.ToDoApp.entity.User;
import com.to_do.ToDoApp.enums.TaskStatus;
import com.to_do.ToDoApp.repository.ToDoTaskRepository;
import com.to_do.ToDoApp.repository.UserRepository;
import com.to_do.ToDoApp.schemaobjects.request.CreateTaskRequestSo;
import com.to_do.ToDoApp.schemaobjects.request.SubCreateTaskRequestSo;
import com.to_do.ToDoApp.schemaobjects.request.UpdateTaskRequestSo;
import com.to_do.ToDoApp.service.TaskService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final ToDoTaskRepository toDoTaskRepository;
    private final UserContext userContext;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public List<Task> getTasks() {
        // TODO Make this Paginated
        String userId = userContext.getUserId();
        User user = userRepository.findByUsername(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return user.getTasks();
    }

    @Override
    public Task getTaskById(Integer taskId) {
        String userId = userContext.getUserId();
        return getTaskNotFound(taskId, userId);
    }

    @Override
    public void createTask(CreateTaskRequestSo createTaskRequestSo) {

        String userIdVal = userContext.getUserId();
        User user = userRepository.findByUsername(userIdVal).orElseThrow(() -> new RuntimeException("User not found"));

        Task task = Task.builder()
                .user(user)
                .taskDescription(createTaskRequestSo.getTaskDescription())
                .dueDate(createTaskRequestSo.getDueDate())
                .taskStatus(TaskStatus.PENDING)
                .build();
        toDoTaskRepository.save(task);
    }

    @Override
    public void createSubTask(SubCreateTaskRequestSo subCreateTaskRequestSo) {
        String userIdVal = userContext.getUserId();
        User user = userRepository.findByUsername(userIdVal).orElseThrow(() -> new RuntimeException("User not found"));
        Task parentTask = getTaskNotFound(subCreateTaskRequestSo.getParentTaskId(), userIdVal);


        Task task = Task.builder()
                .user(user)
                .taskDescription(subCreateTaskRequestSo.getTaskDescription())
                .dueDate(subCreateTaskRequestSo.getDueDate())
                .taskStatus(TaskStatus.PENDING)
                .build();

        parentTask.addSubTask(task);
        toDoTaskRepository.save(parentTask);
    }

    @Transactional
    public void updateTask(UpdateTaskRequestSo updateTaskRequestSo) {
        String userId = userContext.getUserId();
        Task task = getTaskNotFound(updateTaskRequestSo.getTaskId(), userId);

        copyNonNullProperties(updateTaskRequestSo, task);

        try {
            toDoTaskRepository.save(task);
        } catch (OptimisticLockingFailureException e) {
            throw new RuntimeException("Failed to update task due to concurrent modification. Please retry.");
        }
    }

    private void copyNonNullProperties(UpdateTaskRequestSo source, Task target) {
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    private String[] getNullPropertyNames(Object source) {
        return Arrays.stream(source.getClass().getDeclaredFields())
                .filter(field -> {
                    try {
                        field.setAccessible(true);
                        return field.get(source) == null;
                    } catch (IllegalAccessException e) {
                        return false;
                    }
                })
                .map(field -> field.getName())
                .toArray(String[]::new);
    }

    @Override
    @Transactional
    public void updateTaskStatus(Integer taskId, Integer parentTaskId, TaskStatus taskStatus) {
        String userId = userContext.getUserId();
        Task task = getTaskNotFound(taskId, userId);

        task.setTaskStatus(taskStatus);
        if(!ObjectUtils.isEmpty(task.getSubTasks())) {
            task.getSubTasks().stream().forEach(subtask -> subtask.setTaskStatus(taskStatus));
        }

        try {
            toDoTaskRepository.save(task);
        } catch (OptimisticLockingFailureException e) {
            throw new RuntimeException("Task status update failed due to concurrent modification. Please retry.");
        }
    }

    private Task getTaskNotFound(Integer taskId, String userId) {
        return toDoTaskRepository.findByTaskIdAndUser_Username(taskId, userId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }
}
