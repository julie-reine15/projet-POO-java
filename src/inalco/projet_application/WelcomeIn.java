package inalco.projet_application;

import com.opencsv.CSVWriter;

import java.io.*;
import java.util.List;
import java.util.Scanner;


public class WelcomeIn {

    Utilisateur user;
    Database userData = new Database();
    List<Integer> idNumberList = userData.getIdNb();
    List<String[]> userDetails = userData.getUsersDetails();
    File user_gazetteer = userData.getUser_gazetteer();
    protected boolean newOrnot; // si l'utilisateur vient de s'inscrire (true) ou pas (false)

    /**
     * constructeur de la classe
     * donne quelques détails sur l'application
     * @throws IOException
     */
    public WelcomeIn() throws IOException {
        System.out.println("Bienvenue sur notre application d'apprentissage des langues !");
        System.out.println("Si vous êtes nouveau parmi nous, vous pouvez facilement et rapidement créer un nouveau profil");
        System.out.println("Notez que pour le moment, vous ne pouvez associer qu'une langue par profil, mais qu'il est possible " +
                "de faire autant de comptes que nécessaire si vous voulez apprendre plus d'une langue !");
        System.out.println("Notre application offre pour le moment la possibilité d'apprendre 3 langues :\n- le français\n- le néerlandais\n- le russe");
        System.out.println("et propose 2 types d'exercices comportant 4 difficultés différentes :\n- débutant\n- intermédiaire\n- avancé\n- pro\n");
    }

    public void setNewOrnot(boolean yesOrNo){
        this.newOrnot = yesOrNo;
    }

    public boolean getNewOrNot(){
        return this.newOrnot;
    }

    /**
     * inscrit un utilisateur dans la base de données ou le log à son profil
     *
     * @return l'utilisateur qui vient de se connecter
     * @throws NoSuchOptionException
     * @throws UserNotFoundException
     * @throws IdAlreadyExistingException
     * @throws IOException
     */
    public Utilisateur inscription() throws NoSuchOptionException, UserNotFoundException, IdAlreadyExistingException, IOException {

        System.out.println("Êtes-vous inscrit ? Y/n");

        Scanner input = new Scanner(System.in);
        String id = input.nextLine();

        if (id.toLowerCase().startsWith("y")){
            setNewOrnot(false);
            System.out.println("Veuillez entrer votre id :");
            input = new Scanner(System.in);
            String inputId = input.nextLine();
//            System.out.println(this.idNumberList);
            if (this.idNumberList.contains(Integer.valueOf(inputId)) ){ // si l'id rentré par l'utilisateur existe bien
                for (String [] uD : this.userDetails){ // on récupère les infos de son profil
                    if (uD[0].equals(inputId)){
//                        int idN = Integer.parseInt(uD[0]);
//                        String name = uD[1];
//                        System.out.println(uD[2].substring(1, uD[2].length()-1));
//                        CodeLangue.CODE_LANGUE cl = CodeLangue.CODE_LANGUE.valueOf(uD[2]);
//                        Niveau niveau = Niveau.valueOf(uD[3]);
//                        float point = Float.parseFloat(uD[4]);
//                        boolean contributeur = Boolean.parseBoolean(uD[5]);

                        this.user = new Utilisateur(uD); // l'utilisateur est loggé avec ses infos
//                        this.user = new ()
                    }
                }
//                return this.user.getUser();
                return this.user;
            } else {
                throw new UserNotFoundException("Il semblerait que vous ne soyez pas enregistré. Réessayez ou inscrivez-vous pour enregistrer votre identifiant.");
            }
        } else if (id.toLowerCase().startsWith("n")) { // si l'utilisateur n'est pas inscrit
            // il crée son profil
//            String[] infoProfil;
            setNewOrnot(true);
            System.out.println("Créez un identifiant constitué uniquement de chiffres : ");
            input = new Scanner(System.in);
            int inputNewId = input.nextInt();

            if (this.idNumberList.contains(inputNewId)){
                throw new IdAlreadyExistingException("Cet Id existe déjà, merci de recommencer");
            } else {
                System.out.println("Identifiant créé avec succès !");

                System.out.println("Veuillez maintenant créer un pseudonyme : ");
                input = new Scanner(System.in);
                String name = input.nextLine();

                System.out.println("Choisissez la langue que vous voulez apprendre : ");
                System.out.println("Pour le néerlandais tapez NL\nPour le français tapez FR\nPour le russe tapez RU");
                input = new Scanner(System.in);
                CodeLangue.CODE_LANGUE choix_langue = CodeLangue.CODE_LANGUE.valueOf(input.nextLine().toUpperCase());

                System.out.println("Enfin, souhaitez-vous être un contributeur ou un utilisateur de l'application ?");
                System.out.println("Tapez false pour utilisateur\nTapez true pour contributeur");
                System.out.println("⚠️ si vous choisissez d'être contributeur vous ne pourrez pas réaliser d'exercice (trop facile sinon)");
                input = new Scanner(System.in);
                boolean contributeur = input.nextBoolean();

                // on logge l'user avec les infos qu'il a renseigné (il commence par défaut au niveau étudiant à 0 point)
                this.user = new Utilisateur(new String[]{String.valueOf(inputNewId), name, String.valueOf(choix_langue), String.valueOf(Niveau.DÉBUTANT), String.valueOf((float) 0.0), String.valueOf(contributeur)});

                System.out.println("Votre inscription a bien été prise en compte !");
                System.out.println("Желаем удачи! / Veel geluk! / Bonne chance !");

                nouvelUtilisateur(); // création de l'user
//                userData.updateUserDetails(new String[]{String.valueOf(inputNewId), name, String.valueOf(choix_langue), String.valueOf(Niveau.DÉBUTANT), String.valueOf((float) 0.0), String.valueOf(contributeur)});
            }
        } else {
            throw new NoSuchOptionException("Choix indisponible, veuillez réessayer");
        }
//        return this.user.getUser();
        return this.user;
    }

    /**
     * si l'utilisateur est nouveau on l'inscrit dans la base de données
     * @throws IOException
     */
    public void nouvelUtilisateur() throws IOException{
            FileWriter outputfile = new FileWriter(this.user_gazetteer, true);

            CSVWriter writer = new CSVWriter(outputfile);

            String id = String.valueOf(this.user.getId());
            String name = this.user.getName();
            String langue = String.valueOf(this.user.getLangage());
            String niveau = String.valueOf(this.user.getLevel());
            String point = String.valueOf(this.user.getPoint());
            String contributeur = String.valueOf(this.user.getContributeur());

            String[] record = {id, name, langue, niveau, point, contributeur};

            writer.writeNext(record, false);

            writer.close();
    }
}
