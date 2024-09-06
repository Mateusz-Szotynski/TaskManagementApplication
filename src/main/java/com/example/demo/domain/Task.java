package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.UUID;

@Table("task")
@Getter
@Setter
public class Task {

    @Id
    private Long id;
    private String title;
    private String description;
    @Column("priority")
    private Priority priority;
    private LocalDate dueToDate;
    private Boolean isCompleted;

    /*
    *   Creates Task and delegates id to be assigned by database.
    *   Asserts that title and description cannot be null, also DueToDate cannot
    *   be before now() if so throws IllegalArgumentException
    *   If priority is null then assigns low priority
    * */

    public Task(String title, String description, Priority priority, LocalDate dueToDate) {
        Assert.notNull(title, "Task title cannot be null");
        Assert.notNull(description, "Task description cannot be null");
        Assert.notNull(dueToDate, "Due To date cannot be null");
        Assert.isTrue(dueToDate.isAfter(LocalDate.now()), "Due To date cannot be before now");
        this.title = title;
        this.description = description;
        this.priority = priority != null ? priority : Priority.LOW;
        this.dueToDate = dueToDate;
        isCompleted = false;
    }

    public Task(String title, String description, LocalDate dueToDate) {
        this(title, description, null, dueToDate);
    }

    public Task() {
    }
}
