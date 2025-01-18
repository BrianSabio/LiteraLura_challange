# Desafío Literalura
Este proyecto, llamado "Literalura", fue realizado como parte de la formación en el programa Oracle Next Education (Oracle ONE). Es una aplicación de consola en Java que permite a los usuarios buscar libros por título, listar libros y autores registrados, listar autores vivos en un determinado año, y listar libros por idioma.

## Organización del código
El proyecto utiliza Java como lenguaje de programación, Spring Boot como framework y Maven para la gestión de dependencias. Está organizado en varios paquetes:

- **`com.aluracursos.desafio_literalura.models`**: Contiene las clases de modelo para Datos, Libros, DatosLibros, Autores y DatosAutores.
  - Datos.java: Conecta la lista de libros del JSON y la aplicación.
  - DatosLibros.java: Conecta los datos de los libros del JSON y las variables de la aplicación.
  - DatosAutores.java: Conecta los datos de los autores del JSON y la aplicación.
  - Libros.java: Define los atributos de los libros y crea su tabla en la base de datos. Relación ManyToOne con Autores.
  - Autores.java: Define los atributos de los autores y crea su tabla en la base de datos. Relación OneToMany con Libros.

- **`com.aluracursos.desafio_literalura.repositorio`**: Interfaces de repositorio para interactuar con la base de datos.
  - IAutoresRepository.java: Consultas relacionadas con los autores.
  - ILibrosRepository.java: Consultas relacionadas con los libros.

- **`com.aluracursos.desafio_literalura.service`**: Clases de servicio para consumir la API de Gutendex y convertir los datos.
  - ConsumoApi.java: Obtiene datos de la API y los devuelve como JSON.
  - ConvierteDatos.java: Convierte el JSON en una clase genérica.
  - IConvierteDatos.java: Define el tipo de dato genérico.

- **`com.aluracursos.desafio_literalura.principal`**: Contiene la clase Principal que maneja la lógica principal de la aplicación.
  - Principal.java: Clase principal que gestiona la interacción con el usuario y las funcionalidades del menú.

El proyecto cuenta con:
- Dependencias como Jackson Dataformat, Jackson Core, Spring Data JPA y PostgreSQL Driver.
- Configuración de base de datos en `application.properties`.
   
## Tecnologías utilizadas
- Java SE17
- Maven
- Spring Boot V3.3.0
- PostgreSQL

## Estado del proyecto
Finalizado.


## Autor:
**Brian Sabio**


## Licencias

MIT License















