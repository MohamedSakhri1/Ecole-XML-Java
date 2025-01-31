<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format" exclude-result-prefixes="fo"
                xmlns:date="http://exslt.org/dates-and-times">
    <xsl:template match="/">
        <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
            <!-- Modèle de pages -->
            <fo:layout-master-set>
                <fo:simple-page-master master-name="A4"
                                       page-width="210mm" page-height="297mm"
                                       margin="1cm">
                    <!-- Région principale -->
                    <fo:region-body margin="1cm" margin-top="4cm" />
                    <!-- Tête de page aka header -->
                    <fo:region-before extent="4cm"/>
                    <!-- Pied de page aka footer -->
                    <fo:region-after extent="4cm"/>
                </fo:simple-page-master>
            </fo:layout-master-set>

            <!-- Contenus -->
            <fo:page-sequence master-reference="A4">

                <!-- Contenu de la tête de page avec l'image headerScolarite.png -->
                <fo:static-content flow-name="xsl-region-before">
                    <fo:block text-align="center">
                        <fo:external-graphic src="src/main/resources/images/headerScolarite.png" content-width="100%"/>
                    </fo:block>
                </fo:static-content>

                <!-- Contenu du pied de page avec l'image footer.png -->
                <fo:static-content flow-name="xsl-region-after">
                    <fo:block text-align="center" margin-bottom="30px">
                        <fo:external-graphic src="src/main/resources/images/footerScolarite.png" content-width="58%"/>
                    </fo:block>
                </fo:static-content>


                <!-- Contenu principal -->
                <fo:flow flow-name="xsl-region-body">
                    <fo:block text-align="center" font-weight="bold" font-size="18pt" margin-bottom="20pt" margin-top="20" text-decoration="underline">
                        ATTESTATION DE SCOLARITÉ
                    </fo:block>

                    <fo:block text-align="left">
                        <fo:block margin-top="1cm" font-size="12pt">Le Directeur de l'École Nationale des Sciences Appliquées de Tanger atteste que l'étudiant :</fo:block>
                        <fo:block margin-top="0.75cm">
                            Monsieur/Madame
                            <fo:inline font-weight="bold">
                                <xsl:value-of select="Etudiant/Nom"/><fo:inline>&#160;</fo:inline><xsl:value-of select="Etudiant/Prenom"/>
                            </fo:inline>
                        </fo:block>

                        <fo:block margin-top="0.75cm">
                            Numéro de la carte d'identité nationale : <xsl:value-of select="Etudiant/CIN"/>
                        </fo:block>
                        <fo:block margin-top="0.75cm">
                            Né le : <xsl:value-of select="Etudiant/DateNaissance"/> à <xsl:value-of select="Etudiant/LieuNaissance"/>
                        </fo:block>
                        <fo:block margin-top="0.75cm">
                            Poursuit ses études à l'École Nationale des Sciences Appliquées de Tanger pour l'année universitaire 2024/2025.
                        </fo:block>
                        <fo:block margin-top="0.75cm">
                            <fo:inline text-decoration="underline">Diplôme</fo:inline> : Génie Informatique
                        </fo:block>
                        <fo:block margin-top="0.75cm">
                            <fo:inline text-decoration="underline">Filière</fo:inline> : Génie Informatique
                        </fo:block>
                        <fo:block margin-top="0.75cm">
                            <fo:inline text-decoration="underline">Année</fo:inline> : 2ème Année Génie Informatique
                        </fo:block>
                    </fo:block>

                    <fo:block text-align="right" margin-top="0.75cm">
                        <fo:block>Fait à TANGER, le <xsl:value-of select="format-date(current-date(), '[D01] [MN,*-3] [Y]')"/></fo:block>
                        <fo:block margin-top="0.75cm" margin-right="3cm">
                            Le Directeur :
                        </fo:block>
                        <fo:block>
                            <fo:block margin-right="1.5cm">
                                <fo:external-graphic src="src/main/resources/images/cachet.jpg" content-height="3cm"/>
                            </fo:block>
                        </fo:block>
                    </fo:block>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>
