package inalco.projet_application;

/**
 * la classe morceau permet d'instancier les morceaux fixe d'une phrase d'un exercice
 */
public class Morceaux {

    protected String morceau;
    protected int start, end;


    /**
     * constructeur de la classe
     * @param sentence une phrase au format string qui sera découpée en morceaux
     * @param start indice de début du morceau
     * @param end indice de fin du morceau
     */
    Morceaux(String sentence, int start, int end){
        this.start = start;
        this.end = end;
        this.morceau = sentence.substring(this.start, this.end);
    }

    /**
     * @return : le morceau format string sans aucun #
     */
    public String pourProf(){
        return this.morceau;
    }

    /**
     * @return : le morceau format string sans aucun #
     */
    public String pourEleve(){
        // renvoie la chaîne telle quelle
        return this.morceau;
    }
    /**
     * @return : le morceau format string sans aucun #
     */
    public String reponseAttendue(){
        // renvoie la chaîne telle quelle
        return this.morceau;
    }
}
