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

public class CreartionNoteXml {
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

            // Racine <Notes>
            Element rootElement = doc.createElement("Notes");
            doc.appendChild(rootElement);

            String previousModuleCode = ""; // Stocker la valeur précédente pour les cellules vides de ModuleCode

            // Parcourir les lignes des étudiants
            for (int rowIndex = 5; rowIndex <= sheet.getLastRowNum(); rowIndex++) { // Les données commencent à la ligne 6
                Row row = sheet.getRow(rowIndex);
                if (row == null) continue; // Ignorer les lignes vides

                // Récupérer CodeApogee (colonne A, index 0)
                Cell cellCodeApogee = row.getCell(0);
                String codeApogee = cellCodeApogee != null ? String.valueOf((int) cellCodeApogee.getNumericCellValue()) : "";

                // Parcourir les colonnes pour récupérer les moyennes et informations associées
                for (int col = 7; col < row.getLastCellNum(); col++) { // Colonnes des modules
                    Cell cellMoyenne = row.getCell(col);
                    String moyenne = cellMoyenne != null ? String.valueOf((int) cellMoyenne.getNumericCellValue()) : "";

                    if (moyenne.isEmpty()) continue; // Ignorer les colonnes sans moyenne

                    // Récupérer SousModule (ligne 4, index 3)
                    Cell cellSousModule = sheet.getRow(3).getCell(col);
                    String sousModule = cellSousModule != null ? cellSousModule.toString().trim() : "";

                    // Récupérer ModuleCode (ligne 2, index 1) ou garder la valeur précédente
                    Cell cellModuleCode = sheet.getRow(1).getCell(col);
                    String moduleCode = cellModuleCode != null && !cellModuleCode.toString().trim().isEmpty()
                            ? cellModuleCode.toString().trim()
                            : previousModuleCode;

                    // Mettre à jour la valeur précédente de ModuleCode si la cellule n'est pas vide
                    if (cellModuleCode != null && !cellModuleCode.toString().trim().isEmpty()) {
                        previousModuleCode = moduleCode;
                    }

                    // Créer l'élément <Note>
                    Element note = doc.createElement("Note");

                    // Ajouter les sous-éléments <CodeApogee>, <ModuleCode>, <SousModule>, <Moyenne>
                    Element elementCodeApogee = doc.createElement("CodeApogee");
                    elementCodeApogee.appendChild(doc.createTextNode(codeApogee));
                    note.appendChild(elementCodeApogee);

                    Element elementModuleCode = doc.createElement("ModuleCode");
                    elementModuleCode.appendChild(doc.createTextNode(moduleCode));
                    note.appendChild(elementModuleCode);

                    Element elementSousModule = doc.createElement("SousModule");
                    elementSousModule.appendChild(doc.createTextNode(sousModule));
                    note.appendChild(elementSousModule);

                    Element elementMoyenne = doc.createElement("Moyenne");
                    elementMoyenne.appendChild(doc.createTextNode(moyenne));
                    note.appendChild(elementMoyenne);

                    // Ajouter l'élément <Note> à la racine <Notes>
                    rootElement.appendChild(note);
                }
            }

            // Écrire dans un fichier XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("/Users/anashilaly/Desktop/Ecole_XML_Java/src/main/resources/Fichiers XML/Note/Notes.xml"));

            transformer.transform(source, result);

            System.out.println("Fichier XML généré avec succès : Notes.xml");

            workbook.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
