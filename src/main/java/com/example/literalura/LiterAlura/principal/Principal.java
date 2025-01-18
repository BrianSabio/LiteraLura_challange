package com.example.literalura.LiterAlura.principal;

import com.example.literalura.LiterAlura.models.*;
import com.example.literalura.LiterAlura.repository.IAutoresRepository;
import com.example.literalura.LiterAlura.repository.ILibrosRepository;
import com.example.literalura.LiterAlura.service.ConsumoApi;
import com.example.literalura.LiterAlura.service.ConvierteDatos;

import java.util.*;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConvierteDatos conversor = new ConvierteDatos();
    private final static String URL_BASE = "https://gutendex.com/books/?search=";

    private IAutoresRepository autoresRepository;
    private ILibrosRepository librosRepository;

    public Principal(IAutoresRepository autoresRepository, ILibrosRepository librosRepository) {
        this.autoresRepository = autoresRepository;
        this.librosRepository = librosRepository;
    }

    public void muestraElMenu () {
        var opcion = -1;
        System.out.println("\n     Bienvenido\nSeleccione una opción:");
        while (opcion != 0) {
            var menu = """
                    \n
                    1 - Buscar libro por título
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma

                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    agregarLibros();
                    break;
                case 2:
                    librosRegistrados();
                    break;
                case 3:
                    autoresRegistrados();
                    break;
                case 4:
                    autoresPorAno();
                    break;
                case 5:
                    listarPorIdioma();
                    break;
                case 0:
                    System.out.println("Finalizando aplicación...");
                    break;

                default:
                    System.out.println("Opción no válida, intenta de nuevo");
            }

        }
    }

    private Datos getDatosLibros() {
        var nombreLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "+"));
        Datos datosLibros = conversor.obtenerDatos(json, Datos.class);
        return datosLibros;
    }

    private Libros crearLibro(DatosLibros datosLibros, Autores autor) {
        if (autor != null) {
            return new Libros(datosLibros, autor);
        } else {
            System.out.println("El autor es null, no se puede crear el libro");
            return null;
        }
    }

    private  void agregarLibros() {
        System.out.println("Ingresa el libro que deseas buscar: ");
        Datos datos = getDatosLibros();
        if (!datos.resultados().isEmpty()) {
            DatosLibros datosLibro = datos.resultados().get(0);
            DatosAutores datosAutores = datosLibro.autor().get(0);
            Libros libro = null;
            Libros libroRepositorio = librosRepository.findByTitulo(datosLibro.titulo());
            if (libroRepositorio != null) {
                System.out.println("Ya se encuentra en la base de datos");
                System.out.println(libroRepositorio.toString());
            } else {
                Autores autorRepositorio = autoresRepository.findByNameIgnoreCase(datosLibro.autor().get(0).nombreAutor());
                if (autorRepositorio != null) {
                    libro = crearLibro(datosLibro, autorRepositorio);
                    librosRepository.save(libro);
                    System.out.println("*- Libro agregado -*\n");
                    System.out.println(libro);
                } else {
                    Autores autor = new Autores(datosAutores);
                    autor = autoresRepository.save(autor);
                    libro = crearLibro(datosLibro, autor);
                    librosRepository.save(libro);
                    System.out.println("*- Libro agregado -*\n");
                    System.out.println(libro);
                }
            }
        } else {
            System.out.println("El libro no existe, ingresa otro");
        }
    }

    private void librosRegistrados() {
        List<Libros> libros = librosRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados");
            return;
        }
        System.out.println("*- Libros registrados: -*\n");
        libros.stream()
                .sorted(Comparator.comparing(Libros::getTitulo))
                .forEach(System.out::println);
    }

    private void autoresRegistrados() {
        List<Autores> autores = autoresRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados");
            return;
        }
        System.out.println("*- Autores registrados: -*\n");
        autores.stream()
                .sorted(Comparator.comparing(Autores::getName))
                .forEach(System.out::println);
    }

    private void autoresPorAno() {
        System.out.println("Ingresa el año en el que deseas buscar: ");
        var ano = teclado.nextInt();
        teclado.nextLine();
        if(ano < 0) {
            System.out.println("El ano no puede ser negativo, intenta de nuevo");
            return;
        }
        List<Autores> autoresPorAno = autoresRepository.findByAnoNacimientoLessThanEqualAndAnoMuerteGreaterThanEqual(ano, ano);
        if (autoresPorAno.isEmpty()) {
            System.out.println("No hay autores registrados en ese año");
            return;
        }
        System.out.println("*- Autores vivos registrados en el año " + ano + " : -*\n");
        autoresPorAno.stream()
                .sorted(Comparator.comparing(Autores::getName))
                .forEach(System.out::println);
    }

    private void listarPorIdioma() {
        try {
            System.out.println("Ingresa el idioma por el que deseas buscar: ");
            String menu = """
                    
                    es - Espanol
                    en - Inglés
                    fr - Francés
                    pt - Portugués
                    """;
            System.out.println(menu);
            var idioma = teclado.nextLine();
            if (!idioma.equals("es") && !idioma.equals("en") && !idioma.equals("fr") && !idioma.equals("pt")) {
                System.out.println("Idioma no válido, intenta de nuevo");
                return;
            }
            List<Libros> librosPorIdioma = librosRepository.findByLenguajesContaining(idioma);
            if (librosPorIdioma.isEmpty()) {
                System.out.println("No hay libros registrados en ese idioma");
                return;
            }
            System.out.println("*- Libros registrados en el idioma seleccionado: -*\n");
            librosPorIdioma.stream()
                    .sorted(Comparator.comparing(Libros::getTitulo))
                    .forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Ingrese un valor válido");
        }
    }
}




