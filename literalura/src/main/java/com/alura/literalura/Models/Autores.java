package com.alura.literalura.Models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Autores(
        @JsonAlias("name") String nombre,
        @JsonAlias("") String anhioNacimiento,
        @JsonAlias("") String anhioMuerte
) {
}
