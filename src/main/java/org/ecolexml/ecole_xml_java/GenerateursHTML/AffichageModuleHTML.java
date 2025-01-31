package org.ecolexml.ecole_xml_java.GenerateursHTML;


import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

public class AffichageModuleHTML {
    public static void main(String[] args) {
        // Définition des chemins des fichiers
        String xmlFile = "src/main/resources/Fichiers_XQuery/Affichage_GINF32.xml"; // Fichier XML source
        String xsltFile = "src/main/resources/Fichiers_XSLT/AffichageModule.xsl"; // Fichier XSLT
        String outputHtml = "src/main/resources/Documents_HTML/AffichageModule.html"; // Fichier HTML généré

        try {
            // Création de la fabrique et du transformeur
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(new File(xsltFile)));

            // Source XML et sortie HTML
            StreamSource xmlSource = new StreamSource(new File(xmlFile));
            StreamResult htmlOutput = new StreamResult(new File(outputHtml));

            // Exécution de la transformation
            transformer.transform(xmlSource, htmlOutput);

            System.out.println("✅ Transformation réussie : " + outputHtml);
        } catch (TransformerException e) {
            e.printStackTrace();
            System.err.println("❌ Erreur lors de la transformation XSLT !");
        }
    }
}

