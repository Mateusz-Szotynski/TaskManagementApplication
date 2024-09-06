package com.example.demo.domain;

import org.springframework.data.relational.core.mapping.Table;

public enum Priority {
    LOW(1), MEDIUM(2), HIGH(3), CRITICAL(4);

    private final Integer id;

    Priority(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public static Priority fromId(int id) {
        for (Priority priority : values()) {
            if (priority.id == id) {
                return priority;
            }
        }
        throw new IllegalArgumentException("Invalid Priority id: " + id);
    }
}
