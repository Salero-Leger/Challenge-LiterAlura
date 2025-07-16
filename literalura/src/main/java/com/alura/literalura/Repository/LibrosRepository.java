package com.alura.literalura.Repository;

import com.alura.literalura.Models.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibrosRepository extends JpaRepository<Libro, Long> {

    // Buscar libro por título exacto
    Optional<Libro> findByTituloIgnoreCase(String titulo);

    //Buscar por codigo de idioma
    @Query("SELECT l FROM Libro l WHERE LOWER(l.lenguajes) LIKE LOWER(CONCAT('%', :idioma, '%'))")
    List<Libro> buscarLibrosPorIdioma(@org.springframework.data.repository.query.Param("idioma") String idioma);
}