-- liquibase formatted sql

-- changeset ramoni73:3
-- Заполнение таблицы races (Расы)
INSERT INTO races (name, description) VALUES
('Человек', 'Адаптирующиеся и амбициозные представители человечества'),
('Эльф', 'Долгоживущие магические существа с связью с природой'),
('Дварф', 'Выносливые подземные жители, мастера камня и металла'),
('Полуорк', 'Сильные и выносливые потомки орков');

-- changeset ramoni73:4
-- Заполнение subraces (Подрасы)
INSERT INTO subraces (race_id, name, description) VALUES
((SELECT id FROM races WHERE name = 'Эльф'), 'Высший эльф', 'Эльфы с врожденными магическими способностями'),
((SELECT id FROM races WHERE name = 'Эльф'), 'Лесной эльф', 'Эльфы, связанные с дикой природой'),
((SELECT id FROM races WHERE name = 'Дварф'), 'Холмовой дварф', 'Дварфы с повышенной мудростью'),
((SELECT id FROM races WHERE name = 'Дварф'), 'Горный дварф', 'Дварфы с бонусом к силе');

-- changeset ramoni73:5
-- Заполнение classes (Классы)
INSERT INTO classes (name, hit_dice) VALUES
('Воин', '1d10'),
('Плут', '1d8'),
('Жрец', '1d8'),
('Маг', '1d6'),
('Варвар', '1d12');

-- changeset ramoni73:6
-- Заполнение backgrounds (Происхождения)
INSERT INTO backgrounds (name, description) VALUES
('Народник', 'Вы выросли среди простых людей'),
('Мудрец', 'Вы посвятили жизнь изучению древних знаний'),
('Преступник', 'Вы владеете теневыми навыками улиц'),
('Благородный', 'Вы родились в знатной семье');

-- changeset ramoni73:7
-- Заполнение ability_bonus_sources (Бонусы рас)
INSERT INTO ability_bonus_sources (source_type, source_id, ability_id, bonus) VALUES
('RACE', (SELECT id FROM races WHERE name = 'Человек'), (SELECT id FROM abilities WHERE short_name = 'STR'), 1),
('RACE', (SELECT id FROM races WHERE name = 'Человек'), (SELECT id FROM abilities WHERE short_name = 'DEX'), 1),
('RACE', (SELECT id FROM races WHERE name = 'Эльф'), (SELECT id FROM abilities WHERE short_name = 'DEX'), 2),
('RACE', (SELECT id FROM races WHERE name = 'Дварф'), (SELECT id FROM abilities WHERE short_name = 'CON'), 2),
('SUBRACE', (SELECT id FROM subraces WHERE name = 'Горный дварф'), (SELECT id FROM abilities WHERE short_name = 'STR'), 2);

-- changeset ramoni73:8
-- Добавляем индексы для часто используемых полей
CREATE INDEX idx_races_name ON races(name);
CREATE INDEX idx_classes_name ON classes(name);
CREATE INDEX idx_skills_name ON skills(name);