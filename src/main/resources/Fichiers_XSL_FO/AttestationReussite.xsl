<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format"
                exclude-result-prefixes="fo"
                xmlns:date="http://exslt.org/dates-and-times">

    <xsl:template match="/">
        <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
            <!-- Modèle de pages -->
            <fo:layout-master-set>
                <fo:simple-page-master master-name="A4"
                                       page-width="210mm" page-height="297mm"
                                       margin="1cm">
                    <fo:region-body margin="1cm" margin-top="4cm"/>
                    <fo:region-before extent="1.5cm"/>
                    <fo:region-after extent="1cm"/>
                </fo:simple-page-master>
            </fo:layout-master-set>

            <fo:page-sequence master-reference="A4">
                <!-- ✅ En-tête avec logo -->
                <fo:static-content flow-name="xsl-region-before">
                    <fo:block text-align="center"  >
                        <fo:external-graphic src="src/main/resources/images/headerReussite.png" content-width="95%"/>
                    </fo:block>
                </fo:static-content>


                <!-- ✅ Pied de page avec note et image -->
                <fo:static-content flow-name="xsl-region-after">
                    <fo:block text-align="left" font-size="10pt" margin-left="1cm">
                        <fo:block>Avis important: il ne peut être délivré qu'un seul exemplaire de cette attestation. Aucun duplicata ne sera fourni.</fo:block>
                    </fo:block>
                </fo:static-content>

                <fo:flow flow-name="xsl-region-body" width="100%" text-align="center">
                    <!-- ✅ Titre -->
                    <fo:block text-align="center" font-weight="bold" font-size="18pt" margin-bottom="10pt" border="2pt solid black">
                        ATTESTATION DE RÉUSSITE
                    </fo:block>

                    <!-- ✅ Infos étudiant -->
                    <fo:block margin-top="1cm">
                        Le Directeur de l'École Nationale des Sciences Appliquées de Tanger atteste que :
                    </fo:block>

                    <fo:block margin-top="0.6cm" font-weight="bold">
                        <xsl:value-of select="concat(Etudiant/Nom, ' ', Etudiant/Prenom)"/>
                    </fo:block>

                    <fo:block margin-top="0.6cm">
                        Né le <xsl:value-of select="Etudiant/DateNaissance"/> à <xsl:value-of select="Etudiant/LieuNaissance"/>
                    </fo:block>

                    <fo:block margin-top="0.6cm">
                        a été déclaré admis au niveau :
                    </fo:block>

                    <fo:block margin-top="0.6cm" font-weight="bold">
                        2ème Année Génie Informatique (Génie Logiciel et Systèmes d'Information)
                    </fo:block>

                    <!-- ✅ Calcul des moyennes et affichage -->
                    <fo:block margin-top="0.6cm">
                        au titre de l'année universitaire 2024/2025 avec la mention :
                    </fo:block>

                    <xsl:variable name="modules" select="distinct-values(Etudiant/NotesEtudiant/Note/ModuleCode)"/>
                    <xsl:variable name="moyenneGenerale">
                        <xsl:choose>
                            <xsl:when test="count($modules) > 0">
                                <xsl:value-of select="sum(for $m in $modules
                                                          return sum(Etudiant/NotesEtudiant/Note[ModuleCode = $m]/Moyenne)
                                                          div count(Etudiant/NotesEtudiant/Note[ModuleCode = $m]))
                                                 div count($modules)"/>
                            </xsl:when>
                            <xsl:otherwise>0</xsl:otherwise>
                        </xsl:choose>
                    </xsl:variable>

                    <fo:block font-weight="bold" text-align="center" border-bottom="1pt solid black" padding-bottom="11mm">
                        <xsl:choose>
                            <xsl:when test="$moyenneGenerale >= 16">
                                <xsl:text>Très bien </xsl:text>
                            </xsl:when>
                            <xsl:when test="$moyenneGenerale >= 14 and $moyenneGenerale &lt; 16">
                                <xsl:text>Bien </xsl:text>
                            </xsl:when>
                            <xsl:when test="$moyenneGenerale >= 12 and $moyenneGenerale &lt; 14">
                                <xsl:text>Assez bien </xsl:text>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:text>Passable </xsl:text>
                            </xsl:otherwise>
                        </xsl:choose>

                    </fo:block>

                    <!-- ✅ Cachet et signature -->
                    <fo:block margin-top="1cm" text-align="center">
                        <fo:external-graphic src="src/main/resources/images/cachet.jpg" content-height="3cm"/>
                    </fo:block>

                    <fo:block margin-top="3cm" text-align="right">
                        <fo:block text-align="right" margin-right="3cm">
                            Fait à TANGER, le <xsl:value-of select="format-date(current-date(), '[D01] [MN,*-3] [Y]')"/>
                        </fo:block>
                        <fo:block>Le Directeur de l'École Nationale des Sciences Appliquées de Tanger</fo:block>
                    </fo:block>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>
