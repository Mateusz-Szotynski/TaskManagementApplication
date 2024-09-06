CREATE TABLE IF NOT EXISTS priority(
    id SMALLINT NOT NULL PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS task(
    id TEXT NOT NULL PRIMARY KEY,
    title TEXT NOT NULL,
    description TEXT NOT NULL,
    priority SMALLINT NOT NULL,
    due_to_date TIMESTAMP WITH TIME ZONE NOT NULL,
    is_completed BOOLEAN NOT NULL,
    CONSTRAINT fk_priority
        FOREIGN KEY (priority)
        REFERENCES priority(id)
);