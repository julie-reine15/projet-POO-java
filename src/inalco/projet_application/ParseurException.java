package inalco.projet_application;

public class ParseurException extends Exception{

    /**
     * quand le parseur n'est pas content
     * @param message message à afficher quand l'exception est thrown
     */
    public ParseurException(String message){
        super(message);
    }
}
