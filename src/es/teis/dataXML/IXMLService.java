/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package es.teis.dataXML;

import es.teis.data.exceptions.LecturaException;
import es.teis.model.Partido;
import java.util.ArrayList;

/**
 *
 * @author maria
 */
public interface IXMLService {

    public static final String PARTIDO_TAG = "partido";
    public static final String PARTIDO_VOTOS_NUM_TAG = "votos_numero";
    public static final String PARTIDO_VOTOS_PORC_TAG = "votos_porciento";
    public static final String PARTIDO_NOMBRE_TAG = "nombre";

    public static final String PARTIDO_ATT_ID = "id";

    

    ArrayList<Partido> leerPartidos(String ruta, float umbral) throws LecturaException;

}
