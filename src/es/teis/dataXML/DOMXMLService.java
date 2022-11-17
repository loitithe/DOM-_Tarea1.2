/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.teis.dataXML;

import es.teis.data.exceptions.LecturaException;
import es.teis.model.Partido;
import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author rodri
 */
public class DOMXMLService implements IXMLService {

    float umbralPartido;
    ArrayList<Partido> partidoUmbral = new ArrayList<>();
    //propiedades del objeto partido
    long id;
    String nombre;
    int votos;
    float porcentaje;
    Partido partido;

   
     @Override
        public ArrayList<Partido> leerPartidos
        (String ruta, float umbral) throws LecturaException {
            try {
                File inputFile = new File(ruta);
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(inputFile);

                doc.getDocumentElement().normalize();
                System.out.println("Root element" + doc.getDocumentElement().getNodeName());

                NodeList nList = doc.getElementsByTagName(PARTIDO_TAG);

                System.out.println("====================================");
                for (int i = 0; i < nList.getLength(); i++) {
                    Node nNode = nList.item(i);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        umbralPartido = Float.parseFloat(eElement.getElementsByTagName(PARTIDO_VOTOS_PORC_TAG).item(0).getTextContent());
                        if (umbralPartido > umbral) {
                            id = Long.parseLong(eElement.getAttribute(PARTIDO_ATT_ID));
                            nombre = eElement.getElementsByTagName(PARTIDO_NOMBRE_TAG).item(0).getTextContent();
                            votos = Integer.parseInt(eElement.getElementsByTagName(PARTIDO_VOTOS_NUM_TAG).item(0).getTextContent());
                            porcentaje = Float.parseFloat(eElement.getElementsByTagName(PARTIDO_VOTOS_PORC_TAG).item(0).getTextContent());
                            partido = new Partido(id, nombre, votos, porcentaje);
                            partidoUmbral.add(partido);
                        }
                    }
                }

            } catch (Exception e) {
            }
            return partidoUmbral;
        }

    }
    

