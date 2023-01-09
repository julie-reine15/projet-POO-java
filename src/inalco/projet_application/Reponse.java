package inalco.projet_application;

public class Reponse {
    /**
     * type de résultats que peut avoir un élément corrigé
     */
    enum REPONSE {
        CORRECT,
        INCORRECT,
        NON_RÉPONDU
    }

    /**
     * constructeur de la classe
     */
    private Reponse(){}

    /**
     * transforme une string correspondant à une des constantes de l'énumération et renvoie la constante correspondante
     * @param name une string qui correspond à une des constantes de l'énumération
     * @return une des constantes de l'énumération qui correspond à la string en paramètre
     * @throws NoSuchOptionException
     */
    public static REPONSE valueOf(String name) throws NoSuchOptionException{
        REPONSE rep = null;
        if (name.equals(REPONSE.CORRECT.toString())){
            rep = REPONSE.CORRECT;
        } else if (name.equals(REPONSE.INCORRECT.toString())) {
            rep = REPONSE.INCORRECT;
        } else if (name.equals(REPONSE.NON_RÉPONDU.toString())) {
            rep = REPONSE.NON_RÉPONDU;
        } else {
            throw new NoSuchOptionException("choix indisponible");
        }
        return rep;
    }
}


