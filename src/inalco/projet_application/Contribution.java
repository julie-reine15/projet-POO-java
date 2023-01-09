package inalco.projet_application;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Contribution {

    CodeLangue.CODE_LANGUE langueContributeur; // langue dans laquelle le contributeur veut contribuer
    Parsing parseurMw = new Parseur("#.*?#"); // parseur des phrases format prof
    Parsing parseurTranslation = new Parseur("(.*?)(,\\s)(.*)"); // parseur pour les couples de mots à traduire
    Database usersData= new Database();

    public Contribution(CodeLangue.CODE_LANGUE langueContributeur) throws IOException {
        this.langueContributeur = langueContributeur;
    }

    /**
     * permet au contributeur de choisir ce qu'il veut faire entre consultation des exercices en fonction du niveau ou des profils utilisateurs et enrichissement
     * @throws NoSuchOptionException
     * @throws IOException
     * @throws ParseurException
     */
    public void chooseContribution() throws NoSuchOptionException, IOException, ParseurException {
        System.out.println("Que voulez-vous faire ?");
        System.out.println("Tapez 1 pour consulter le contenu d'un exercice");
        System.out.println("Tapez 2 pour enrichir un exercice");
        System.out.println("Tapez 3 pour consulter les profils des utilisateurs de l'application");
        System.out.println("Tapez 4 pour consulter le classements des utilisateurs (par points)");

        Scanner input = new Scanner(System.in);
        int choix = input.nextInt();

        switch (choix){
            case 1 -> {
                System.out.println("Tapez 1 pour consulter le contenu d'un exercice de phrases à trous");
                System.out.println("Tapez 2 pour consulter le contenu d'un exercice de traduction");

                input = new Scanner(System.in);
                int choixEx = input.nextInt();

                switch (choixEx){
                    case 1 -> seeExMw();
                    case 2 -> seeExTranslation();
                }
            }
            case 2 -> {
                System.out.println("Tapez 1 pour enrichir le contenu d'un exercice de phrases à trous");
                System.out.println("Tapez 2 pour enrichir le contenu d'un exercice de traduction");

                input = new Scanner(System.in);
                int choixEx = input.nextInt();

                switch (choixEx){
                    case 1 -> enrichExMw();
                    case 2 -> enrichExTranslation();
                }
            }
            case 3 -> getUsersDetails();
            case 4 -> this.usersData.seeRanking();
            default -> throw new NoSuchOptionException("Ce choix n'est pas disponible");
        }
    }

    /**
     * affiche les détails de tous les profils des utilisateurs de l'application
     */
    public void getUsersDetails (){
        List<String[]> usersDetails = this.usersData.getUsersDetails();

        for (String[] userDetails : usersDetails){
            System.out.println(Arrays.toString(userDetails));
        }
    }

    /**
     * affiche les phrases d'un exercice de phrases à trou
     * @throws NoSuchOptionException
     * @throws IOException
     */
    public void seeExMw() throws NoSuchOptionException, IOException {
        String pathToExercice;

        // sélection des exercices dans la langue choisie par le contributeur
        switch (this.langueContributeur){
            case FR -> pathToExercice = "fr/fr_";
            case RU -> pathToExercice = "ru/ru_";
            case NL -> pathToExercice = "nl/nl_";
            default -> throw new NoSuchOptionException("Nous travaillons d'arrache-pied pour ajouter sous peu de nouvelles fonctionnalités à notre application");
        }

        // choix du niveau de l'exercice à consulter
        System.out.println("Quel exercice voulez-vous consulter ?");
        System.out.println("Tapez 1 pour le niveau débutant");
        System.out.println("Tapez 2 pour le niveau intermédiaire");
        System.out.println("Tapez 3 pour le niveau avancé");
        System.out.println("Tapez 4 pour le niveau pro");

        Scanner input = new Scanner(System.in);
        int rep = input.nextInt();

        switch (rep){
            case 1 -> pathToExercice = pathToExercice + "lvl1.txt";
            case 2 -> pathToExercice = pathToExercice + "lvl2.txt";
            case 3 -> pathToExercice = pathToExercice + "lvl3.txt";
            case 4 -> pathToExercice = pathToExercice + "lvl4.txt";
            default -> throw new NoSuchOptionException("Ce choix est indisponible !");
        }

        String fileLine = new String (Files.readAllBytes(Paths.get(pathToExercice)));

        // affichage des phrases du fichier
        for (String line : fileLine.split("\n")){
            System.out.println(line);
        }
    }

    /**
     * affiche les mots en langue source et en langue cible d'un exercice de traduction
     * @throws NoSuchOptionException
     * @throws IOException
     */
    public void seeExTranslation() throws NoSuchOptionException, IOException {
        String pathToExercice;

        // sélection des exercices dans la langue choisie par le contributeur
        switch (this.langueContributeur){
            case RU -> pathToExercice = "ru/translation_";
            case NL -> pathToExercice = "nl/translation_";
            default -> throw new NoSuchOptionException("Ce type d'exercice n'est pas disponible pour cette langue");
        }
        // choix du niveau de l'exercice à consulter
        System.out.println("Quel exercice voulez-vous consulter ?");
        System.out.println("Tapez 1 pour le niveau débutant");
        System.out.println("Tapez 2 pour le niveau intermédiaire");
        System.out.println("Tapez 3 pour le niveau avancé");
        System.out.println("Tapez 4 pour le niveau pro");

        Scanner input = new Scanner(System.in);
        int rep = input.nextInt();

        switch (rep){
            case 1 -> pathToExercice = pathToExercice + "lvl1.txt";
            case 2 -> pathToExercice = pathToExercice + "lvl2.txt";
            case 3 -> pathToExercice = pathToExercice + "lvl3.txt";
            case 4 -> pathToExercice = pathToExercice + "lvl4.txt";
            default -> throw new NoSuchOptionException("Ce choix est indisponible !");
        }

        String fileLine = new String (Files.readAllBytes(Paths.get(pathToExercice)));

        for (String line : fileLine.split("\n")){
            System.out.println(line);
        }
    }

    /**
     * permet au contributeur d'ajouter un couple de mots à un exercice
     * @throws NoSuchOptionException
     * @throws IOException
     * @throws ParseurException
     */
    public void enrichExMw() throws NoSuchOptionException, IOException, ParseurException {
        String pathToExercice;

        // sélection des exercices dans la langue choisie par le contributeur
        switch (this.langueContributeur){
            case FR -> pathToExercice = "fr/fr_";
            case RU -> pathToExercice = "ru/ru_";
            case NL -> pathToExercice = "nl/nl_";
            default -> throw new NoSuchOptionException("Nous travaillons d'arrache-pied pour ajouter sous peu de nouvelles fonctionnalités à notre application");
        }

        // choix du niveau de l'exercice à enrichir
        System.out.println("Quel exercice voulez-vous enrichir ?");
        System.out.println("Tapez 1 pour le niveau débutant");
        System.out.println("Tapez 2 pour le niveau intermédiaire");
        System.out.println("Tapez 3 pour le niveau avancé");
        System.out.println("Tapez 4 pour le niveau pro");

        Scanner input = new Scanner(System.in);
        int rep = input.nextInt();
        int lvl;

        // en fonction du niveau de l'exercice choisi, explication des consignes à suivre
        switch (rep){
            case 1 -> {
                pathToExercice = pathToExercice + "lvl1.txt";
                System.out.println("Vous allez à présent pouvoir ajouter autant de phrases à trous que voulu à l'exercice niveau débutant en "+ langueContributeur);
                System.out.println("Pour cela, vous devrez indiquer le mot que l'utilisateur devra retrouver entre #");
                System.out.println("Exemple : \"Les #chaussettes# de l’archiduchesse sont bien sèches\" deviendra \"Les ___ de l’archiduchesse sont bien sèches\"");
                System.out.println("⚠️ Dans cet exercice uniquement 1 mot par phrase devra être entouré de # sinon elle ne sera pas acceptée");
                lvl = 1;
            }
            case 2 -> {
                pathToExercice = pathToExercice + "lvl2.txt";
                System.out.println("Vous allez à présent pouvoir ajouter autant de phrases à trous que voulu à l'exercice niveau intermédiaire en "+ langueContributeur);
                System.out.println("Pour cela, vous devrez indiquer les mots que l'utilisateur devra retrouver entre #");
                System.out.println("Exemple : \"Les #chaussettes# de l’archiduchesse sont bien #sèches#\" deviendra \"Les ___ de l’archiduchesse sont bien ___\"");
                System.out.println("⚠️ Dans cet exercice uniquement 2 mots par phrase devront être entourés de # sinon elle ne sera pas acceptée");
                System.out.println("⚠️ Il est également inutile et impossible de mettre 2 mots à la suite entre #");
                lvl = 2;
            }
            case 3 -> {
                pathToExercice = pathToExercice + "lvl3.txt";
                System.out.println("Vous allez à présent pouvoir ajouter autant de phrases à trous que voulu à l'exercice niveau avancé en "+ langueContributeur);
                System.out.println("Pour cela, vous devrez indiquer les mots que l'utilisateur devra retrouver entre #");
                System.out.println("Exemple : \"Les #chaussettes# de l’archiduchesse sont #bien# sèches, #archi# sèches\" deviendra \"Les ___ de l’archiduchesse sont ___ sèches, ___ sèches\"");
                System.out.println("⚠️ Dans cet exercice uniquement 3 mots par phrase devront être entourés de # sinon elle ne sera pas acceptée");
                System.out.println("⚠️ Il est également inutile et impossible de mettre 2 mots à la suite entre #");
                lvl = 3;
            }
            case 4 -> {
                pathToExercice = pathToExercice + "lvl4.txt";
                System.out.println("Vous allez à présent pouvoir ajouter autant de phrases à trous que voulu à l'exercice niveau pro en "+ langueContributeur);
                System.out.println("Pour cela, vous devrez indiquer les mots que l'utilisateur devra retrouver entre #");
                System.out.println("Exemple : \"Les #chaussettes# de l’#archiduchesse# sont bien #sèches#, archi sèches\" deviendra \"Les ___ de l’___ sont bien ___, archi sèches\"");
                System.out.println("⚠️ Dans cet exercice uniquement 4 mots par phrase devront être entourés de # sinon elle ne sera pas acceptée");
                System.out.println("⚠️ Il est également inutile et impossible de mettre 2 mots à la suite entre #");
                lvl = 4;
            }
            default -> throw new NoSuchOptionException("Ce choix est indisponible !");
        }

        FileWriter fw = new FileWriter(pathToExercice,true); // pour ajouter la nouvelle phrase à la fin du fichier

        boolean keepGoing = true;

        while (keepGoing){ // tant que l'utilisateur voudra ajouter une phrase
            System.out.print("Entrez votre phrase -> ");
            input = new Scanner(System.in);

            String newSent = input.nextLine().trim();

            if (parseurMw.verifNbMw(newSent, lvl)){ // vérification que la phrase corresponde bien au format attendu
                fw.write("\n"+newSent); // si oui on l'écrit à la fin du fichier
            } else {
                throw new ParseurException("La phrase n'a pas le format demandé, merci de recommencer");
            }
            System.out.println("Voulez-vous ajouter une nouvelle phrase ? Y/n");
            input = new Scanner(System.in);

            if (input.nextLine().toLowerCase().startsWith("n")){ // si le contributeur n'a plus rien à ajouter, la boucle s'arrête
                keepGoing=false;
            }
        }
        fw.close();
        System.out.println("Merci pour votre contribution !");
    }

    public void enrichExTranslation() throws NoSuchOptionException, IOException, ParseurException {
        String pathToExercice;

        // sélection des exercices dans la langue choisie par le contributeur
        switch (this.langueContributeur){
            case RU -> pathToExercice = "ru/translation_";
            case NL -> pathToExercice = "nl/translation_";
            default -> throw new NoSuchOptionException("Ce type d'exercice n'est pas disponible pour cette langue");
        }

        // choix du niveau de l'exercice à enrichir
        System.out.println("Quel exercice voulez-vous enrichir ?");
        System.out.println("Tapez 1 pour le niveau débutant");
        System.out.println("Tapez 2 pour le niveau intermédiaire");
        System.out.println("Tapez 3 pour le niveau avancé");
        System.out.println("Tapez 4 pour le niveau pro");

        Scanner input = new Scanner(System.in);
        int rep = input.nextInt();

        // en fonction du niveau de l'exercice choisi, explication des consignes à suivre
        switch (rep){
            case 1 -> {
                pathToExercice = pathToExercice + "lvl1.txt";
                System.out.println("Vous allez pouvoir ajouter autant de couples de mots langue source -> langue cible que voulu en "+ langueContributeur);
                System.out.println("Pour cela, vous devrez écrire le mot en langue source suivi d'une virgule et d'un espace puis le mot en langue cible");
                System.out.println("Exemple : ampoule, light bulb");
                System.out.println("⚠️ La langue source doit TOUJOURS être le français");
            }
            case 2 -> {
                pathToExercice = pathToExercice + "lvl2.txt";
                System.out.println("Vous allez pouvoir ajouter autant de couples de mots langue source -> langue cible que voulu en "+ langueContributeur);
                System.out.println("Pour cela, vous devrez écrire le mot en langue source suivi d'une virgule et d'un espace puis le mot en langue cible");
                System.out.println("Exemple : ampoule, light bulb");
                System.out.println("💡️ Pour cette difficulté vous pouvez mettre des petits groupes nominaux comme \"le ciel\"");
                System.out.println("⚠️ La langue source doit TOUJOURS être le français");
            }
            case 3 -> {
                pathToExercice = pathToExercice + "lvl3.txt";
                System.out.println("Vous allez pouvoir ajouter autant de couples de mots langue source -> langue cible que voulu en "+ langueContributeur);
                System.out.println("Pour cela, vous devrez écrire le mot en langue source suivi d'une virgule et d'un espace puis le mot en langue cible");
                System.out.println("Exemple : ampoule, light bulb");
                System.out.println("💡️ Pour cette difficulté vous pouvez mettre des petites expressions / phrases comme \"je suis fatiguée\"");
                System.out.println("⚠️ La langue source doit TOUJOURS être le français");
            }
            case 4 -> {
                pathToExercice = pathToExercice + "lvl4.txt";
                System.out.println("Vous allez pouvoir ajouter autant de couples de mots langue source -> langue cible que voulu en "+ langueContributeur);
                System.out.println("Pour cela, vous devrez écrire le mot en langue source suivi d'une virgule et d'un espace puis le mot en langue cible");
                System.out.println("Exemple : ampoule, light bulb");
                System.out.println("💡 Pour cette difficulté vous pouvez mettre des expressions / phrases comme \"elle préfère se déplacer à pied\"");
                System.out.println("⚠️ La langue source doit TOUJOURS être le français");
            }
            default -> throw new NoSuchOptionException("Ce choix est indisponible !");
        }

        FileWriter fw = new FileWriter(pathToExercice,true); // pour ajouter la nouvelle phrase à la fin du fichier

        boolean keepGoing = true;


        while (keepGoing){ // tant que l'utilisateur voudra ajouter un couple de mots
            System.out.print("Entrez votre couple de mots -> ");
            input = new Scanner(System.in);

            String newWords = input.nextLine().trim();
            if(parseurTranslation.verifWords(newWords)){ // vérification que le couple de mot correspond au format attendu
                fw.write("\n"+newWords); // si oui, on l'ajoute à la fin du fichier
            } else {
            throw new ParseurException("Les mots ne sont pas au format demandé, merci de recommencer");
            }

            System.out.println("Voulez-vous ajouter une nouveau couple de mots ? Y/n");
            input = new Scanner(System.in);

            if (input.nextLine().toLowerCase().startsWith("n")){ // si le contributeur n'a plus rien à ajouter la boucle s'arrête
                keepGoing=false;
            }
        }
        fw.close();
    }
}
