package inalco.projet_application;

import com.opencsv.CSVWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static java.lang.Integer.parseInt;

public class Database {

    List<Integer> idNb; // liste contenant les id des utilisateurs
    List<String[]> userDetails; // liste contenant tous les détails des profils des utilisateurs
    File user_gazetteer = new File("utilisateurGazetteer.csv"); // fichier contenant les informations des utilisateurs inscrits sur l'application

    /**
     * constructeur de la classe qui lit l'attribut user_gazetteer et rempli les attributs idNb & userDetails
     *
     * @throws IOException
     */
    public Database() throws IOException {

        String separator = ",";
        BufferedReader br = new BufferedReader(new FileReader(user_gazetteer));
        String line;
        this.idNb = new ArrayList<>();
        this.userDetails = new ArrayList<>();

        while ((line = br.readLine()) != null) {
            String[] lineInfo = line.split(separator);
            try {
                int idNumber = parseInt(lineInfo[0]);
                this.idNb.add(idNumber); // ajout des n° d'utilisateur à la liste
                this.userDetails.add(lineInfo); // ajout des infos des utilisateurs à la liste
            } catch (NumberFormatException e) {
                continue;
            }
        }
    }

    /**
     * permet de mettre à jour la liste des utilisateurs sans avoir besoin de redémarrer le programme
     * @param user un tableau de string contenant les infos du profil à ajouter
     */
    public void updateUserDetails(String[] user) {
        this.userDetails.add(user);
    }

    /**
     * @return la liste des id des utilisateurs
     */
    public List<Integer> getIdNb() {
        return idNb;
    }

    /**
     * @return la liste des infos des profils des utilisateurs
     */
    public List<String[]> getUsersDetails() {
        return userDetails;
    }

    /**
     * @return le fichier contenant les informations sur les utilisateurs inscrits sur l'application
     */
    public File getUser_gazetteer() {
        return user_gazetteer;
    }

    /**
     * met à jour les points d'un utilisateur après un exercice
     *
     * @param idUser     id de l'utilisateur
     * @param pointToAdd nombre de points obtenus à l'issue de l'exercice
     * @throws IOException
     */
    public void updatePoint(int idUser, float pointToAdd) throws IOException {
//        System.out.println(this.userDetails.size());
        if (this.user_gazetteer.delete()) { // pour mettre à jour la base de données on doit supprimer entièrement son contenu
            ArrayList<String[]> newUserInfo = new ArrayList<>();

            for (String[] userInfo : userDetails) { // dans la liste qui contient toutes les infos des profils
                if (Integer.valueOf(userInfo[0]).equals(idUser)) { // lorsque l'un des id correspond à celui de l'utilisateur loggé

//                    System.out.println(userInfo[3]);
                    System.out.println("point avant l'exercice : " + userInfo[4]); // on lui affiche ses points avant l'exercice
                    float beforeEx = Float.parseFloat(userInfo[4]); // on les stocke dans une variable pour gérer le passage de niveau après l'exercice
                    userInfo[4] = String.valueOf(Float.parseFloat(userInfo[4]) + pointToAdd); // on lui ajoute les points qu'il a obtenu en faisant l'exercice
                    System.out.println("point après l'exercice : " + userInfo[4]); // on lui affiche son nouveau nombre de points

                    if (Float.parseFloat(userInfo[4]) >= 30 && beforeEx < 30) { // si l'utilisateur dépasse 30 points il passe au niveau pro
                        userInfo[3] = this.updateNiveau(userInfo[3]);
                        System.out.println("Félicitations, vous passez au niveau supérieur ! Vous pouvez maintenant accéder à l'exercice " + userInfo[3]);
                    } else if (Float.parseFloat(userInfo[4]) >= 20 && beforeEx < 20) { // si l'utilisateur dépasse 20 points il passe au niveau avancé
                        userInfo[3] = this.updateNiveau(userInfo[3]);
                        System.out.println("Félicitation vous passez au niveau supérieur ! Vous pouvez maintenant accéder à l'exercice " + userInfo[3]);
                    } else if (Float.parseFloat(userInfo[4]) >= 10 && beforeEx < 10) { // si l'utilisateur dépasse 10 points il passe au niveau intermédiaire
                        userInfo[3] = this.updateNiveau(userInfo[3]);
                        System.out.println("Félicitation vous passez au niveau supérieur ! Vous pouvez maintenant accéder à l'exercice " + userInfo[3]);
                    }
                    newUserInfo.add(userInfo); // on ajoute à la liste les nouvelles infos de l'utilisateur
                } else {
                    newUserInfo.add(userInfo); // on ajoute aussi les infos des autres profils inchangées
                }
            }
            String[] header = {"Identifiant", "Pseudo", "Langue", "Niveau", "Points", "Contributeur"}; // header du fichier csv

            FileWriter outputfile = new FileWriter(this.user_gazetteer, true); // on réécrit dans le même fichier qu'on a vidé au début
            CSVWriter writer = new CSVWriter(outputfile);
            writer.writeNext(header, false);

            for (String[] nui : newUserInfo) {
                writer.writeNext(nui, false); // on réécrit chaque profil qu'on a ajouté précédemment à la liste dans la base de données
            }
            writer.close();
        } else {
            System.out.println("ne fonctionne pas"); // au cas où mais en principe ça fonctionne
        }
    }

    /**
     * met à jour le niveau d'un utilisateur
     *
     * @param user_level niveau auquel se trouve l'utilisateur
     * @return le niveau atteint par l'utilisateur
     */
    public String updateNiveau(String user_level) {
        Niveau lvl = Niveau.valueOf(user_level);
        switch (lvl) { // suivant le niveau actuel de l'utilisateur quand la méthode est appelée on le fait passer au niveau supérieur
            case DÉBUTANT -> user_level = String.valueOf(Niveau.INTERMÉDIAIRE);
            case INTERMÉDIAIRE -> user_level = String.valueOf(Niveau.AVANCÉ);
            case AVANCÉ -> user_level = String.valueOf(Niveau.PRO);
        }
        return user_level;
    }

    /**
     * permet d'afficher le classement des utilisateurs de l'application en fonction de leur points
     *  -> les contributeurs ne sont pas pris en compte comme leur point reste toujours à 0
     */
    public void seeRanking() {
        ArrayList<List<Object>> info = new ArrayList<>();
        for (String[] userInfo : userDetails) {
            if (Boolean.parseBoolean(userInfo[5])){
                // si l'utilisateur est un contributeur on ne le prend pas en compte
            } else { // si l'utilisateur n'est pas un contributeur, on le prend en compte dans le classement
                List<Object> lo = new ArrayList<>();
                lo.add(userInfo[0]);
                lo.add(userInfo[1]);
                lo.add(userInfo[4]);

//                System.out.println(userInfo[0]+userInfo[1]);

                if (info.isEmpty()) {
                    info.add(lo);

                } else if (info.size() == 1) {
                    if (Float.parseFloat(userInfo[4]) > Float.parseFloat(String.valueOf(info.get(0).get(2)))) {
                        info.add(lo);
                    } else {
                        info.add(0, lo);
                    }
                } else if (Float.parseFloat(userInfo[4])>Float.parseFloat(String.valueOf(info.get(info.size()-1).get(2)))) {
                    info.add(lo);
                } else if (Float.parseFloat(userInfo[4]) == 0.0) {
                    info.add(0, lo);
                } else {
                    int stop = info.size();
                    for (int i = 1; i < stop; i++) {
                        float toCompare1 = Float.parseFloat(String.valueOf(info.get(i - 1).get(2)));
                        float toCompare2 = Float.parseFloat(String.valueOf(info.get(i).get(2)));
                        float x = Float.parseFloat(userInfo[4]);

                        if (toCompare1 < x) {
                            if (x < toCompare2) {
                                info.add(i, lo);
                            }
                        }
                    }
                }
            }
            System.out.println(info);
        }
        int i = info.size()-1;
        while (i>=0) {
            System.out.println(info.get(i).toString());
            i--;
        }
    }
}
