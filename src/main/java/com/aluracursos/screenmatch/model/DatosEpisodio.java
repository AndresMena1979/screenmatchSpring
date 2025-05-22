package com.aluracursos.screenmatch.model;                      // Define el paquete donde se encuentra esta clase. En este caso, el paquete model se usa para representar modelos de datos (entidades).

import com.fasterxml.jackson.annotation.JsonAlias;              // Importa la anotación @JsonAlias de Jackson, que permite mapear nombres de campos JSON a atributos de Java, incluso si los nombres no coinciden exactamente.
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;   // Importa la anotación @JsonIgnoreProperties, que permite ignorar propiedades desconocidas en el JSON al deserializar.

@JsonIgnoreProperties(ignoreUnknown = true)                     // Indica que si el JSON tiene propiedades que no están definidas en esta clase, deben ser ignoradas en lugar de lanzar una excepción.

public record DatosEpisodio(@JsonAlias("Title") String titulo,  // Define un record en Java. Un record es una clase inmutable y concisa que se usa para almacenar datos.
                                                                // Mapea el campo "Title" del JSON al atributo titulo del record.

                            @JsonAlias("Episode")  Integer numeroEpisodio,
                            @JsonAlias("imdbRating")  String evaluacion,
                            @JsonAlias("Released")  String fechaDeLanzamiento) {


}
