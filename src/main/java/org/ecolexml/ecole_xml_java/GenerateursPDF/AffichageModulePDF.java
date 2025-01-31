package org.ecolexml.ecole_xml_java.GenerateursPDF;

import org.apache.fop.apps.*;
import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

public class AffichageModulePDF {
    public static void main(String[] args) {
        try {
            // Fichiers XML et XSL-FO
            File xmlFile = new File("src/main/resources/Fichiers_XQuery/testAffichage.xml");
            File xslFile = new File("src/main/resources/Fichiers_XSL_FO/AffichageModule.xsl");
            File outputDir = new File("src/main/resources/Documents_PDF");
            outputDir.mkdirs(); // Créer le dossier si inexistant
            File pdfFile = new File(outputDir, "AffichageModule.pdf");

            System.out.println("📄 Génération du PDF : " + pdfFile.getAbsolutePath());

            // ✅ Création d'une configuration FOP sans conflit
            FopFactoryBuilder builder = new FopFactoryBuilder(new File(".").toURI());
            FopFactory fopFactory = builder.build(); // Nouvelle façon de créer le FopFactory

            // ✅ Utilisation d'un FOUserAgent propre
            FOUserAgent foUserAgent = fopFactory.newFOUserAgent();

            // Sortie vers un fichier
            OutputStream out = new BufferedOutputStream(new FileOutputStream(pdfFile));

            try {
                // Configuration du processeur FOP
                Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

                // Création du Transformer pour appliquer XSL-FO
                TransformerFactory factory = TransformerFactory.newInstance();
                Transformer transformer = factory.newTransformer(new StreamSource(xslFile));

                // Transformation XML -> PDF via XSL-FO
                Source src = new StreamSource(xmlFile);
                Result res = new SAXResult(fop.getDefaultHandler());
                transformer.transform(src, res);

                System.out.println("✅ PDF généré avec succès !");
            } finally {
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ Erreur lors de la génération du PDF !");
        }
    }
}
