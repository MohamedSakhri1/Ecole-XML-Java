module org.ecolexml.ecole_xml_java {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.ecolexml.ecole_xml_java to javafx.fxml;
    exports org.ecolexml.ecole_xml_java;
}