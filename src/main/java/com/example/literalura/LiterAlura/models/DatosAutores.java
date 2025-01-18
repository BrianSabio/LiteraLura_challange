package com.example.literalura.LiterAlura.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutores(
        @JsonAlias("name") String nombreAutor,
        @JsonAlias("birth_year") int anoNacimiento,
        @JsonAlias("death_year") int anoMuerte
) {
}