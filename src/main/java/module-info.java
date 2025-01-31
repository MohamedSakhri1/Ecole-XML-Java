module org.ecolexml.ecole_xml_java {
    requires javafx.controls;
    requires javafx.fxml;
    requires Saxon.HE;
    requires org.apache.poi.poi;
    requires java.xml;


    opens org.ecolexml.ecole_xml_java to javafx.fxml;
    exports org.ecolexml.ecole_xml_java;
}