package inalco.projet_application;

public class UserNotFoundException extends Exception {

    /**
     * quand l'utilisateur dit qu'il est inscrit mais que sont id n'est pas dans la base de données
     * @param message message à afficher lorsque l'exception est thrown
     */
    public UserNotFoundException(String message){
        super(message);
    }
}
