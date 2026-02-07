-- liquibase formatted sql

-- changeset ramoni73:V3__background
CREATE TABLE background (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(50) UNIQUE NOT NULL,
    description TEXT,
    equipment VARCHAR(500),
    instruments VARCHAR(250),
    feat_id UUID REFERENCES feat(id)
);

-- changeset ramoni73:V3__background_ability
CREATE TABLE background_ability (
    background_id UUID REFERENCES background(id),
    ability_id UUID REFERENCES ability(id),
    PRIMARY KEY (background_id, ability_id)
);

-- changeset ramoni73:V3__background_skill
CREATE TABLE background_skill (
    background_id UUID NOT NULL REFERENCES background(id),
    skill_id UUID NOT NULL REFERENCES skill(id),
    PRIMARY KEY (background_id, skill_id)
);
