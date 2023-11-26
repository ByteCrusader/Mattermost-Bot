# mattermost-bot

## Краткое описание

Данный проект призван продемонстрировать реализацию Бота в [Mattermost](https://mattermost.com).

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
