package com.alura.literalura.Models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Autores {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(unique = true)
        private @JsonAlias("name") String nombre;

        private @JsonAlias("birth_year") String anhioNacimiento;

        private @JsonAlias("death_year") String anhioMuerte;


        @ManyToMany(mappedBy = "autores", fetch = FetchType.EAGER)
        private List<Libro> librosEscritos = new ArrayList<>();

        public Autores() {}

        public Autores(String nombre, String anhioNacimiento, String anhioMuerte) {
                this.nombre = nombre;
                this.anhioNacimiento = anhioNacimiento;
                this.anhioMuerte = anhioMuerte;
                this.librosEscritos = new ArrayList<>();
        }


        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getNombre() {
                return nombre;
        }

        public void setNombre(String nombre) {
                this.nombre = nombre;
        }

        public String getAnhioNacimiento() {
                return anhioNacimiento;
        }

        public void setAnhioNacimiento(String anhioNacimiento) {
                this.anhioNacimiento = anhioNacimiento;
        }

        public String getAnhioMuerte() {
                return anhioMuerte;
        }

        public void setAnhioMuerte(String anhioMuerte) {
                this.anhioMuerte = anhioMuerte;
        }

        public List<Libro> getLibrosEscritos() {
                return librosEscritos;
        }

        public void setLibrosEscritos(List<Libro> librosEscritos) {
                this.librosEscritos = librosEscritos;
        }

        @Override
        public String toString() {
                return "Autores{" +
                        "id=" + id +
                        ", nombre='" + nombre + '\'' +
                        ", anhioNacimiento='" + anhioNacimiento + '\'' +
                        ", anhioMuerte='" + anhioMuerte + '\'' +
                        '}';
        }
}