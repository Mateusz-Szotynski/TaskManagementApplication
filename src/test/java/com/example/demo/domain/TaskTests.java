package com.example.demo.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


public class TaskTests {

    /*
    * Tests behaviour of Task.class
    * Checks all possible scenarios of creating and updating object.
    * That includes:
    *   - creates Task with all parameters
    *   - creates Task without priority (default is being used by constructor)
    *   - throws exception when no title is provided
    *   - throws exception when no description is provided
    *   - throws exception when no datetime is provided
    *   - throws exception when datetime is now
    *   - throws exception when datetime is past
    * */

    private final String happyTitle = "testTitle";
    private final String happyDescription = "testDescription";
    private final Priority defaultPriority = Priority.LOW;
    private final LocalDate happyDueToDate = LocalDate.now().plusDays(3);

    @DisplayName("Creates TaskDAO with all parameters")
    @ParameterizedTest
    @EnumSource(Priority.class)
    void createTaskDAOWithAllParameters(Priority priority) {
        Task task = new Task(happyTitle, happyDescription, priority,
                happyDueToDate);

        assertAll(() -> {
            assertNotNull(task);
            assertNull(task.getId());
            assertEquals(happyTitle, task.getTitle());
            assertEquals(happyDescription, task.getDescription());
            assertEquals(priority, task.getPriority());
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
            assertNull(task.getId());
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
                happyDescription, defaultPriority, happyDueToDate));
    }

    @Test
    @DisplayName("Throws IllegalArgumentException because of no description")
    void taskDAOWithoutDescriptionThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Task(happyTitle,
                null, defaultPriority, happyDueToDate));
    }

    @Test
    @DisplayName("Throws IllegalArgumentException because of no date")
    void taskDAODueToNoDateThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Task(happyTitle,
                happyDescription, defaultPriority, null));
    }

    @Test
    @DisplayName("Throws IllegalArgumentException because of due to date as now")
    void taskDAODueToDateNowThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Task(happyTitle,
                happyDescription, defaultPriority, LocalDate.now()));
    }

    @Test
    @DisplayName("Throws IllegalArgumentException because of due to date as past")
    void taskDAODueToDateBeforeThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Task(happyTitle,
                happyDescription, defaultPriority, LocalDate.of(2020, 2, 19)));
    }

    @Test
    @DisplayName("Throws IllegalArgumentException because of trying to set title as null")
    void taskDAOSetTitleToNull() {
        Task task = new Task(happyTitle, happyDescription, happyDueToDate);

        assertAll(() -> {
            assertThrows(IllegalArgumentException.class, () -> task.setTitle(null));
            assertEquals(happyTitle, task.getTitle());
        });
    }

    @Test
    @DisplayName("Throws IllegalArgumentException because of trying to set description as null")
    void TaskDAOSetDescriptionToNull() {
        Task task = new Task(happyTitle, happyDescription, happyDueToDate);

        assertAll(() -> {
            assertThrows(IllegalArgumentException.class, () -> task.setDescription(null));
            assertEquals(happyDescription, task.getDescription());
        });

    }

    @Test
    @DisplayName("Throws IllegalArgumentException because of trying to set dueToDate as null")
    void TaskDAOSetDueToDateToNull() {
        Task task = new Task(happyTitle, happyDescription, happyDueToDate);

        assertAll(() -> {
            assertThrows(IllegalArgumentException.class, () -> task.setDueToDate(null));
            assertEquals(happyDueToDate, task.getDueToDate());
        });
    }

    @Test
    @DisplayName("Throws IllegalArgumentException because of trying to set dueToDate to past and to now")
    void TaskDAOSetDueToDateToPastAndNow() {
        Task task = new Task(happyTitle, happyDescription, happyDueToDate);

        assertAll(() -> {
            assertThrows(IllegalArgumentException.class, () -> task.setDueToDate(LocalDate.now().minusDays(1)));
            assertThrows(IllegalArgumentException.class, () -> task.setDueToDate(LocalDate.now()));
            assertEquals(happyDueToDate, task.getDueToDate());
        });
    }
}
