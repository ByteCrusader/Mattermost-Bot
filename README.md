# mattermost-bot

## Краткое описание

Данный проект призван продемострировать реализацию Бота в [Mattermost](https://mattermost.com).  
Так как проект в процессе разрабоки, на данный момент доступны следующие API:

- `GET /health` - возвращает объект `{"status": "OK"}` в формате `application/json`

## Сборка проекта

Перед запуском выполните сборку проекта `./gradlew clean bootJar`

## Локальный запуск

Для запуска проекта docker контейнера выполните `docker-compose up -d --build`

## Технологический стек

- Java 17
- Spring Boot 3.1.1
- Spring WebFlux (Reactive Arch)
- OpenAPI
- TestContainers

## Полезные ссылки

- [API Mattermost](https://developers.mattermost.com/integrate/getting-started/)
