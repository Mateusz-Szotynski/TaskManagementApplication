package com.example.demo.domain;

import com.example.demo.application.TaskService;
import com.example.demo.interfaces.rest.TaskController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@Testcontainers
@ExtendWith(MockitoExtension.class)
public class TaskControllerTests {

    private final String happyTitle = "testTitle";
    private final String happyDescription = "testDescription";
    private final Priority happyHighPriority = Priority.HIGH;
    private final Priority happyMediumPriority = Priority.MEDIUM;
    private final LocalDate happyDueToDate = LocalDate.now().plusDays(3);

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @Test
    @DisplayName("Returns all tasks with particular title")
    void findTasksByTitle() {
        when(taskService.findTasksByTitle(happyTitle)).thenReturn(List.of(
                new Task(happyTitle, happyDescription, happyDueToDate),
                new Task(happyTitle, happyDescription, happyHighPriority, happyDueToDate)
        ));

        ResponseEntity<List<Task>> taskList = taskController.findTasksByTitle(happyTitle);

        assertAll(() -> {
            assertFalse(taskList.getBody().isEmpty());
            assertEquals(HttpStatus.OK, taskList.getStatusCode());
            assertEquals(2, taskList.getBody().size());
            assertTrue(taskList.getBody().stream().allMatch((e) -> e.getTitle().contentEquals(happyTitle)));
        });

    }
}
