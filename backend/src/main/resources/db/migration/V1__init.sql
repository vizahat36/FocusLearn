-- initial schema for FocusLearn

CREATE TABLE users (
    id uuid PRIMARY KEY,
    email varchar(255) NOT NULL UNIQUE,
    password_hash varchar(255) NOT NULL,
    name varchar(255),
    avatar_url varchar(1024),
    created_at timestamptz NOT NULL,
    updated_at timestamptz
);

CREATE TABLE user_roles (
    user_id uuid REFERENCES users(id) ON DELETE CASCADE,
    role varchar(64) NOT NULL,
    PRIMARY KEY (user_id, role)
);

CREATE TABLE journeys (
    id uuid PRIMARY KEY,
    title varchar(1024) NOT NULL,
    description text,
    visibility varchar(32) NOT NULL,
    tags varchar(1024),
    owner_id uuid NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    source_journey_id uuid,
    created_at timestamptz NOT NULL,
    updated_at timestamptz
);

CREATE TABLE steps (
    id uuid PRIMARY KEY,
    journey_id uuid NOT NULL REFERENCES journeys(id) ON DELETE CASCADE,
    title varchar(1024),
    type varchar(32),
    video_id varchar(128),
    video_url varchar(2048),
    position_index integer,
    duration_seconds integer,
    created_at timestamptz NOT NULL,
    updated_at timestamptz
);

CREATE INDEX idx_steps_journey_pos ON steps(journey_id, position_index);

CREATE TABLE notes (
    id uuid PRIMARY KEY,
    user_id uuid NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    step_id uuid REFERENCES steps(id) ON DELETE SET NULL,
    journey_id uuid REFERENCES journeys(id) ON DELETE CASCADE,
    content text,
    timestamp_seconds integer,
    created_at timestamptz NOT NULL,
    updated_at timestamptz
);

CREATE TABLE progress (
    id uuid PRIMARY KEY,
    user_id uuid NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    journey_id uuid NOT NULL REFERENCES journeys(id) ON DELETE CASCADE,
    step_id uuid,
    status varchar(32),
    last_position_seconds integer,
    percent_watched double precision,
    total_time_spent_seconds bigint,
    updated_at timestamptz
);

CREATE TABLE import_jobs (
    id uuid PRIMARY KEY,
    user_id uuid NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    source_url varchar(2048),
    status varchar(32),
    error_msg text,
    created_at timestamptz NOT NULL,
    completed_at timestamptz
);
