package org.ecolexml.ecole_xml_java.GenerateursHTML;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

public class EdtFromXSL {
    public static void fn(boolean isFromApi) {
        try {
            // Définition des fichiers XML et XSLT
            String xmlFile = "src/main/resources/Fichiers_XML/Emploi du temps/Emploi_du_temps.xml";
            String xslFile = "src/main/resources/Fichiers_XSLT/EDT.xslt";
            String outputHtmlFile = "src/main/resources/Documents_HTML/EDT/EDT_XSL.html";

            // Création des objets de transformation
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(new File(xslFile)));

            // Transformation du fichier XML en HTML
            transformer.transform(new StreamSource(new File(xmlFile)), new StreamResult(new File(outputHtmlFile)));

            System.out.println("Fichier HTML généré avec succès : " + outputHtmlFile);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
