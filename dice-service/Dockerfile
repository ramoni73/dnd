# Используем официальный образ Java 21
FROM openjdk:21-jdk-slim

# Рабочая директория
WORKDIR /app

# Копируем собранный JAR-файл (предполагается, что он называется dice-service.jar)
COPY target/dice-service-1.0.0.jar /app/dice-service.jar

# Открываем порт, на котором работает приложение (например, 8080)
EXPOSE 8080

# Команда запуска
ENTRYPOINT ["java", "-jar", "dice-service.jar"]