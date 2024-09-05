package com.example.demo.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


public class TaskDAOTests {

    private final String happyTitle = "testTitle";
    private final String happyDescription = "testDescription";
    private final Priority happyHighPriority = Priority.HIGH;
    private final Priority happyMediumPriority = Priority.MEDIUM;
    private final LocalDate happyDueToDate = LocalDate.now().plusDays(3);

    @Test
    void createTaskDAOWithAllParameters() {
        TaskDAO taskDAO = new TaskDAO(happyTitle, happyDescription, happyHighPriority,
                happyDueToDate);

        assertAll(() -> {
            assertNotNull(taskDAO);
            assertNotNull(taskDAO.getId());
            assertEquals(taskDAO.getTitle(), happyTitle);
            assertEquals(taskDAO.getDescription(), happyDescription);
            assertEquals(taskDAO.getPriority(), happyHighPriority);
            assertEquals(taskDAO.getDueToDate(), happyDueToDate);
            assertFalse(taskDAO.getIsCompleted());
        });
    }
}
