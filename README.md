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


##Liste des json pour les requêtes dans swagger :
1.	Json pour swagger :
Rubrique : message
•	PUT /api/message{id} : 
•	Id : 2
•	Json :
{
  "id": 2,
  "message": "Bonjour, ceci est un message de test.",
  "createdAt": "2024-12-01T10:00:41.277Z",
  "updatedAt": "2024-12-01T10:00:41.277Z",
  "rental": {
    "id": 2,
    "name": "Appartement Paris",
    "surface": 75,
    "price": 1200,
    "picture": "https://exemple.com/images/appartement_paris.jpg",
    "description": "Bel appartement situé au centre de Paris, proche des commodités.",
    "createdAt": "2024-12-01T10:00:41.277Z",
    "updatedAt": "2024-12-01T10:00:41.277Z"
  }
}

•	POST /api/message :
•	Json

{
  "id": 1,
  "message": "Bienvenue dans notre somptueux chalet montagnard, parfait pour une escapade relaxante.",
  "createdAt": "2024-12-01T10:05:52.173Z",
  "updatedAt": "2024-12-01T10:05:52.173Z",
  "rental": {
    "id": 3,
    "name": "Chalet des Alpes",
    "surface": 150,
    "price": 3000,
    "picture": "https://exemple.com/images/chalet_alpes.jpg",
    "description": "Luxueux chalet avec vue imprenable sur les montagnes, équipé d'un spa et d'une cheminée.",
    "createdAt": "2024-12-01T10:05:52.173Z",
    "updatedAt": "2024-12-01T10:05:52.173Z"
  }
}




Rubrique Utilisateurs :
•	PUT /api/user/{id}
•	Id 2
•	Json
{
  "id": 2,
  "username": "admin",
  "email": "traveller123@example.com",
  "password": "admin1",
  "role": [
    {
      "id": 1,
      "name": "ADMIN"
    }
  ],
  "createdAt": "2024-12-01T10:14:16.439Z",
  "updatedAt": "2024-12-01T10:14:16.439Z",
  "rentals": [
    {
      "id": 1,
      "name": "Bel appartement de 2 chambres.",
      "surface": 55.5,
      "price": 750.0,
      "picture": "https://example.com/pictures/rental1.jpg",
      "description": "Bel appartement de 2 chambres.",
      "createdAt": "2024-12-01T10:14:16.439Z",
      "updatedAt": "2024-12-01T10:14:16.439Z"
    }
  ],
  "messages": [
    {
      "id": 1,
      "message": "Séjour incroyable, nous reviendrons certainement l'année prochaine !",
      "createdAt": "2024-12-01T10:14:16.439Z",
      "updatedAt": "2024-12-01T10:14:16.439Z",
      "rental": {
        "id": 1,
        "name": "Bel appartement de 2 chambres.",
        "surface": 55.5,
        "price": 750.0,
        "picture": "https://example.com/pictures/rental1.jpg",
        "description": "Bel appartement de 2 chambres.",
        "createdAt": "2024-12-01T10:14:16.439Z",
        "updatedAt": "2024-12-01T10:14:16.439Z"
      }}
  ]
}

•	POST/api/user
•	Json
{
  "username": "yourusername",
  "email": "youremail@example.com",
  "password": "yourpassword",
  "role": [
    {
      "id": 1,
      "name": "ADMIN"
    }
  ],
  "createdAt": "2024-12-01T10:55:11.579Z",
  "updatedAt": "2024-12-01T10:55:11.579Z",
  "rentals": [
    {
      "id": 1,
      "name": "Location 1",
      "surface": 55.5,
      "price": 750.0,
      "picture": "https://example.com/pictures/rental1.jpg",
      "description": "Bel appartement de 2 chambres.",
      "createdAt": "2024-12-01T10:55:11.579Z",
      "updatedAt": "2024-12-01T10:55:11.579Z"
    }
  ],
  "messages": [
    {
      "id": 1,
      "message": "Séjour incroyable, nous reviendrons certainement l'année prochaine !",
      "createdAt": "2024-12-01T10:55:11.579Z",
      "updatedAt": "2024-12-01T10:55:11.579Z",
      "rental": {
        "id": 1,
        "name": "Location 1",
        "surface": 55.5,
        "price": 750.0,
        "picture": "https://example.com/pictures/rental1.jpg",
        "description": "Bel appartement de 2 chambres.",
        "createdAt": "2024-12-01T10:55:11.579Z",
        "updatedAt": "2024-12-01T10:55:11.579Z"
      }
    }
  ]
}

Rubrique Locations :
•	PUT /api/rental/{id}
•	Id : 2
•	Json
{
  "id": 0,
  "name": "location mise à jour",
  "surface": 75,
  "price": 950,
  "picture": "https://example.com/pictures/updatedrental.jpg",
  "description": "Description mise à jour pour la location.",
  "createdAt": "2024-12-01T11:56:58.583Z",
  "updatedAt": "2024-12-01T11:56:58.583Z"
}

•	POST /api/rental
•	Json
{
  "id": 0,
  "name": "Nouvelle location",
  "surface": 60,
  "price": 800,
  "picture": "https://example.com/pictures/newrental.jpg",
  "description": "Une nouvelle description pour la location.",
  "createdAt": "2024-12-01T11:56:58.583Z",
  "updatedAt": "2024-12-01T11:56:58.583Z"
}


•	POST /api/role 
•	Json
{
  "id": 3,
  "name": "MODERATOR"
}
•	Faite un get pour vérifier ensuite que le MODERATOR est bien ajouter


•	PUT /api/role/{id}
•	Id : 3
•	Json
{
  "id": 3,
  "name": "Modification MODERATOR"
}




