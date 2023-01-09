package inalco.projet_application;

public class NoSuchOptionException extends Exception{

    /**
     * quand l'utilisateur fait un choix qui n'existe pas
     * @param message message à afficher quand l'exception est thrown
     */
    public NoSuchOptionException(String message){
        super(message);
    }
}