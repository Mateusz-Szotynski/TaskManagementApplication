package com.example.demo.domain;

import com.example.demo.application.TaskService;
import com.example.demo.domain.exceptions.TaskNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.assertArg;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@Testcontainers
@ExtendWith(MockitoExtension.class)
public class TaskServiceTests {

    private final String happyTitle = "testTitle";
    private final String happyDescription = "testDescription";
    private final Priority happyHighPriority = Priority.HIGH;
    private final Priority happyMediumPriority = Priority.MEDIUM;
    private final LocalDate happyDueToDate = LocalDate.now().plusDays(3);

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    @DisplayName("Finds and returns found tasks by title")
    void returnTasksByTitle() {
        when(taskRepository.findByTitle(happyTitle)).thenReturn(List.of(
                new Task(happyTitle, happyDescription, happyDueToDate),
                new Task(happyTitle, happyDescription, happyHighPriority, happyDueToDate)
        ));

        List<Task> taskList = taskService.findTasksByTitle(happyTitle);

        assertAll(() -> {
            assertFalse(taskList.isEmpty());
            assertEquals(2, taskList.size());
            assertTrue(taskList.stream().allMatch((e) -> e.getTitle().contentEquals(happyTitle)));
        });
    }

    @Test
    @DisplayName("Couldnt find a task with particular title. Throws TaskNotFoundException")
    void noTaskTitleAvailableThrowsTaskNotFoundException() {
        when(taskRepository.findByTitle(happyTitle)).thenReturn(List.of());

        assertThrows(TaskNotFoundException.class, () -> taskService.findTasksByTitle(happyTitle));
    }

    @Test
    @DisplayName("Finds and returns found tasks by Priority")
    void returnTaskByPriority() {
        when(taskRepository.findByPriority(happyHighPriority)).thenReturn(List.of(
                new Task(happyTitle, happyDescription, happyHighPriority, happyDueToDate),
                new Task(happyTitle+"2", happyDescription, happyHighPriority, happyDueToDate)
        ));
        List<Task> taskList = taskRepository.findByPriority(happyHighPriority);

        assertAll(() -> {
            assertFalse(taskList.isEmpty());
            assertEquals(2, taskList.size());
            assertTrue(taskList.stream().allMatch((e) -> e.getPriority().equals(happyHighPriority)));
        });
    }

    @Test
    @DisplayName("Couldnt find a task with particular Priority. Throws TaskNotFoundException")
    void noTaskPriorityAvailableThrowsTaskNotFoundException() {
        when(taskRepository.findByPriority(happyMediumPriority)).thenReturn(List.of());

        assertThrows(TaskNotFoundException.class, () -> taskService.findTasksByPriority(happyMediumPriority));
    }
}
