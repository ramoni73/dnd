-- liquibase formatted sql

-- changeset ramoni73:1
-- Таблицы-справочники
CREATE TABLE races (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    description TEXT
);

CREATE TABLE subraces (
    id SERIAL PRIMARY KEY,
    race_id INT NOT NULL REFERENCES races(id),
    name VARCHAR(50) NOT NULL,
    description TEXT,
    UNIQUE(race_id, name)
);

CREATE TABLE classes (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    hit_dice VARCHAR(10) NOT NULL
);

CREATE TABLE backgrounds (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    description TEXT
);

-- Характеристики (Core Abilities)
CREATE TABLE abilities (
    id SERIAL PRIMARY KEY,
    name VARCHAR(20) UNIQUE NOT NULL,
    short_name VARCHAR(3) UNIQUE NOT NULL  -- STR, DEX и т.д.
);

INSERT INTO abilities (name, short_name) VALUES
('Сила', 'STR'),
('Ловкость', 'DEX'),
('Телосложение', 'CON'),
('Интеллект', 'INT'),
('Мудрость', 'WIS'),
('Харизма', 'CHA');

-- Навыки (Skills)
CREATE TABLE skills (
    id SERIAL PRIMARY KEY,
    ability_id INT NOT NULL REFERENCES abilities(id),
    name VARCHAR(50) UNIQUE NOT NULL,
    description TEXT
);

INSERT INTO skills (ability_id, name) VALUES
(1, 'Атлетика'),
(2, 'Акробатика'),
(2, 'Ловкость рук'),
(2, 'Скрытность'),
(4, 'Магия'),
(4, 'История'),
(4, 'Расследование'),
(4, 'Природа'),
(4, 'Религия'),
(5, 'Обращение с животными'),
(5, 'Проницательность'),
(5, 'Медицина'),
(5, 'Восприятие'),
(5, 'Выживание'),
(6, 'Обман'),
(6, 'Запугивание'),
(6, 'Выступление'),
(6, 'Убеждение');

-- Основная таблица персонажей
CREATE TABLE characters (
    id UUID PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    race_id INT NOT NULL REFERENCES races(id),
    subrace_id INT REFERENCES subraces(id),
    class_id INT NOT NULL REFERENCES classes(id),
    background_id INT NOT NULL REFERENCES backgrounds(id),
    level INT NOT NULL CHECK (level >= 1),
    experience INT NOT NULL CHECK (experience >= 0)
);

-- Базовые характеристики персонажа
CREATE TABLE character_abilities (
    character_id UUID REFERENCES characters(id),
    ability_id INT REFERENCES abilities(id),
    base_value INT NOT NULL CHECK (base_value BETWEEN 1 AND 30),
    PRIMARY KEY (character_id, ability_id)
);

-- Бонусы к характеристикам от разных источников
CREATE TABLE ability_bonus_sources (
    id SERIAL PRIMARY KEY,
    source_type VARCHAR(20) NOT NULL,  -- RACE, SUBRACE, BACKGROUND и т.д.
    source_id INT NOT NULL,  -- ID из соответствующей таблицы
    ability_id INT REFERENCES abilities(id),
    bonus INT NOT NULL,
    CHECK (source_type IN ('RACE', 'SUBRACE', 'BACKGROUND', 'FEAT'))
);

-- Владение навыками
CREATE TABLE character_skills (
    character_id UUID REFERENCES characters(id),
    skill_id INT REFERENCES skills(id),
    proficiency_type VARCHAR(20) NOT NULL CHECK (proficiency_type IN ('NONE', 'PROFICIENCY', 'EXPERTISE')),
    PRIMARY KEY (character_id, skill_id)
);

-- Бонусы к навыкам от разных источников
CREATE TABLE skill_bonus_sources (
    id SERIAL PRIMARY KEY,
    source_type VARCHAR(20) NOT NULL,
    source_id INT NOT NULL,
    skill_id INT REFERENCES skills(id),
    bonus INT NOT NULL
);

-- Индексы
CREATE INDEX idx_ability_bonus_sources ON ability_bonus_sources(source_type, source_id);
CREATE INDEX idx_skill_bonus_sources ON skill_bonus_sources(source_type, source_id);
CREATE INDEX idx_character_abilities ON character_abilities(character_id);
CREATE INDEX idx_character_skills ON character_skills(character_id);