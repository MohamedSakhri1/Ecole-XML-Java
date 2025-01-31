package org.ecolexml.ecole_xml_java.GenerateursPDF;

import static org.ecolexml.ecole_xml_java.GenerateursPDF.AttestationReussitePDF.*;

public class ReleveNotes {
    public static void main(String[] args) {
        try {
            // ---------------------- Paramètre : Numéro d'Apogée
            String apogee = "21010401"; // Numéro d'exemple

            // ---------------------- Étape 1 : Exécution de XQuery pour générer le fichier XML
            String xqueryFilePath = "src/main/resources/Fichiers_XQuery/getNotesEtudiant.xq";
            String outputXmlPath = "src/main/resources/Fichiers_XQuery/Releves_notes_result_avec_Xquery/NotesEtudiant_"+apogee+".xml";
            executeXQuery(xqueryFilePath, outputXmlPath, apogee);

            // ---------------------- Étape 2 : Génération du PDF avec FOP
            String xslFoPath = "src/main/resources/Fichiers_XSL_FO/ReleveDeNote.xsl";
            String outputPdfPath = "src/main/resources/Documents_PDF/ReleveNote/ReleveNote_"+apogee+".pdf";
            generatePDF(outputXmlPath, xslFoPath, outputPdfPath);

            System.out.println("✅ PDF généré avec succès : " + outputPdfPath);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ Erreur lors de la génération du PDF !");
        }
    }
}
