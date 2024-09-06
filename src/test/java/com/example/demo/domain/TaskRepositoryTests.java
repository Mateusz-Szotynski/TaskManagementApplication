package com.example.demo.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TaskRepositoryTests {

    private final String happyTitle = "testTitle";
    private final String happyDescription = "testDescription";
    private final Priority defaultPriority = Priority.LOW;
    private final Priority happyHighPriority = Priority.HIGH;
    private final Priority happyMediumPriority = Priority.MEDIUM;
    private final LocalDate happyDueToDate = LocalDate.now().plusDays(3);


    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0");

    @Autowired
    private TaskRepository taskRepository;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url",postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.initialization-mode", () -> "always" );
    }

    @Test
    void connect() {
        assertAll(() -> {
            assertTrue(postgres.isCreated());
            assertTrue(postgres.isRunning());
        });
    }

    @Test
    void testDatabaseConnection() throws Exception {
        String schemaSql = Files.readString(Path.of("src/test/resources/schema.sql"));
        try (var connection = postgres.createConnection("")) {
            try (var statement = connection.createStatement()) {
                statement.execute(schemaSql);
            }
        }
    }

    @Test
    void testTableExists() {
        try (var connection = postgres.createConnection("")) {
            var resultSet = connection.createStatement().executeQuery("SELECT * FROM task LIMIT 1;");
            assertNotNull(resultSet);
        } catch (Exception e) {
            fail("Table 'task' does not exist or is not initialized properly.");
        }
    }
}
