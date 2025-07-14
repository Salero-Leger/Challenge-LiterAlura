package com.alura.literalura.Models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Libros {
    private @JsonAlias("id") Long id;
    private @JsonAlias("title") String titulo;
    private @JsonAlias("subjects") List<String> temas;
    private @JsonAlias("authors")List<Autores> autores;
    private @JsonAlias("summaries")List<String> sinopsis;
    private @JsonAlias("download_count")Integer cantidadDescargas;
    private @JsonAlias("languages")List<String> lenguajes;


    public Libros(){}

    public Libros(Libros libros) {
        this.id = libros.getId();
        this.titulo = libros.getTitulo();
        this.temas = libros.getTemas();
        this.autores = libros.getAutores();
        this.sinopsis = libros.getSinopsis();
        this.cantidadDescargas = libros.getCantidadDescargas();
        this.lenguajes = libros.getLenguajes();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<String> getTemas() {
        return temas;
    }

    public void setTemas(List<String> temas) {
        this.temas = temas;
    }

    public List<Autores> getAutores() {
        return autores;
    }

    public void setAutores(List<Autores> autores) {
        this.autores = autores;
    }

    public List<String> getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(List<String> sinopsis) {
        this.sinopsis = sinopsis;
    }

    public Integer getCantidadDescargas() {
        return cantidadDescargas;
    }

    public void setCantidadDescargas(Integer cantidadDescargas) {
        this.cantidadDescargas = cantidadDescargas;
    }

    public List<String> getLenguajes() {
        return lenguajes;
    }

    public void setLenguajes(List<String> lenguajes) {
        this.lenguajes = lenguajes;
    }

    @Override
    public String toString() {
        return "Libros{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", temas=" + temas +
                ", autores=" + autores +
                ", sinopsis=" + sinopsis +
                ", cantidadDescargas=" + cantidadDescargas +
                ", lenguajes=" + lenguajes +
                '}';
    }
}



