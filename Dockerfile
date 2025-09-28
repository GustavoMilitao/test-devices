FROM openjdk:21-jdk-slim AS build
WORKDIR /app

# Instala Maven e dependências necessárias
RUN apt-get update && \
    apt-get install -y maven && \
    rm -rf /var/lib/apt/lists/*

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:21-jre-slim
WORKDIR /app
COPY --from=build /app/target/devices-api-0.0.1-SNAPSHOT.jar devices-api.jar

ENTRYPOINT ["java", "-jar", "devices-api.jar"]
