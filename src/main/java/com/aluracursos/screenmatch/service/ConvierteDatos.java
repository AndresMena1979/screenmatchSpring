package com.aluracursos.screenmatch.service;                  // Define el paquete donde se encuentra la clase. Ayuda a organizar el proyecto por funcionalidad (en este caso, servicios).

import com.fasterxml.jackson.core.JsonProcessingException;    // Importa la excepción que puede lanzar Jackson si ocurre un error al procesar JSON.

import com.fasterxml.jackson.databind.ObjectMapper;           //  Importa la clase ObjectMapper de Jackson, que se usa para convertir entre objetos Java y JSON (en ambas direcciones).

public class ConvierteDatos implements IConvierteDatos {      // Define la clase pública ConvierteDatos, e indica que implementa la interfaz IConvierteDatos, lo cual obliga a implementar su método.

    private ObjectMapper objectMapper = new ObjectMapper();   // Crea una instancia de ObjectMapper, que será usada para convertir JSON a objetos Java.
                                                              // ️ Esta es una instancia reutilizable que realiza el trabajo de deserialización.

    @Override                                                 // Indica que se está sobreescribiendo (implementando) un método definido en la interfaz IConvierteDatos.
    public <T> T obtenerDatos(String Json, Class<T> clase) {  /* Método genérico que:

                                                                 Recibe una cadena JSON.

                                                                 Recibe la clase del tipo que se desea crear (por ejemplo, DatosSerie.class).

                                                                  Devuelve un objeto de tipo T (puede ser cualquier clase que tú definas). */



        try {                                                     // Comienza un bloque try para manejar posibles errores al convertir el JSON.


        return objectMapper.readValue(Json, clase);               /* Usa ObjectMapper para convertir el texto JSON (json) en una instancia de la clase especificada (clase).
➡️                                                                   Por ejemplo: si el JSON representa una serie, y la clase es DatosSerie, se devuelve un objeto de tipo DatosSerie.
                                                                   */

        } catch (JsonProcessingException e) {                       // Captura una excepción en caso de que el JSON esté mal formado o no se pueda convertir.
            throw new RuntimeException(e);                          // Lanza una excepción en tiempo de ejecución si ocurre un error, para que el programa no continúe con datos inválidos.
        }


       /* Se utiliza ObjectMapper de la librería Jackson (comúnmente usada para manejar JSON en Java) para deserializar el JSON en el tipo de clase proporcionado.

        El método readValue convierte el JSON en una instancia de la clase proporcionada (que se pasa a través del parámetro Class<T> clase).*/
    }
}
