package inalco.projet_application;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Exercices {

    Parsing parseur = new Parseur("#.*?#"); // parseur des phrases format prof
    Notation notation = new Notation(); // instance de notation à définir pour noter les exercices
    Niveau levelUser; // niveau de l'utilisateur pour lui donner un exercice adapté à son niveau
    CodeLangue.CODE_LANGUE langageUser; // langue choisie par l'utilisateur afin de lui proposer les exercices dans la bonne langue
    Random generator = new Random(); // permet de supprimer au hasard des éléments des listes des exercices pour en avoir toujours que 10 pour ne pas altérer la notation

    /**
     * constructeur de la classe
     * @param levelUser niveau de l'utilisateur pour lui donner un exercice adapté à son niveau
     * @param langageUser langue choisie par l'utilisateur afin de lui proposer les exercices dans la bonne langue
     */
    public Exercices(Niveau levelUser, CodeLangue.CODE_LANGUE langageUser){
        this.levelUser = levelUser;
        this.langageUser = langageUser;
    }

    /**
     * permet d'appeler la méthode de l'exercice que l'utilisateur souhaite faire
     * @return les points obtenus par l'utilisateur à l'issue de l'exerice pour mettre à jour son total de point dans la base de données
     * @throws ParseurException
     * @throws NoSuchOptionException
     * @throws IOException
     */
    public float chooseExercice() throws ParseurException, NoSuchOptionException, IOException {

        System.out.println("Quel type d'exercice voulez-vous faire aujourd'hui ?");
        System.out.println("Tapez 1 pour faire un exercice de phrases à trous");
        System.out.println("Tapez 2 pour faire un exercice de traduction");

        Scanner input = new Scanner(System.in);
        String exChosen = input.nextLine();
        float point;
        switch (exChosen){
            case "1" -> point = missingWord();
            case "2" -> point = translation();
            default -> throw new NoSuchOptionException("Ce choix n'est pas disponible pour le moment");
        }
        return point;
    }

    /**
     * permet de récupérer les phrases dans les fichiers pour les exercices de phrases à trous
     * @param path le chemin vers le fichier dont on veut récupérer les phrases
     * @return une arraylist contenant chaque phrase du fichier
     * @throws IOException
     */
    public ArrayList<String> getSentences(String path) throws IOException {
        String fileLine = new String (Files.readAllBytes(Paths.get(path)));

        ArrayList<String> sentences = new ArrayList<>(Arrays.asList(fileLine.split("\n")));

        if (sentences.size()>10){ // on enlève au hasard le nombre de phrases nécessaire pour revenir à 10 et ne pas altérer la notation
            for (int i=0; i<=sentences.size()-10; i++){
                int rnd = generator.nextInt(sentences.size() - 1);
                sentences.remove(rnd);
            }
        }
        Collections.shuffle(sentences); // on mélange les phrases pour éviter que l'exercice devienne trop simple s'il est fait plusieurs fois
        return sentences;
    }

    /**
     * @param path le chemin vers le fichier dont on veut récupérer les phrases
     * @return une arraylist contenant phrases parsées donc des listes de Morceaux
     * @throws IOException
     */
    public ArrayList<List<Morceaux>> parse(String path) throws IOException {
        ArrayList<String> sentencesList = getSentences(path);
        ArrayList<List<Morceaux>> parsedSentences = new ArrayList<>();

        for (String sentence : sentencesList){
            List<Morceaux> sentChunk = parseur.parse(sentence); // parsing des phrases
            parsedSentences.add(sentChunk); // ajout des listes de morceaux à la liste
        }
        return parsedSentences;
    }

    /**
     * permet de récupérer les MorceauVariable d'un set de phrase pour les afficher et aider l'utilisateur à faire l'exercice
     * @param parsedSentences  arraylist de phrases parsées
     * @return une arraylist contenant les MorceauVariable d'un set de phrase au format réponse attendue
     */
    public ArrayList<String> getEveryMvFromList(ArrayList<List<Morceaux>> parsedSentences) {
        ArrayList<String> mvFromList = new ArrayList<>();

        for (List<Morceaux> sentence : parsedSentences){ // transformation des listes de morceaux en phrase
            Phrase phrase = new Phrase(sentence);

            for (MorceauVariable mvString : phrase.morceauxVariables){ // et on récupère les morceaux variables de celle-ci
                mvFromList.add(mvString.reponseAttendue()); // pour les ajouter à la liste
            }
        }
        return mvFromList;
        // on aurait pu juste récupérer la liste de morceaux variables de chaque phrase mais le but était vraiment de récupérer
        // leur forme String pour ensuite présenter tous les morceaux variables de l'exercice à l'utilisateur pour l'aider
        // sans donner la réponse trop facilement
    }

    /**
     * récupère les mots en langue source et en langue cible dans des listes correspondantes
     * @param path le chemin vers le fichier dont on veut récupérer les mots à traduire
     * @return une arraylist de listes de morceaux contenant dans une liste les mots à traduire de référence dans la langue source et dans l'autre les traductions en langue cible
     * @throws IOException
     */
    public ArrayList<List<Morceaux>> getRefAndTranslation(String path) throws IOException {
        ArrayList<String> lines = new ArrayList<>(Files.readAllLines(Paths.get(path)));
        Collections.shuffle(lines); // pour ne pas avoir toujours les 10 premiers mots quand un fichier en contient plus

        List<Morceaux> wordRef = new ArrayList<>();
        List<Morceaux> wordTranslation = new ArrayList<>();

        for (int i=0; i<10; i++){
            Morceaux mc = new Morceaux(lines.get(i).split(", ")[0], 0, lines.get(i).split(", ")[0].length()); // les mots en langue source sont des morceaux
            MorceauVariable mv = new MorceauVariable(lines.get(i).split(", ")[1], 0, lines.get(i).split(", ")[1].length()); // les mots à traduire en langue cible sont des morceaux variables
//            System.out.println(mc.reponseAttendue());
//            System.out.println(mv.reponseAttendue());
            wordRef.add(mc);
            wordTranslation.add(mv);
        }

        // pour renvoyer les 2 listes en même temps on les mets dans une arraylist
        return new ArrayList<>(Arrays.asList(wordRef, wordTranslation));
    }

    /**
     * implémente l'exercice de traduction
     * @return la note obtenue à l'issue de l'exercice
     * @throws NoSuchOptionException
     * @throws IOException
     * @throws ParseurException
     */
    public float translation() throws NoSuchOptionException, IOException, ParseurException {
        String pathToExercice;
        float noteTotale= 0;

        // choix de la langue cible en fonction de la langue apprise par l'utilisateur
        switch (this.langageUser){
            case NL -> pathToExercice = "nl/translation_";
            case RU -> pathToExercice = "ru/translation_";
            default -> throw new NoSuchOptionException("Ce type d'exercice n'est pas disponible pour cette langue");
        }
        switch (this.levelUser) {
            case DÉBUTANT -> {
                pathToExercice = pathToExercice + "lvl1.txt";
                this.notation.setSnot(new NotationDebutant());
            }
            case INTERMÉDIAIRE -> {
                pathToExercice = pathToExercice + "lvl2.txt";
                this.notation.setSnot(new NotationIntermediaire());
            }
            case AVANCÉ -> {
                pathToExercice = pathToExercice + "lvl3.txt";
                this.notation.setSnot(new NotationAvance());
            }
            case PRO -> {
                pathToExercice = pathToExercice + "lvl4.txt";
                this.notation.setSnot(new NotationPro());
            }
            default ->
                    throw new NoSuchOptionException("Nous travaillons d'arrache-pied pour ajouter sous peu de nouvelles fonctionnalités à notre application");
        }

        // récupération des listes à partir de la méthode
        ArrayList<List<Morceaux>> words = getRefAndTranslation(pathToExercice);
//        System.out.println(words.size());
        List<Morceaux> wordRef = words.get(0);
        List<Morceaux> wordTranslation = words.get(1);

        System.out.println("Traduisez les mots suivants : ");
        System.out.println("Si vous n'avez pas la réponse appuyez simplement sur entrée pour passer au mot suivant sans perdre de points");

        // pour chaque mot de la liste des mots à traduire
        for (int i=0; i<wordTranslation.size(); i++){
            Phrase ref = new Phrase(Collections.singletonList(wordTranslation.get(i)));

            System.out.print(wordRef.get(i).reponseAttendue() + " -> ");
            Scanner input = new Scanner(System.in);
            String repUtilisateur = input.nextLine();

            ElementReponse er = new ElementReponse(ref, repUtilisateur);
            noteTotale = noteTotale + this.noteTranslation(er, notation);

            System.out.println("Note obtenue pour ce mot : "+er.getNote()+"\n");

        }
        System.out.println("Note totale obtenue pour cet exercice : "+ noteTotale);
        return noteTotale;
    }

    /**
     * implémente l'exerice de phrases à trou
     * @return la note obtenue à l'issue de l'exercice
     * @throws IOException
     * @throws ParseurException
     * @throws NoSuchOptionException
     */
    public float missingWord() throws IOException, ParseurException, NoSuchOptionException {
        String pathToExercice;

        // choix de l'exercice en fonction de la langue de l'utilisateur et de son niveau
        switch (this.langageUser){
            case FR -> pathToExercice = "fr/fr_";
            case RU -> pathToExercice = "ru/ru_";
            case NL -> pathToExercice = "nl/nl_";
            default -> throw new NoSuchOptionException("Nous travaillons d'arrache-pied pour ajouter sous peu de nouvelles fonctionnalités à notre application");
        }

        switch (this.levelUser) {
            case DÉBUTANT -> {
                pathToExercice = pathToExercice + "lvl1.txt";
                this.notation.setSnot(new NotationDebutant());
            }
            case INTERMÉDIAIRE -> {
                pathToExercice = pathToExercice + "lvl2.txt";
                this.notation.setSnot(new NotationIntermediaire());
            }
            case AVANCÉ -> {
                pathToExercice = pathToExercice + "lvl3.txt";
                this.notation.setSnot(new NotationAvance());
            }
            case PRO -> {
                pathToExercice = pathToExercice + "lvl4.txt";
                this.notation.setSnot(new NotationPro());
            }
            default ->
                    throw new NoSuchOptionException("Nous travaillons d'arrache-pied pour ajouter sous peu de nouvelles fonctionnalités à notre application");
        }
//        System.out.println(pathToExercice);

        float noteTotale = 0;

        ArrayList<List<Morceaux>> parsedSentences = parse(pathToExercice);
        ArrayList<String> mvFromList = getEveryMvFromList(parsedSentences);
        Collections.shuffle(mvFromList);

        System.out.println("Avec la liste des mots ci-dessous, complétez ces phrases : ");
        System.out.println("⚠️ Vous devez impérativement respecter la casse initiale de la phrase et des mots manquants");
        System.out.println("💡 Si vous n'avez pas la réponse, remplacez le mot manquant par un X, cela pourrait vous éviter de perdre des points !\n");

            for (List<Morceaux> sentence : parsedSentences){
                Phrase reference = new Phrase(sentence);

                // affichage des mots possibles et de la phrase à trou
                System.out.println(mvFromList);
                System.out.println(reference.pourEleve(sentence));

                Scanner input = new Scanner(System.in);
                String repUtilisateur = input.nextLine();

                ElementReponse er = new ElementReponse(reference, repUtilisateur);
                noteTotale = noteTotale + this.noteMw(er, notation);

                System.out.println("Note obtenue pour cette phrase : "+er.getNote()+"\n");

            }
        System.out.println("Note totale obtenue pour cet exercice : "+ noteTotale);
        return noteTotale;
    }

    /**
     * corrige un élément de réponse pour l'exercice de phrases à trous (appelle la méthode corrigeMw de élément réponse)
     * @param er élément de réponse (morceau de référence et morceau donné par l'utilisateur)
     */
    public void corrigeMw(ElementReponse er){
        er.corrigeMw();
    }


    /**
     * corrige un élément de réponse pour l'exercice de traduction (appelle la méthode corrigeTranslation de élément réponse)
     * @param er élément de réponse (morceau de référence et morceau donné par l'utilisateur)
     */
    public void corrigeTranslation(ElementReponse er){
        er.corrigeTranslation();
    }


    /**
     * note un exercice de phrases à trou (appelle corrigeMw de exercice)
     * @param er élément de réponse (morceau de référence et morceau donné par l'utilisateur)
     * @param notation un type de notation à appliquer selon le niveau de l'utilisateur
     * @return la note obtenue à l'issue de l'exercice
     */
    protected float noteMw(ElementReponse er, Notation notation){
        this.corrigeMw(er);
        er.noteMw(notation);
        return er.getNote();
    }

    /**
     * note un exercice de traduction (appelle corrigeTranslation de exercice)
     * @param er élément de réponse (morceau de référence en langue cible et morceau donné par l'utilisateur en langue cible)
     * @param notation un type de notation à appliquer (dans le cas de cette exercice c'est toujours la notation débutante qui est appliquée)
     * @return la note obtenue à l'issue de l'exercice
     */
    protected float noteTranslation(ElementReponse er, Notation notation){
        this.corrigeTranslation(er);
        er.noteTranslation(notation);
        return er.getNote();
    }
}
