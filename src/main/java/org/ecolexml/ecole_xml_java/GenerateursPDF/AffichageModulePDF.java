package org.ecolexml.ecole_xml_java.GenerateursPDF;

import net.sf.saxon.s9api.*;
import org.apache.fop.apps.*;

import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Classe pour générer l'affichage des modules au format PDF.
 */
public class AffichageModulePDF {

    /**
     * Méthode principale pour générer l'affichage des modules en PDF.
     * @param moduleCode Le code du module.
     * @return Le fichier PDF généré.
     */
    public static File fn(String moduleCode) {
        try {
            // 1. Exécuter XQuery et générer le fichier XML pour le module.
            File xmlFile = new File("src/main/resources/Fichiers_XQuery/affichage_module_result_avec_Xquery/Affichage_" + moduleCode + ".xml");
            executeXQuery(moduleCode, xmlFile);

            // 2. Transformer le fichier XML en PDF avec XSL-FO.
            File xslFoFile = new File("src/main/resources/Fichiers_XSL_FO/AffichageModule.xsl");
            File pdfDir = new File("src/main/resources/Documents_PDF/AffichageModule");
            if (!pdfDir.exists()) pdfDir.mkdirs(); // Créer le dossier si nécessaire
            File pdfFile = new File(pdfDir, "AffichageModule_" + moduleCode + ".pdf");

            generatePDF(xmlFile, xslFoFile, pdfFile);

            // Retourner le fichier PDF généré
            return pdfFile;

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ Erreur lors de la génération du PDF !");
        }
        return null;
    }

    /**
     * Exécute la requête XQuery pour récupérer les données du module et les stocke dans un fichier XML.
     */
    private static void executeXQuery(String moduleCode, File outputFile) throws IOException, SaxonApiException {
        String xqueryFilePath = "src/main/resources/Fichiers_XQuery/getModuleResults.xquery"; // Fichier XQuery

        Processor processor = new Processor(false);
        XQueryCompiler compiler = processor.newXQueryCompiler();
        XQueryExecutable executable = compiler.compile(new File(xqueryFilePath));
        XQueryEvaluator evaluator = executable.load();

        // Définir le paramètre externe du moduleCode
        QName moduleCodeParam = new QName("moduleCode");
        evaluator.setExternalVariable(moduleCodeParam, new XdmAtomicValue(moduleCode));

        // Exécuter XQuery et stocker le résultat
        XdmValue result = evaluator.evaluate();
        writeResultToFile(result, outputFile);
    }

    /**
     * Écrit le résultat XdmValue dans un fichier XML.
     */
    private static void writeResultToFile(XdmValue result, File file) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            for (XdmItem item : result) {
                writer.write(item.toString());
                writer.write("\n");
            }
        }
    }

    /**
     * Génère un fichier PDF à partir d'un fichier XML et d'un XSL-FO.
     */
    public static void generatePDF(File xmlFile, File xslFoFile, File pdfFile) throws Exception {
        // Crée une instance de FopFactory pour générer le PDF
        FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();

        // Prépare la sortie du fichier PDF
        OutputStream out = new BufferedOutputStream(new FileOutputStream(pdfFile));

        try {
            // Crée un objet Fop pour générer le PDF
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
            // Utilise un transformer pour appliquer le XSLT
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer(new StreamSource(xslFoFile));

            // Applique la transformation XSLT sur le fichier XML et génère le PDF
            Source src = new StreamSource(xmlFile);
            Result res = new SAXResult(fop.getDefaultHandler());
            transformer.transform(src, res);
        } finally {
            out.close();
        }
    }
}
