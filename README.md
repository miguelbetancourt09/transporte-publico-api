# API REST — Transporte Público (Ruta y Reserva)

Proyecto académico con **Java 17, Spring Boot 3.5.6, Maven, MySQL, JPA, validación, arquitectura Controller/Service/Repository/Entity y Docker**.

## Modelo

- **Ruta**: `id`, `origen`, `destino`, `horario` (`HH:mm:ss`), `capacidad`.
- **Reserva**: `id`, `pasajero`, `asiento`, `fechaReserva` (`AAAA-MM-DD`), `rutaId`.
- Relación **Ruta (1) → Reserva (N)** mediante clave foránea `reserva.ruta_id`.
- Una reserva está vinculada obligatoriamente a una ruta. No se puede repetir un número de asiento dentro de una misma ruta, ni reservar un asiento fuera de capacidad.
- No se puede eliminar una ruta con reservas registradas. Primero deben eliminarse dichas reservas.

## Abrir en IntelliJ IDEA y ejecutar local con XAMPP

1. Instala **JDK 17** y abre la carpeta del proyecto desde **IntelliJ IDEA → File → Open**. Espera a que Maven cargue el `pom.xml`.
2. Inicia **MySQL** en XAMPP (puerto `3306`). En phpMyAdmin ejecuta: `CREATE DATABASE IF NOT EXISTS transporte_db;`.
3. La configuración predeterminada es MySQL `localhost:3306`, usuario `root` sin contraseña, como suele instalarse en XAMPP. Si tu configuración es distinta, establece las variables de entorno `DB_URL`, `DB_USER` y `DB_PASSWORD`.
4. Ejecuta la clase `TransporteApiApplication`, o desde terminal: `mvn spring-boot:run`.
5. Visita `http://localhost:8080/api/rutas` (debe mostrar `[]` al principio).

> En XAMPP algunas instalaciones incluyen MariaDB en vez de MySQL. Se recomienda MySQL 8; la compatibilidad del motor local debe verificarse si usas MariaDB.

## Alternativa: ejecutar con Docker Compose

Con Docker Desktop abierto, desde la raíz del proyecto:

```bash
docker compose up --build -d
```

La API quedará en `http://localhost:8080` y MySQL estará disponible en tu equipo en `localhost:3307`. No necesitas iniciar MySQL de XAMPP para este método. Para consultar logs: `docker compose logs -f api`. Para detener: `docker compose down`.

## Endpoints

| Método | Ruta | Descripción |
|---|---|---|
| GET | `/api/rutas` | Listar rutas |
| GET | `/api/rutas/{id}` | Obtener ruta |
| POST | `/api/rutas` | Crear ruta |
| PUT | `/api/rutas/{id}` | Modificar ruta |
| DELETE | `/api/rutas/{id}` | Borrar ruta (sin reservas) |
| GET | `/api/rutas/{id}/reservas` | Listar reservas de una ruta |
| GET | `/api/reservas` | Listar reservas |
| GET | `/api/reservas/{id}` | Obtener reserva |
| POST | `/api/reservas` | Crear reserva |
| PUT | `/api/reservas/{id}` | Modificar reserva |
| DELETE | `/api/reservas/{id}` | Borrar reserva |

### Ejemplo: crear una ruta

`POST http://localhost:8080/api/rutas` · Header `Content-Type: application/json`

```json
{"origen":"Armenia","destino":"Pereira","horario":"08:30:00","capacidad":40}
```

### Ejemplo: crear una reserva

`POST http://localhost:8080/api/reservas` · Header `Content-Type: application/json`

```json
{"pasajero":"Ana López","asiento":12,"fechaReserva":"2026-10-23","rutaId":1}
```

La ruta indicada por `rutaId` debe existir. Para ambas entidades, `PUT` recibe el mismo cuerpo que `POST`. Respuestas: `201` creación, `200` consulta/actualización, `204` eliminación, `400` validaciones, `404` no encontrado, `409` conflicto.

## Postman

Importa `postman/Transporte_Publico_API.postman_collection.json`. La variable `baseUrl` inicia en `http://localhost:8080`; cámbiala por el dominio público al desplegar.

## Construir imagen Docker sin Compose

```bash
docker build -t transporte-publico-api .
docker run --rm -p 8080:8080 -e DB_URL="jdbc:mysql://host.docker.internal:3306/transporte_db?useSSL=false&allowPublicKeyRetrieval=true" -e DB_USER=root -e DB_PASSWORD="" transporte-publico-api
```

Para conectarte desde un contenedor a MySQL del anfitrión con `host.docker.internal`, MySQL debe aceptar esa conexión y el usuario tener permisos adecuados. **No publiques MySQL local en Internet.**

## Despliegue en Railway (Dockerfile)

1. Sube esta carpeta a un repositorio de **GitHub** (no subas credenciales ni `.env`).
2. En Railway crea un proyecto, añade **MySQL** y un servicio vinculado al repositorio de GitHub.
3. Configura el servicio API para construir usando el `Dockerfile` incluido.
4. En el servicio de la API añade variables `DB_URL`, `DB_USER`, `DB_PASSWORD` correspondientes a la instancia de MySQL de Railway. `DB_URL` debe ser una URL JDBC, por ejemplo `jdbc:mysql://HOST:3306/NOMBRE_DB?useSSL=false&allowPublicKeyRetrieval=true`, utilizando los valores reales que te proporcione Railway. Si MySQL ofrece una URL de tipo `mysql://`, **no la pongas literalmente** como `DB_URL`: conviértela a `jdbc:mysql://...` y usa por separado el usuario y la contraseña.
5. Despliega y usa **Settings → Networking → Generate Domain** para obtener la dirección HTTPS pública.
6. Prueba `https://TU-DOMINIO/api/rutas`. Añade el enlace real al apartado siguiente.

**Repositorio GitHub:** `PENDIENTE_DE_PUBLICAR`  
**URL de despliegue:** `PENDIENTE_DE_DESPLEGAR`

> Nota: `spring.jpa.hibernate.ddl-auto=update` es práctico para un proyecto académico. Para sistemas productivos conviene usar migraciones (Flyway/Liquibase) y permisos limitados en base de datos.

## Evidencias sugeridas para la entrega

1. Estructura en capas abierta en IntelliJ.
2. Tablas `rutas` y `reservas` en MySQL.
3. Pruebas CRUD en Postman y una reserva relacionada con su ruta.
4. `docker build` y contenedor ejecutándose.
5. Repositorio en GitHub y endpoint GET respondiendo desde Railway.
