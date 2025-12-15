# dnd

## Запуск окружения

### Подготовка

1. Создайте файл `.env` рядом с файлом `[docker-compose.yml](docker-compose.yml)`.
2. Скопируйте содержимое файла `[env_example](env_example)`.
3. Замените значения переменных на свои

### Запуск

1. Выполните команду `docker-compose up -d`.
2. Проверка работы Kafka:
    1. Создайте топик:
       `docker exec -it kafka kafka-topics --create --topic test-topic --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1`
    2. Отправьте сообщение:
       `docker exec -it kafka kafka-console-producer --topic test-topic --bootstrap-server localhost:9092`
        1. Напишите: `Hello, Kafka!`
    3.
    `docker exec -it kafka kafka-console-consumer --topic test-topic --bootstrap-server localhost:9092 --from-beginning`

### Остановка

1. Выполните команду `docker-compose down` или `docker-compose down -v`, если нужно удалить.

### Генерация секрета

```
# PowerShell (Windows)
$bytes = New-Object byte[] 32
(New-Object Random).NextBytes($bytes)
$b64 = [System.Convert]::ToBase64String($bytes)
$b64url = $b64 -replace '\+', '-' -replace '/', '_' -replace '=', ''
$b64url
```