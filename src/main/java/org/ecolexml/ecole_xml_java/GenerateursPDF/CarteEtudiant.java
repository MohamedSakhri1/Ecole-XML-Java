package org.ecolexml.ecole_xml_java.GenerateursPDF;

import net.sf.saxon.s9api.*;
import org.apache.fop.apps.*;

import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

public class CarteEtudiant {

    public static void main(String[] args) {
        try {
            String apogee = "21010278"; // Numéro de l'étudiant à rechercher

            // ✅ Étape 1 : Exécuter XQuery pour récupérer les données de l’étudiant
            File xmlFile = new File("src/main/resources/Fichiers_XQuery/Carte_Etudiant_result_avec_Xquery/CarteEtudiant_" + apogee + ".xml");
            executeXQuery(apogee, xmlFile);

            // ✅ Étape 2 : Transformer le fichier XML en PDF avec XSL-FO
            File xslFoFile = new File("src/main/resources/Fichiers_XSL_FO/CarteEtudiant.xsl");
            File pdfDir = new File("src/main/resources/Documents_PDF/CarteEtudiant");
            pdfDir.mkdirs();
            File pdfFile = new File(pdfDir, "CarteEtudiant_" + apogee + ".pdf");

            generatePDF(xmlFile, xslFoFile, pdfFile);
            System.out.println("✅ PDF généré avec succès : " + pdfFile.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ Erreur lors de la génération du PDF !");
        }
    }

    /**
     * Exécute la requête XQuery pour récupérer les données de l'étudiant et les stocke dans un fichier XML.
     */
    private static void executeXQuery(String apogee, File outputFile) throws IOException, SaxonApiException {
        String xqueryFilePath = "src/main/resources/Fichiers_XQuery/getStudentByApogee.xq"; // Fichier XQuery

        Processor processor = new Processor(false);
        XQueryCompiler compiler = processor.newXQueryCompiler();
        XQueryExecutable executable = compiler.compile(new File(xqueryFilePath));
        XQueryEvaluator evaluator = executable.load();

        // Définir le paramètre externe de l'Apogée
        QName apogeeParam = new QName("apogee");
        evaluator.setExternalVariable(apogeeParam, new XdmAtomicValue(apogee));

        // Exécuter XQuery et stocker le résultat
        XdmValue result = evaluator.evaluate();
        writeResultToFile(result, outputFile);
    }

    /**
     * Écrit le résultat XdmValue dans un fichier XML.
     */
    private static void writeResultToFile(XdmValue result, File file) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
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
    private static void generatePDF(File xmlFile, File xslFoFile, File pdfFile) throws Exception {
        FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();

        OutputStream out = new BufferedOutputStream(new FileOutputStream(pdfFile));
        try {
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xslFoFile));

            Source src = new StreamSource(xmlFile);
            Result res = new SAXResult(fop.getDefaultHandler());
            transformer.transform(src, res);
        } finally {
            out.close();
        }
    }
}
