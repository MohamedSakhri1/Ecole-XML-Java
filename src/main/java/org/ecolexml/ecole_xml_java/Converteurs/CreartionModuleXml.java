package org.ecolexml.ecole_xml_java.Converteurs;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.ss.usermodel.*;

import java.io.File;

public class CreartionModuleXml {
    public static void main(String[] args) {
        try {
            // Lire le fichier Excel
            File file = new File("src/main/java/org/ecolexml/ecole_xml_java/Converteurs/GINF2.xlsx");
            Workbook workbook = WorkbookFactory.create(file);
            Sheet sheet = workbook.getSheetAt(0); // Première feuille

            // Préparer le document XML
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            // Racine <Modules>
            Element rootElement = doc.createElement("Modules");
            doc.appendChild(rootElement);

            // Parcourir les colonnes du fichier Excel pour récupérer les modules
            Row headerRow = sheet.getRow(2); // Ligne des noms de modules
            Row codeRow = sheet.getRow(1);   // Ligne des codes de modules

            String previousModuleName = "";
            String previousModuleCode = "";

            Element currentModule = null; // Variable pour garder une référence au module actuel

            for (int col = 7; col < headerRow.getLastCellNum(); col++) {
                // Récupération du nom et du code du module ou conservation des valeurs précédentes
                String moduleName = (headerRow.getCell(col) != null && headerRow.getCell(col).toString().trim().length() > 0)
                        ? headerRow.getCell(col).toString()
                        : previousModuleName;
                String moduleCode = (codeRow.getCell(col) != null && codeRow.getCell(col).toString().trim().length() > 0)
                        ? codeRow.getCell(col).toString()
                        : previousModuleCode;

                // Mise à jour des valeurs précédentes
                previousModuleName = moduleName;
                previousModuleCode = moduleCode;

                // Récupération des sous-modules depuis la ligne 4 (L4)
                Row subModuleRow = sheet.getRow(3); // Ligne L4 dans Excel (index 3 en Java)
                String subModuleName = (subModuleRow.getCell(col) != null && subModuleRow.getCell(col).toString().trim().length() > 0)
                        ? subModuleRow.getCell(col).toString()
                        : "";

                // Si le module change, créer un nouveau module
                if (currentModule == null || !currentModule.getAttribute("nom").equals(moduleName)) {
                    // Création du nouvel élément Module
                    currentModule = doc.createElement("Module");
                    currentModule.setAttribute("nom", moduleName);
                    currentModule.setAttribute("code", moduleCode);

                    // Ajouter le module à la racine
                    rootElement.appendChild(currentModule);
                }

                // Ajouter le sous-module au module actuel
                Element sousModule = doc.createElement("SousModule");
                sousModule.appendChild(createElement(doc, "Nom", subModuleName));
                currentModule.appendChild(sousModule);
            }





            // Écrire dans un fichier XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("/Users/anashilaly/Desktop/Ecole_XML_Java/src/main/resources/Fichiers XML/Module/Modules.xml"));

            transformer.transform(source, result);

            System.out.println("Fichier XML généré avec succès : Modules.xml");

            workbook.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Méthode utilitaire pour créer des éléments XML
    private static Element createElement(Document doc, String name, String value) {
        Element element = doc.createElement(name);
        element.appendChild(doc.createTextNode(value));
        return element;
    }
}
