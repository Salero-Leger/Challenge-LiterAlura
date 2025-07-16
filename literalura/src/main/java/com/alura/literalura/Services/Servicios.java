package com.alura.literalura.Services;

import com.alura.literalura.Models.Libro;
import com.alura.literalura.Models.Autores;
import com.alura.literalura.Models.Libros;
import com.alura.literalura.Repository.AutoresRepository;
import com.alura.literalura.Repository.LibrosRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class Servicios {

    private final ConsultarAPI consultarAPI = new ConsultarAPI();
    private final Scanner teclado = new Scanner(System.in);
    private final ConvierteDatos convierteDatos = new ConvierteDatos();
    private final LibrosRepository LibroRepositorio;
    private final AutoresRepository AutoresRepositorio;

    public Servicios(LibrosRepository repositorio, AutoresRepository autoresRepositorio) {
        this.LibroRepositorio = repositorio;
        this.AutoresRepositorio = autoresRepositorio;
    }

    public String IntroducirDatosTextos() {
        String texto = teclado.nextLine();
        return texto;
    }

    public Integer IntroducirDatosNumericos() {
        Integer entero = teclado.nextInt();
        teclado.nextLine(); // Consumir el salto de línea pendiente
        return entero;
    }

    @Transactional
    public void BuscarLibrosPortitulos(String tituloUsuario) {
        String tituloLibro = "?search=" + tituloUsuario.replace(" ", "%20");

        try {
            String json = consultarAPI.hacerConsultaAPI(tituloLibro);
            Libros resultados = convierteDatos.obtenerDatos(json, Libros.class);

            if (resultados.getResultados() == null || resultados.getResultados().isEmpty()) {
                System.out.println("No se encontró ningún libro con ese título.");
                return;
            }

            Libro libro = resultados.getResultados().get(0);

            // Verificar si el libro ya existe por título
            Optional<Libro> libroExistente = LibroRepositorio.findByTituloIgnoreCase(libro.getTitulo());
            if (libroExistente.isPresent()) {
                System.out.println("El libro ya está registrado en la base de datos.");
                return;
            }

            // Crear un nuevo libro
            Libro nuevoLibro = new Libro();
            nuevoLibro.setTitulo(libro.getTitulo());
            nuevoLibro.setTemas(libro.getTemas());
            nuevoLibro.setSinopsis(libro.getSinopsis());
            nuevoLibro.setCantidadDescargas(libro.getCantidadDescargas());
            nuevoLibro.setLenguajes(libro.getLenguajes());

            // Procesar autores - CLAVE: trabajar con entidades gestionadas
            List<Autores> autoresGestionados = new ArrayList<>();

            if (libro.getAutores() != null && !libro.getAutores().isEmpty()) {
                for (Autores autorJson : libro.getAutores()) {
                    // Buscar si el autor ya existe
                    Optional<Autores> autorExistente = AutoresRepositorio.findByNombre(autorJson.getNombre());

                    if (autorExistente.isPresent()) {
                        // Usar el autor existente (ya gestionado por Hibernate)
                        autoresGestionados.add(autorExistente.get());
                    } else {
                        // Crear un nuevo autor completamente limpio
                        Autores nuevoAutor = new Autores();
                        nuevoAutor.setNombre(autorJson.getNombre());
                        nuevoAutor.setAnhioNacimiento(autorJson.getAnhioNacimiento());
                        nuevoAutor.setAnhioMuerte(autorJson.getAnhioMuerte());

                        // Guardar el nuevo autor PRIMERO
                        Autores autorGuardado = AutoresRepositorio.save(nuevoAutor);
                        autoresGestionados.add(autorGuardado);
                    }
                }
            }

            // Asignar autores al libro
            nuevoLibro.setAutores(autoresGestionados);

            // Guardar el libro (sin cascada, los autores ya están gestionados)
            Libro libroGuardado = LibroRepositorio.save(nuevoLibro);

            System.out.println("\n--- LIBRO REGISTRADO ---");
            mostrarLibro(libroGuardado);

        } catch (Exception e) {
            System.err.println("Error al buscar el libro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void ConsultarLibrosRegistrados() {
        System.out.println("\n--- LIBROS REGISTRADOS ---");
        List<Libro> libros = LibroRepositorio.findAll();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados en la base de datos.");
            return;
        }

        libros.forEach(this::mostrarLibro);
    }

    public void ListaDeAutoresRegistrados() {
        System.out.println("\n--- AUTORES REGISTRADOS ---");
        List<Autores> autores = AutoresRepositorio.findAll();

        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados en la base de datos.");
            return;
        }

        autores.forEach(this::mostrarAutor);
    }

    public void ListaDeAutoresVivosAnhio(String anhioUsuario) {
        System.out.println("\n--- AUTORES VIVOS EN " + anhioUsuario + " ---");
        List<Autores> autores = AutoresRepositorio.findAll();

        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados en la base de datos.");
            return;
        }

        List<Autores> autoresVivos = autores.stream()
                .filter(autor -> {
                    try {
                        int anio = Integer.parseInt(anhioUsuario);
                        int nacimiento = autor.getAnhioNacimiento() != null ?
                                Integer.parseInt(autor.getAnhioNacimiento()) : 0;
                        int muerte = autor.getAnhioMuerte() != null ?
                                Integer.parseInt(autor.getAnhioMuerte()) : Integer.MAX_VALUE;

                        return nacimiento <= anio && anio <= muerte;
                    } catch (NumberFormatException e) {
                        return false;
                    }
                })
                .toList();

        if (autoresVivos.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año " + anhioUsuario);
            return;
        }

        autoresVivos.forEach(this::mostrarAutor);
    }

 public void ListaDeLibrosPorIdioma() {
        System.out.println("\n--- IDIOMAS DISPONIBLES ---");
        System.out.println("1. Español (es)");
        System.out.println("2. Inglés (en)");
        System.out.println("3. Francés (fr)");
        System.out.println("4. Portugués (pt)");
        System.out.println("5. Alemán (de)");
        System.out.print("Seleccione un idioma: ");

        Integer opcion = IntroducirDatosNumericos(); // Assuming this method exists and works
        String idioma = switch (opcion) {
            case 1 -> "es";
            case 2 -> "en";
            case 3 -> "fr";
            case 4 -> "pt";
            case 5 -> "de";
            default -> {
                System.out.println("Opción no válida. Por favor, intente de nuevo.");
                yield null;
            }
        };

        if (idioma == null) {
            return;
        }

        System.out.println("\n--- LIBROS EN " + getNombreIdioma(idioma) + " ---");

        List<Libro> librosPorIdioma = LibroRepositorio.buscarLibrosPorIdioma(idioma);

        if (librosPorIdioma.isEmpty()) {
            System.out.println("No hay libros registrados en " + getNombreIdioma(idioma) + ".");
            return;
        }


        librosPorIdioma.forEach(this::mostrarLibro);
    }

    private void mostrarLibro(Libro libro) {
        System.out.println("\n----- LIBRO -----");
        System.out.println("Título: " + libro.getTitulo());
        System.out.println("Autores: " + libro.getAutores().stream()
                .map(Autores::getNombre)
                .reduce((a, b) -> a + ", " + b)
                .orElse("Sin autor"));
        System.out.println("Idiomas: " + (libro.getLenguajes() != null ?
                String.join(", ", libro.getLenguajes()) : "No especificado"));
        System.out.println("Descargas: " + libro.getCantidadDescargas());
        System.out.println("-----------------");
    }

    private void mostrarAutor(Autores autor) {
        System.out.println("\n----- AUTOR -----");
        System.out.println("Nombre: " + autor.getNombre());
        System.out.println("Año de nacimiento: " +
                (autor.getAnhioNacimiento() != null ? autor.getAnhioNacimiento() : "Desconocido"));
        System.out.println("Año de muerte: " +
                (autor.getAnhioMuerte() != null ? autor.getAnhioMuerte() : "Vivo"));
        System.out.println("Libros escritos: " +
                (autor.getLibrosEscritos() != null ? autor.getLibrosEscritos().size() : 0));
        System.out.println("----------------");
    }

    private String getNombreIdioma(String codigoIdioma) {
        return switch (codigoIdioma) {
            case "es" -> "Español";
            case "en" -> "Inglés";
            case "fr" -> "Francés";
            case "pt" -> "Portugués";
            case "de" -> "Alemán";
            default -> codigoIdioma;
        };
    }
}