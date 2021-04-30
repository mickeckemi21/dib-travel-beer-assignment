FROM openjdk:8u292-jdk

WORKDIR /usr/src/app

COPY . .

RUN chmod +x ./mvnw
RUN ./mvnw package

ENV PORT=8080

EXPOSE $PORT

CMD ["sh", "-c", "java -jar ./target/beer-assignment-0.0.1-SNAPSHOT.jar --server.port=$PORT"]
