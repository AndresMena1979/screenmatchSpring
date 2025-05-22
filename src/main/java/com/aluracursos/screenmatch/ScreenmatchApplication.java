package com.aluracursos.screenmatch; // Define el paquete donde se encuentra esta clase. Los paquetes ayudan a organizar el código fuente.

import com.aluracursos.screenmatch.model.DatosEpisodio;
import com.aluracursos.screenmatch.model.DatosSerie;       // Importa la clase DatosSerie, que representa la estructura de datos esperada del JSON recibido.
import com.aluracursos.screenmatch.model.DatosTemporada;
import com.aluracursos.screenmatch.principal.EjemploStreams;
import com.aluracursos.screenmatch.principal.Principal;
import com.aluracursos.screenmatch.service.ConsumoAPI;     // Importa la clase ConsumoAPI, que contiene la lógica para hacer una solicitud HTTP a una API externa.
import com.aluracursos.screenmatch.service.ConvierteDatos; // Importa la clase ConvierteDatos, que convierte un JSON en un objeto Java usando Jackson.
import org.springframework.boot.CommandLineRunner;         // Importa la interfaz CommandLineRunner, que permite ejecutar código justo después de iniciar la aplicación.
import org.springframework.boot.SpringApplication;         // Proporciona métodos estáticos para iniciar una aplicación Spring Boot.
import org.springframework.boot.autoconfigure.SpringBootApplication; // Marca la clase como una aplicación Spring Boot e incluye configuración automática.

import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
// Anotación que indica que esta clase es el punto de entrada de la aplicación Spring Boot.
public class ScreenmatchApplication implements CommandLineRunner {  // Clase principal que arranca la aplicación. Implementa CommandLineRunner para ejecutar lógica al inicio.


	//__________________________
	public static void main(String[] args) {                          // Método main estándar que inicia la aplicación Spring Boot.
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {       // Método que se ejecuta después de que la aplicación arranca. Ideal para ejecutar lógica inicial.

		Principal principal = new Principal();

		principal.muestraElMenu();

	//		EjemploStreams ejemploStreams = new EjemploStreams();
	//		ejemploStreams.muestraEjemplo();

	}


}





