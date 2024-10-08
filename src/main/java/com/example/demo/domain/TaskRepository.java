package com.example.demo.domain;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends ListCrudRepository<Task, Long> {
    List<Task> findByTitle(String title);
    List<Task> findByPriority(Priority priority);
    List<Task> findByTitleAndPriority(String title, Priority priority);
}
