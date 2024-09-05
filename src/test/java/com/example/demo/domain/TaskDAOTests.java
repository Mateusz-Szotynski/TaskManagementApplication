package com.example.demo.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


public class TaskDAOTests {

    private final String happyTitle = "testTitle";
    private final String happyDescription = "testDescription";
    private final Priority defaultPriority = Priority.LOW;
    private final Priority happyHighPriority = Priority.HIGH;
    private final Priority happyMediumPriority = Priority.MEDIUM;
    private final LocalDate happyDueToDate = LocalDate.now().plusDays(3);

    @Test
    @DisplayName("Creates TaskDAO with all parameters")
    void createTaskDAOWithAllParameters() {
        TaskDAO taskDAO = new TaskDAO(happyTitle, happyDescription, happyHighPriority,
                happyDueToDate);

        assertAll(() -> {
            assertNotNull(taskDAO);
            assertNotNull(taskDAO.getId());
            assertEquals(happyTitle, taskDAO.getTitle());
            assertEquals(happyDescription, taskDAO.getDescription());
            assertEquals(happyHighPriority, taskDAO.getPriority());
            assertEquals(happyDueToDate, taskDAO.getDueToDate());
            assertFalse(taskDAO.getIsCompleted());
        });
    }

    @Test
    @DisplayName("Creates TaskDAO without priority being passed")
    void createTaskDAOWithoutPriority() {
        TaskDAO taskDAO = new TaskDAO(happyTitle, happyDescription,
                happyDueToDate);

        assertAll(() -> {
            assertNotNull(taskDAO);
            assertNotNull(taskDAO.getId());
            assertEquals(happyTitle, taskDAO.getTitle());
            assertEquals(happyDescription, taskDAO.getDescription());
            assertEquals(defaultPriority, taskDAO.getPriority());
            assertEquals(happyDueToDate, taskDAO.getDueToDate());
            assertFalse(taskDAO.getIsCompleted());
        });
    }

    @Test
    @DisplayName("Throws IllegalArgumentException because of no title")
    void taskDAOWithoutTitleThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new TaskDAO(null,
                happyDescription, happyHighPriority, happyDueToDate));
    }

    @Test
    @DisplayName("Throws IllegalArgumentException because of no description")
    void taskDAOWithoutDescriptionThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new TaskDAO(happyTitle,
                null, defaultPriority, happyDueToDate));
    }

    @Test
    @DisplayName("Throws IllegalArgumentException because of setting due to date as now")
    void taskDAODueToDateNowThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new TaskDAO(happyTitle,
                happyDescription, happyMediumPriority, LocalDate.now()));
    }

    @Test
    @DisplayName("Throws IllegalArgumentException because of setting due to date as past")
    void taskDAODueToDateBeforeThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new TaskDAO(happyTitle,
                happyDescription, happyMediumPriority, LocalDate.of(2020, 2, 19)));
    }
}
