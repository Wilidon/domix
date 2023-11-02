FROM maven:3.8.5-openjdk-17-slim
WORKDIR /app
COPY . .
EXPOSE 8090
RUN mvn clean package --no-transfer-progress -DskipTests=true
ENTRYPOINT ["mvn", "spring-boot:run"]
