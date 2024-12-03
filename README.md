# Chatop Project

## Description

Chatop est une application construite avec Spring Boot qui utilise Spring Security pour la gestion de la sécurité. L'application utilise JWT pour l'authentification et intègre Swagger pour la documentation de l'API.

## Configuration des Variables d'Environnement
### Étape 1 : Obtenez la Clé de Déchiffrement
La clé de déchiffrement vous sera fournie séparément par l'administrateur du projet. Assurez-vous de la garder en sécurité, car elle sera nécessaire pour déchiffrer le fichier `.env.encrypted`.
### Étape 2 : Utilisez le Script de Déchiffrement :

1. Dans le terminal, exécutez la commande suivante pour déchiffrer le fichier `.env.encrypted` :

   openssl enc -d -aes-256-cbc -pbkdf2 -in .env.encrypted -out .env -k "VOTRE_CLE_DE_DECHIFFREMENT" -md sha256

### Propriétés Spring

Installation
Pré-requis
Java 17
Docker
Maven
Installation des Dépendances

## Installation de docker sur votre machine :
https://docs.docker.com/get-docker/

# Installation de Docker

## Windows

1. Téléchargez l'installateur de Docker Desktop depuis [Docker Hub](https://www.docker.com/products/docker-desktop).
2. Suivez les instructions de l'installateur pour compléter l'installation.
3. Redémarrez votre ordinateur si nécessaire.
4. Lancez Docker Desktop depuis le menu Démarrer.

## MacOS

1. Téléchargez Docker Desktop pour Mac depuis [Docker Hub](https://www.docker.com/products/docker-desktop).
2. Ouvrez le fichier téléchargé et suivez les instructions pour déplacer Docker dans le dossier Applications.
3. Lancez Docker depuis le dossier Applications.
4. Suivez les instructions à l'écran pour terminer la configuration initiale.

## Linux

1. Suivez les instructions spécifiques à votre distribution Linux sur [la documentation Docker](https://docs.docker.com/engine/install/).

## Vérification

Pour vérifier que Docker est correctement installé, ouvrez un terminal et tapez la commande suivante :
docker --version

Pour lancer une instance MySQL avec Docker, utilisez la commande suivante :
docker run --name mon_mysql -e MYSQL_ROOT_PASSWORD=${DB_PASSWORD} -e MYSQL_USER=${DB_USERNAME} -e MYSQL_PASSWORD=${DB_PASSWORD} -e MYSQL_DATABASE=chatop -p 3306:3306 -d mysql

Remplacement des variables
Remplacez ${DB_PASSWORD} par le mot de passe que vous avez obtenu lors du décryptage du fichier .env.
Remplacez ${DB_USERNAME} par le nom d'utilisateur que vous avez obtenu lors du décryptage du fichier .env.


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
"id": 1,
"name": "string",
"surface": 0,
"price": 0,
"picture": "string",
"description": "string",
"created_at": "2024/12/03",
"updated_at": "2024/12/03",
"proprietaire_id": 1
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




