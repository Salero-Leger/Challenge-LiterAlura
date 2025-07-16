package com.alura.literalura.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Libros {
    @JsonProperty("results") private List<Libro> resultados;
    public Libros(){

    }

    public List<Libro> getResultados() {
        return resultados;
    }

    public void setResultados(List<Libro> resultados) {
        this.resultados = resultados;
    }

    @Override
    public String toString() {
        return "Libros{" +
                "resultados=" + resultados +
                '}';
    }
}
