-- MySQL-compatible schema for FocusLearn (V1)

CREATE TABLE users (
    id CHAR(36) PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    name VARCHAR(255),
    avatar_url VARCHAR(1024),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);

CREATE TABLE user_roles (
    user_id CHAR(36) NOT NULL,
    role VARCHAR(64) NOT NULL,
    PRIMARY KEY (user_id, role),
    CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE journeys (
    id CHAR(36) PRIMARY KEY,
    title VARCHAR(1024) NOT NULL,
    description TEXT,
    visibility VARCHAR(32) NOT NULL,
    tags VARCHAR(1024),
    owner_id CHAR(36) NOT NULL,
    source_journey_id CHAR(36),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT fk_journeys_owner FOREIGN KEY (owner_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE steps (
    id CHAR(36) PRIMARY KEY,
    journey_id CHAR(36) NOT NULL,
    title VARCHAR(1024),
    type VARCHAR(32),
    video_id VARCHAR(128),
    video_url VARCHAR(2048),
    position_index INT,
    duration_seconds INT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT fk_steps_journey FOREIGN KEY (journey_id) REFERENCES journeys(id) ON DELETE CASCADE
);

CREATE INDEX idx_steps_journey_pos ON steps(journey_id, position_index);

CREATE TABLE notes (
    id CHAR(36) PRIMARY KEY,
    user_id CHAR(36) NOT NULL,
    step_id CHAR(36),
    journey_id CHAR(36),
    content TEXT,
    timestamp_seconds INT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT fk_notes_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_notes_step FOREIGN KEY (step_id) REFERENCES steps(id) ON DELETE SET NULL,
    CONSTRAINT fk_notes_journey FOREIGN KEY (journey_id) REFERENCES journeys(id) ON DELETE CASCADE
);

CREATE TABLE progress (
    id CHAR(36) PRIMARY KEY,
    user_id CHAR(36) NOT NULL,
    journey_id CHAR(36) NOT NULL,
    step_id CHAR(36),
    status VARCHAR(32),
    last_position_seconds INT,
    percent_watched DOUBLE,
    total_time_spent_seconds BIGINT,
    updated_at TIMESTAMP,
    CONSTRAINT fk_progress_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_progress_journey FOREIGN KEY (journey_id) REFERENCES journeys(id) ON DELETE CASCADE
);

CREATE TABLE import_jobs (
    id CHAR(36) PRIMARY KEY,
    user_id CHAR(36) NOT NULL,
    source_url VARCHAR(2048),
    status VARCHAR(32),
    error_msg TEXT,
    created_at TIMESTAMP NOT NULL,
    completed_at TIMESTAMP,
    CONSTRAINT fk_import_jobs_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE notifications (
    id CHAR(36) PRIMARY KEY,
    user_id CHAR(36) NOT NULL,
    title VARCHAR(255),
    message TEXT,
    read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_notifications_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE refresh_tokens (
    id CHAR(36) PRIMARY KEY,
    token VARCHAR(255) NOT NULL UNIQUE,
    user_email VARCHAR(255) NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL
);

-- ensure users.email unique
ALTER TABLE users ADD UNIQUE INDEX ux_users_email (email);

-- set engine to InnoDB for foreign key support
ALTER TABLE users ENGINE=InnoDB;
ALTER TABLE user_roles ENGINE=InnoDB;
ALTER TABLE journeys ENGINE=InnoDB;
ALTER TABLE steps ENGINE=InnoDB;
ALTER TABLE notes ENGINE=InnoDB;
ALTER TABLE progress ENGINE=InnoDB;
ALTER TABLE import_jobs ENGINE=InnoDB;
ALTER TABLE notifications ENGINE=InnoDB;
ALTER TABLE refresh_tokens ENGINE=InnoDB;
