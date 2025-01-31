<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:fo="http://www.w3.org/1999/XSL/Format">

    <xsl:output method="xml" indent="yes"/>

    <xsl:template match="/">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="A4">
                    <fo:region-body margin="1cm"/>
                </fo:simple-page-master>
            </fo:layout-master-set>

            <fo:page-sequence master-reference="A4">
                <fo:flow flow-name="xsl-region-body">
                    <fo:block font-size="14pt" font-family="Arial" text-align="center" font-weight="bold" margin-bottom="10pt">
                        Affichage module <xsl:value-of select="Module/@nom"/>
                    </fo:block>

                    <fo:table border="solid 1pt black" table-layout="fixed" width="100%">
                        <fo:table-header>
                            <fo:table-row background-color="#f2f2f2">
                                <fo:table-cell border="solid 1pt black" display-align="center"><fo:block text-align="center" font-size="8pt" font-weight="bold">Code Apogée</fo:block></fo:table-cell>
                                <fo:table-cell border="solid 1pt black" display-align="center"><fo:block text-align="center" font-size="8pt" font-weight="bold">Nom</fo:block></fo:table-cell>
                                <fo:table-cell border="solid 1pt black" display-align="center"><fo:block text-align="center" font-size="8pt" font-weight="bold">Prénom</fo:block></fo:table-cell>
                                <fo:table-cell border="solid 1pt black" display-align="center"><fo:block text-align="center" font-size="8pt" font-weight="bold">Date de Naissance</fo:block></fo:table-cell>

                                <xsl:for-each select="Module/Etudiant[1]/SousModule">
                                    <fo:table-cell border="solid 1pt black" display-align="center">
                                        <fo:block text-align="center" font-size="8pt" font-weight="bold"><xsl:value-of select="@nom"/></fo:block>
                                    </fo:table-cell>
                                </xsl:for-each>

                                <fo:table-cell border="solid 1pt black" display-align="center"><fo:block text-align="center" font-size="8pt" font-weight="bold">Moyenne</fo:block></fo:table-cell>
                            </fo:table-row>
                        </fo:table-header>

                        <fo:table-body>
                            <xsl:for-each select="Module/Etudiant">
                                <fo:table-row>
                                    <fo:table-cell border="solid 1pt black" display-align="center"><fo:block text-align="center" font-size="8pt" font-weight="bold"><xsl:value-of select="CodeApogee"/></fo:block></fo:table-cell>
                                    <fo:table-cell border="solid 1pt black" display-align="center"><fo:block text-align="center" font-size="8pt" font-weight="bold"><xsl:value-of select="Nom"/></fo:block></fo:table-cell>
                                    <fo:table-cell border="solid 1pt black" display-align="center"><fo:block text-align="center" font-size="8pt" font-weight="bold"><xsl:value-of select="Prenom"/></fo:block></fo:table-cell>
                                    <fo:table-cell border="solid 1pt black" display-align="center"><fo:block text-align="center" font-size="8pt" font-weight="bold"><xsl:value-of select="DateNaissance"/></fo:block></fo:table-cell>

                                    <xsl:for-each select="SousModule">
                                        <fo:table-cell border="solid 1pt black" display-align="center">
                                            <fo:block text-align="center" font-size="8pt" font-weight="bold"><xsl:value-of select="Note"/></fo:block>
                                        </fo:table-cell>
                                    </xsl:for-each>

                                    <xsl:variable name="totalNotes" select="sum(SousModule/Note)"/>
                                    <xsl:variable name="nbNotes" select="count(SousModule/Note)"/>
                                    <xsl:variable name="moyenne">
                                        <xsl:choose>
                                            <xsl:when test="$nbNotes &gt; 0">
                                                <xsl:value-of select="format-number($totalNotes div $nbNotes, '0.00')"/>
                                            </xsl:when>
                                            <xsl:otherwise>0.00</xsl:otherwise>
                                        </xsl:choose>
                                    </xsl:variable>

                                    <fo:table-cell border="solid 1pt black" display-align="center">
                                        <xsl:attribute name="background-color">
                                            <xsl:choose>
                                                <xsl:when test="$moyenne &gt;= 12">#98FB98</xsl:when>  <!-- Vert -->
                                                <xsl:when test="$moyenne &gt;= 8 and $moyenne &lt; 12">#ff9023</xsl:when>  <!-- Orange -->
                                                <xsl:when test="$moyenne &lt; 8">#f00020</xsl:when>  <!-- Rouge -->
                                            </xsl:choose>
                                        </xsl:attribute>

                                        <fo:block text-align="center" font-size="8pt" font-weight="bold">
                                            <xsl:value-of select="format-number($moyenne, '0.00')"/>
                                        </fo:block>
                                    </fo:table-cell>

                                </fo:table-row>
                            </xsl:for-each>
                        </fo:table-body>
                    </fo:table>
                </fo:flow>
            </fo:page-sequence>
        </fo:root>
    </xsl:template>
</xsl:stylesheet>