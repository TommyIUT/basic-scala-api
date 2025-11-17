# Basic Scala API

Une API REST simple construite avec **Scala 3**, **http4s**, **Cats Effect** et **Circe** pour gérer des films et leurs évaluations. 

## Thème

Cette API permet de :
- Gérer une liste d'**utilisateurs**
- Consulter un catalogue de **films**
- Créer et consulter des **évaluations** (ratings) de films par les utilisateurs

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

Le serveur démarre sur **http://localhost:8081** avec des données de test pré-chargées via le Seeder.

### Lancer les tests unitaires
```bash
sbt test
```

### Exemples avec curl

**GET tous les utilisateurs**
```powershell
curl http://localhost:8081/users
```

**POST un nouvel utilisateur**
```powershell
curl -X POST http://localhost:8081/users `
  -H "Content-Type: application/json" `
  -d '{"pseudo":"alice","description":"Cinéphile"}'
```

**GET tous les films**
```powershell
curl http://localhost:8081/films
```

**POST un nouveau film**
```powershell
curl -X POST http://localhost:8081/films `
  -H "Content-Type: application/json" `
  -d '{"title":"Inception","releaseDate":"2010-07-16","director":"Christopher Nolan"}'
```

**GET une évaluation par ID**
```powershell
curl http://localhost:8081/ratings/<RATING_ID>
```

**POST une évaluation**
```powershell
curl -X POST http://localhost:8081/ratings `
  -H "Content-Type: application/json" `
  -d '{"userId":"<USER_ID>","filmId":"<FILM_ID>","score":9,"comment":"Excellent"}'
```

## Architecture

- **InMemoryStore** : Stockage en mémoire (données perdues au redémarrage)
- **Routes** : Gestion des endpoints HTTP avec http4s DSL
- **Seeder** : Chargement de données de test au démarrage
- **Models** : Case classes pour User, Film, Rating

**Port par défaut** : `8081`
