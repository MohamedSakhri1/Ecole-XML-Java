package org.ecolexml.ecole_xml_java.GenerateursPDF;

import net.sf.saxon.s9api.*;
import org.apache.fop.apps.*;

import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

public class AffichageModulePDF {
    public static void main(String[] args) {
        try {
            // 🟢 Étape 1 : Exécuter XQuery et générer le XML
            String moduleCode = "GINF32"; // Module à traiter
            String xqueryFilePath = "src/main/resources/Fichiers_XQuery/getModuleResults.xquery";
            String xmlOutputPath = "src/main/resources/Fichiers_XQuery/affichage_module_result_avec_Xquery/Affichage_" + moduleCode + ".xml";

            System.out.println("📌 Exécution de XQuery...");
            executeXQuery(xqueryFilePath, xmlOutputPath, moduleCode);
            System.out.println("✅ Fichier XML généré : " + xmlOutputPath);

            // 🟢 Étape 2 : Transformer XML en PDF avec XSL-FO
            String xslFoFilePath = "src/main/resources/Fichiers_XSL_FO/AffichageModule.xsl";
            String pdfOutputPath = "src/main/resources/Documents_PDF/AffichageModule/AffichageModule_"+ moduleCode +".pdf";

            System.out.println("📌 Transformation en PDF...");
            transformXMLtoPDF(xmlOutputPath, xslFoFilePath, pdfOutputPath);
            System.out.println("✅ Fichier PDF généré : " + pdfOutputPath);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ Erreur lors du traitement !");
        }
    }

    /**
     * Exécute une requête XQuery et écrit le résultat dans un fichier XML.
     */
    private static void executeXQuery(String xqueryFilePath, String outputFilePath, String moduleCode) throws SaxonApiException, IOException {
        Processor processor = new Processor(false);
        XQueryCompiler compiler = processor.newXQueryCompiler();
        XQueryExecutable executable = compiler.compile(new File(xqueryFilePath));
        XQueryEvaluator evaluator = executable.load();

        // Définir le paramètre externe
        QName moduleCodeParam = new QName("moduleCode");
        evaluator.setExternalVariable(moduleCodeParam, new XdmAtomicValue(moduleCode));

        // Exécuter la requête
        XdmValue result = evaluator.evaluate();
        writeResultToFile(result, outputFilePath);
    }

    /**
     * Écrit le résultat de XQuery dans un fichier XML.
     */
    private static void writeResultToFile(XdmValue result, String filePath) throws IOException {
        File outputFile = new File(filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n");
            for (XdmItem item : result) {
                writer.write(item.toString());
                writer.write("\n");
            }
        }
    }

    /**
     * Transforme un fichier XML en PDF via XSL-FO.
     */
    private static void transformXMLtoPDF(String xmlFilePath, String xslFoFilePath, String pdfOutputPath) throws Exception {
        // ✅ Création d'une configuration FOP propre
        FopFactoryBuilder builder = new FopFactoryBuilder(new File(".").toURI());
        FopFactory fopFactory = builder.build(); // Nouvelle façon de créer le FopFactory
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();

        // Création du dossier PDF si inexistant
        File pdfFile = new File(pdfOutputPath);
        pdfFile.getParentFile().mkdirs();

        try (OutputStream out = new BufferedOutputStream(new FileOutputStream(pdfFile))) {
            // ✅ Configuration du processeur FOP
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

            // ✅ Création du Transformer pour appliquer XSL-FO
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(new File(xslFoFilePath)));

            // ✅ Transformation XML -> PDF via XSL-FO
            Source src = new StreamSource(new File(xmlFilePath));
            Result res = new SAXResult(fop.getDefaultHandler());
            transformer.transform(src, res);
        }
    }
}