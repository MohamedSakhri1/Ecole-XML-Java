package org.ecolexml.ecole_xml_java.GenerateursHTML;


import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.charset.StandardCharsets;

import net.sf.saxon.s9api.*;

public class AffichageModuleHTML {
    public static String fn(String moduleCode,boolean isFromApi) {
        // Définition des chemins des fichiers
        try {
            // 🟢 Étape 1 : Exécuter XQuery et générer le XML
            //String moduleCode = "GINF41"; // Module à traiter
            String xqueryFilePath = "src/main/resources/Fichiers_XQuery/getModuleResults.xquery";
            String xmlOutputPath = "src/main/resources/Fichiers_XQuery/affichage_module_result_avec_Xquery/Affichage_" + moduleCode + ".xml";

            System.out.println("📌 Exécution de XQuery...");
            executeXQuery(xqueryFilePath, xmlOutputPath, moduleCode);
            System.out.println("✅ Fichier XML généré : " + xmlOutputPath);

            // 🟢 Étape 2 : Transformer XML en HTML avec XSLT
            String xsltFilePath = "src/main/resources/Fichiers_XSLT/AffichageModule.xsl";
            String htmlOutputPath = isFromApi ?
                                "src/main/ApiGeneratedFiles/AffichageModule_"+ moduleCode +".html"
                                : "src/main/resources/Documents_HTML/AffichageModule/AffichageModule_"+ moduleCode +".html";

            System.out.println("📌 Transformation en HTML...");
            transformXMLtoHTML(xmlOutputPath, xsltFilePath, htmlOutputPath);
            System.out.println("✅ Fichier HTML généré : " + htmlOutputPath);
            return htmlOutputPath;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ Erreur lors du traitement !");
        }
        return null;
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
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), StandardCharsets.UTF_8))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>\n"); //standalone="no"
            for (XdmItem item : result) {
                writer.write(item.toString());
                writer.write("\n");
            }
        }
    }

    /**
     * Transforme un fichier XML en HTML via XSLT.
     */
    private static void transformXMLtoHTML(String xmlFile, String xsltFile, String outputHtml) throws TransformerException {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer(new StreamSource(new File(xsltFile)));

        transformer.transform(
                new StreamSource(new File(xmlFile)),
                new StreamResult(new File(outputHtml))
        );
    }
}

