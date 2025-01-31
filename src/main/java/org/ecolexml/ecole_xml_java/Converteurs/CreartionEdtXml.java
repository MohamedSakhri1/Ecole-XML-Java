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

public class CreartionEdtXml {
    public static void main(String[] args) {
        try {
            // Lire le fichier Excel
            File file = new File("/Users/anashilaly/Desktop/Ecole_XML_Java/src/main/java/org/ecolexml/ecole_xml_java/Converteurs/EDT_GINF2.xlsx");
            Workbook workbook = WorkbookFactory.create(file);
            Sheet sheet = workbook.getSheetAt(0); // Première feuille

            // Préparer le document XML
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            // Racine <Eemploi_de_temps>
            Element rootElement = doc.createElement("Eemploi_de_temps");
            rootElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            rootElement.setAttribute("xsi:noNamespaceSchemaLocation", "Emploi_de_temps.XSD");
            doc.appendChild(rootElement);

            // Ajouter l'élément <Filiere>
            Element filiere = doc.createElement("Filiere");
            filiere.setAttribute("nom", "ginf2"); // Nom de la filière
            rootElement.appendChild(filiere);

            // Initialiser les variables pour gérer le jour précédent
            String previousDay = "";
            Element jourElement = null;

            // Parcourir les lignes du fichier Excel
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);

                // Vérifier si la ligne est vide
                if (row == null || isRowEmpty(row)) {
                    continue; // Ignorer les lignes vides
                }

                // Récupérer les données directement
                String jour = row.getCell(0).toString().trim();
                String heureDebut = row.getCell(1).toString().trim();
                String heureFin = row.getCell(2).toString().trim();
                String type = row.getCell(3).toString().trim();
                String module = row.getCell(4).toString().trim();
                String prof = row.getCell(5).toString().trim();
                String salle = row.getCell(6).toString().trim();

                // Si le jour change, créer un nouvel élément <Jour>
                if (!jour.equals(previousDay)) {
                    jourElement = doc.createElement("Jour");
                    jourElement.setAttribute("nom", jour);
                    jourElement.setAttribute("date", "XX/XX"); // Ajouter une date par défaut ou modifiez selon vos besoins
                    filiere.appendChild(jourElement);
                    previousDay = jour; // Mettre à jour le jour précédent
                }

                // Ajouter un nouveau module pour chaque ligne
                Element moduleElement = doc.createElement("Module");
                moduleElement.setAttribute("nom", module);

                // Ajouter l'élément <Creneau>
                Element creneau = doc.createElement("Creneau");
                creneau.setAttribute("type", type);

                Element heure = doc.createElement("Heure");
                heure.appendChild(doc.createTextNode(heureDebut + " - " + heureFin));
                creneau.appendChild(heure);

                Element profElement = doc.createElement("Prof");
                profElement.appendChild(doc.createTextNode(prof));
                creneau.appendChild(profElement);

                Element salleElement = doc.createElement("Salle");
                salleElement.appendChild(doc.createTextNode(salle));
                creneau.appendChild(salleElement);

                // Ajouter le créneau au module
                moduleElement.appendChild(creneau);

                // Ajouter le module au jour
                if (jourElement != null) {
                    jourElement.appendChild(moduleElement);
                }
            }

            // Écrire dans un fichier XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("/Users/anashilaly/Desktop/Ecole_XML_Java/src/main/resources/Fichiers_XML/Emploi du temps/Emploi_du_temps.xml"));

            transformer.transform(source, result);

            System.out.println("Fichier XML généré avec succès : Emploi_du_temps.xml");

            workbook.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Vérifier si une ligne est vide
    private static boolean isRowEmpty(Row row) {
        for (int cellIndex = 0; cellIndex < row.getLastCellNum(); cellIndex++) {
            Cell cell = row.getCell(cellIndex);
            if (cell != null && !cell.toString().trim().isEmpty()) {
                return false; // La ligne n'est pas vide si une cellule contient une valeur
            }
        }
        return true; // La ligne est vide
    }
}
