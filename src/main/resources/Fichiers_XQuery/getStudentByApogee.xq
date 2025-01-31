declare variable $apogee external;

for $etudiant in doc("../Fichiers_XML/Etudiant/Etudiants.xml")/Etudiants/Etudiant
where $etudiant/CodeApogee = $apogee
return $etudiant