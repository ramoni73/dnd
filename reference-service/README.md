# Reference Service

### Запуск БД

1. Перейдите в папку `reference-service\distribution>`
2. Соберите контейнер PostgreSQL `docker build -t reference_service_postgres .`
3. Запустите контейнер PostgreSQL `docker run --name reference_service_postgres -p 5432:5432 -d reference_service_postgres`

### Сборка

1. Сборка образа `docker build -t ramoni73/reference-service:latest .`
2. Вход в Docker Hub `docker login`
3. Публикация образа `docker push ramoni73/reference-service:latest`
