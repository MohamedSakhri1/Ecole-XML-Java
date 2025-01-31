declare variable $apogee external;

let $etudiants := doc("../Fichiers_XML/Etudiant/Etudiants.xml")/Etudiants/Etudiant

for $etudiant in $etudiants[CodeApogee = $apogee]
return
    <Etudiant xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:noNamespaceSchemaLocation="Attestation.xsd">
        <CodeApogee>{data($etudiant/CodeApogee)}</CodeApogee>
        <CIN>{data($etudiant/CIN)}</CIN>
        <CNE>{data($etudiant/CNE)}</CNE>
        <Nom>{data($etudiant/Nom)}</Nom>
        <Prenom>{data($etudiant/Prenom)}</Prenom>
        <LieuNaissance>{data($etudiant/LieuNaissance)}</LieuNaissance>
        <DateNaissance>{data($etudiant/DateNaissance)}</DateNaissance>
        <Email>{data($etudiant/Email)}</Email>
        <Telephone>{data($etudiant/Telephone)}</Telephone>
        <Premiere_inscription>{data($etudiant/Premiere_inscription)}</Premiere_inscription>
        <Classe>{data($etudiant/Classe)}</Classe>
        <Photo_path>{data($etudiant/Photo_path)}</Photo_path>
    </Etudiant>
