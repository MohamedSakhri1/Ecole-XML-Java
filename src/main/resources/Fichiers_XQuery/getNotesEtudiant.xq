declare variable $apogee external;

let $etudiants := doc("../Fichiers_XML/Etudiant/Etudiants.xml")/Etudiants/Etudiant
let $notes := doc("../Fichiers_XML/Note/Notes.xml")/Notes/Note
let $modules := doc("../Fichiers_XML/Module/Modules.xml")/Modules/Module

for $etudiant in $etudiants[CodeApogee = $apogee]
return
    <Etudiant xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:noNamespaceSchemaLocation="etudiant.xsd">
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

        <!-- Ajout des notes de l'étudiant -->
        <NotesEtudiant xsi:noNamespaceSchemaLocation="notes.xsd">
            {
                for $note in $notes[CodeApogee = $apogee]
                let $module := $modules[@code = $note/ModuleCode]  (: Récupération du module correspondant :)
                return
                    <Note>
                        <ModuleCode>{concat(data($note/ModuleCode)," : ", data($module/@nom))}</ModuleCode>
                        <SousModule>{data($note/SousModule)}</SousModule>
                        <Moyenne>{data($note/Moyenne)}</Moyenne>
                    </Note>
            }
        </NotesEtudiant>
    </Etudiant>
