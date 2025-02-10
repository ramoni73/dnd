-- liquibase formatted sql

-- changeset ramoni73:feat_type
CREATE TYPE feat_category AS ENUM ('ORIGIN', 'GENERAL', 'FIGHTING_STYLE', 'EPIC_BOON');

-- changeset ramoni73:feat
CREATE TABLE feat (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(50) UNIQUE NOT NULL,
    category feat_category NOT NULL
);

-- changeset ramoni73:feat_property
CREATE TABLE feat_property (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    feat_id UUID NOT NULL REFERENCES feat(id),
    name VARCHAR(100) NOT NULL,
    value TEXT NOT NULL
);

CREATE INDEX idx_feat_property_feat_id ON feat_property(feat_id);