<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format">

    <xsl:template match="/">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="AA"
                                       page-height="6cm"
                                       page-width="13cm"
                                       margin-top="0.2cm"
                                       margin-left="0.2cm"
                                       margin-right="0.1cm"
                                       margin-bottom="0.1cm">
                    <fo:region-body margin-top="3.2cm"/>
                    <fo:region-before extent="3cm"/>
                    <fo:region-after extent="1cm"/>
                    <fo:region-start extent="3cm"/>
                    <fo:region-end extent="3cm"/>
                </fo:simple-page-master>
            </fo:layout-master-set>

            <fo:page-sequence master-reference="AA">

                <!-- 🏷️ En-tête (Universite et École) -->
                <fo:static-content flow-name="xsl-region-before">
                    <fo:block font-family="Roboto" font-size="8px" color="#02306E" text-align="center" margin-top="0.05cm" margin-bottom="-0.02cm">
                        Université Abdelmalek Essaâdi
                    </fo:block>

                    <fo:block font-family="Roboto" font-size="8px" color="#02306E" text-align="center" margin-top="0.05cm" margin-bottom="-0.02cm">
                        École Nationale des Sciences Appliquées
                    </fo:block>

                    <fo:block font-family="Roboto" font-size="8px" color="#02306E" text-align="center" margin-top="0.05cm" margin-bottom="-0.02cm">
                        Tanger
                    </fo:block>

                    <fo:block color="#ff7f00" font-size="13" font-weight="bold" text-align="center">
                        ________________________________
                    </fo:block>

                    <fo:block font-family="Roboto" color="#191970" font-size="medium" font-weight="bold" text-align="center" margin-top="2px">
                        CARTE D'ETUDIANT
                    </fo:block>
                </fo:static-content>

                <!-- 🔹 Pied de page (Première inscription) -->
                <fo:static-content flow-name="xsl-region-after">
                    <fo:block font-family="Roboto" font-size="9px" font-weight="bold" color="#02306E" text-align="center" margin-top="0.5cm" margin-bottom="-0.02cm">
                        Première Inscription : <xsl:value-of select="/Etudiant/Premiere_inscription"/>
                    </fo:block>
                </fo:static-content>

                <!-- 🔹 Logo et Photo Étudiant -->
                <fo:static-content flow-name="xsl-region-start" >
                    <fo:block margin-left="9px">
                        <fo:external-graphic src="src/main/resources/images/logo.png" content-height="scale-to-fit"
                                             content-width="scale-to-fit"
                                             width="1.6cm"
                                             height="1.3cm"
                                             scaling="non-uniform"/>
                    </fo:block>
                    <fo:block>&#160;</fo:block>
                    <fo:block>&#160;</fo:block>

                    <!-- 📸 Photo Étudiant -->
                    <fo:block margin-left="15px">
                        <xsl:choose>
                            <xsl:when test="/Etudiant/Photo_path != 'null'">
                                <fo:external-graphic src="src/main/java/org/ecolexml/ecole_xml_java/Converteurs/{/Etudiant/Photo_path}"
                                                     content-height="scale-to-fit"
                                                     content-width="scale-to-fit"
                                                     width="1.85cm"
                                                     height="2.35cm"
                                                     scaling="non-uniform"/>
                            </xsl:when>
                            <xsl:otherwise>
                                <fo:external-graphic src="src/main/resources/images/default_photo.png" height="0.85in" content-width="0.85in"/>
                            </xsl:otherwise>
                        </xsl:choose>
                    </fo:block>

                    <fo:block space-before="199px"/>

                </fo:static-content>

                <!-- 🔹 Logo ENSA & Code Barre -->
                <fo:static-content flow-name="xsl-region-end">
                    <fo:block margin-left="18.5px">
                        <fo:external-graphic src="src/main/resources/images/Ensat.png" content-height="scale-to-fit"
                                             content-width="scale-to-fit"
                                             width="2.1cm"
                                             height="1.3cm"
                                             scaling="non-uniform"/>
                    </fo:block>
                    <fo:block text-align="center" padding-top="0.5in">
                        <fo:external-graphic src="src/main/resources/images/scanbar.png"  content-height="scale-to-fit"
                                             content-width="scale-to-fit"
                                             width="1.85cm"
                                             height="1.85cm"
                                             scaling="non-uniform"/>
                    </fo:block>
                </fo:static-content>

                <!-- 📄 Informations Étudiant -->
                <fo:flow flow-name="xsl-region-body">
                    <fo:block font-family="Roboto"  color="#02306E" font-size="7px" font-weight="bold" margin-left="80px" padding-top="4px">
                        <xsl:value-of select="/Etudiant/Nom"/>
                    </fo:block>
                    <fo:block font-family="Roboto"  color="#02306E" font-size="7px" font-weight="bold" margin-left="80px" padding-top="3px">
                        <xsl:value-of select="/Etudiant/Prenom"/>
                    </fo:block>
                    <fo:block font-family="Roboto"  color="#02306E" font-size="7px" font-weight="bold" margin-left="80px" padding-top="3px">
                        <xsl:value-of select="/Etudiant/CodeApogee"/>
                    </fo:block>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>
