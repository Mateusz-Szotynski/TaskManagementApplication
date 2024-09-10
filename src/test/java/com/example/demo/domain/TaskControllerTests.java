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

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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
    @DisplayName("finds and returns all task")
    void findTasksWithoutParams() {
        when(taskService.findAllTasks()).thenReturn(List.of(
                new Task(happyTitle, happyDescription, happyMediumPriority, happyDueToDate),
                new Task(happyTitle, happyDescription, happyDueToDate)
        ));

        ResponseEntity<List<Task>> response = taskController.findTasks(null, null);

        assertAll(() -> {
                assertNotNull(response.getBody());
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertEquals(taskService.findAllTasks(), response.getBody());
                assertEquals(2, response.getBody().size());
        });
    }

    @Test
    @DisplayName("Finds and returns tasks with provided priority")
    void findTasksWithPriorityParam() {
        when(taskService.findTasksByPriority(happyMediumPriority)).thenReturn(List.of(
                new Task(happyTitle, happyDescription, happyMediumPriority, happyDueToDate),
                new Task(happyTitle, happyDescription, happyMediumPriority, happyDueToDate)
        ));

        ResponseEntity<List<Task>> taskListFromDb = taskController.findTasks(null, happyMediumPriority);

        assertAll(() -> {
            assertNotNull(taskListFromDb.getBody());
            assertEquals(HttpStatus.OK, taskListFromDb.getStatusCode());
            assertEquals(2, taskListFromDb.getBody().size());
            assertTrue(taskListFromDb.getBody().stream().allMatch((e) -> e.getPriority().equals(happyMediumPriority)));
        });
    }

    @Test
    @DisplayName("Finds and returns task list with provided title")
    void findTasksWithTitleParam() {
        when(taskService.findTasksByTitle(happyTitle)).thenReturn(List.of(
                new Task(happyTitle, happyDescription, happyMediumPriority, happyDueToDate),
                new Task(happyTitle, happyDescription, happyHighPriority, happyDueToDate),
                new Task(happyTitle, happyDescription, happyDueToDate)
        ));

        ResponseEntity<List<Task>> taskListFromDb = taskController.findTasks(happyTitle, null);

        assertAll(() -> {
            assertNotNull(taskListFromDb.getBody());
            assertEquals(HttpStatus.OK, taskListFromDb.getStatusCode());
            assertEquals(3, taskListFromDb.getBody().size());
            assertTrue(taskListFromDb.getBody().stream().allMatch((e) -> e.getTitle().equalsIgnoreCase(happyTitle)));
        });
    }

    @Test
    @DisplayName("Find and returns task list with provided title and priority")
    void findTasksWithTitleAndPriorityParam() {
        when(taskService.findTasksByTitleAndPriority(happyTitle, happyHighPriority)).thenReturn(List.of(
                new Task(happyTitle, happyDescription, happyHighPriority, happyDueToDate),
                new Task(happyTitle, happyDescription, happyHighPriority, happyDueToDate)
        ));

        ResponseEntity<List<Task>> taskListFromDb = taskController.findTasks(happyTitle, happyHighPriority);

        assertAll(() -> {
            assertNotNull(taskListFromDb.getBody());
            assertEquals(HttpStatus.OK, taskListFromDb.getStatusCode());
            assertTrue(taskListFromDb.getBody().stream().allMatch(e -> e.getTitle().equalsIgnoreCase(happyTitle) &&
                    e.getPriority().equals(happyHighPriority)));
            assertEquals(2, taskListFromDb.getBody().size());
        });
    }
}
