FROM openjdk:8u292-jdk

WORKDIR /usr/src/app

COPY . .

RUN chmod +x ./mvnw
RUN ./mvnw package

EXPOSE 8080

CMD ["java", "-jar", "./target/beer-assignment-0.0.1-SNAPSHOT.jar"]
