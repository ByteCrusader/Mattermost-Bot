FROM eclipse-temurin:17.0.7_7-jdk as builder
COPY build/libs/bricklayer-user.jar /temp/bricklayer-user.jar
RUN java -Djarmode=layertools -jar /temp/bricklayer-user.jar extract --destination /extract/

FROM eclipse-temurin:17.0.7_7-jdk
ARG APP_HOME=/app
WORKDIR $APP_HOME
COPY --from=builder extract/dependencies/ $APP_HOME/
COPY --from=builder extract/application/ $APP_HOME/
COPY --from=builder extract/snapshot-dependencies/ $APP_HOME/
COPY --from=builder extract/spring-boot-loader/ $APP_HOME/

EXPOSE 8080 9000
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
