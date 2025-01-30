declare variable $moduleCode external;

let $etudiants := doc("../Fichiers XML/Etudiant/Etudiants.xml")/Etudiants/Etudiant
let $notes := doc("../Fichiers XML/Note/Notes.xml")/Notes/Note
let $modules := doc("../Fichiers XML/Module/Modules.xml")/Modules/Module

for $module in $modules[@code = $moduleCode]
return
    <Module nom="{data($module/@nom)}">
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
