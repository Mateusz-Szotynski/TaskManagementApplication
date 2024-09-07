package com.example.demo.application;

import com.example.demo.domain.Priority;
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

    public List<Task> findTasksByPriority(Priority priority) {
        List<Task> taskList = taskRepository.findByPriority(priority);
        if (taskList.isEmpty()) {
            throw new TaskNotFoundException("There are no tasks with: " + priority + " priority");
        }
        return taskList;
    }

    public List<Task> findAllTasks() {
        List<Task> taskList = taskRepository.findAll();
        if (taskList.isEmpty()) {
            throw new TaskNotFoundException("There are no tasks at all");
        }
        return taskList;
    }

    public List<Task> findTasksByTitleAndPriority(String title, Priority priority) {
        List<Task> taskList = taskRepository.findByTitleAndPriority(title, priority);
        if (taskList.isEmpty()) {
            throw new TaskNotFoundException("There are no tasks with: " + title + " title and " + priority
            + " priority");
        }
    }


}
