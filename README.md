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

## Lancer l'application avec docker
## généer l'image docker MysSQL
docker run --name mon_mysql -e MYSQL_ROOT_PASSWORD=1234 -e MYSQL_USER=elvis -e MYSQL_PASSWORD=1234 -e MYSQL_DATABASE=chatop -p 3306:3306 -d mysql

## Installation de docker 
## cloner le projet
depuis vs code ou tous autre ide :
git clone https://github.com/elvis-messiaen/chatop.git
## Installer les dépendances
mvn install
## Lancer l'application
mvn spring-boot:run 

Documentation de l'API
Vous pouvez consulter la documentation de l'API via Swagger à l'adresse suivante :
http://localhost:8080/swagger-ui/swagger-ui/index.html#

Allez dans la section POST /login de Swagger.
Utilisez les informations d'identification suivantes pour vous connecter :
Login: admin1
Mot de passe: admin
Copiez le token JWT obtenu et utilisez-le dans la section Authorize en haut de la page Swagger pour authentifier vos requêtes.
Vous pouvez tester l'ensemble des endpoints de l'API en utilisant le token JWT pour l'authentification



