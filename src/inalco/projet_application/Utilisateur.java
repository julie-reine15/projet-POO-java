package inalco.projet_application;

public class Utilisateur {

    int id; // id de l'utilisateur
    String name; // pseudo de l'utilisateur
    CodeLangue.CODE_LANGUE langage; // langue apprise par l'utilisateur
    Niveau level; // niveau de l'utilisateur
    float point; // points totaux de l'utilisateur (tout exercice confondu)
    boolean contributeur; // si l'user est un contributeur (s'il peut ajouter des éléments aux exercices)

    /**
     * constructeur de la classe
     * @param userDetails liste contenant les informations du profil utilisateur
     */
    public Utilisateur(String[] userDetails){
        this.id = Integer.parseInt(userDetails[0]);
        this.name = userDetails[1];
        this.langage = CodeLangue.CODE_LANGUE.valueOf(userDetails[2]);
        this.level = Niveau.valueOf(userDetails[3]);
        this.point = Float.parseFloat(userDetails[4]);
        this.contributeur = Boolean.parseBoolean(userDetails[5]);
    }

    public String[] getUser(){
        return new String[]{String.valueOf(this.id), this.name, String.valueOf(this.langage), String.valueOf(this.level), String.valueOf(this.point), String.valueOf(this.contributeur)};
    }

    /**
     * @return le pseudo de l'utilisateur
     */
    public String getName() {
        return name;
    }

    /**
     * @return id de l'utilisateur
     */
    public int getId() {
        return id;
    }

    /**
     * @return langue apprise par l'utilisateur
     */
    public CodeLangue.CODE_LANGUE getLangage() {
        return langage;
    }

    /**
     * @return niveau de l'utilisateur
     */
    public Niveau getLevel() {
        return level;
    }

    /**
     * @return points totaux de l'utilisateur (tout exercice confondu)
     */
    public float getPoint() {
        return point;
    }

    /**
     * @param point met à jour les points d'un utilisateur
     */
    public void setPoint(float point) {
        this.point = this.point + point;
    }

    /**
     * @return si l'user est un contributeur (0 si non, 1 si oui)
     */
    public boolean getContributeur(){return this.contributeur;}
}
