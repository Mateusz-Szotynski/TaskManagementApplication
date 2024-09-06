package com.example.demo.application;

import com.example.demo.domain.Task;
import com.example.demo.domain.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    private TaskDTO convertToDTO(Task task) {
        return new TaskDTO(
                task.getTitle(),
                task.getDescription(),
                task.getPriority(),
                task.getDueToDate(),
                task.getIsCompleted()
        );
    }

    private Task convertToDAO(TaskDTO taskDTO) {
        return new Task(
                taskDTO.getTitle(),
                taskDTO.getDescription(),
                taskDTO.getPriority(),
                taskDTO.getDueToDate()
        );
    }
}
