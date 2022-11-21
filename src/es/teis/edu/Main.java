package es.teis.edu;

import es.teis.data.PartidoObjectPersistencia;
import es.teis.data.exceptions.LecturaException;
import es.teis.dataXML.DOMXMLService;
import es.teis.model.Partido;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase Main: Se encarga de crear objetos de tipo IXMLService para leer el
 * documento XML y extraer un ArrayList<Partido>. A continuación, deberá crear
 * un objeto que implemente IPersistencia y crear un archivo con
 * ObjectOutputStream. Finalmente, deberá leer los datos escritos en el fichero
 * de salida.
 *
 * @author rguido
 */
public class Main {

    private static String ELECCIONES_INPUT_FILE = Paths.get("src", "docs", "elecciones.xml").toString();
    public static String ELECCIONES_OUTPUT_FILE = Paths.get("src", "docs", "elecciones_output.dat").toString();
    private static String ELECCIONES_OUTPUT_XML = Paths.get("src", "docs", "elecciones_output.xml").toString();
    public static String PARTIDOS_OUTPUT_FILE = Paths.get("src", "docs", "partidos_output.dat").toString();

    public static float UMBRAL_PORCENTAJE = 0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws LecturaException {

        DOMXMLService dOMXMLService = new DOMXMLService();
        ArrayList<Partido> partidos = new ArrayList<>(dOMXMLService.leerPartidos(ELECCIONES_INPUT_FILE, UMBRAL_PORCENTAJE));
        //  Partido nuevoPartido = new Partido(73, "Partido Innova", 8290, 73);
        // partidos.add(nuevoPartido);
        // mostrar(partidos);
        PartidoObjectPersistencia objectPersistencia = new PartidoObjectPersistencia();

        int opcion = 0;
        int umbral;
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("## MENU OPCIONES ##\n0.Salir\n1.Mostrar partidos\n2.Mostar partidos segun umbral"
                    + "\n3.Crear fichero .dat\n4.Crear DOM partidos\n5.Escribir arraylist en .dat\n6.Leer Todo"); 
            try {
                opcion = Integer.parseInt(sc.nextLine());
                switch (opcion) {
                    case 1:
                        //Mostrar todos los partidos : umbral 0
                        objectPersistencia.leerTodo(ELECCIONES_OUTPUT_FILE);
                        break;
                    case 2:
                        /*Mostrar partidos segun umbral : si cambias el umbral modificas la coleccion
                        Y si creas un xml o fichero dat utilizara estos datos para crear el archivo
                        */
                        System.out.println("indique el umbral de porcentaje ");
                        UMBRAL_PORCENTAJE = Float.parseFloat(sc.nextLine());
                      
                        mostrar(dOMXMLService.leerPartidos(ELECCIONES_INPUT_FILE, UMBRAL_PORCENTAJE));
                        break;
                    case 3:
                        //crear .dat a partir de la coleccion partidos
                        objectPersistencia.crearFicheroPartidos(partidos, PARTIDOS_OUTPUT_FILE);
                        break;
                    case 4:
                        //crea dom a partir de la coleccion partidos
                        objectPersistencia.crearDOMPartidos(ELECCIONES_OUTPUT_XML, partidos);
                        break;
                    case 5:
                        //sobrescribirá el fichero desde el comienzo sea cual sea su contenido
                        objectPersistencia.escribir(partidos,ELECCIONES_OUTPUT_FILE);
                        break;
                    case 6: 
                        break;
                    default:
                        System.out.println("saliendo...");
                }
            } catch (NumberFormatException e) {
                e.getStackTrace();
            } catch (InputMismatchException eo) {
                eo.printStackTrace();
            }
        } while (opcion != 0);
    }

    private static void mostrar(ArrayList<Partido> partidos) {

        System.out.println("Se han leído: ");
        for (Partido partido : partidos) {
            System.out.println(partido);

        }
    }

}
