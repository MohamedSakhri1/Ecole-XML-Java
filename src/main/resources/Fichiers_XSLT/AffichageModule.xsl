<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">

    <xsl:output method="html" indent="yes"/>
    <xsl:template match="/">
        <html>
            <head>
                <title>Affichage module <xsl:value-of select="Module/@nom"/></title>
                <style>
                    table {
                    width: 100%;
                    border-collapse: collapse;
                    }
                    th, td {
                    border: 1px solid black;
                    padding: 5px;
                    text-align: center;
                    }
                    th {
                    background-color: #f2f2f2;
                    }
                </style>
            </head>
            <body>
                <h2 style="text-align:center;">Affichage module <xsl:value-of select="Module/@nom"/></h2>
                <table>
                    <!-- En-tête -->
                    <tr>
                        <th>Code Apogée</th>
                        <th>Nom</th>
                        <th>Prénom</th>
                        <th>Date de Naissance</th>
                        <!-- Boucle pour générer dynamiquement les en-têtes des sous-modules -->
                        <xsl:for-each select="Module/Etudiant[1]/SousModule">
                            <th><xsl:value-of select="@nom"/></th>
                        </xsl:for-each>
                        <th>Moyenne</th>
                    </tr>

                    <!-- Corps du tableau -->
                    <xsl:for-each select="Module/Etudiant">
                        <tr>
                            <td><xsl:value-of select="CodeApogee"/></td>
                            <td><xsl:value-of select="Nom"/></td>
                            <td><xsl:value-of select="Prenom"/></td>
                            <td><xsl:value-of select="DateNaissance"/></td>

                            <!-- Boucle pour remplir dynamiquement les colonnes des sous-modules -->
                            <xsl:for-each select="SousModule">
                                <td>
                                    <xsl:value-of select="Note"/>
                                </td>
                            </xsl:for-each>

                            <!-- Moyenne -->
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
                            <td>
                                <xsl:attribute name="style">
                                    background-color:
                                    <xsl:choose>
                                        <xsl:when test="$moyenne &gt;= 12">#98FB98</xsl:when>
                                        <xsl:when test="$moyenne &gt;= 8 and $moyenne &lt; 12">#ff9023</xsl:when>
                                        <xsl:when test="$moyenne &lt; 8">#f00020</xsl:when>
                                    </xsl:choose>;
                                </xsl:attribute>
                                <xsl:value-of select="format-number($moyenne, '0.00')"/>
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
