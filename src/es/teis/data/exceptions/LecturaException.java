/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.teis.data.exceptions;

/**
 * Clase Main: Se encarga de crear objetos de tipo IXMLService para leer el documento XML y
 * extraer un ArrayList<Partido>. A continuación, deberá crear un objeto que implemente IPersistencia
 * y crear un archivo con ObjectOutputStream. Finalmente, deberá leer los datos escritos en el fichero de salida.
 * @author rguido
 */
public class LecturaException extends Exception {

    private String rutaFichero;

    public LecturaException(String string, String rutaFichero) {

        super(string);
        this.rutaFichero = rutaFichero;
    }

    public String getRutaFichero() {
        return rutaFichero;
    }
    
    

}
