package com.example.demo.application;

import com.example.demo.domain.TaskDAO;
import com.example.demo.domain.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    private TaskDTO convertToDTO(TaskDAO taskDAO) {
        return new TaskDTO(
                taskDAO.getTitle(),
                taskDAO.getDescription(),
                taskDAO.getPriority(),
                taskDAO.getDueToDate(),
                taskDAO.getIsCompleted()
        );
    }

    private TaskDAO convertToDAO(TaskDTO taskDTO) {
        return new TaskDAO(
                taskDTO.getTitle(),
                taskDTO.getDescription(),
                taskDTO.getPriority(),
                taskDTO.getDueToDate()
        );
    }
}
