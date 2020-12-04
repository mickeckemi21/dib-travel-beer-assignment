# Getting Started with Beer Assignment

### How to run the App
Application can be run by executing the following command:

    mvnw spring-boot:run

Above command will execute Spring's maven plugin and start the application on port 8080.

### Model / Data-transfer objects
There is single DTO which represents a beer in the App:

#### Beer data-transfer object
```java
class BeerDto {
    
    private String name;
    private String description;
    private String internalId; // string representation of java.util.UUID
    private Double averageTemperature;
    
}
```

### Available REST endpoints
Following endpoints with short description are available in the App:

- `GET /beers` - returns a JSON array of available beers
- `GET /beers/{internalId}` - returns a specific beers based on its internal id (internal id is represented as *UUID*)
- `DELETE /beers/{internalId}` - deletes the specified beer from the database
- `GET /beers/fill` - fills database of beers up to 10 elements

### More TODOs

- Unit tests
- E2E tests (e.g. via Postman)
- Improving Error handling (e.g. custom error messages instead of Spring's whiteable pages)
- Using some proven SQL database in production instead of H2 (e.g. Postgres)
- Security to protect endpoints that mutate entries in database
