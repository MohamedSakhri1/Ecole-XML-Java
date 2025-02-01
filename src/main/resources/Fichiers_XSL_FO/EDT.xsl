<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format">

    <xsl:output method="xml" indent="yes"/>

    <xsl:template match="/">
        <fo:root>
            <!-- Configuration de la page en mode paysage -->
            <fo:layout-master-set>
                <fo:simple-page-master master-name="A4-landscape"
                                       page-width="297mm"
                                       page-height="210mm"
                                       margin-top="10mm"
                                       margin-bottom="10mm"
                                       margin-left="10mm"
                                       margin-right="10mm">
                    <fo:region-body margin-top="15mm" margin-bottom="15mm"/>
                    <fo:region-before extent="10mm"/>
                    <fo:region-after extent="20mm"/>
                </fo:simple-page-master>
            </fo:layout-master-set>

            <fo:page-sequence master-reference="A4-landscape">
                <fo:flow flow-name="xsl-region-body">

                    <!-- Titre centré -->
                    <fo:block text-align="center" font-size="16pt" font-weight="bold" space-after="10mm">
                        Emploi du Temps
                    </fo:block>

                    <!-- Tableau de l'emploi du temps -->
                    <fo:table table-layout="fixed" border="0.5pt solid black" width="100%">
                        <fo:table-column column-width="10%"/> <!-- Colonne des horaires -->
                        <fo:table-column column-width="15%"/>
                        <fo:table-column column-width="15%"/>
                        <fo:table-column column-width="15%"/>
                        <fo:table-column column-width="15%"/>
                        <fo:table-column column-width="15%"/>
                        <fo:table-column column-width="15%"/>

                        <!-- En-tête des jours -->
                        <fo:table-header>
                            <fo:table-row background-color="#DDD">
                                <fo:table-cell border="0.5pt solid black">
                                    <fo:block text-align="center" font-weight="bold">Heures</fo:block>
                                </fo:table-cell>
                                <xsl:for-each select="Eemploi_de_temps/Filiere/Jour">
                                    <fo:table-cell border="0.5pt solid black">
                                        <fo:block text-align="center" font-weight="bold">
                                            <xsl:value-of select="@nom"/>
                                            <fo:block font-size="10pt" font-style="italic">
                                                <xsl:value-of select="@date"/>
                                            </fo:block>
                                        </fo:block>
                                    </fo:table-cell>
                                </xsl:for-each>
                            </fo:table-row>
                        </fo:table-header>

                        <!-- Corps du tableau avec les créneaux -->
                        <fo:table-body>
                            <xsl:for-each select="Eemploi_de_temps/Filiere/Jour[1]/Module/Creneau">
                                <xsl:variable name="heure" select="Heure"/>
                                <fo:table-row>
                                    <!-- Colonne des horaires -->
                                    <fo:table-cell border="0.5pt solid black" background-color="#EEE">
                                        <fo:block text-align="center">
                                            <xsl:value-of select="$heure"/>
                                        </fo:block>
                                    </fo:table-cell>

                                    <!-- Colonnes des jours -->
                                    <xsl:for-each select="../../..//Jour">
                                        <xsl:variable name="creneau" select=".//Module/Creneau[Heure=$heure]"/>
                                        <fo:table-cell border="0.5pt solid black">
                                            <xsl:choose>
                                                <xsl:when test="$creneau">
                                                    <fo:block text-align="center" font-weight="bold" background-color="{chooseColor($creneau/@type)}">
                                                        <xsl:value-of select="$creneau/../@nom"/>
                                                        <fo:block font-size="10pt">
                                                            Prof: <xsl:value-of select="$creneau/Prof"/>
                                                        </fo:block>
                                                        <fo:block font-size="10pt">
                                                            Salle: <xsl:value-of select="$creneau/Salle"/>
                                                        </fo:block>
                                                        <fo:block font-size="9pt" font-style="italic">
                                                            <xsl:value-of select="$creneau/@type"/>
                                                        </fo:block>
                                                    </fo:block>
                                                </xsl:when>
                                                <xsl:otherwise>
                                                    <fo:block>-</fo:block>
                                                </xsl:otherwise>
                                            </xsl:choose>
                                        </fo:table-cell>
                                    </xsl:for-each>
                                </fo:table-row>
                            </xsl:for-each>
                        </fo:table-body>
                    </fo:table>

                    <!-- Légende -->
                    <fo:block space-before="10mm" font-size="10pt">
                        <fo:block font-weight="bold">Légende des en-têtes :</fo:block>
                        <fo:block>CM : Cours Magistraux</fo:block>
                        <fo:block>TD : Travaux Dirigés</fo:block>
                        <fo:block>TP : Travaux Pratiques</fo:block>
                    </fo:block>

                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>
