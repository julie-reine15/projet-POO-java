package inalco.projet_application;

public class IdAlreadyExistingException extends Exception{

    /**
     * si un utilisateur veut s'inscrire avec un id déjà utilisé
     * @param message message à afficher quand l'exception est thrown
     */
    public IdAlreadyExistingException(String message){
        super(message);
    }
}
