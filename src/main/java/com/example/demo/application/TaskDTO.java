package com.example.demo.application;

import com.example.demo.domain.Priority;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.Assert;

import java.time.LocalDate;

@Getter
@Setter
public class TaskDTO {
    private String title;
    private String description;
    private Priority priority;
    private LocalDate dueToDate;
    private Boolean isCompleted;

    public TaskDTO(String title, String description, Priority priority,
                   LocalDate dueToDate, Boolean isCompleted) {
        Assert.notNull(title, "Task title cannot be null");
        Assert.notNull(description, "Task description cannot be null");
        Assert.notNull(dueToDate, "Due To date cannot be null");
        Assert.isTrue(dueToDate.isAfter(LocalDate.now()), "Due To date cannot be before now");
        this.title = title;
        this.description = description;
        this.priority = priority != null ? priority : Priority.LOW;
        this.dueToDate = dueToDate;
        this.isCompleted = isCompleted;
    }

    public TaskDTO(String title, String description, LocalDate dueToDate, Boolean isCompleted) {
        this(title, description, null, dueToDate, isCompleted);
    }

}
