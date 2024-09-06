package com.example.demo.domain;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TaskRepositoryTests {

    private final String happyTitle = "testTitle";
    private final String happyDescription = "testDescription";
    private final Priority happyHighPriority = Priority.HIGH;
    private final Priority happyMediumPriority = Priority.MEDIUM;
    private final LocalDate happyDueToDate = LocalDate.now().plusDays(3);


    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0");

    @Autowired
    private TaskRepository taskRepository;

    @BeforeAll
    static void setup() {
        postgres.start();
    }

    @AfterEach
    void clear() {
        taskRepository.deleteAll();
    }

    @AfterAll
    static void close() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url",postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.sql.init.mode", () -> "always" );
        registry.add("testcontainers.reuse.enable", () -> "false");
    }

    @Test
    @DisplayName("Tests if database is created, running and app is conncected")
    void testDatabaseConnection() throws Exception {
        var connection = postgres.createConnection("");
        assertAll(() -> {
            assertTrue(postgres.isCreated());
            assertTrue(postgres.isRunning());
            assertTrue(postgres.createConnection("").isValid(1000));
        });
    }

    @Test
    @DisplayName("Tests if table 'task' is created")
    void testTableExists() {
        try (var connection = postgres.createConnection("")) {
            var resultSet = connection.createStatement().executeQuery("SELECT * FROM task LIMIT 1;");
            assertNotNull(resultSet);
        } catch (Exception e) {
            fail("Table 'task' does not exist or is not initialized properly.");
        }
    }

    @Test
    @DisplayName("Tests saving Task to database")
    void saveTaskToDb() {
        Task task = new Task(happyTitle, happyDescription, happyDueToDate);
        taskRepository.save(task);

        Task taskFromDb = taskRepository.findAll().getFirst();

        assertAll(() -> {
            assertNotNull(taskFromDb);
            assertEquals(happyTitle, taskFromDb.getTitle());
            assertEquals(happyDescription, taskFromDb.getDescription());
            assertEquals(happyDueToDate, taskFromDb.getDueToDate());
            assertFalse(taskFromDb.getIsCompleted());
        });
    }

    @Test
    @DisplayName("Retrieve tasks by title")
    void retrieveTasksByTitle() {
        List<Task> taskList = List.of(new Task(happyTitle, happyDescription, happyDueToDate),
                new Task(happyTitle, happyDescription, happyHighPriority, happyDueToDate),
                new Task("title", happyDescription, happyMediumPriority, happyDueToDate));
        taskRepository.saveAll(taskList);

        List<Task> retrievedTasksList = taskRepository.findByTitle(happyTitle);

        assertAll(() -> {
            assertFalse(retrievedTasksList.isEmpty());
            assertEquals(2, retrievedTasksList.size());
            assertTrue(retrievedTasksList.stream().allMatch((e) -> e.getTitle().contains(happyTitle)));
        });
    }

    @Test
    @DisplayName("Delete Task from database by entity")
    void deleteTaskFromDbByEntity() {
        Task task = new Task(happyTitle, happyDescription, happyMediumPriority, happyDueToDate);
        taskRepository.save(task);

        taskRepository.delete(task);
        List<Task> byTitle = taskRepository.findByTitle(happyTitle);

        assertAll(() -> {
            assertTrue(byTitle.isEmpty());
            assertThrows(NoSuchElementException.class, () -> taskRepository.findByTitle(happyTitle).getFirst());
        });
    }
}
