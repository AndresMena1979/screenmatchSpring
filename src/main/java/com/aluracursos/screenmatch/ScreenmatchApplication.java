package com.aluracursos.screenmatch; // Define el paquete donde se encuentra esta clase. Los paquetes ayudan a organizar el código fuente.

import com.aluracursos.screenmatch.model.DatosSerie;       // Importa la clase DatosSerie, que representa la estructura de datos esperada del JSON recibido.
import com.aluracursos.screenmatch.service.ConsumoAPI;     // Importa la clase ConsumoAPI, que contiene la lógica para hacer una solicitud HTTP a una API externa.
import com.aluracursos.screenmatch.service.ConvierteDatos; // Importa la clase ConvierteDatos, que convierte un JSON en un objeto Java usando Jackson.
import org.springframework.boot.CommandLineRunner;         // Importa la interfaz CommandLineRunner, que permite ejecutar código justo después de iniciar la aplicación.
import org.springframework.boot.SpringApplication;         // Proporciona métodos estáticos para iniciar una aplicación Spring Boot.
import org.springframework.boot.autoconfigure.SpringBootApplication; // Marca la clase como una aplicación Spring Boot e incluye configuración automática.



@SpringBootApplication                                              // Anotación que indica que esta clase es el punto de entrada de la aplicación Spring Boot.
public class ScreenmatchApplication implements CommandLineRunner {  // Clase principal que arranca la aplicación. Implementa CommandLineRunner para ejecutar lógica al inicio.



//__________________________
	public static void main(String[] args) {                          // Método main estándar que inicia la aplicación Spring Boot.
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {       // Método que se ejecuta después de que la aplicación arranca. Ideal para ejecutar lógica inicial.

var consumoAPI = new ConsumoAPI();                           // Se crea una instancia de la clase que realiza solicitudes HTTP.
var json = consumoAPI.obtenerDatos("http://www.omdbapi.com/?t=game+of+thrones&apikey=464cb2ce");   // Llama a la API OMDb y obtiene una respuesta en formato JSON con los datos de la serie "Game of Thrones".
	//	System.out.println(json);

 ConvierteDatos conversor = new ConvierteDatos();  // Se crea una instancia del conversor de datos JSON a objetos Java.

		var datos = conversor.obtenerDatos(json, DatosSerie.class);  // Convierte el JSON obtenido en una instancia de DatosSerie usando el método obtenerDatos.


		System.out.println(datos);  // Imprime por consola el objeto DatosSerie, que contiene los datos de la serie convertidos desde el JSON.


	}

}





