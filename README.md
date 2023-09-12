# spring-microservices

## Prerequisites

### Product Services
1. create database productservices; Execute this to create productservices database in you mysql
2. create application.properties with the input below

spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver<br />
spring.datasource.url=jdbc:mysql://localhost:3306/productservices<br />
spring.datasource.username=<<YOUR_USERNAME>><br />
spring.datasource.password=<<YOUR_PASSWORD>><br /><br />

spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect<br />
spring.jpa.hibernate.ddl-auto=create-drop<br /><br />

server.port=0