#  Une application pour gérer une bibliothèque

Cette application permettra de gérer les **livres**. Voici les principales fonctionnalités à développer :

## Base de Données
La base de données devra contenir des informations sur :
- Les **livres** (titre, année de publication, etc.)

### Exemples de données à inclure
- **Titre du livre**
- **Année de publication**
- **Nom de l'auteur**

## Fonctionnalités à Implémenter

### 1. Ajouter un livre
- Permet d'ajouter un livre avec :
  - **Titre**
  - **Année de publication**
  - **Auteur**
  - **Image**
  - **Genre**
  - **Existence**

### 2. Modifier un livre
- Permet de modifier les informations d'un livre existant.

### 3. Supprimer un livre
- Permet de supprimer un livre de la base de données.

### 4. Afficher tous les livres
- Affiche une liste de tous les livres avec le **nom de l'auteur**.

### 5. Rechercher un livre
- Permet de rechercher un livre par :
  - **Titre**
  - **Année de publication**
  - **Auteur**
  - **Genre**
  - **Génerale**

## Interface Graphique
- **Formulaires** pour :
  - Saisir les informations des livres et des auteurs.
- **Tableau (JTable)** pour afficher les livres et leurs informations.

## Résultat 
Une application intuitive, permettant de gérer efficacement une bibliothèque avec une interface graphique conviviale.
![Capture d'écran 2025-01-23 211605](https://github.com/user-attachments/assets/f5eba404-e70f-4750-a474-e1e966520dac)


 # Instructions pour Accéder au Projet

Pour utiliser directement l'application, suivez ces étapes simples :

## Étape 1 : Préparer l'environnement
1. **Télécharger et installer WAMP Server** :
   - Assurez-vous que WAMP Server est installé sur votre machine.
   - Configurez la base de données pour écouter sur le port **3306** (par défaut).

## Étape 2 : Configurer la base de données
1. Importez les fichiers SQL nécessaires pour créer les tables et insérer les données initiales :
   - Ces fichiers se trouvent dans le dossier `database` fourni avec le projet.
   - Utilisez phpMyAdmin (ou tout autre outil) pour importer les fichiers.

## Étape 3 : Exécuter l'application
- Utilisez le fichier exécutable approprié pour démarrer l'application :
  - **Windows** : Double-cliquez sur `project.exe`.
  - **Java** : Assurez-vous que Java est installé, puis exécutez la commande suivante dans le terminal :
    ```bash
    java -jar project.jar
    ```
    ou juste naviguez jusqu'au fichier **`src/Gestion_Livres/MainFrame2.java`** et cliquez sur **Exécuter** pour lancer le programme.

## Étape 4 : Interagir avec l'application
- Une fois l'application lancée :
  - Vous pouvez ajouter, modifier, afficher, rechercher ou supprimer des livres et auteurs.
  - Toutes les données seront synchronisées avec la base de données via le port **3306**.

> **Remarque** : Assurez-vous que WAMP Server est démarré avant d'utiliser l'application.

 ![Enregistrement de l'écran 2025-01-23 183244](https://github.com/user-attachments/assets/315a9d35-a19a-444f-8836-cd63d06f4e75)
ou

![Enregistrement de l'écran 2025-01-23 183141](https://github.com/user-attachments/assets/463f3106-1c99-4fd8-bb4b-7100d81b8c18)

