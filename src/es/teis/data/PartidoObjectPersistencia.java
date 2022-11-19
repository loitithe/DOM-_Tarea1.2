/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.teis.data;

import es.teis.model.Partido;
import es.teis.model.Version;
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
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
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

    private static final String VERSIONES_TAG = "versiones";
    private static final String VERSION_TAG = "version";
    private static final String VERSION_NOMBRE_TAG = "nombre";
    private static final String VERSION_API_TAG = "api";
    private static final String VERSION_ATT_NUMERO = "num";

    private static final String DATOS = Paths.get("src", "docs", "elecciones_output.dat").toString();

    private static final String VERSIONES_OUTPUT_FILE = Paths.get("src", "docs", "versiones_output.xml").toString();
    private static final String VERSIONES_DTD_FILE = "versiones.dtd";

    /**
     * sobrescribirá el fichero desde el comienzo sea cual sea su contenido.
     *  guardas en un .dat el ArrayList de Partido
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
        System.err.println("LEER TODO");
        ArrayList<Partido> listTodo = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(DATOS);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object aux = ois.readObject();
            while (aux != null) {
                if ( aux instanceof Partido) {
                    listTodo.add((Partido)aux);
                    System.out.println(aux.toString());
                }
                aux= ois.readObject();
            }
            ois.close();
        } catch (Exception e) {
        }
        return listTodo;
    }

    public void crearFicheroPartidos(ArrayList<Partido> partidos, String ruta) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(ruta)));
            for (Partido partido : partidos) {
                System.out.println("crearficheropartidos : "+partido);
                oos.writeObject(partido);
            }
            oos.close();
        } catch (IOException ex) {
            Logger.getLogger(PartidoObjectPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void crearDOMPartidos(String ruta) {
        try {
            ArrayList<Version> versions = crearVersion();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation implementation = builder.getDOMImplementation();
            //doctype
            DocumentType doctype = implementation.createDocumentType(VERSIONES_TAG, null, VERSIONES_DTD_FILE);
            //crea document con elemento raiz
            Document document = implementation.createDocument(null, VERSIONES_TAG, doctype);
            Element root = document.getDocumentElement();

            for (Version v : versions) {
                //desde el document creamos un nuevo elemento
                Element eVersion = document.createElement(VERSION_TAG);
                eVersion.setAttribute(VERSION_ATT_NUMERO, String.valueOf(v.getNum()));
                crearElement(document, eVersion, VERSION_NOMBRE_TAG, v.getNombre());
                crearElement(document, eVersion, VERSION_API_TAG, String.valueOf(v.getApi()));
                root.appendChild(eVersion);
            }
            //Generar el transformador para obtener el documento XML en un fichero
            TransformerFactory fabricaTransformador = TransformerFactory.newInstance();
            //espacios para identar cada linea
            fabricaTransformador.setAttribute("indent-number", 4);
            Transformer transformer = fabricaTransformador.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

//            FileInputStream fis = new FileInputStream(ruta);
//            ObjectInputStream ois = new ObjectInputStream(fis);
//            Partido p;

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(PartidoObjectPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(PartidoObjectPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void crearElement(Document document, Node padre, String tag, String text) {
        Node node = document.createElement(tag);
        Node nodeText = document.createTextNode(text);
        padre.appendChild(node);
        padre.appendChild(nodeText);

    }

    private static ArrayList<Version> crearVersion() {
        ArrayList<Version> versions = new ArrayList<>();
        Version versionA = new Version(1, "nombreA", 2);
        Version versionB = new Version(1.5, "nombreB", 3);
        Version versionC = new Version(2, "nombreC", 4);
        versions.add(versionA);
        versions.add(versionB);
        versions.add(versionC);

        return versions;
    }

}
