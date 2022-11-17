/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.teis.data;

import es.teis.model.Partido;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author rodri
 */
public class PartidoObjectPersistencia implements IPersistencia {

    /**
     * sobrescribirá el fichero desde el comienzo sea cual sea su contenido.
     *
     * @param partidos
     * @param ruta
     */
    @Override
    public void escribir(ArrayList<Partido> partidos, String ruta) {
        crearFicheroPartidos(partidos, ruta);

    }

    /**
     * controla EOFException para detectar el fin del fichero y salir del bucle
     * de lectura.
     *
     * @param ruta
     * @return
     */
    @Override
    public ArrayList<Partido> leerTodo(String ruta) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void crearFicheroPartidos(ArrayList<Partido> partidos, String ruta) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(ruta)));
            for (Partido partido : partidos) {
                oos.writeObject(partido);
            }
            oos.close();
        } catch (IOException ex) {
            Logger.getLogger(PartidoObjectPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void crearXMLPartidos(String ruta) {
        try {
            FileInputStream fis = new FileInputStream(ruta);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Partido p;
            try {
                DocumentType doctype = null;
                Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().getDOMImplementation().createDocument(null, "partidos", doctype);
                
            } catch (Exception e) {
            }

        } catch (IOException ex) {
            Logger.getLogger(PartidoObjectPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void crearElement(String text, Document document, Node padre, String tag) {
        Node node = document.createElement(tag);
        Node nodeText = document.createTextNode(text);
        padre.appendChild(node);
        padre.appendChild(nodeText);

    }
}
