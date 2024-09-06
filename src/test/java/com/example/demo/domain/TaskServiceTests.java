package com.example.demo.domain;

import com.example.demo.application.TaskService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
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
    private final LocalDate happyDueToDate = LocalDate.now().plusDays(3);;

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
}
