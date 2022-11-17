/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package es.teis.data;

import es.teis.model.Partido;
import java.util.ArrayList;

/**
 *
 * @author mfernandez
 */
public interface IPersistencia {

    void escribir(ArrayList<Partido> partidos, String ruta);

    ArrayList<Partido> leerTodo(String ruta);

}
