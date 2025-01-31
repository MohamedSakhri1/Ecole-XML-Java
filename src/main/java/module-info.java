module org.ecolexml.ecole_xml_java {

    requires Saxon.HE;
    requires org.apache.poi.poi;
    requires fop;
    requires html2pdf;
    requires java.desktop;

    opens org.ecolexml.ecole_xml_java to javafx.fxml;
    exports org.ecolexml.ecole_xml_java;
}