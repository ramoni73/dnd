-- liquibase formatted sql

-- changeset ramoni73:basic_data_ability
INSERT INTO ability (code, name, description) VALUES
('STR', 'Сила', 'Физическая мощь'),
('DEX', 'Ловкость', 'Проворство, рефлексы и баланс'),
('CON', 'Телосложение', 'Здоровье и выносливость'),
('INT', 'Интеллект', 'Логика и память'),
('WIS', 'Мудрость', 'Наблюдательность и умственная стойкость'),
('CHA', 'Харизма', 'Уверенность, самообладание и обаяние');

-- changeset ramoni73:basic_data_skill
INSERT INTO skill (ability_id, code, name, description) VALUES
((SELECT id FROM ability WHERE code = 'STR'), 'Athletics', 'Атлетика', 'Прыгнуть дальше обычного, остаться на плаву в бурной воде или сломать что-то.'),
((SELECT id FROM ability WHERE code = 'DEX'), 'Acrobatics', 'Акробатика', 'Удержаться на ногах в сложной ситуации или выполнить акробатический трюк.'),
((SELECT id FROM ability WHERE code = 'DEX'), 'Sleight_of_Hand', 'Ловкость рук', 'Обыскать карман, спрятать предмет в руке или выполнить фокус.'),
((SELECT id FROM ability WHERE code = 'DEX'), 'Stealth', 'Скрытность', 'Остаться незамеченным, передвигаясь тихо и прячась за объектами.'),
((SELECT id FROM ability WHERE code = 'INT'), 'Arcana', 'Аркана', 'Вспомнить знания о заклинаниях, магических предметах и планах бытия.'),
((SELECT id FROM ability WHERE code = 'INT'), 'History', 'История', 'Вспомнить знания об исторических событиях, людях, нациях и культурах.'),
((SELECT id FROM ability WHERE code = 'INT'), 'Investigation', 'Анализ', 'Найти скрытую информацию в книгах или понять, как что-то работает.'),
((SELECT id FROM ability WHERE code = 'INT'), 'Nature', 'Природа', 'Вспомнить знания о местности, растениях, животных и погоде.'),
((SELECT id FROM ability WHERE code = 'INT'), 'Religion', 'Религия', 'Вспомнить знания о богах, религиозных ритуалах и священных символах.'),
((SELECT id FROM ability WHERE code = 'WIS'), 'Animal_Handling', 'Уход за животными', 'Успокоить или дрессировать животное, или заставить его вести себя определённым образом.'),
((SELECT id FROM ability WHERE code = 'WIS'), 'Insight', 'Проницательность', 'Определить настроение и намерения человека.'),
((SELECT id FROM ability WHERE code = 'WIS'), 'Medicine', 'Медицина', 'Диагностировать болезнь или определить, что убило недавно погибшего.'),
((SELECT id FROM ability WHERE code = 'WIS'), 'Perception', 'Внимательность', 'С использованием комбинации чувств заметить что-то, что легко пропустить.'),
((SELECT id FROM ability WHERE code = 'WIS'), 'Survival', 'Выживание', 'Идти по следам, собирать съедобные растения, найти тропу или избежать природных опасностей.'),
((SELECT id FROM ability WHERE code = 'CHA'), 'Deception', 'Обман', 'Убедительно солгать или убедительно носить маскировку.'),
((SELECT id FROM ability WHERE code = 'CHA'), 'Intimidation', 'Запугивание', 'Внушить трепет или заставить кого-то сделать то, что вам нужно.'),
((SELECT id FROM ability WHERE code = 'CHA'), 'Performance', 'Выступление', 'Выступить, рассказать историю, исполнить музыку или станцевать.'),
((SELECT id FROM ability WHERE code = 'CHA'), 'Persuasion', 'Убеждение', 'Честно и вежливо убедить кого-то в чём-то.');

-- changeset ramoni73:basic_data_character_class
INSERT INTO character_class (code, name, description) VALUES
('Bard', 'Бард', 'Читайте заклинания, которые вдохновляют и исцеляют союзников или очаровывают врагов. Затем присоединитесь к Коллегии...'),
('Barbarian', 'Варвар', 'Врывайтесь в бой, охваченные Яростью. Затем следуйте Пути...'),
('Fighter', 'Воин', 'Овладейте всеми видами оружия и доспехов. Затем воплотитесь в...'),
('Wizard', 'Волшебник', 'Изучайте арканную магию и овладевайте заклинаниями для любых целей. Затем станьте...'),
('Druid', 'Друид', 'Управляйте магией природы, исцеляйте, принимайте звериные облики и контролируйте стихии. Затем присоединитесь к Кругу...'),
('Cleric', 'Жрец', 'Призывайте божественную магию для исцеления, укрепления и сокрушения. Затем овладейте Доменом...'),
('Warlock', 'Колдун', 'Накладывайте заклинания, основанные на оккультных знаниях. Затем заключите пакт с покровителем...'),
('Monk', 'Монах', 'Бросайтесь в ближний бой и выходите из него, нанося быстрые и мощные удары. Затем станьте Мастером...'),
('Paladin', 'Паладин', 'Разите врагов и защищайте союзников с помощью божественной и боевой мощи. Затем свяжите себя клятвой...'),
('Rogue', 'Плут', 'Наносите смертоносные скрытые атаки, избегая опасности с помощью скрытности. Затем воплотите в себе...'),
('Ranger', 'Следопыт', 'Объедините боевые навыки, природную магию и умения выживания. Затем воплотите в себе...'),
('Sorcerer', 'Чародей', 'Используйте врожденную магию, формируя энергию по своему желанию. Затем направьте...');
