module org.ecolexml.ecole_xml_java {

    requires Saxon.HE;
    requires org.apache.poi.poi;
    requires fop;
    requires java.desktop;
    requires spring.web;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires fop.core;
    requires spring.core;

    exports org.ecolexml.ecole_xml_java;
}