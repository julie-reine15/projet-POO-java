
package inalco.projet_application;


import java.io.IOException;
import java.util.Scanner;

public class Application {


    /**
     * méthode qui permet de lancer l'application'
     * @throws IOException
     * @throws UserNotFoundException
     * @throws NoSuchOptionException
     * @throws IdAlreadyExistingException
     * @throws ParseurException
     */
    public void displayApplication() throws IOException, UserNotFoundException, NoSuchOptionException, IdAlreadyExistingException, ParseurException {
        WelcomeIn welcomeIn = new WelcomeIn();
        Database db = new Database();

        Utilisateur user = welcomeIn.inscription();

        if (welcomeIn.getNewOrNot()){ // si l'utilisateur vient de s'inscrire on doit ajouter manuellement à la liste
            // qui contient déjà tout ceux qui étaient inscrits pour que le calcul des points se fasse correctement
            db.updateUserDetails(user.getUser());
        }


        System.out.println("Votre profil : ");
        String name_user = user.getName();
        System.out.println("Pseudonyme : "+name_user);
        int id_user = user.getId();
        System.out.println("Id : "+id_user);
        Niveau level_user = user.getLevel();
        System.out.println("Niveau : "+level_user);
        CodeLangue.CODE_LANGUE langue_user = user.getLangage();
        System.out.println("Langue apprise : "+langue_user);
        float point_user = user.getPoint();
        System.out.println("Nombre de points obtenus : "+point_user);
        boolean contributeur = user.getContributeur();
        System.out.println("Contributeur : "+contributeur);
        System.out.println();

        boolean keepGoing = true;
        Scanner input;

        if (contributeur){ // si l'utilisateur est un contributeur
            Contribution contribution = new Contribution(langue_user);
            while(keepGoing){
                contribution.chooseContribution();
                System.out.println("\nSouhaitez-vous faire autre chose ? Y/n");
                input = new Scanner(System.in);
                String rep = input.nextLine().toLowerCase();

                if (rep.startsWith("n")){ // si l'utilisateur n'a plus rien à faire la boucle s'arrête
                    keepGoing = false;
                } else if (rep.startsWith("y")) {
                    keepGoing = true;
                } else {
                    throw new NoSuchOptionException("Ce choix n'est pas disponible");
                }
            }

        } else { // l'utilisateur n'est pas contributeur

            Exercices exercice = new Exercices(level_user, langue_user);

            while(keepGoing){
                System.out.println("Que voulez-vous faire aujourd'hui ?");
                System.out.println("Tapez 1 pour faire un exercice");
                System.out.println("Tapez 2 pour consulter votre place dans le classement");

                input = new Scanner(System.in);
                int choixUt = input.nextInt();

                switch (choixUt) {  // suivant son choix,
                    case 1 -> {
                        float point = exercice.chooseExercice(); // il choisit de faire un exercice
//                        System.out.println("point app : " + point);
                        db.updatePoint(id_user, point);
//                        System.out.println("ok");
                    }
                    case 2 -> db.seeRanking(); // il consulte le classement des utilisateurs
                }

                System.out.println("\nSouhaitez-vous faire autre chose ? Y/n");
                input = new Scanner(System.in);
                String rep = input.nextLine().toLowerCase();
                if (rep.startsWith("n")){ // si l'utilisateur n'a plus rien à faire la boucle s'arrête
                    keepGoing = false;
                } else if (rep.startsWith("y")) {
                    keepGoing = true;
                } else {
                    throw new NoSuchOptionException("Ce choix n'est pas disponible");
                }
            }
        }
        System.out.println("\n\nNous espérons que vous avez passé un agréable moment. À bientôt ! ");
    }


    public static void main(String[] args) throws IOException, UserNotFoundException, NoSuchOptionException, IdAlreadyExistingException, ParseurException {

        Application app = new Application();
        app.displayApplication();
    }
}
