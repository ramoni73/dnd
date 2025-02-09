-- liquibase formatted sql

-- changeset ramoni73:basic_uuid_extension
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- changeset ramoni73:basic_types
CREATE TYPE ability_code AS ENUM ('STR', 'DEX', 'CON', 'INT', 'WIS', 'CHA');

-- changeset ramoni73:basic_tables_ability
CREATE TABLE ability (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    code ability_code NOT NULL,
    name VARCHAR(20) UNIQUE NOT NULL,
    description VARCHAR(150) NOT NULL
);

-- changeset ramoni73:basic_tables_skill
CREATE TABLE skill (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    ability_id UUID NOT NULL REFERENCES ability(id),
    code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(50) UNIQUE NOT NULL,
    description VARCHAR(150) NOT NULL
);

CREATE INDEX idx_skill_ability_id ON skill(ability_id);

-- changeset ramoni73:basic_tables_feat
CREATE TYPE feat_category AS ENUM ('ORIGIN', 'GENERAL', 'FIGHTING_STYLE', 'EPIC_BOON');

CREATE TABLE feat (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(50) UNIQUE NOT NULL,
    category feat_category NOT NULL
);

-- changeset ramoni73:basic_tables_character_class
CREATE TABLE character_class (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(50) UNIQUE NOT NULL,
    description VARCHAR(500) NOT NULL
);

CREATE TABLE character_class_property (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    class_id UUID NOT NULL REFERENCES character_class(id),
    name VARCHAR(100) NOT NULL,
    value VARCHAR(500) NOT NULL
);

CREATE INDEX idx_character_class_property_class_id ON character_class_property(class_id);

-- changeset ramoni73:basic_tables_race
CREATE TABLE race (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(50) UNIQUE NOT NULL,
    description TEXT
);

CREATE TABLE race_property (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    race_id UUID NOT NULL REFERENCES race(id),
    name VARCHAR(100) NOT NULL,
    value VARCHAR(500) NOT NULL
);

CREATE INDEX idx_race_property_race_id ON race_property(race_id);

CREATE TABLE sub_race (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    race_id UUID NOT NULL REFERENCES race(id),
    code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(50) UNIQUE NOT NULL,
    description TEXT
);

CREATE INDEX idx_sub_race_race_id ON sub_race(race_id);

CREATE TABLE sub_race_property (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    sub_race_id UUID NOT NULL REFERENCES sub_race(id),
    name VARCHAR(100) NOT NULL,
    value VARCHAR(500) NOT NULL
);

CREATE INDEX idx_sub_race_property_sub_race_id ON sub_race_property(sub_race_id);