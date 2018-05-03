# QRCode generator
Rest API form QRCode generation

## Tech Stack
* Java 8
* Spring Boot
* zxing, for the QRCode generation

## Controllers

### URI(s)
Description | URI | HTTP Method | Payload | Note(s)
----------- | --- | ----------- | ------- | -------
Service health check | /health | GET | |
QRCode generation | /generate | POST | { "name":"name", "url":"url" } |
QRCode generation and return the image | /generateAndGet | POST | { "name":"name", "url":"url" } |
QRCode generation and return the image string Base64 encoded | /generateAndGetString | POST | { "name":"name", "url":"url" } |

## Usage
```
mvn clean spring-boot:run
```

## Test

### Integration
Check newman scripts at src/test/resources

## Further reading
[zxing on github](https://github.com/zxing/zxing)
