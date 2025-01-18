package com.example.literalura.LiterAlura.repository;

import com.example.literalura.LiterAlura.models.Autores;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IAutoresRepository extends JpaRepository<Autores, Long> {
    Autores findByNameIgnoreCase(String nombre);

    List<Autores> findByAnoNacimientoLessThanEqualAndAnoMuerteGreaterThanEqual(int anoInicial, int anoFinal);
}