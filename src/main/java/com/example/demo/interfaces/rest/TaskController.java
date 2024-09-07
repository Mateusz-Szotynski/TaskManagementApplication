package com.example.demo.interfaces.rest;

import com.example.demo.application.TaskService;
import com.example.demo.domain.Priority;
import com.example.demo.domain.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/findTasks")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Task>> findTasks(@RequestParam(required = false) String title,
                                                @RequestParam(required = false) Priority priority) {
        if (priority == null && title == null) {
            return ResponseEntity.ok(taskService.findAllTasks());
        } else if (priority != null && title == null) {
            return ResponseEntity.ok(taskService.findTasksByPriority(priority));
        } else if (priority != null && title != null) {
            return ResponseEntity.ok(taskService.findTasksByTitleAndPriority(title, priority));
        } else {
            return ResponseEntity.ok(taskService.findTasksByTitle(title));
        }
    }
}
