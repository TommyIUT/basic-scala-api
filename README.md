# Basic Scala API

Une API REST simple construite avec **Scala 3**, **http4s**, **Cats Effect** et **Circe** pour gérer des films et leurs évaluations par les utilisateurs.

## Thème

Cette API permet de :
- Gérer une liste d'**utilisateurs**
- Gérer un catalogue de **films**
- Créer des **évaluations** (ratings) de films par les utilisateurs

En résumé cette API simple est la base d'un système de notation de films en communauté.

## Structure des données

### Utilisateurs
```json
{
  "id": "UUID",
  "pseudo": "String",
  "description": "Option[String]"
}
```

### Films
```json
{
  "id": "UUID",
  "title": "String",
  "releaseDate": "LocalDate (YYYY-MM-DD)",
  "director": "String"
}
```

### Évaluations (Ratings)
```json
{
  "id": "UUID",
  "userId": "UUID",
  "filmId": "UUID",
  "score": "Int (0-10)",
  "comment": "Option[String]"
}
```

## Routes disponibles

### Utilisateurs
- `GET /users` - Récupérer tous les utilisateurs
- `GET /users/:id` - Récupérer un utilisateur par ID
- `POST /users` - Créer un nouvel utilisateur

### Films
- `GET /films` - Récupérer tous les films
- `GET /films/:id` - Récupérer un film par ID
- `POST /films` - Créer un nouveau film

### Évaluations
- `GET /ratings` - Récupérer toutes les évaluations
- `GET /ratings/:userId` - Récupérer les évaluations par utilisateur par ID
- `POST /ratings` - Créer une nouvelle évaluation

## Installation et exécution

### Prérequis
- Java 21+
- Scala 3.7.3
- sbt 1.11.7+

### Lancer le serveur
```bash
sbt run
```

Le serveur démarre sur **http://localhost:8081** avec des données de test pré-chargées via le Seeder. Les données pré-chargées contiennent 2 de mes films préférés je les recommande.

### Lancer les tests unitaires
```bash
sbt test
```