# spring-microservices

## Prerequisites

### Product Services
1. create database productservices; Execute this to create productservices database in you mysql
2. create application.properties with the input below

spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/productservices
spring.datasource.username=<<YOUR_USERNAME>>
spring.datasource.password=<<YOUR_PASSWORD>>

spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.hibernate.ddl-auto=create-drop

server.port=0