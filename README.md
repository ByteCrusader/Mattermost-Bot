# mattermost-bot

## Краткое описание

Данный проект призван продемострировать реализацию Бота в [Mattermost](https://mattermost.com).

## API эндпоинты приложения

Так как проект в процессе разрабоки, на данный момент доступны следующие API

### Get health info of app

Возвращает данные о состоянии приложения

```http
  GET /health
```

#### Пример:

Запрос 
`http://arch.homework/health`  

Ответ 
`{"status": "OK"}`  

Формат
`application/json`  

### Get health info of app with rewrite

Возвращает данные о состоянни приложения с рерайтом url

```http
  GET /otusapp/{some_user}/*
```

| Parameter   | Type     | Description                     |
|:------------| :------- |:--------------------------------|
| `some_user` | `string` | **Required**. Some student name |

#### Пример:

Запрос
`http://arch.homework/otusapp/{some_user}/health`  будет перенаправлен на `http://arch.homework/health`  

Ответ
`{"status": "OK"}`

Формат
`application/json`

### Get actuator health info of app

Возвращает данные о состоянии приложения, подготовленные acuator-ом

```http
  GET /actuator/health
```

#### Пример:

Запрос
`http://arch.homework/actuator/health`

Ответ
`{"status": "UP",.....}`

Формат
`application/json`

## Сборка проекта

Перед запуском выполните сборку проекта `./gradlew clean bootJar`

## Сборка образа в Docker

Для сборки образа выполните в корне проекта `docker build -t bytecrusader/mattermost-bot .`  
P.S. для публикации образа `docker push bytecrusader/mattermost-bot`  
P.S.S. необходимо учитывать, что тег в таком случае будет `latest`  

## Локальный запуск

Для запуска проекта docker контейнера выполните `docker-compose up -d --build`

## Запуск в k8s

Для запуска в k8s кластере необходимо выполнить `kubectl apply -f .` в папке `deploy`

## Очистка объектов k8s

Для очистки созданных в k8s сущностей выполнить 
```
kubectl delete deployment mattermost-bot
kubectl delete ingress mattermost-bot
kubectl delete service mattermost-bot
kubectl delete configmap mattermost-bot-config
```

## Технологический стек

- Java 17
- Spring Boot 3.1.1
- Spring WebFlux (Reactive Arch)
- OpenAPI
- TestContainers

## Полезные ссылки

- [API Mattermost](https://developers.mattermost.com/integrate/getting-started/)
