package com.example.demo.application;

import com.example.demo.domain.Priority;
import com.example.demo.domain.Task;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskDTO implements Task {
    private String title;
    private String description;
    private Priority priority;
    private LocalDate dueToDate;


}
