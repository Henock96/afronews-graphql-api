FROM maven:3.9.2-eclipse-temurin-17-alpine as builder

ARG DATABASE_URL

ENV DATABASE_URL=$DATABASE_URL \
    DATABASE_DIRECT_URL=$DATABASE_URL

COPY ./src src/
COPY ./pom.xml pom.xml

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
COPY --from=builder target/*.jar afronews_graphql.jar
EXPOSE 8080
CMD ["java", "-jar", "afronews_graphql.jar"]