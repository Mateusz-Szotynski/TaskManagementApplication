package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.util.Assert;

import java.util.UUID;

@Table
@Getter
@Setter
public class Task {
    @Id
    private String id;
    private String title;
    private String description;
    private Priority priority;
    private boolean isCompleted;

    /*
    *   Creates Task and assigns an id with UUID.toString()
    *   Asserts that title and description cannot be null, if so throws IllegalArgumentException
    *   If priority is null then assigns low priority
    * */
    public Task(String title, String description, Priority priority) {
        Assert.notNull(title, "Task title cannot be null");
        Assert.notNull(description, "Task description cannot be null");
        id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.priority = priority == null ? Priority.LOW : priority;
        isCompleted = false;
    }

    public Task(String title, String description) {
        this(title, description, null);
    }
}
