-- liquibase formatted sql

-- changeset ramoni73:V3__background
CREATE TABLE background (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(50) UNIQUE NOT NULL,
    description TEXT
);

-- changeset ramoni73:V3__background_ability
CREATE TABLE background_ability (
    background_id UUID REFERENCES background(id),
    ability_id UUID REFERENCES ability(id),
    PRIMARY KEY (background_id, ability_id)
);

-- changeset ramoni73:V3__background_feat
CREATE TABLE background_feat (
    background_id UUID REFERENCES background(id),
    feat_id UUID REFERENCES feat(id),
    PRIMARY KEY (background_id, feat_id)
);

-- changeset ramoni73:V3__background_skill
CREATE TABLE background_skill (
    background_id UUID NOT NULL REFERENCES background(id),
    skill_id UUID NOT NULL REFERENCES skill(id),
    PRIMARY KEY (background_id, skill_id)
);

-- changeset ramoni73:V3__background_instrument
CREATE TABLE background_instrument (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    background_id UUID NOT NULL REFERENCES background(id),
    value TEXT NOT NULL
);

CREATE INDEX idx_background_instrument_background_id ON background_instrument(background_id);

-- changeset ramoni73:V3__background_equipment
CREATE TABLE background_equipment (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    background_id UUID NOT NULL REFERENCES background(id),
    value TEXT NOT NULL
);

CREATE INDEX idx_background_equipment_background_id ON background_equipment(background_id);
