declare variable $moduleCode external;

let $etudiants := doc("../Fichiers_XML/Etudiant/Etudiants.xml")/Etudiants/Etudiant
let $notes := doc("../Fichiers_XML/Note/Notes.xml")/Notes/Note
let $modules := doc("../Fichiers_XML/Module/Modules.xml")/Modules/Module

for $module in $modules[@code = $moduleCode]
return
    <Module nom="{data($module/@nom)}" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:noNamespaceSchemaLocation="Affichage.xsd">
        {
            for $etudiant in $etudiants
            let $codeApogee := data($etudiant/CodeApogee)
            let $notesEtudiant := $notes[CodeApogee = $codeApogee and ModuleCode = $moduleCode]
            where exists($notesEtudiant) (: Vérification que l'étudiant a des notes :)
            return
                <Etudiant>
                    <CodeApogee>{$codeApogee}</CodeApogee>
                    <Nom>{data($etudiant/Nom)}</Nom>
                    <Prenom>{data($etudiant/Prenom)}</Prenom>
                    <DateNaissance>{data($etudiant/DateNaissance)}</DateNaissance>
                    {
                        for $sousModule in $module/SousModule
                        let $note := $notesEtudiant[SousModule = data($sousModule/Nom)]/Moyenne
                        where exists($note) (: Vérification que la note existe :)
                        return
                            <SousModule nom="{data($sousModule/Nom)}">
                                <Note>{data($note)}</Note>
                            </SousModule>
                    }
                </Etudiant>
        }
    </Module>
