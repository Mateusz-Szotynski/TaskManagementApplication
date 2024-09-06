-- task table
CREATE TABLE IF NOT EXISTS task(
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title TEXT NOT NULL,
    description TEXT NOT NULL,
    priority TEXT NOT NULL,
    due_to_date TIMESTAMP WITH TIME ZONE NOT NULL,
    is_completed BOOLEAN NOT NULL,
    CONSTRAINT chck_priority CHECK ( priority LIKE 'LOW' OR
        priority LIKE 'MEDIUM' OR
        priority LIKE 'HIGH' OR
        priority LIKE 'CRITICAL')
);