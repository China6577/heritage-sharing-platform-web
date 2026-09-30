FROM alpine:3.22 AS build
RUN apk add --no-cache openjdk21-jdk maven
WORKDIR /build
COPY pom.xml .
RUN mvn -B -ntp dependency:go-offline
COPY src ./src
RUN mvn -B -ntp -DskipTests package

FROM alpine:3.22
RUN apk add --no-cache openjdk21-jre-headless
WORKDIR /app
COPY --from=build /build/target/*.jar app.jar
RUN mkdir -p /app/uploads
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
