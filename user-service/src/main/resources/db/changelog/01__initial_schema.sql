-- liquibase formatted sql

-- changeset ramoni73:basic_uuid_extension
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- changeset ramoni73:basic_tables_user
CREATE TABLE app_user (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

CREATE TABLE user_identity (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL REFERENCES app_user(id) ON DELETE CASCADE,
    external_id VARCHAR(255) NOT NULL,
    provider VARCHAR(50) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,

    UNIQUE (external_id, provider),
    UNIQUE (user_id, provider)
);
