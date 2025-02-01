package org.ecolexml.ecole_xml_java.Api;

import org.ecolexml.ecole_xml_java.GenerateursHTML.AffichageModuleHTML;
import org.ecolexml.ecole_xml_java.GenerateursHTML.EdtFromXSL;
import org.ecolexml.ecole_xml_java.GenerateursHTML.EdtHTML;
import org.ecolexml.ecole_xml_java.GenerateursPDF.AttestationReussitePDF;
import org.ecolexml.ecole_xml_java.GenerateursPDF.AttestationScolaritePDF;
import org.ecolexml.ecole_xml_java.GenerateursPDF.CarteEtudiant;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Controller
public class MainController {

    @GetMapping("/edt")
    public String getEdt(){
        EdtHTML.fn(true);
        return "EDT";
    }

    @GetMapping("/affichage/html")
    public ResponseEntity<String> getAffichageHTML(@RequestParam(name = "module", required = false, defaultValue = "GINF31") String moduleCode) {
        try {
            // Générer le fichier HTML et récupérer son chemin
            String stringPath = AffichageModuleHTML.fn(moduleCode);

            // Construire le chemin absolu du fichier généré
            Path filePath = Path.of(stringPath);

            // Vérifier si le fichier existe
            if (!Files.exists(filePath)) {
                return ResponseEntity.status(404).body("<h1>Erreur : Fichier HTML introuvable</h1>");
            }

            // Lire le contenu du fichier HTML
            String content = Files.readString(filePath, StandardCharsets.UTF_8);
            return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(content);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("<h1>Erreur lors de la lecture du fichier HTML</h1>");
        }
    }

    @GetMapping("/attestationScolarite/pdf")
    public ResponseEntity<byte[]> generateAttestationScolaritePDF(@RequestParam String apogee) {
        try {
            // Appel de la méthode fn de AttestationScolaritePDF pour générer le PDF
            File pdfFile = AttestationScolaritePDF.fn(apogee);

            if (pdfFile == null || !pdfFile.exists()) {
                return ResponseEntity.status(404).body(("PDF introuvable").getBytes());
            }

            // Lire le fichier PDF généré
            byte[] pdfBytes = new byte[(int) pdfFile.length()];
            try (FileInputStream fis = new FileInputStream(pdfFile)) {
                fis.read(pdfBytes);
            }

            // Retourner le PDF avec le bon type MIME et le header pour ouvrir dans le navigateur
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header("Content-Disposition", "inline; filename=\"" + pdfFile.getName() + "\"")
                    .body(pdfBytes);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(("Erreur lors de la génération du PDF").getBytes());
        }
    }


    @GetMapping("/attestationReussite/pdf")
    public ResponseEntity<byte[]> generateAttestationReussitePDF(@RequestParam String apogee) {
        try {
            // Appel de la méthode fn de AttestationReussitePDF
            File pdfFile = AttestationReussitePDF.fn(apogee);

            if (pdfFile == null || !pdfFile.exists()) {
                return ResponseEntity.status(404).body(("PDF introuvable").getBytes());
            }

            // Lire le fichier PDF généré
            byte[] pdfBytes = new byte[(int) pdfFile.length()];
            try (FileInputStream fis = new FileInputStream(pdfFile)) {
                fis.read(pdfBytes);
            }

            // Retourner le PDF avec le bon type MIME et en-têtes pour l'affichage dans le navigateur
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=AttestationReussite_" + apogee + ".pdf")
                    .body(pdfBytes);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(("Erreur lors de la génération du PDF").getBytes());
        }
    }



    @GetMapping("/CarteEtudiant/pdf")
    public ResponseEntity<byte[]> generateCarteEtudiantPDF(@RequestParam String apogee) {
        try {
            // Appel de la méthode fn de CarteEtudiant pour générer le PDF
            File pdfFile = CarteEtudiant.fn(apogee);

            if (pdfFile == null || !pdfFile.exists()) {
                return ResponseEntity.status(404).body(("PDF introuvable").getBytes());
            }

            // Lire le fichier PDF généré
            byte[] pdfBytes = new byte[(int) pdfFile.length()];
            try (FileInputStream fis = new FileInputStream(pdfFile)) {
                fis.read(pdfBytes);
            }

            // Retourner le PDF avec le bon type MIME
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(pdfBytes);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(("Erreur lors de la génération du PDF").getBytes());
        }
    }
    private String HtmlParser(String HtmlFilePath) {
        return null;
    }




}
