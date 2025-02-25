# Используем официальный образ Java 21
FROM openjdk:21-jdk-slim

# Рабочая директория
WORKDIR /app

# Копируем собранный JAR-файл
COPY target/dice-service-1.0.0.jar /app/dice-service.jar

# Открываем порт, на котором работает приложение
EXPOSE 8080

# Команда запуска
ENTRYPOINT ["java", "-jar", "dice-service.jar"]