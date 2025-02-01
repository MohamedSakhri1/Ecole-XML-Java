package org.ecolexml.ecole_xml_java.GenerateursPDF;

import net.sf.saxon.s9api.*;
import org.apache.fop.apps.*;

import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

/**
 * Classe pour générer l'attestation de réussite au format PDF.
 */
public class AttestationReussitePDF {

    /**
     * Méthode principale pour générer l'attestation de réussite en PDF.
     * @param apogee Le numéro d'apogée de l'étudiant.
     * @return Le fichier PDF généré.
     */
    public static File fn(String apogee) {
        try {
            // 1. Exécuter XQuery et générer le fichier XML pour l'étudiant.
            File xmlFile = new File("src/main/resources/Fichiers_XQuery/Attestation_reussite_result_avec_Xquery/NotesEtudiant" + apogee + ".xml");
            executeXQuery(apogee, xmlFile);

            // 2. Transformer le fichier XML en PDF avec XSL-FO.
            File xslFoFile = new File("src/main/resources/Fichiers_XSL_FO/attestationReussite.xsl");
            File pdfDir = new File("src/main/resources/Documents_PDF/AttestationReussite");
            if (!pdfDir.exists()) pdfDir.mkdirs(); // Créer le dossier si nécessaire
            File pdfFile = new File(pdfDir, "AttestationReussite_" + apogee + ".pdf");

            // Appel de la fonction pour générer le PDF
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
     * Exécute la requête XQuery pour récupérer les données de l'étudiant et les stocke dans un fichier XML.
     */
    public static void executeXQuery(String apogee, File outputFile) throws IOException, SaxonApiException {
        String xqueryFilePath = "src/main/resources/Fichiers_XQuery/getNotesEtudiant.xq"; // Fichier XQuery

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
    public static void writeResultToFile(XdmValue result, File file) throws IOException {
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
     * Cette méthode est modifiée pour correspondre exactement à la logique utilisée dans AttestationScolaritePDF.
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
            // Ferme le flux de sortie
            out.close();
        }
    }
}
