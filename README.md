# Présentation du projet

Ce projet est dans un cadre purement educatif, les fichiers générées sont bien identiques aux documents administarifs réels de L'ecole nationale des sciences appliquées de Tanger (ENSAT).

Le projet est un service `Api` qui génére dynamiquement les documents administratif suivantes :
+ Relevée de notes et résultats
+ Attestation de scolarité
+ Attestation du réussite
+ Carte d'étudiant
+ Affichage d'un module

De plus ce projet fournit des pages `HTML` dynamiques suivantes :
+ Emploi du temps
+ Affichage d'un module

> [!CAUTION]
> Toute utilisation en dehors du cadre éducatif est hors notre responsabilité.

# Lancer le projet localement
### 1. Clonnage du projet
Si c'est votre 1er fois a utiliser ce service, vous pouvez clonner le répertoire Github etulisant cette commende :
```

git clone https://github.com/MohamedSakhri1/Ecole-XML-Java.git

```
Si vous n'avez pas git installez le depuis ce [lien](https://git-scm.com/downloads) .

### 2. Installation du bach commande `mvn` et le Java JDK

+ Installer Java JDK si vous n'avez pas déjà sur votre machine utilisez ce lien : [Installez Java JDK](https://www.oracle.com/java/technologies/downloads/).
+ Si vous n'avez pas la commande `mvn` installez-la ulisant ce lien : [Installer mnv](https://git-scm.com/downloads),<br/>
et suivez les etapes pour ajouter le `.bin` au `PATH` variables.

Puis dans le repertoire racine du projet `/Ecole-XML-Java`, vous pouvez executer la commande de construction du projet
```

mvn clean install

```
### 3. Demarrer le serveur
Finalement executer la commande qui lence le projet
```

mvn spring-boot:run

```
# Guide d'utilisation des APIs
## Introduction
Les Apis fournis sont sous forme de Get Requests avec la configuration suivante :
+ le host : `localhost`
+ le port par default : `9090`
vous pouvez changer le port de `9090` vers un choix personel, dans le fichier `Ecole-XML-Java\src\main\resources\application.properties`.
```

server.port=9090
spring.application.name=Ecole_XML_Java

```

## Les PDFs
La forme generale du lien sera :<br>
Request : `http://localhost:9090/<element a afficher>/pdf?<arguemnt>=<valeur>`
<br><br>
### 1. Relevée de notes et résultats
Element a afficher : `releveNotes`<br>
Argument : `apogee`<br>
Valeur : la valeur du numero d'apogée d'un étudiant dans GINF2 2024/25<br>

Exemple : `http://localhost:9090/releveNotes/pdf?apogee=21010401`

<br>

![Resultat de Api 1](https://github.com/user-attachments/assets/1f340b80-cdab-45a7-ba9c-b48e336a99eb)


### 2. Attestation de scolarité
Element a afficher : `attestationScolarite`<br>
Argument : `apogee`<br>
Valeur : la valeur du numero d'apogée d'un étudiant dans GINF2 2024/25<br>

Exemple : `http://localhost:9090/attestationScolarite/pdf?apogee=21010401`

<br>

![API 2](https://github.com/user-attachments/assets/363c4d29-84c4-429c-9cb2-a287e80ea5eb)


### 3. Attestation du réussite
Element a afficher : `attestationReussite`<br>
Argument : `apogee`<br>
Valeur : la valeur du numero d'apogée d'un étudiant dans GINF2 2024/25<br>

Exemple : `http://localhost:9090/attestationReussite/pdf?apogee=21010401`

<br>

![Capture d'écran 2025-02-01 152805](https://github.com/user-attachments/assets/f6337cb6-d272-456f-ac9c-ffedd50bd5ed)


### 4. Carte d'étudiant
Element a afficher : `CarteEtudiant`<br>
Argument : `apogee`<br>
Valeur : la valeur du numero d'apogée d'un étudiant dans GINF2 2024/25<br>

Exemple : `http://localhost:9090/CarteEtudiant/pdf?apogee=21010278`

<br>

![image](https://github.com/user-attachments/assets/b424c9b5-b9d8-4f7d-a27e-4cc51f198fce)


### 4. Affichage d'un module
Element a afficher : `affichage`<br>
Argument : `module`<br>
Valeur : Le code d'un module du classe GINF2 (de GINF31 à GINF46)<br>

Exemple : `http://localhost:9090/affichage/pdf?module=GINF44`

<br>

![image](https://github.com/user-attachments/assets/b0001033-8385-4cd7-9c3c-ae2b267ed5be)



## Les Pages HTML

### 1. Affichage d'un module

Syntaxe : `http://localhost:9090/affichage/html?module=<code du module>`

Exemple : `http://localhost:9090/affichage/html?module=GINF31`

<br>


![image](https://github.com/user-attachments/assets/0a78eb9e-b13e-499f-b655-f15bddd12cb0)


### 2. Emploi du temps

Syntaxe : `http://localhost:9090/edt`

<br>


![image](https://github.com/user-attachments/assets/c654930a-3790-4674-8ba5-6c029f8c1cd5)
