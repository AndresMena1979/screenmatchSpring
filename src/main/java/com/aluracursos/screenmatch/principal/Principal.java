package com.aluracursos.screenmatch.principal;

import com.aluracursos.screenmatch.model.DatosEpisodio;
import com.aluracursos.screenmatch.model.DatosSerie;
import com.aluracursos.screenmatch.model.DatosTemporada;
import com.aluracursos.screenmatch.model.Episodio;
import com.aluracursos.screenmatch.service.ConsumoAPI;
import com.aluracursos.screenmatch.service.ConvierteDatos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    //Busca los datos generales de la serie

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = "http://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=464cb2ce";

    private ConvierteDatos conversor = new ConvierteDatos();



    public void muestraElMenu(){
        System.out.println("Por favor escribe el nombre de la serie que deseas buscar");
        var nombreSerie = teclado.nextLine();

        var json = consumoAPI.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + API_KEY);   /* Llama a la API OMDb y obtiene una respuesta en formato JSON con los datos de
                                                                                                                          la serie "Game of Thrones".*/

        var datos = conversor.obtenerDatos(json,DatosSerie.class);

       // System.out.println(datos);

// Busca los datos de todas las temporadas

        List<DatosTemporada> temporadas = new ArrayList<>();
        for (int i = 1; i < datos.totalDeTemporadas(); i++) {

            json = consumoAPI.obtenerDatos(URL_BASE+nombreSerie.replace(" ", "+")+"&Season="+i+ API_KEY);   /* Llama a la API OMDb y obtiene una respuesta en formato JSON con
                                                                                                                                    los datos de la serie "Game of Thrones".*/

            var datosTemporadas = conversor.obtenerDatos(json,DatosTemporada.class);
            temporadas.add(datosTemporadas);

        }

       // temporadas.forEach(System.out::println); //Imprime las temporadas con sus episodios


//       Mostrar solo el titulo de los episodios para las temporadas
//
//        for (int i = 0; i < datos.totalDeTemporadas(); i++) {
//
//            List<DatosEpisodio>episodiosTemporada=temporadas.get(i).episodios();
//
//            for (int j = 0; j < episodiosTemporada.size(); j++) {
//
//                System.out.println(episodiosTemporada.get(j).titulo());
//
//
//            }
//
//
//        }

 //   temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo()))); //FUncion lambda

//Convertir todas las informaciones a una lista del tipo DatosEpisodio


List<DatosEpisodio> datosEpisodios = temporadas.stream()
                                               .flatMap(t -> t.episodios().stream())
                                               .collect(Collectors.toList());

 // Top 5 episodios
        System.out.println("Top 5 episodios");
        datosEpisodios.stream()
                .filter(e -> !e.evaluacion().equalsIgnoreCase("N/A"))
                .peek(e-> System.out.println("Primer filtro ((N/A) "+e))   /* El método .peek() en Java se usa dentro de una Stream para inspeccionar (espiar) los elementos mientras
                                                                                           fluyen por el pipeline, sin modificarlos. Es útil, por ejemplo, para ver qué está pasando internamente
                                                                                           durante una cadena de operaciones, como filter(), map(), collect(), etc.    */
                .sorted(Comparator.comparing(DatosEpisodio::evaluacion).reversed())
                .limit(5)
                .forEach(System.out::println);


// Convirtiendo los datos a una lista de tipo episodio

        List<Episodio> episodios = temporadas.stream()                //Convierte la lista de temporadas en un Stream para poder usar operaciones funcionales como map, flatMap, filter, etc.

                .flatMap(t ->t.episodios().stream()    /* Para cada temporada t, toma la lista de episodios de esa temporada (t.episodios()) y la convierte también en un Stream.
                                                                         Pero en lugar de obtener una lista de listas de episodios (es decir, un Stream<Stream<DatosEpisodio>>), usa flatMap para
                                                                         "aplanarlos" todos en un solo Stream<DatosEpisodio>. */
                .map(d -> new Episodio(t.numero(),d)))  //Para cada episodio d de la temporada t, crea un nuevo objeto de tipo Episodio usando el número de la temporada t.numero() y el DatosEpisodio d.
                                                                     //  Esto requiere que tu clase Episodio tenga un constructor (Episodio
                .collect(Collectors.toList());                       // Reúne todos esos objetos Episodio en una lista (List<Episodio>).


        episodios.forEach(System.out::println);                      // Imprime cada episodio usando el toString() de la clase Episodio.


        //Busqueda de Episodio a partir de X año

        System.out.println("Por favor indica el año a partir del cual deseas ver los episodios");

        var fecha= teclado.nextInt();                              // ← Lee el año ingresado por el usuario (por ejemplo: 2015)
        teclado.nextLine();                                         // ← Limpia el salto de línea pendiente

        LocalDate fechaBusqueda = LocalDate.of(fecha,1,1);    // ← Crea una fecha con el 1 de enero de ese año
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");    // ← Formato para imprimir la fecha en estilo día/mes/año

        episodios.stream()                  //Esto convierte la lista episodios en un flujo de datos (Stream)

                .filter(e -> e.getFechaDeLanzamiento() != null  && e.getFechaDeLanzamiento().isAfter(fechaBusqueda))   //Esto filtra el stream, es decir: descarta los episodios que no cumplen la condición.
                                                                                                                               // e -> ... Esto es una expresión lambda. La variable e representa cada objeto Episodio que pasa por el stream.
                                                                                                                               // e.getFechaDeLanzamiento() != null,  Nos aseguramos de que el episodio tiene una fecha de lanzamiento válida. Si es null, lo ignoramos.
                                                                                                                               //Si ambas condiciones son verdaderas, el episodio pasa el filtro y continúa en el stream.
                .forEach(e -> System.out.println(           // Este bloque se encarga de imprimir los episodios filtrados

                        "Temporada " + e.getTemporada() +                                                                /*e es el episodio actual del stream.
                                                                                                                         e.getTemporada() devuelve el número de la temporada.
                                                                                                                         e.getTitulo() devuelve el título del episodio.
                                                                                                                         e.getFechaDeLanzamiento().format(dtf) devuelve la fecha en el formato dd/MM/yyyy.*/
                                "Episodio " + e.getTitulo() +
                                "Fecha de Lanzamiento " + e.getFechaDeLanzamiento().format(dtf)
                ));

//--------------------------------------------------------------
        // Busca episodio por pedazo de titulo

        System.out.println("Escriba el pedazo de titulo del episodio que deseas ver");                          // Se pide al usuario que escriba una parte del título del episodio (puede ser una palabra o fragmento).

        var pedazoTitulo= teclado.nextLine();                                                                  //→ Se guarda en la variable pedazoTitulo.

        Optional<Episodio> episodioBuscado = episodios.stream()                                                // episodios.stream(): transforma la lista de episodios en un Stream para poder procesarlos.
                .filter(e -> e.getTitulo().toUpperCase().contains(pedazoTitulo.toUpperCase()))        // .filter(...): busca episodios cuyo título contenga el texto que escribió el usuario.
                .findFirst();                                                                                 // .findFirst(): devuelve el primer episodio que cumple la condición (si existe).

                                                                                                              /* a función .contains() en Java se usa para verificar si una cadena de texto contiene otra cadena
                                                                                                                 de texto dentro de ella. Retorna un valor booleano (true o false). */

        if (episodioBuscado.isPresent()){                                       //.isPresent(): verifica si se encontró un episodio. Para acceder al valor dentro de Optional, podemos usar ifPresent y orElse
            System.out.println(" Episodio encontrado");
            System.out.println("Los Datos son: " + episodioBuscado.get());     // .get(): obtiene el episodio dentro del Optional con todos los datos. También se puede acceder a una propiedad: get().getTitulo()
        } else {

            System.out.println("Episodio no encontrado");
        }
//--------------------------------------------------
        Map<Integer,Double> evaluacionesPorTemporada = episodios.stream()      // Convierte la lista de episodios en un Stream, para poder procesarla con operaciones funcionales como filter, collect, etc.
                .filter(e -> e.getEvaluacion() > 0.0)                  /* Filtra los episodios para quedarte solo con aquellos que tienen una evaluación mayor a 0.
                                                                                  ️ Esto evita promediar episodios sin evaluación (que quizás valen 0 por defecto).*/

                .collect(Collectors.groupingBy(Episodio::getTemporada,        // collect(...): Agrupa y transforma los datos del stream. Aquí se usa para recolectar el resultado en un Map. Este es el paso donde recolectamos los resultados y los transformamos en un Map.
                                                                              // groupingBy(Episodio::getTemporada, ...) : Agrupa los episodios por el número de temporada (por ejemplo, 1, 2, 3...).
                                                                              // El Map que se genera tendrá como clave el número de temporada (Integer)  Y como valor: el resultado del segundo parámetro...
                 Collectors.averagingDouble(Episodio::getEvaluacion)));       // Este es el valor del Map: para cada grupo (temporada), calcula el promedio de las evaluaciones (getEvaluacion()).



        // Collectors.averagingDouble(Episodio::getEvaluacion)
        // Una vez que agrupamos, usamos esta parte para decir qué hacer con cada grupo:
        // Calcula el promedio de las evaluaciones de los episodios de cada temporada.
        // averagingDouble(...) necesita un número tipo double, por eso usamos Episodio::getEvaluacion.

        System.out.println(evaluacionesPorTemporada);


   //---------------------------------------------

   DoubleSummaryStatistics est = episodios.stream()     // episodios.stream() Convierte la lista episodios en un Stream para poder procesarla con operaciones funcionales como filter y collect.
           .filter(e->e.getEvaluacion()>0.0)   // .filter(e -> e.getEvaluacion() > 0.0) : Filtra los episodios: Solo deja pasar aquellos cuya evaluación (getEvaluacion()) sea mayor que 0.0.
                                                       // Esto descarta episodios sin calificación o con errores.

           .collect(Collectors.summarizingDouble(Episodio::getEvaluacion));  // .collect(Collectors.summarizingDouble(Episodio::getEvaluacion)) Este paso:
                                                                              /*     Recolecta (collect) estadísticas sobre los valores double obtenidos de Episodio::getEvaluacion.
                                                                                     Devuelve un objeto de tipo DoubleSummaryStatistics.
                                                                                     Este objeto contiene: Count → Cuántos elementos se evaluaron. Sum → La suma de todas las evaluaciones.
                                                                                      Min → La evaluación más baja.  Max → La evaluación más alta. Average → El promedio de las evaluaciones. */


        System.out.println(est);                                               // Se imprime todo las estadisticas, pero tambien podemos imprimir solo los valores que queramos
        System.out.println("Media de las evaluaciones: " + est.getAverage());  //
        System.out.println("Episodio Mejor evaluado: " + est.getMax());
        System.out.println("Episodio Peor evaluado: " + est.getMin());


    }



}




