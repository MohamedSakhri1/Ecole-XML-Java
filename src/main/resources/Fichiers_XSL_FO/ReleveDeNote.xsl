<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format" exclude-result-prefixes="fo"
                xmlns:date="http://exslt.org/dates-and-times">

    <xsl:template match="/">
        <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
            <fo:layout-master-set>
                <fo:simple-page-master master-name="A4"
                                       page-width="210mm" page-height="297mm"
                                       margin="1cm">
                    <fo:region-body margin="1cm" margin-top="4cm"/>
                    <fo:region-before extent="4cm"/>
                    <fo:region-after extent="0.5cm"/>
                </fo:simple-page-master>
            </fo:layout-master-set>

            <fo:page-sequence master-reference="A4">
                <!-- Contenu de la tête de page -->
                <fo:static-content flow-name="xsl-region-before">
                    <fo:block text-align="center">
                        <fo:external-graphic src="src/main/resources/Images/headerReleve2.png" content-width="55%"/>
                    </fo:block>
                </fo:static-content>
                <!-- Contenu du pied de page : numéro de la page -->
                <fo:static-content flow-name="xsl-region-after">
                    <fo:block text-align="center">
                        <fo:block text-align="left" font-size="10pt" margin-left="1cm">
                            <fo:block>Avis important: il ne peut être délivré qu'un seul exemplaire de cette attestation. Aucun duplicate ne sera fourni.</fo:block>
                        </fo:block>
                    </fo:block>
                </fo:static-content>
                <!-- Contenu de la partie centrale -->
                <fo:flow flow-name="xsl-region-body">

                    <fo:block text-align="center" font-weight="bold" font-size="18pt"
                              margin-bottom="1pt" border="2pt solid black" background-color="lightgray">
                        RELEVE DE NOTES ET RESULTATS
                    </fo:block>
                    <fo:block text-align="center" margin-right="4cm" margin-left="4cm" margin-top="0.3cm"
                              font-weight="bold" font-size="14pt" margin-bottom="0.5cm"
                              border="1pt solid black" background-color="lightgray">
                        Session 1
                    </fo:block>

                    <fo:block>
                        <fo:table width="50%">
                            <fo:table-body>
                                <fo:table-row>
                                    <fo:table-cell>
                                        <fo:block font-weight="bold">
                                            <xsl:value-of select="concat(//Etudiant/Nom, ' ', //Etudiant/Prenom)"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                                <fo:table-row>
                                    <fo:table-cell padding-top="0.3cm">
                                        <fo:block>
                                            N° Etudiant : <xsl:value-of select="//Etudiant/CodeApogee"/>
                                        </fo:block>
                                    </fo:table-cell>
                                </fo:table-row>
                            </fo:table-body>
                        </fo:table>

                        <fo:block margin-top="0.2cm">
                            a obtenu les notes suivantes :
                        </fo:block>

                        <fo:table margin-top="0.4cm" border-style="solid" border-width="1pt">
                            <fo:table-column column-width="45%"/>
                            <fo:table-column column-width="15%"/>
                            <fo:table-column column-width="25%"/>
                            <fo:table-column column-width="15%"/>
                            <fo:table-body>

                                <!-- En-tête du tableau -->
                                <fo:table-row border-style="solid" border-width="1pt">
                                    <fo:table-cell text-align="center" border-right="1pt solid black">
                                        <fo:block>Module</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border-right="1pt solid black">
                                        <fo:block text-align="center">Note/Bareme</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell border-right="1pt solid black">
                                        <fo:block text-align="center">Session</fo:block>
                                    </fo:table-cell>
                                    <fo:table-cell>
                                        <fo:block text-align="center">Pts jury</fo:block>
                                    </fo:table-cell>
                                </fo:table-row>

                                <!-- Regroupement des sous-modules par module -->
                                <xsl:for-each-group select="//Etudiant/NotesEtudiant/Note" group-by="ModuleCode">
                                    <xsl:variable name="moduleCode" select="current-grouping-key()"/>
                                    <xsl:variable name="moduleMoyenne" select="avg(current-group()/Moyenne)"/>

                                    <fo:table-row border-style="double" border-width="1pt">
                                        <fo:table-cell padding-top="0.1cm" padding-bottom="0.1cm" border-right="1pt solid black">
                                            <fo:block>
                                                <xsl:value-of select="$moduleCode"/>
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell padding-top="0.1cm" padding-bottom="0.1cm" border-right="1pt solid black">
                                            <fo:block text-align="center">
                                                <xsl:value-of select="format-number($moduleMoyenne, '0.00')"/>/20
                                            </fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell padding-top="0.1cm" padding-bottom="0.1cm" border-right="1pt solid black">
                                            <fo:block text-align="center">Session 1 2024/2025</fo:block>
                                        </fo:table-cell>
                                        <fo:table-cell padding-top="0.1cm" padding-bottom="0.1cm">
                                            <fo:block/>
                                        </fo:table-cell>
                                    </fo:table-row>
                                </xsl:for-each-group>

                            </fo:table-body>
                        </fo:table>


                        <fo:block font-weight="bold" margin-top="0.3cm">
                            <fo:inline>Resultat d'admission session 1 : </fo:inline>
                            <fo:inline><xsl:value-of select="format-number(avg(//Etudiant/NotesEtudiant/Note/Moyenne), '0.000')"/> /20</fo:inline>
                        </fo:block>
                    </fo:block>

                    <fo:block text-align="center" margin-top="0.2cm">
                        <fo:external-graphic src="src/main/resources/Images/cachet.jpg" content-height="2.5cm"/>
                    </fo:block>

                    <fo:block text-align="left" margin-top="0.2cm">
                        <fo:block>Fait à TANGER, le <xsl:value-of select="format-date(current-date(), '[D01] [MN,*-3] [Y]')"/></fo:block>
                        <fo:block margin-top="0.2cm" margin-right="3cm">
                            Le Directeur de l'École Nationale des Sciences Appliquées de Tanger
                        </fo:block>
                    </fo:block>

                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>
