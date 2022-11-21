/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.teis.data.exceptions;

/**
 * @author rguido
 */
public class LecturaException extends Exception {

    private String rutaFichero;

    /**
     * Constructor por defecto
     * @param string 
     * @param rutaFichero
     */
    public LecturaException(String string, String rutaFichero) {

        super(string);
        this.rutaFichero = rutaFichero;
    }

    /**
     * Método que devuelve la ruta del fichero
     * @return 
     */
    public String getRutaFichero() {
        return rutaFichero;
    }

}
