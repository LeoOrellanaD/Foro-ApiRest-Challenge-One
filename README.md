# Oracle ONE Desafío Foro

Este proyecto es una API REST para gestionar tópicos en un foro. Utiliza Spring Boot, Spring Data JPA, Spring Security, y JWT para la autenticación.

## Requisitos

- Java 17
- Maven
- MySQL

## Configuración

### Base de Datos

Asegúrate de tener una base de datos MySQL configurada y accesible. Debes actualizar las propiedades de conexión en `src/main/resources/application.properties`:

## Funcionalidades

### Listado de Tópicos
- **Ruta:** `GET /topicos`
- **Descripción:** Obtiene una lista de todos los tópicos. Puede utilizar paginación y ordenación por fecha de creación.

### Detalle de Tópico
- **Ruta:** `GET /topicos/{id}`
- **Descripción:** Obtiene los detalles de un tópico específico por su ID.

### Creación de Tópico
- **Ruta:** `POST /topicos`
- **Descripción:** Crea un nuevo tópico.

### Actualización de Tópico
- **Ruta:** `PUT /topicos/{id}`
- **Descripción:** Actualiza los datos de un tópico existente. Puede actualizar el título, mensaje y estado.

### Eliminación de Tópico
- **Ruta:** `DELETE /topicos/{id}`
- **Descripción:** Elimina un tópico específico por su ID.
