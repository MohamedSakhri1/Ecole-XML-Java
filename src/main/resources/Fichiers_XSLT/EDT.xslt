<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema"
>
    <xsl:output method="html" indent="yes"/>

    <xsl:template match="/">
        <html>
            <head>
                <style>
                    body { position: relative; font-family: Arial; }
                    .emploi { position: relative; width: 1000px; height: 800px; background-size: contain; }
                    .date-de-jour { position: absolute; top: 5px;  }
                    .cours { position: absolute; width: 154px; height: 82px; background: #fff; border-radius: 10px; border: solid .5px; overflow: hidden; text-align: center; font-size: 10px; }
                    .top { padding: 2px; border-bottom: black solid .5px; }
                    .buttom {overflow: inherit; padding-top: 8px; margin: 5px;}
                    p { margin: 0px; }
                    strong { font-size: 14px;  text-wrap-mode: nowrap;text-transform: uppercase;}
                </style>
            </head>
            <body>
                <div class="emploi">
                    <img src="Images/EDT_BASE.png" style="position:absolute; width:100%; z-index:-1; left: 50%;"/>

                    <xsl:for-each select="Eemploi_de_temps/Filiere/Jour">
                        <xsl:variable name="jour" select="@nom"/>
                        <xsl:variable name="leftPosition">
                            <xsl:choose>
                                <xsl:when test="$jour='Lundi'">548</xsl:when>
                                <xsl:when test="$jour='Mardi'">707</xsl:when>
                                <xsl:when test="$jour='Mercredi'">866</xsl:when>
                                <xsl:when test="$jour='Jeudi'">1026</xsl:when>
                                <xsl:when test="$jour='Vendredi'">1184</xsl:when>
                                <xsl:when test="$jour='Samedi'">1342</xsl:when>
                                <xsl:otherwise>0</xsl:otherwise>
                            </xsl:choose>
                        </xsl:variable>

                        <xsl:for-each select="Module">
                            <xsl:variable name="moduleNom" select="@nom"/>
                            <xsl:for-each select="Creneau">
                                <xsl:variable name="type" select="@type"/>
                                <xsl:variable name="heure" select="Heure"/>
                                <xsl:variable name="prof" select="Prof"/>
                                <xsl:variable name="salle" select="Salle"/>

                                <xsl:variable name="backgroundColor">
                                    <xsl:choose>
                                        <xsl:when test="$type='CM'">#B5A9FC</xsl:when>
                                        <xsl:when test="$type='TD'">#F9FCA9</xsl:when>
                                        <xsl:when test="$type='TP'">#A9FCAD</xsl:when>
                                        <xsl:otherwise>#FFFFFF</xsl:otherwise>
                                    </xsl:choose>
                                </xsl:variable>

                                <xsl:variable name="top">
                                    <xsl:choose>
                                        <xsl:when test="contains($heure, '09:00')">104</xsl:when>
                                        <xsl:when test="contains($heure, '11:00')">217</xsl:when>
                                        <xsl:when test="contains($heure, '13:30')">358</xsl:when>
                                        <xsl:when test="contains($heure, '14:30')">415</xsl:when>
                                        <xsl:when test="contains($heure, '15:30')">472</xsl:when>
                                        <xsl:when test="contains($heure, '16:30')">529</xsl:when>
                                        <xsl:otherwise>0</xsl:otherwise>
                                    </xsl:choose>
                                </xsl:variable>

                                <div class="cours" style="top:{$top}px; left:{$leftPosition}px;">
                                    <div class="top" style="background-color:{$backgroundColor};">
                                        <xsl:value-of select="$type"/> - <xsl:value-of select="$heure"/>
                                    </div>
                                    <div class="buttom">
                                        <strong><xsl:value-of select="$moduleNom"/></strong>
                                        <p style="font-size: 11px; text-transform: uppercase;"><xsl:value-of select="$prof"/></p>
                                        <p>Salle: <xsl:value-of select="translate($salle, 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ')"/></p>
                                    </div>
                                </div>
                            </xsl:for-each>
                        </xsl:for-each>
                    </xsl:for-each>


                    <!-- Ajouter la date courante de chaque jour -->
                    <xsl:for-each select="(1,2,3,4,5,6)">

                        <xsl:variable name="leftPosition">
                            <xsl:choose>
                                <xsl:when test="position()=1">548</xsl:when>
                                <xsl:when test="position()=2">707</xsl:when>
                                <xsl:when test="position()=3">866</xsl:when>
                                <xsl:when test="position()=4">1026</xsl:when>
                                <xsl:when test="position()=5">1184</xsl:when>
                                <xsl:when test="position()=6">1342</xsl:when>
                                <xsl:otherwise>0</xsl:otherwise>
                            </xsl:choose>
                        </xsl:variable>


                        <h5 class="date-de-jour" style="left:{$leftPosition + 62}px;">

                            <xsl:variable name="currentDate" select="current-date()"/>
                            <xsl:variable name="calculatedDate" select="adjust-date-to-timezone($currentDate + xs:dayTimeDuration(concat('P', position() - 1, 'D')))"/>

                            <xsl:value-of select="concat(format-number(day-from-date($calculatedDate), '00'), '/', format-number(month-from-date($calculatedDate), '00'))"/>
                        </h5>
                    </xsl:for-each>
                </div>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
