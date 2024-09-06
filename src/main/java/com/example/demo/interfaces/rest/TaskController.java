package com.example.demo.interfaces.rest;

import com.example.demo.application.TaskService;
import com.example.demo.domain.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Task>> findTasksByTitle(@RequestParam String title) {
        return ResponseEntity.ok(taskService.findTasksByTitle(title));
    }
}
