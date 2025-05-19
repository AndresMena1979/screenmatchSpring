package com.aluracursos.screenmatch.service; // Define el paquete en el que se encuentra esta clase (service), que agrupa servicios o lógica de negocio.

import java.io.IOException;        // Importa la clase IOException, necesaria para manejar errores de entrada/salida.
import java.net.URI;               // Importa la clase URI, que representa una URL para construir la solicitud HTTP.
import java.net.http.HttpClient;   //Importa HttpClient, que se usa para enviar solicitudes HTTP.
import java.net.http.HttpRequest;  //Importa HttpRequest, que representa una solicitud HTTP que será enviada.
import java.net.http.HttpResponse; // Importa HttpResponse, que representa la respuesta recibida tras una solicitud HTTP.

public class ConsumoAPI {         //Define la clase pública ConsumoAPI, que encapsula la lógica para hacer peticiones HTTP.


        public String obtenerDatos(String url) {            // Método público que recibe una URL como parámetro y devuelve una cadena (String) con la respuesta JSON.
            HttpClient client = HttpClient.newHttpClient(); // Crea una instancia del cliente HTTP que usará para enviar la solicitud.
            HttpRequest request = HttpRequest.newBuilder()  // Comienza la construcción de una nueva solicitud HTTP.
                    .uri(URI.create(url))                   // Especifica la URL (convertida a URI) como destino de la solicitud.
                    .build();                               // Finaliza la construcción del objeto HttpRequest.
            HttpResponse<String> response = null;           // Declara una variable que almacenará la respuesta HTTP como texto (String), inicializada en null.
            try {                                           // Inicia un bloque try para manejar posibles excepciones durante el envío de la solicitud.
                response = client                           // Envía la solicitud y recibe la respuesta. La respuesta será procesada como una cadena (String).
                        .send(request, HttpResponse.BodyHandlers.ofString());
            } catch (IOException e) {                       // Captura errores de entrada/salida (por ejemplo, si no se puede acceder al servidor).
                throw new RuntimeException(e);              // Lanza una excepción de tipo RuntimeException si ocurre un error de I/O.
            } catch (InterruptedException e) {              // Captura interrupciones si el hilo fue interrumpido mientras esperaba la respuesta.
                throw new RuntimeException(e);              // También lanza una excepción si la solicitud fue interrumpida.
            }

            String json = response.body();                  // Extrae el cuerpo de la respuesta HTTP (que contiene el JSON como texto plano).
            return json;                                    // Devuelve la cadena JSON al que haya llamado este método.
        }
    }

