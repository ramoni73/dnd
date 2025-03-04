// Создаем базу данных
db = db.getSiblingDB('character_db');

// Создаем пользователя
db.createUser({
  user: "character_user",
  pwd: "character_password",
  roles: [
    { role: "readWrite", db: "character_db" }
  ]
});

// Создаем коллекцию и добавляем начальные данные
db.createCollection("character");