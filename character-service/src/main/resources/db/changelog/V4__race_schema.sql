-- liquibase formatted sql

-- changeset ramoni73:race
CREATE TABLE race (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(50) UNIQUE NOT NULL,
    description TEXT,
    creature_type VARCHAR(25) NOT NULL,
    creature_size VARCHAR(120) NOT NULL,
    speed integer
);

-- changeset ramoni73:race_trait
CREATE TABLE race_special_trait (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    race_id UUID NOT NULL REFERENCES race(id),
    name VARCHAR(100) NOT NULL,
    value TEXT NOT NULL
);

CREATE INDEX idx_race_property_race_id ON race_special_trait(race_id);

-- changeset ramoni73:sub_race
CREATE TABLE sub_race (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    race_id UUID NOT NULL REFERENCES race(id),
    code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(50) UNIQUE NOT NULL,
    description TEXT
);

CREATE INDEX idx_sub_race_race_id ON sub_race(race_id);

-- changeset ramoni73:sub_race_property
CREATE TABLE sub_race_property (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    sub_race_id UUID NOT NULL REFERENCES sub_race(id),
    name VARCHAR(100) NOT NULL,
    value TEXT NOT NULL
);

CREATE INDEX idx_sub_race_property_sub_race_id ON sub_race_property(sub_race_id);