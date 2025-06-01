# EntrenaSync Workouts Microservice

## Descripción

Este microservicio forma parte del ecosistema **EntrenaSync** y se encarga de la gestión completa de entrenamientos (workouts) del sistema. Proporciona operaciones CRUD (Crear, Leer, Actualizar, Eliminar) para el manejo de entrenamientos, incluyendo detalles de entrenamiento, filtros avanzados, especificaciones de consulta y sistema de caché optimizado.

## Tecnologías Utilizadas

-   **Kotlin** - Lenguaje de programación principal
-   **Spring Boot 3.x** - Framework de aplicación
-   **Spring Web** - Para la creación de API REST
-   **Spring Data JPA** - Integración con base de datos relacional
-   **Spring Cache** - Sistema de caché integrado
-   **PostgreSQL** - Base de datos relacional para persistencia
-   **Jakarta Validation** - Validación de datos de entrada
-   **Gradle (Kotlin)** - Gestión de dependencias y construcción del proyecto
-   **Hibernate** - ORM para mapeo objeto-relacional
-   **Hypersistence Utils** - Manejo de tipos JSON en PostgreSQL

## Características Principales

### Gestión de Entrenamientos

-   ✅ Creación de nuevos entrenamientos con detalles
-   ✅ Consulta de entrenamientos por ID
-   ✅ Listado paginado y filtrado de entrenamientos
-   ✅ Actualización de información de entrenamientos
-   ✅ Eliminación de entrenamientos
-   ✅ Validación completa de datos de entrada
-   ✅ Manejo global de excepciones
-   ✅ Sistema de caché para optimización de rendimiento
-   ✅ Filtros avanzados por múltiples criterios
-   ✅ Especificaciones dinámicas para consultas complejas

### Modelos de Datos

-   **Workout**: Información completa del entrenamiento (nombre, duración, fecha, estado de completado)
-   **WorkoutDetails**: Detalles específicos del entrenamiento (descripción, intensidad, ejercicios, detalles adicionales)

## Estructura del Proyecto

```
src/main/kotlin/entrenasync/workoutsmicroservice/
├── rest/
│   ├── workout/
│   │   ├── controllers/
│   │   │   └── WorkoutController.kt              # Endpoints REST
│   │   ├── dtos/
│   │   │   ├── WorkoutCreateRequest.kt           # DTO para creación
│   │   │   ├── WorkoutUpdateRequest.kt           # DTO para actualización
│   │   │   └── WorkoutResponse.kt                # DTO de respuesta
│   │   ├── models/
│   │   │   └── Workout.kt                        # Entidad principal
│   │   ├── services/
│   │   │   ├── IWorkoutService.kt                # Interfaz del servicio
│   │   │   ├── WorkoutServiceImpl.kt             # Implementación del servicio
│   │   │   └── specifications/
│   │   │       └── WorkoutSpecifications.kt      # Especificaciones JPA
│   │   ├── repositories/
│   │   │   └── IWorkoutRepository.kt             # Repositorio principal
│   │   ├── mappers/
│   │   │   └── WorkoutMapper.kt                  # Transformadores de datos
│   │   └── exceptions/
│   │       ├── WorkoutException.kt               # Excepción base
│   │       └── WorkoutNotFoundException.kt       # Excepción específica
│   └── workoutDetails/
│       ├── dtos/
│       │   ├── WorkoutDetailsCreateRequest.kt    # DTO para creación
│       │   ├── WorkoutDetailsUpdateRequest.kt    # DTO para actualización
│       │   └── WorkoutDetailsResponse.kt         # DTO de respuesta
│       ├── models/
│       │   └── WorkoutDetails.kt                 # Entidad de detalles
│       ├── repositories/
│       │   └── IWorkoutDetailsRepository.kt      # Repositorio de detalles
│       └── mappers/
│           └── WorkoutDetailsMapper.kt           # Transformadores de detalles
└── middleWare/
    └── exceptionHandler/
        └── GlobalExceptionHandler.kt             # Manejo global de errores
```

## API Endpoints

### Workouts

| Método   | Endpoint            | Descripción                               |
| -------- | ------------------- | ----------------------------------------- |
| `GET`    | `/Workouts`         | Obtener entrenamientos filtrados (paginado) |
| `GET`    | `/Workouts/{id}`    | Obtener entrenamiento por ID             |
| `POST`   | `/Workouts`         | Crear nuevo entrenamiento                |
| `PUT`    | `/Workouts/{id}`    | Actualizar entrenamiento existente       |
| `DELETE` | `/Workouts/{id}`    | Eliminar entrenamiento                   |

### Filtros Disponibles

Los entrenamientos pueden filtrarse por:
- **ids**: Lista de IDs específicos
- **name**: Nombre del entrenamiento (búsqueda parcial)
- **trainingDuration**: Duración específica del entrenamiento
- **trainingCompletedDate**: Fecha de finalización
- **completed**: Estado de completado (true/false)

### Paginación y Ordenamiento

- **page**: Número de página (default: 0)
- **size**: Tamaño de página (default: 10)
- **sortBy**: Campo de ordenamiento (default: "id")
- **direction**: Dirección de ordenamiento (ASC/DESC, default: ASC)

## Configuración

### Variables de Entorno

#### Base de Datos (Desarrollo)
-   `DATABASE_HOST`: Host de PostgreSQL (default: workouts-microservice-postgres-db)
-   `DATABASE_PORT`: Puerto de PostgreSQL (default: 5432)
-   `DATABASE_NAME`: Nombre de la base de datos (default: workoutsdb)
-   `DATABASE_USER`: Usuario de PostgreSQL (default: postgres)
-   `DATABASE_PASSWORD`: Contraseña de PostgreSQL (default: postgres)

#### Base de Datos (Producción)
-   `WORKOUTS-HOSTNAME`: Hostname de PostgreSQL en producción
-   `WORKOUTS-USERNAME`: Usuario de PostgreSQL en producción
-   `WORKOUTS-PASSWORD`: Contraseña de PostgreSQL en producción

### Profiles de Spring

-   **dev**: Configuración para desarrollo local
-   **prod**: Configuración para producción

## Soporte

Para soporte técnico o preguntas, contacta con el equipo de desarrollo de EntrenaSync.