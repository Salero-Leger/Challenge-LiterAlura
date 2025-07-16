package com.alura.literalura.Services;

import org.springframework.stereotype.Component;

@Component
public class Principal {
    private ConvierteDatos convierteDatos = new ConvierteDatos();
    private ConsultarAPI consultarAPI = new ConsultarAPI();

    private final Menus menu;

    public Principal(Menus menu) {
        this.menu = menu;
    }


    public void probando(){
        menu.MenuPrincipal();
     /*   String json = consultarAPI.hacerConsultaAPI("https://gutendex.com/books");
        System.out.println(json);
        System.out.println(convierteDatos.obtenerDatos(json, Libros.class));
        Libros libros = (convierteDatos.obtenerDatos(json, Libros.class)) ;
        System.out.println(libros);*/
    }
}
