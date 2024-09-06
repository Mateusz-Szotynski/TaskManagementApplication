package com.example.demo.domain;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends ListCrudRepository<Task, String> {
    @Override
    @Query("SELECT t * FROM task as t WHERE t.id = ? LIMIT 1")
    Optional<Task> findById(String s);
}
