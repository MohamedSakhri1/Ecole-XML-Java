package org.ecolexml.ecole_xml_java;

import net.sf.saxon.s9api.*;

import java.io.*;

public class TestXquery {

    public static void main(String[] args) throws SaxonApiException, IOException {
        // Définir le chemin du fichier XQuery
        String xqueryFilePath = "src/main/resources/Fichiers XQuery/getModuleResults.xquery";

        // ---------------------- Paramètre externe
        String moduleCode = "GINF32";

        // Chemin de sortie du fichier XML
        String outputFilePath = "src/main/resources/Fichiers XML/Affichage de module/Affichage_"+moduleCode+".xml";

        // Créer le processor Saxon
        Processor processor = new Processor(false);

        // Compiler le fichier XQuery
        XQueryCompiler compiler = processor.newXQueryCompiler();
        XQueryExecutable executable = compiler.compile(new File(xqueryFilePath));

        // Charger l'exécutable XQuery
        XQueryEvaluator evaluator = executable.load();

        // Définir le paramètre externe
        QName moduleCodeParam = new QName("moduleCode");
        XdmAtomicValue moduleCodeValue = new XdmAtomicValue(moduleCode);
        evaluator.setExternalVariable(moduleCodeParam, moduleCodeValue);

        // Exécuter la requête XQuery
        XdmValue result = evaluator.evaluate();

        // Écrire le résultat dans un fichier XML
        writeResultToFile(result, outputFilePath);

        System.out.println("Résultat stocké dans : " + outputFilePath);
    }

    /**
     * Écrit le résultat XdmValue dans un fichier XML avec une référence DTD.
     */
    private static void writeResultToFile(XdmValue result, String filePath) throws IOException {
        File outputFile = new File(filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            // Ajout de la déclaration XML et de la référence DTD
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n");

            // Ajout du contenu XML généré
            for (XdmItem item : result) {
                writer.write(item.toString());
                writer.write("\n");
            }
        }
    }
}
