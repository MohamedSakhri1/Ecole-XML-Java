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

public class CreartionEtudiantXml {
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

            // Racine <Etudiants>
            Element rootElement = doc.createElement("Etudiants");
            doc.appendChild(rootElement);

            // Parcourir les lignes du fichier Excel
            for (Row row : sheet) {
                if (row.getRowNum() < 6) continue; // Ignorer la ligne d'en-tête

                // Création de l'élément <Etudiant>
                Element etudiant = doc.createElement("Etudiant");

                // Récupérer les données des colonnes Excel
                String codeApogee = String.valueOf((int) row.getCell(0).getNumericCellValue());
                String cin = row.getCell(1).toString();
                String cne = row.getCell(2).toString();
                String nom = row.getCell(3).toString();
                String prenom = row.getCell(4).toString();
                String lieuNaissance = row.getCell(5).toString();
                String dateNaissance = row.getCell(6).toString();

                // Ajouter les éléments XML
                etudiant.appendChild(createElement(doc, "CodeApogee", codeApogee));
                etudiant.appendChild(createElement(doc, "CIN", cin));
                etudiant.appendChild(createElement(doc, "CNE", cne));
                etudiant.appendChild(createElement(doc, "Nom", nom));
                etudiant.appendChild(createElement(doc, "Prenom", prenom));
                etudiant.appendChild(createElement(doc, "LieuNaissance", lieuNaissance));
                etudiant.appendChild(createElement(doc, "DateNaissance", dateNaissance));

                // Champs manquants (hardcodés)
                etudiant.appendChild(createElement(doc, "Email", nom.toLowerCase() + "." + prenom.toLowerCase() + "@etu.uae.ac.ma"));
                etudiant.appendChild(createElement(doc, "Telephone", "06" + (int)(Math.random() * 100000000))); // Numéro aléatoire
                String fileName = file.getName(); // Récupère "GINF2.xlsx"
                String className = fileName.substring(0, fileName.lastIndexOf('.')).toUpperCase(); // "GINF2"
                if(className.equals("GINF2")){
                    etudiant.appendChild(createElement(doc, "Premiere_inscription", "2021/2022"));
                }else if (className=="GINF3"){
                    etudiant.appendChild(createElement(doc, "Premiere_inscription", "2020/2021"));
                }else {
                    etudiant.appendChild(createElement(doc, "Premiere_inscription", "2022/2023"));
                }

                etudiant.appendChild(createElement(doc, "Classe", className));
                etudiant.appendChild(createElement(doc, "Photo_path", "/Users/anashilaly/Desktop/Ecole_XML_Java/src/main/java/org/ecolexml/ecole_xml_java/Converteurs/images.png")); // Par défaut

                // Ajouter <Etudiant> à la racine
                rootElement.appendChild(etudiant);
            }

            // Écrire dans un fichier XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("/Users/anashilaly/Desktop/Ecole_XML_Java/src/main/resources/Fichiers XML/Etudiant/Etudiants.xml"));

            transformer.transform(source, result);

            System.out.println("Fichier XML généré avec succès : etudiants.xml");

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
