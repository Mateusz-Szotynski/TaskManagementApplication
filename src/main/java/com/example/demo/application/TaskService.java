package com.example.demo.application;

import com.example.demo.domain.Task;
import com.example.demo.domain.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findTasksByTitle(String title) {
        return taskRepository.findByTitle(title);
    }


}
