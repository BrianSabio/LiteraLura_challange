package com.example.literalura.LiterAlura.repository;

import com.example.literalura.LiterAlura.models.Libros;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ILibrosRepository extends JpaRepository<Libros, Long> {
    Libros findByTitulo(String titulo);

    List<Libros> findByLenguajesContaining(String lenguaje);
}