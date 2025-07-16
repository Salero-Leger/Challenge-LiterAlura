package com.alura.literalura.Models;

import com.alura.literalura.Services.StringListConverter;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "libros")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDB;

    @Column(unique = true)
    private @JsonAlias("title") String titulo;

    @Convert(converter = StringListConverter.class)
    @Column(length = 5000)
    private @JsonAlias("subjects") List<String> temas;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "libros_autores",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private @JsonAlias("authors") List<Autores> autores = new ArrayList<>();

    @Convert(converter = StringListConverter.class)
    @Column(length = 5000)
    private @JsonAlias("summaries") List<String> sinopsis;

    private @JsonAlias("download_count") Integer cantidadDescargas;

    @Convert(converter = StringListConverter.class)
    @Column(length = 5000)
    private @JsonAlias("languages") List<String> lenguajes;

    public Libro() {}

    public Libro(String titulo, List<String> temas, List<String> sinopsis,
                 Integer cantidadDescargas, List<String> lenguajes) {
        this.titulo = titulo;
        this.temas = temas;
        this.sinopsis = sinopsis;
        this.cantidadDescargas = cantidadDescargas;
        this.lenguajes = lenguajes;
        this.autores = new ArrayList<>();
    }

    // Getters y setters
    public Long getIdDB() {
        return idDB;
    }

    public void setIdDB(Long idDB) {
        this.idDB = idDB;
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
        return "Libro{" +
                "id=" + idDB +
                ", titulo='" + titulo + '\'' +
                ", temas=" + temas +
                ", autores=" + autores +
                ", sinopsis=" + sinopsis +
                ", cantidadDescargas=" + cantidadDescargas +
                ", lenguajes=" + lenguajes +
                '}';
    }
}