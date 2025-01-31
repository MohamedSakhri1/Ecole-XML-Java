<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xs="http://www.w3.org/2001/XMLSchema"
                exclude-result-prefixes="xs"
                version="2.0">

    <xsl:template match="/">
        <html>
            <head>
                <title>Affichage</title>
                <style>
                    table {
                    max-width: 100%;
                    width: 100%;
                    border-collapse: collapse;
                    margin-bottom: 20px;
                    }
                    th, td {
                    max-width: 150px;
                    overflow: hidden;
                    text-overflow: ellipsis;
                    white-space: nowrap;
                    border: 1px solid black;
                    padding: 5px;
                    }
                    .module-header {
                    font-weight: bold;
                    text-align: center;
                    background-color: #f2f2f2;
                    }
                    h1 {
                    text-align: center;
                    }
                </style>
            </head>
            <body>
                <h1>Affichage Du Module : <xsl:value-of select="/Module/@nom"/></h1>
                <table>
                    <tr>
                        <th colspan="4" rowspan="3"></th>
                        <th colspan="3"><xsl:value-of select="/Module/@nom"></xsl:value-of></th>

                    </tr>
                    <tr>
                        <xsl:for-each select="/Module/Etudiant[1]/SousModule">
                            <th rowspan="1"><xsl:value-of select="@nom"/></th>
                        </xsl:for-each>
                    </tr>
                    <tr>
                        <xsl:for-each select="/Module/Etudiant[1]/SousModule">
                            <th rowspan="2">1</th>
                        </xsl:for-each>
                    </tr>
                    <tr>
                        <th>Code Apogée</th>
                        <th>Nom</th>
                        <th>Prénom</th>
                        <th>Date de naissance</th>
                    </tr>
                    <xsl:apply-templates select="/Module/Etudiant"/>
                </table>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="Etudiant">
        <tr>
            <td><xsl:value-of select="CodeApogee"/></td>
            <td><xsl:value-of select="Nom"/></td>
            <td><xsl:value-of select="Prenom"/></td>
            <td><xsl:value-of select="DateNaissance"/></td>
            <xsl:apply-templates select="SousModule"/>
        </tr>
    </xsl:template>

    <xsl:template match="SousModule">
        <td><xsl:value-of select="Note"/></td>
    </xsl:template>


</xsl:stylesheet>
