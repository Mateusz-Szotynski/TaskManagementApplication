package com.example.demo.application;

import com.example.demo.domain.Task;
import com.example.demo.domain.TaskRepository;
import com.example.demo.domain.exceptions.TaskNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findTasksByTitle(String title) {
        List<Task> taskList = taskRepository.findByTitle(title);
        if (taskList.isEmpty()) {
            throw new TaskNotFoundException("There are no tasks with: " + title + " title");
        }
        return taskList;
    }


}
