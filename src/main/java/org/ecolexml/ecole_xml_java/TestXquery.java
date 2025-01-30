package org.ecolexml.ecole_xml_java;

import net.sf.saxon.s9api.*;

import javax.xml.transform.Source;
import java.io.File;
import java.io.IOException;

public class TestXquery {

    public static void main(String[] args) throws SaxonApiException, IOException {
        // Définir le chemin du fichier XQuery
        String xqueryFilePath = "src/main/resources/Fichiers XQuery/getModuleResults.xquery";

        // Paramètre externe que nous allons passer (par exemple, "math")
        String moduleCode = "GINF32";

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

        // Passer le paramètre externe à la requête
        evaluator.setExternalVariable(moduleCodeParam, moduleCodeValue);

        // Charger le fichier XML
        File xmlFile = new File("src/main/resources/FichiersXML/Module/Modules.xml");
        XdmNode xmlDocument = processor.newDocumentBuilder().build(xmlFile);

        // Exécuter la requête XQuery
        evaluator.setContextItem(xmlDocument);
        XdmValue result = evaluator.evaluate();

        // Afficher le résultat
        printXdmValue(result);
    }

    private static void printXdmValue(XdmValue result) {
        if (result.isEmpty()) {
            System.out.println("Aucun résultat trouvé.");
        } else {
            // Afficher chaque élément du résultat
            for (XdmItem item : result) {
                System.out.println(item.toString());
            }
        }
    }
}
