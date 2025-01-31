package org.ecolexml.ecole_xml_java.GenerateursPDF;

import net.sf.saxon.s9api.*;
import org.apache.fop.apps.*;

import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

public class AttestationReussitePDF {
    public static void main(String[] args) {
        try {
            // ---------------------- Paramètre : Numéro d'Apogée
            String apogee = "21010278"; // Numéro d'exemple

            // ---------------------- Étape 1 : Exécution de XQuery pour générer le fichier XML
            String xqueryFilePath = "src/main/resources/Fichiers_XQuery/getNotesEtudiant.xq";
            String outputXmlPath = "src/main/resources/Fichiers_XQuery/Attestation_reussite_result_avec_Xquery/NotesEtudiant"+apogee+".xml";
            executeXQuery(xqueryFilePath, outputXmlPath, apogee);

            // ---------------------- Étape 2 : Génération du PDF avec FOP
            String xslFoPath = "src/main/resources/Fichiers_XSL_FO/attestationReussite.xsl";
            String outputPdfPath = "src/main/resources/Documents_PDF/AttestationReussite/AttestationReussite"+apogee+"..pdf";
            generatePDF(outputXmlPath, xslFoPath, outputPdfPath);

            System.out.println("✅ PDF généré avec succès : " + outputPdfPath);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ Erreur lors de la génération du PDF !");
        }
    }

    /**
     * Exécute une requête XQuery pour générer un fichier XML à partir des données d'un étudiant.
     */
    public static void executeXQuery(String xqueryFile, String outputXml, String apogee) throws SaxonApiException, IOException {
        Processor processor = new Processor(false);
        XQueryCompiler compiler = processor.newXQueryCompiler();
        XQueryExecutable executable = compiler.compile(new File(xqueryFile));
        XQueryEvaluator evaluator = executable.load();

        // Définir la variable externe pour le numéro d'Apogée
        QName apogeeParam = new QName("apogee");
        XdmAtomicValue apogeeValue = new XdmAtomicValue(apogee);
        evaluator.setExternalVariable(apogeeParam, apogeeValue);

        // Exécuter et stocker le résultat
        XdmValue result = evaluator.evaluate();
        writeXmlToFile(result, outputXml);
    }

    /**
     * Sauvegarde le résultat de XQuery dans un fichier XML.
     */
    private static void writeXmlToFile(XdmValue result, String filePath) throws IOException {
        File file = new File(filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write(result.toString());
        }
    }

    /**
     * Transforme un fichier XML en PDF à l'aide de XSL-FO et Apache FOP.
     */
    public static void generatePDF(String xmlFile, String xslFoFile, String outputPdf) throws Exception {
        File xmlSource = new File(xmlFile);
        File xslFoSource = new File(xslFoFile);
        File pdfFile = new File(outputPdf);

        FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
        OutputStream out = new BufferedOutputStream(new FileOutputStream(pdfFile));

        try {
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer(new StreamSource(xslFoSource));

            Source src = new StreamSource(xmlSource);
            Result res = new SAXResult(fop.getDefaultHandler());
            transformer.transform(src, res);
        } finally {
            out.close();
        }
    }
}
