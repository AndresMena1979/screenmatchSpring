package com.aluracursos.screenmatch.model;                     // Define el paquete donde se encuentra esta clase. En este caso, el paquete model se usa para representar modelos de datos (entidades).

import com.fasterxml.jackson.annotation.JsonAlias;            // Importa la anotación @JsonAlias de Jackson, que permite mapear nombres de campos JSON a atributos de Java, incluso si los nombres no coinciden exactamente.
import com.fasterxml.jackson.annotation.JsonIgnoreProperties; // Importa la anotación @JsonIgnoreProperties, que permite ignorar propiedades desconocidas en el JSON al deserializar.

@JsonIgnoreProperties(ignoreUnknown = true)                   //  Indica que si el JSON tiene propiedades que no están definidas en esta clase, deben ser ignoradas en lugar de lanzar una excepción.

public record DatosSerie(@JsonAlias("Title") String titulo,   // Define un record en Java. Un record es una clase inmutable y concisa que se usa para almacenar datos.
                                                              // Mapea el campo "Title" del JSON al atributo titulo del record.
                        @JsonAlias("totalSeasons") Integer totalDeTemporadas, //Mapea el campo "totalSeasons" del JSON al atributo totalDeTemporadas.
                        @JsonAlias("imdbRating") String evaluacion) {         //Mapea el campo "imdbRating" del JSON al atributo evaluacion.


    /* Esta clase sirve para representar los datos de una serie recibidos en formato JSON desde una API (como OMDb).
    Los nombres de los campos en el JSON pueden ser distintos a los nombres de los atributos del record, por eso se usa @JsonAlias.*/
}
