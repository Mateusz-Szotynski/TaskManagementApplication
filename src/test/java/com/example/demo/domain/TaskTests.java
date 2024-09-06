package com.example.demo.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


public class TaskTests {

    private final String happyTitle = "testTitle";
    private final String happyDescription = "testDescription";
    private final Priority defaultPriority = Priority.LOW;
    private final Priority happyHighPriority = Priority.HIGH;
    private final Priority happyMediumPriority = Priority.MEDIUM;
    private final LocalDate happyDueToDate = LocalDate.now().plusDays(3);

    @Test
    @DisplayName("Creates TaskDAO with all parameters")
    void createTaskDAOWithAllParameters() {
        Task task = new Task(happyTitle, happyDescription, happyHighPriority,
                happyDueToDate);

        assertAll(() -> {
            assertNotNull(task);
            assertNotNull(task.getId());
            assertEquals(happyTitle, task.getTitle());
            assertEquals(happyDescription, task.getDescription());
            assertEquals(happyHighPriority, task.getPriority());
            assertEquals(happyDueToDate, task.getDueToDate());
            assertFalse(task.getIsCompleted());
        });
    }

    @Test
    @DisplayName("Creates TaskDAO without priority being passed")
    void createTaskDAOWithoutPriority() {
        Task task = new Task(happyTitle, happyDescription,
                happyDueToDate);

        assertAll(() -> {
            assertNotNull(task);
            assertNotNull(task.getId());
            assertEquals(happyTitle, task.getTitle());
            assertEquals(happyDescription, task.getDescription());
            assertEquals(defaultPriority, task.getPriority());
            assertEquals(happyDueToDate, task.getDueToDate());
            assertFalse(task.getIsCompleted());
        });
    }

    @Test
    @DisplayName("Throws IllegalArgumentException because of no title")
    void taskDAOWithoutTitleThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Task(null,
                happyDescription, happyHighPriority, happyDueToDate));
    }

    @Test
    @DisplayName("Throws IllegalArgumentException because of no description")
    void taskDAOWithoutDescriptionThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Task(happyTitle,
                null, defaultPriority, happyDueToDate));
    }

    @Test
    @DisplayName("Throws IllegalArgumentException because of setting due to date as now")
    void taskDAODueToDateNowThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Task(happyTitle,
                happyDescription, happyMediumPriority, LocalDate.now()));
    }

    @Test
    @DisplayName("Throws IllegalArgumentException because of setting due to date as past")
    void taskDAODueToDateBeforeThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Task(happyTitle,
                happyDescription, happyMediumPriority, LocalDate.of(2020, 2, 19)));
    }
}
