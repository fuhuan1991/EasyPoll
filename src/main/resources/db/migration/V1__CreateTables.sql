CREATE TABLE IF NOT EXISTS poll (
    id VARCHAR(22) PRIMARY KEY NOT NULL,
    title VARCHAR(200) NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS question (
    id UUID PRIMARY KEY NOT NULL,
    index int NOT NULL,
    poll_id VARCHAR(22) NOT NULL REFERENCES "poll" (id),
    name VARCHAR(500) NOT NULL,
    type VARCHAR(20) NOT NULL
    CHECK (
        type = 'SINGLE' OR
        type = 'MULTIPLE'
    )
);

CREATE TABLE IF NOT EXISTS option (
    id UUID PRIMARY KEY NOT NULL,
    index int NOT NULL,
    question_id UUID NOT NULL REFERENCES "question" (id),
    name VARCHAR(500) NOT NULL,
    count int NOT NULL
);