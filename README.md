# Minimal example

see issue https://github.com/spring-projects/spring-data-commons/issues/2503

```
docker-compose up
```
Then go these to browser URIs in the following order:
```
GET localhost:8080 // => []
GET localhost:8080/x/add // => [{"id":1,"name":"x"}]
GET localhost:8080 // => [{"id":1,"name":"x"}]
GET localhost:8080/x/delete // => "ok"
GET localhost:8080 // => [{"id":1,"name":"x"}]
```

Note that the delete is not executed.

Then add `suspend` to the delete method in the repository, restart the backend and do:
```
GET localhost:8080/x/delete // => "ok"
GET localhost:8080 // => []
```
