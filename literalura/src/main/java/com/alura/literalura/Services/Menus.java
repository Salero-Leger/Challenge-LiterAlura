package com.alura.literalura.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Menus {
    private final Servicios servicios;

    @Autowired
    public Menus(Servicios servicios) {
        this.servicios = servicios;
    }

    public void MenuPrincipal() {
        Integer eleccionUsuario = 0;
        do {
            System.out.print("""
                    
                    --- Bienvenido a los registro de la biblioteca Gutenberg ---
                    Opciones disponibles:
                    1. Buscar Libros por títulos
                    2. Consultar Libros ya registrados
                    3. Lista de Autores ya registrados
                    4. Lista de Autores vivos en un determinado año
                    5. Lista de Libros por idioma
                    6. Salir
                    
                    Ingrese una opción:""");

            eleccionUsuario = servicios.IntroducirDatosNumericos();

            switch (eleccionUsuario) {
                case 1:
                    System.out.print("Introduzca el título del libro a buscar: ");
                    String tituloUsuario = servicios.IntroducirDatosTextos();
                    servicios.BuscarLibrosPortitulos(tituloUsuario);
                    break;
                case 2:
                    servicios.ConsultarLibrosRegistrados();
                    break;
                case 3:
                    servicios.ListaDeAutoresRegistrados();
                    break;
                case 4:
                    System.out.print("Introduzca el año para buscar autores vivos: ");
                    String anhioUsuario = servicios.IntroducirDatosTextos();
                    servicios.ListaDeAutoresVivosAnhio(anhioUsuario);
                    break;
                case 5:
                    servicios.ListaDeLibrosPorIdioma();
                    break;
                case 6:
                    System.out.println("\n¡Vuelva pronto!\n");
                    break;
                default:
                    System.out.println("\n¡Seleccione una opción válida!\n");
            }
        } while (eleccionUsuario != 6);
    }
}