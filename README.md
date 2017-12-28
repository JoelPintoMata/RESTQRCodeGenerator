# QRCode generator
Rest API form QRCode generation

## Tech Stack
* Java 8
* Spring Boot
* zxing, for the QRCode generation

## Controllers

### URI(s)
Description | URI | HTTP Method | Payload | Note
----------- | --- | ----------- | ------- | ----
Service health check | http://<host>:<server port>/health | GET | { "name":"name", "url":"url" } |
QRCode generation | http://<host>:<server port>/generate | POST | { "name":"name", "url":"url" } |

## Usage
```
mvn clean spring-boot:run
```

## Further reading
[zxing on github](https://github.com/zxing/zxing)