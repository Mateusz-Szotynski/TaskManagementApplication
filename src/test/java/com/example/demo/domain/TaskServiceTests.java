package com.example.demo.domain;

import com.example.demo.application.TaskService;
import com.example.demo.domain.exceptions.TaskNotFoundException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@Testcontainers
@ExtendWith(MockitoExtension.class)
public class TaskServiceTests {

    /*
     * Tests behaviour of TaskService.class
     * Checks all possible scenarios of responses
     * That includes:
     *   - finds and returns found tasks by title
     *   - throws exception because of no task with provided title
     *   - finds and returns found tasks by Priority
     *   - throws exception because of no task with provided priority
     *
     */

    private final String happyTitle = "testTitle";
    private final String happyDescription = "testDescription";
    private final Priority happyHighPriority = Priority.HIGH;
    private final Priority happyMediumPriority = Priority.MEDIUM;
    private final LocalDate happyDueToDate = LocalDate.now().plusDays(3);

    @Container
    static GenericContainer debian = new GenericContainer("debian:latest");

    @BeforeAll
    static void startUp() {
        debian.start();
    }

    @AfterAll
    static void shutDown() {
        debian.close();
    }

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    @DisplayName("Finds and returns all tasks")
    void returnAllTasks() {
        when(taskRepository.findAll()).thenReturn(List.of(
                new Task(happyTitle, happyDescription, happyDueToDate),
                new Task(happyTitle, happyDescription, happyMediumPriority, happyDueToDate),
                new Task(happyTitle, happyDescription, happyHighPriority, happyDueToDate),
                new Task(happyTitle + "x2", happyDescription, happyDueToDate)
        ));

        List<Task> taskList = taskService.findAllTasks();

        assertAll(() -> {
            assertFalse(taskList.isEmpty());
            assertEquals(4, taskList.size());
        });
    }

    @Test
    @DisplayName("Couldn't find any task. Throws TaskNotFoundException")
    void noTaskAvailable() {
        when(taskRepository.findAll()).thenReturn(List.of());

        assertAll(() -> {
            assertTrue(taskRepository.findAll().isEmpty());
            assertThrows(TaskNotFoundException.class, () -> taskService.findAllTasks());
        });
    }


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
        List<Task> taskList = taskService.findTasksByPriority(happyHighPriority);

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

        assertAll(() -> {
            assertTrue(taskRepository.findByPriority(happyMediumPriority).isEmpty());
            assertThrows(TaskNotFoundException.class, () -> taskService.findTasksByPriority(happyMediumPriority));
        });
    }

    @Test
    @DisplayName("Finds task list with provided title and priority")
    void returnTaskByTitleAndPriority() {
        when(taskRepository.findByTitleAndPriority(happyTitle, happyMediumPriority)).thenReturn(List.of(
                new Task(happyTitle, happyDescription, happyMediumPriority, happyDueToDate),
                new Task(happyTitle, happyDescription, happyMediumPriority, happyDueToDate)
        ));

        List<Task> tasksFromDb = taskService.findTasksByTitleAndPriority(happyTitle, happyMediumPriority);

        assertAll(() -> {
            assertFalse(tasksFromDb.isEmpty());
            assertEquals(2, tasksFromDb.size());
            assertTrue(tasksFromDb.stream().allMatch((e) -> e.getTitle().equalsIgnoreCase(happyTitle) &&
                    e.getPriority().equals(happyMediumPriority)));
        });
    }

    @Test
    @DisplayName("Couldnt find a tasks with provided title and priority. Throws exception")
    void noTaskWithTitleAndPriorityAvailable() {
        when(taskRepository.findByTitleAndPriority(happyTitle, happyMediumPriority)).thenReturn(List.of());


        assertAll(() -> {
            assertTrue(taskRepository.findByTitleAndPriority(happyTitle, happyMediumPriority).isEmpty());
            assertThrows(TaskNotFoundException.class, () -> taskService.findTasksByTitleAndPriority(happyTitle, happyMediumPriority));
        });
    }

    @ParameterizedTest
    @DisplayName("Saves Task to database")
    @EnumSource(Priority.class)
    void savesTaskToDatabase(Priority priority) {
        Task task = new Task(happyTitle, happyDescription, priority, happyDueToDate);
        when(taskRepository.save(task)).thenReturn(task);

        Task savedTask = taskService.saveTask(task);

        assertAll(() -> {
            assertNotNull(savedTask);
        });
    }
}
