package org.ecolexml.ecole_xml_java.GenerateursHTML;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

public class CreationAffichageModule {
    public static void main(String[] args) throws Exception {
        TransformerFactory factory = TransformerFactory.newInstance();
        Source xslt = new StreamSource(new File("src/main/resources/Fichiers_XSLT/AffichageHTML.xslt"));
        Source xml = new StreamSource(new File("src/main/resources/Fichiers_XML/Affichage de module/Affichage_GINF32.xml"));
        Result output = new StreamResult(new File("src/main/resources/Documents_HTML/Affichage_GINF32.html"));

        Transformer transformer = factory.newTransformer(xslt);
        transformer.transform(xml, output);

        System.out.println("Transformation complete. Check output.html");
    }
}
