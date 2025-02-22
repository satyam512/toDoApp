package com.to_do.ToDoApp.repository;

import com.to_do.ToDoApp.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ToDoTaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findAllByUserId(Integer userId);
    Optional<Task> findByUserIdAndTaskId(Integer userId, Integer taskId);
}
