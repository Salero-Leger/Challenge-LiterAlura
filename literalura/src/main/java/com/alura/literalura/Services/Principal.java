package com.alura.literalura.Services;

import com.alura.literalura.Models.Libro;
import com.alura.literalura.Models.Libros;

public class Principal {
    private ConvierteDatos convierteDatos = new ConvierteDatos();
    private ConsultarAPI consultarAPI = new ConsultarAPI();
    public void probando(){
        String json = consultarAPI.hacerConsultaAPI("https://gutendex.com/books");
        System.out.println(json);
        System.out.println(convierteDatos.obtenerDatos(json, Libros.class));
        Libros libros = (convierteDatos.obtenerDatos(json, Libros.class)) ;
        System.out.println(libros);
    }
}
