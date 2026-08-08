FROM maven:3.9.9-eclipse-temurin-21

WORKDIR /app

COPY . .

RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

EXPOSE 8080

CMD ["./mvnw", "spring-boot:run"]