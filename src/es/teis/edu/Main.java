
package es.teis.edu;

import es.teis.data.PartidoObjectPersistencia;
import es.teis.data.exceptions.LecturaException;
import es.teis.dataXML.DOMXMLService;
import es.teis.model.Partido;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Clase Main: Se encarga de crear objetos de tipo IXMLService para leer el documento XML y
 * extraer un ArrayList<Partido>. A continuación, deberá crear un objeto que implemente IPersistencia
 * y crear un archivo con ObjectOutputStream. Finalmente, deberá leer los datos escritos en el fichero de salida.
 * @author rguido
 */
public class Main {

    private static String ELECCIONES_INPUT_FILE = Paths.get("src", "docs", "elecciones.xml").toString();
    private static String ELECCIONES_OUTPUT_FILE = Paths.get("src", "docs", "elecciones_output.dat").toString();

    private static float UMBRAL_PORCENTAJE = 3;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws LecturaException {
        
        DOMXMLService dOMXMLService= new DOMXMLService();
        ArrayList<Partido>partidos=new ArrayList<>(dOMXMLService.leerPartidos(ELECCIONES_INPUT_FILE, UMBRAL_PORCENTAJE));
        mostrar(partidos);
      
        PartidoObjectPersistencia objectPersistencia=new PartidoObjectPersistencia();
        objectPersistencia.escribir(partidos, ELECCIONES_OUTPUT_FILE);
    }

    private static void mostrar(ArrayList<Partido> partidos) {
        System.out.println("Se han leído: ");
        for (Partido partido : partidos) {
            System.out.println(partido);

        }
    }

}
