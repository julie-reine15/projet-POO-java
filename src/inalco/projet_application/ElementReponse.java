package inalco.projet_application;

public class ElementReponse {

    protected Phrase enonce; // énoncé de l'exercice
    protected Phrase repUtilisateur; // réponse de l'utilisateur
    protected Correction correction = new Correction(); // instance de correction pour appliquer la correction
    protected float note; // note obtenue à l'issue d'une correction


    /**
     * constructeur de la classe
     * @param enonce une instance de la classe phrase représentant l'énoncé d'une phrase à trou (format réponse attendue)
     * @param reponseUtilisateur une instance de la classe phrase représentant la réponse de l'utilisateur (format réponse attendue)
     * @throws ParseurException
     */
    public ElementReponse(Phrase enonce, String reponseUtilisateur) throws ParseurException {
        this.enonce = enonce;
        this.repUtilisateur = new Phrase(enonce.analyseRepEleve(reponseUtilisateur)); // transformation de la réponse utilisateur en phrase
//        System.out.println("er "+repEleve.repAttendue(repEleve.phrase));
    }

    /**
     * effectue la correction pour les exercices de phrases à trou
     */
    public void corrigeMw(){
        correction.correctionMw(this.enonce, this.repUtilisateur);
    }

    /**
     * effectue la correction pour les exercices de traduction
     */
    public void corrigeTranslation(){correction.correctionTranslation(this.enonce, this.repUtilisateur);}

    /**
     * @return la correction effectuée sur un exercice
     */
    public Correction getCorrection() {
        return correction;
    }

    /**
     * @return la phrase de référence d'un exerice (une phrase à trou format réponse attendue ou le mot de référence dans la langue cible pour l'exercice de traduction)
     */
    public Phrase getEnonce(){
        return this.enonce;
    }

    /**
     * @return la phrase de la réponse de l'utilisateur d'un exercice
     */
    public Phrase getRepUtilisateur(){
        return this.repUtilisateur;
    }

    /**
     * effectue la notation d'un exercice de phrases à trous
     * @param notation une instance d'une notation
     */
    public void noteMw(Notation notation){
        this.note = notation.applyNotation(correction.getElementCorrige());
    }

    /**
     * effectue la notation d'un exercice de traduction
     * @param notation une instance d'une notation
     */
    public void noteTranslation(Notation notation){
        this.note = notation.applyNotation(correction.getElementCorrige());
    }

    /**
     * @return la note obtenue à un exercice après correction et notation de celui-ci
     */
    public float getNote(){
        return this.note;
    }
}
