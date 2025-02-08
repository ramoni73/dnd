# Character Service

### Запуск БД

1. Перейдите в папку `character-service\distribution>`
2. Соберите контейнер PostgreSQL `docker build -t character_service_postgres .`
3. Запустите контейнер PostgreSQL `docker run --name character_service_postgres -p 5432:5432 -d character_service_postgres`
