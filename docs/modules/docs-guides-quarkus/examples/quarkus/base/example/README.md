# Base example

Base onecx application example

Build and start project
```shell
mvn clean compile quarkus:dev
```

Create a user
```shell
curl -v -X POST -H "Content-Type: application/json" --data '{"id":"1","name":"a"}' localhost:8080/internal/user
```

Load user
```shell
curl -v localhost:8080/internal/user/1
```