package org.ecolexml.ecole_xml_java.Api;

import org.ecolexml.ecole_xml_java.GenerateursHTML.AffichageModuleHTML;
import org.ecolexml.ecole_xml_java.GenerateursHTML.EdtFromXSL;
import org.ecolexml.ecole_xml_java.GenerateursHTML.EdtHTML;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Controller
public class MainController {

    // HTML
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

            // Trouver le fichier HTML et le convertire en String
            String content = HtmlParser(stringPath);

            // Vérifier si le fichier existe
            if (content == null) {
                return ResponseEntity.status(404).body("<h1>Erreur : Fichier HTML introuvable</h1>");
            }
            return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(content);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("<h1>Erreur lors de la lecture du fichier HTML</h1>");
        }
    }

    private String HtmlParser(String HtmlFilePath) throws IOException {
        // Construire le chemin absolu du fichier généré
        Path filePath = Path.of(HtmlFilePath);

        // Vérifier si le fichier existe
        if (!Files.exists(filePath)) {
            return null;
        }
        // Lire le contenu du fichier HTML
        return Files.readString(filePath, StandardCharsets.UTF_8);
    }

    // PDF
    @GetMapping("affichage/pdf")
    public ResponseEntity<String> getAffichagePDF(@RequestParam(name = "module", required = false,defaultValue = "GINF31") String moduleCode) {
        return null;
    }



}
