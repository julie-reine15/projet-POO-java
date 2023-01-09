package inalco.projet_application;


import java.util.*;

/**
 * Permet de reconstituer au format adéquat, en fonction de l'utilisateur, une phrase qui a été parsée et
 * transformée en liste de morceaux
 */
public class Phrase {

    /**
     * la classe phrase va parcourir une liste de morceaux et afficher la phrase selon le rendu souhaité
     */

    protected List<Morceaux> phrase; // liste contenant tous les morceaux d'une phrase
    protected List<Morceaux> morceauxFixe; // liste contenant tous les morceaux fixes
    protected List<MorceauVariable> morceauxVariables; // liste contenant tous les morceaux variables

    /**
     * transforme une liste de morceaux en phrase
     * @param sentence : une liste de morceaux provenant d'une phrase qui a été parsée
     */
    public Phrase(List<Morceaux> sentence){
        this.phrase = sentence;
        morceauxFixe = new LinkedList<>();
        morceauxVariables = new LinkedList<>();

        for (Morceaux m : sentence){
            if (m instanceof MorceauVariable){
                morceauxVariables.add((MorceauVariable) m);
            } else {
                morceauxFixe.add(m);
            }
        }
    }

    /**
     * @param sentChunk : une liste de morceaux provenant d'une phrase qui a été parsée
     * @return : un string buffer, correspondant à la phrase avec les mots que l'élève est censé retrouver entre #
     */
    public StringBuffer pourProf(List<Morceaux> sentChunk){
        StringBuffer sentProf = new StringBuffer();

        for (int i = 0; i<sentChunk.size(); i++){
            if (i==sentChunk.size()-1){
                sentProf.append(sentChunk.get(i).pourProf());
            } else {
                String morceau = sentChunk.get(i).pourProf()+" ";
                sentProf.append(morceau);
            }
        }
        return sentProf;
    }

    /**
     * @param sentChunk : une liste de morceaux provenant d'une phrase qui a été parsée
     * @return : un string buffer, correspondant à la phrase avec les mots que l'élève est censé retrouver remplacé
     * par "___"
     */
    public StringBuffer pourEleve(List<Morceaux> sentChunk){
        StringBuffer sentEleve = new StringBuffer();

        for (int i = 0; i<sentChunk.size(); i++){
            if (i==sentChunk.size()-1){
                sentEleve.append(sentChunk.get(i).pourEleve());
            } else {
                String morceau = sentChunk.get(i).pourEleve()+" ";
                sentEleve.append(morceau);
            }
        }
        return sentEleve;
    }

    /**
     * @param sentChunk : une liste de morceaux provenant d'une phrase qui a été parsée
     * @return : un string buffer, correspondant à la phrase avec tous les mots
     */
    public StringBuffer repAttendue(List<Morceaux> sentChunk){
        StringBuffer repAttendue = new StringBuffer();

        for (int i = 0; i<sentChunk.size(); i++){
            if (i==sentChunk.size()-1){
                repAttendue.append(sentChunk.get(i).reponseAttendue());
            } else {
                String morceau = sentChunk.get(i).reponseAttendue()+" ";
                repAttendue.append(morceau);
            }
        }
        return repAttendue;
    }

    public List<Morceaux> analyseRepEleve(String rep) throws ParseurException{
        int startMorceau;
        int endMorceau = 0;
        List<Morceaux> repEleve = new ArrayList<>();

        for (int i = 0; i<this.phrase.size(); i++){
//            System.out.println("M P "+this.phrase.get(i).reponseAttendue());
            if(!(this.phrase.get(i) instanceof MorceauVariable)){ // si c'est un morceau
                if(rep.contains(this.phrase.get(i).reponseAttendue())){ // et que la réponse de l'élève contient ce même morceau
                    startMorceau = rep.indexOf(this.phrase.get(i).reponseAttendue());
                    endMorceau = startMorceau + this.phrase.get(i).reponseAttendue().length();

                    Morceaux mc = new Morceaux(rep, startMorceau, endMorceau);
                    repEleve.add(mc);

                } else {
                    throw new ParseurException("La phrase initiale ne doit pas être modifiée !");
                }
            } else { // si c'est un morceau variable
                startMorceau = endMorceau;
                if (i==this.phrase.size()-1){
                    endMorceau = rep.length();
                } else {
                    if (!rep.contains(this.phrase.get(i+1).reponseAttendue())){
                        throw new ParseurException("La phrase initiale ne doit pas être modifiée !");
                    } else {
                        endMorceau = rep.indexOf(this.phrase.get(i+1).reponseAttendue());
                    }
                }
                MorceauVariable mv = new MorceauVariable(rep, startMorceau, endMorceau);
                repEleve.add(mv);
            }
        }
//        Phrase p = new Phrase(repEleve);
//        System.out.println(p.repAttendue(repEleve));
        return repEleve;
    }

    public Iterator<MorceauVariable> getIteratorMv(){
        return morceauxVariables.iterator();
    }

}