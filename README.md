# Chatop Project

## Description

Chatop est une application construite avec Spring Boot qui utilise Spring Security pour la gestion de la sécurité. L'application utilise JWT pour l'authentification et intègre Swagger pour la documentation de l'API.

## Configuration

### Propriétés Spring

```properties
spring.application.name=chatop
spring.datasource.url=jdbc:mysql://localhost:3306/chatop
spring.datasource.username=elvis
spring.datasource.password=1234
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false

security.jwt.token.secret-key=myverysecuresecretkeywhichis256bitslong!!
chatop.app.jwtExpirationMs=86400000

springdoc.swagger-ui.enabled=true
springdoc.swagger-ui.path=/swagger-ui/index.html

Installation
Pré-requis
Java 17
Docker
Maven
Installation des Dépendances

