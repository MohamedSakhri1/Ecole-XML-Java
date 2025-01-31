package org.ecolexml.ecole_xml_java.GenerateursHTML;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class EdtHTML {
    public static void main(String[] args) {
        try {
            // path de fichier XML
            String fileName = "src/main/resources/Fichiers_XML/Emploi du temps/Emploi_du_temps.xml";
            // Charger le fichier XML
            File xmlFile = new File(fileName);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            // path du ficher HTML a génerer
            String htmlFilePath = "src/main/resources/Documents_HTML/EDT/EDT.html";
            // Générer le fichier HTML
            generateHTML(doc,htmlFilePath);
            System.out.println("Emploi du temps généré : "+ htmlFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void generateHTML(Document doc, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Début du HTML
            writer.write("<!DOCTYPE html>\n<html>\n<head>\n");
            writer.write("<style>\n");
            writer.write("body { position: relative; font-family: Arial; }\n");
            writer.write(".emploi { position: relative; width: 1000px; height: 800px; background-size: contain; }\n");
            writer.write(".date-de-jour {position: absolute; top: 5px; }\n");
            writer.write(".cours { position: absolute; width: 154px; height: 82px; background: #fff; border-radius: 10px; border: solid .5px; overflow: hidden; text-align: center; font-size: 10px; }\n");
            writer.write(".top { padding: 2px; border-bottom: black solid .5px;}\n");
            writer.write(".buttom {overflow: inherit; padding-top: 8px; margin: 5px;}\n");
            writer.write("p {margin: 0px;}\nstrong {font-size: 13px; text-wrap-mode: nowrap;}\n");
            writer.write("</style>\n</head>\n<body>\n");
            writer.write("<div class='emploi'>\n");
            writer.write("<img src='Images/EDT_BASE.png' \n" +
                            " style='position:absolute; width:100%; z-index:-1; left: 50%;'>");

            // Mapping des jours et des heures pour le positionnement
            Map<String, Integer> joursPosition = Map.of(
                    "Lundi", 548, "Mardi", 707, "Mercredi", 866,
                    "Jeudi", 1026, "Vendredi", 1184, "Samedi",1342
            );
            Map<String, Integer> heuresPosition = Map.of(
                    "09:00", 116,
                    "11:00",217,
                    "13:30",358,
                    "14:30",415,
                    "15:30", 472,
                    "16:30", 529
            );
            Map<String,String> creneauBackground = Map.of(
                    "CM","#B5A9FC",
                    "TD", "#F9FCA9",
                    "TP","#A9FCAD"
            );


            // Parcourir les jours et créneaux
            NodeList jours = doc.getElementsByTagName("Jour");
            for (int i = 0; i < jours.getLength(); i++) {
                Element jourElement = (Element) jours.item(i);
                String jour = jourElement.getAttribute("nom");
                int left = joursPosition.getOrDefault(jour, 0);

                NodeList modules = jourElement.getElementsByTagName("Module");
                for (int j = 0; j < modules.getLength(); j++) {
                    Element moduleElement = (Element) modules.item(j);
                    String moduleNom = moduleElement.getAttribute("nom");

                    NodeList creneaux = moduleElement.getElementsByTagName("Creneau");
                    for (int k = 0; k < creneaux.getLength(); k++) {
                        Element creneauElement = (Element) creneaux.item(k);
                        String creneauType = creneauElement.getAttribute("type");
                        String backgroundColor = creneauBackground.getOrDefault(creneauType,"#B5A9FC");
                        String heure = creneauElement.getElementsByTagName("Heure").item(0).getTextContent();
                        String prof = creneauElement.getElementsByTagName("Prof").item(0).getTextContent();
                        String salle = creneauElement.getElementsByTagName("Salle").item(0).getTextContent();

                        int top = heuresPosition.getOrDefault(heure.split(" - ")[0], 0);

                        // Ajouter le bloc HTML
                        writer.write("<div class='cours' style='top:" + top + "px; left:" + left + "px;'>\n");
                        writer.write("<div class='top' style='background-color:"+ backgroundColor+" ;'>" +
                                                creneauType + " - " + heure +
                                         "</div>\n");
                        writer.write("<div class='buttom' >\n" +
                                "<strong>" + moduleNom.toUpperCase() + "</strong>\n");
                        writer.write("<p style=' font-size: 11px;'>"+prof.toUpperCase() + "</p>\n");
                        writer.write("<p>Salle: " + salle.toUpperCase() + "</p>\n");
                        writer.write("</div>\n");
                        writer.write("</div>\n");
                    }
                }
            }

            // ajouter des dates de la semaine courante
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");
            // Obtenir la date d'aujourd'hui
            LocalDate today = LocalDate.now();
            // Trouver le lundi de la semaine courante
            LocalDate lundi = today.with(DayOfWeek.MONDAY);

            Map<String, Integer> datePositions = Map.of(
                    "MONDAY", 610, "TUESDAY", 769, "WEDNESDAY", 929,
                    "THURSDAY", 1088, "FRIDAY", 1247, "SATURDAY",1406
            );
            // Afficher les dates de la semaine (Lundi → Samedi)
            for (int i = 0; i < 6; i++) {
                LocalDate jour = lundi.plusDays(i);
                Integer datePosition = datePositions.getOrDefault(jour.getDayOfWeek().toString(),0);
                writer.write("<h5 class='date-de-jour' style='left:" + datePosition + "px;'>"+jour.format(formatter) +"</h5>\n");
            }

            // Fin du HTML
            writer.write("</div>\n</body>\n</html>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

