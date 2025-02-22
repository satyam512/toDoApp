package com.to_do.ToDoApp.entity;

import com.to_do.ToDoApp.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer taskId;
    private Integer userId;
    private TaskStatus taskStatus;
    private String taskDescription;
    private LocalDate dueDate;

    @Version
    private Long version;

    @ManyToOne
    @JoinColumn(name = "parent_task_id")
    private Task parentTask;

    @OneToMany(mappedBy = "parentTask", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> subTasks;

    @Column(name = "created_at")
    private LocalDate createdAt;

    public void addSubTask(Task subTask) {
        subTasks.add(subTask);
        subTask.setParentTask(this);
    }
}
