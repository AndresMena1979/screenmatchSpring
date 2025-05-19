package com.aluracursos.screenmatch.service;  // Paquete donde se encuentra la interfaz

public interface IConvierteDatos {           // Definición de la interfaz IConvierteDatos

    <T> T obtenerDatos(String Json, Class <T> clase) ;  // Método genérico que convierte un JSON en un objeto de tipo T
                                                        // Class <T> clase: Este parámetro es una clase de tipo T que se pasa para saber a qué tipo de objeto debe convertirse el JSON.
}
