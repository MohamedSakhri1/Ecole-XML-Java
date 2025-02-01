package org.ecolexml.ecole_xml_java.Api;

import org.ecolexml.ecole_xml_java.GenerateursHTML.EdtHTML;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/edt")
    public String getEdt(){
        EdtHTML.fn(true);
        return "EDT";
    }
}
