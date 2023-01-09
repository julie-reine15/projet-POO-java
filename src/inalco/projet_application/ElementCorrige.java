package inalco.projet_application;


public class ElementCorrige {

    protected Morceaux mvRef; // morceau de référence
    protected Morceaux mvUtilisateur; // morceau donné par l'utilisateur
    protected Reponse.REPONSE resultat; // résultat obtenu après correction

    /**
     * constructeur de la classe
     * @param mvRef morceau de référence venant de la phrase de référence
     * @param mvUtilisateur morceau venant de la réponse de l'utilisateur
     */
    public ElementCorrige(Morceaux mvRef, Morceaux mvUtilisateur){
        this.mvRef = mvRef;
        this.mvUtilisateur = mvUtilisateur;
    }

    /**
     * permet d'attribuer un résultat à la correction de 2 éléments corrigés
     * @param rep le résultat de la correction associé au couple de morceaux qui forment l'élément corrigé
     */
    public void setResultat(Reponse.REPONSE rep){
        this.resultat = rep;
    }

    /**
     * @return le résultat d'une correction
     */
    public Reponse.REPONSE getResultat(){
        return this.resultat;
    }

    /**
     * @return le morceau de référence de l'élément corrigé
     */
    public Morceaux getMvRef() {
        return mvRef;
    }

    /**
     * @return le morceau produit par l'utilisateur de l'élément corrigé
     */
    public Morceaux getMvUtilisateur() {
        return mvUtilisateur;
    }
}
