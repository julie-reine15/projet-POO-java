package inalco.projet_application;

/**
 * la classe morceauVariable permet d'instancier les morceaux des phrases d'un exericce qui seront, pour un élève,
 * le mot à retrouver, pour un prof, le mot que l'élève doit trouver signalé entre 2 # et enfin la réponse attendue
 * pour la correction de l'exercice en question
 */
public class MorceauVariable extends Morceaux{

    /**
     * constructeur de la classe
     * @param sentence une phrase au format string qui sera découpée en morceaux
     * @param start indice de début du morceau
     * @param end indice de fin du morceau
     */
    protected MorceauVariable(String sentence, int start, int end){
        super(sentence, start, end);
    }

    /**
     *
     * @return : doit afficher le morceau avec les # autours de mots qui sont normalement des morceaux variables
     */
    @Override
    public String pourProf() {
        return "#"+morceau+"# ";
    }

    /**
     *
     * @return : le morceau au format élève -> les morceaux variables sont remplacés par "___"
     */
    @Override
    public String pourEleve(){
        return morceau.replaceAll(morceau,"___");
    }

}
