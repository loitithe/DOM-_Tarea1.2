/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.teis.data;

import es.teis.model.Partido;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author rodri
 */
public class PartidoObjectPersistencia implements IPersistencia {

    private static final String PARTIDOS_TAG = "partidos";
    private static final String PARTIDOS_ID_TAG = "id";
    private static final String PARTIDO_TAG = "partido";
    private static final String PARTIDO_NOMBRE_TAG = "nombre";
    private static final String PARTIDO_VOTOS_TAG = "votos";
    private static final String PARTIDO_PORCENTAJE_TAG = "porcentaje";

    private static final String DATOS = Paths.get("src", "docs", "elecciones_output.dat").toString();

    private static final String PARTIDOS_OUTPUT_FILE = Paths.get("src", "docs", "elecciones.xml").toString();
    private static final String PARTIDOS_DTD_FILE = "partidos.dtd";

    /**
     * sobrescribirá el fichero desde el comienzo sea cual sea su contenido.
     * guardas en un .dat el ArrayList de Partido
     *
     * @param partidos coleccion a utlizar para sobresscribir el DOM
     * @param ruta
     */
    @Override
    public void escribir(ArrayList<Partido> partidos, String ruta) {

    }

    /**
     * controla EOFException para detectar el fin del fichero y salir del bucle
     * de lectura.
     * @param ruta a utlizar
     * @return ArrayList
     */
    @Override
    public ArrayList<Partido> leerTodo(String ruta) {
        System.err.println("LEER TODO");
        ArrayList<Partido> listTodo = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(DATOS);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object aux = ois.readObject();
            while (aux != null) {
                if (aux instanceof Partido) {
                    listTodo.add((Partido) aux);
                    System.out.println("leertodo" + aux.toString());
                }
                aux = ois.readObject();
            }
            ois.close();
        } catch (EOFException e) {
        } catch (IOException ex) {
            Logger.getLogger(PartidoObjectPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PartidoObjectPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listTodo;
    }

    /**
     * Función que crea un fichero .dat usilitzando los datos de una coleccion pasada por parámetro
     * @param partidos : ArrayList
     * @param ruta
     */
    public void crearFicheroPartidos(ArrayList<Partido> partidos, String ruta) {
        System.out.println("Creando fichero partidos...");
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(ruta)));
            for (Partido partido : partidos) {
                System.out.println("Partido añadido : " + partido);
                oos.writeObject(partido);
            }
            oos.close();
        } catch (IOException ex) {
            Logger.getLogger(PartidoObjectPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Función que crea un archivo Dom a partir de una colección pasada por parámetro
     * @param ruta 
     * @param partidos : ArrayList
     */
    public void crearDOMPartidos(String ruta, ArrayList<Partido> partidos) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation implementation = builder.getDOMImplementation();
//            //doctype
            DocumentType doctype = implementation.createDocumentType(PARTIDOS_TAG, null, PARTIDOS_DTD_FILE);
//            //crea document con elemento raiz
            Document document = implementation.createDocument(null, PARTIDOS_TAG, null);
            Element root = document.getDocumentElement();
            for (Partido partido : partidos) {
                Element ePartido = document.createElement(PARTIDO_TAG);
                ePartido.setAttribute(PARTIDOS_ID_TAG, String.valueOf(partido.getId()));
                crearElement(document, ePartido, PARTIDO_NOMBRE_TAG, partido.getNombre());
                crearElement(document, ePartido, PARTIDO_VOTOS_TAG, String.valueOf(partido.getVotos()));
                crearElement(document, ePartido, PARTIDO_PORCENTAJE_TAG, String.valueOf(partido.getPorcentaje()).toString());
                root.appendChild(ePartido);
            }
            //Generar el transformador para obtener el documento XML en un fichero
            TransformerFactory fabricaTransformador = TransformerFactory.newInstance();
            //espacios para identar cada linea
            fabricaTransformador.setAttribute("indent-number", 4);
            Transformer transformer = fabricaTransformador.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            //origen de la transformacion: document
            Source origen = new DOMSource(document);
            //stream a fichero
            Result destino = new StreamResult(PARTIDOS_OUTPUT_FILE);
            transformer.transform(origen, destino);

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(PartidoObjectPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(PartidoObjectPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(PartidoObjectPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Función que crea la estructura de nodos y texto para la creaacion de un
     * xml
     *
     * @param document
     * @param padre
     * @param tag
     * @param text
     */
    void crearElement(Document document, Node padre, String tag, String text) {
        Node node = document.createElement(tag);
        Node nodeText = document.createTextNode(text);
        padre.appendChild(node);
        node.appendChild(nodeText);

    }

}
