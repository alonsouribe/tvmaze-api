# tvmaze-api

## Cómo correr

Debes estar en la carpeta raíz del proyecto (donde está `pom.xml`) antes de ejecutar estos comandos. Configura la variable de entorno `MONGODB_URI` con tu cadena de conexión de MongoDB Atlas:

`Edit Configurations > Environment Variables`
```
mongodb+srv://<usuario>:<password>@<cluster>.mongodb.net/<baseDeDatos>?appName=<appName>
```

**Windows (CMD):**
```
mvnw spring-boot:run
```

La API queda en `http://localhost:8080`.

## Endpoints

- `GET /api/shows/search?q={query}` — busca shows
- `GET /api/shows/{id}` — detalle de un show (con cache en Mongo)
- `POST /api/shows/{id}/comments` — guarda un comentario `{ comment, rating }`