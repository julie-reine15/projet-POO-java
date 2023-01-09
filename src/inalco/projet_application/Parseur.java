package inalco.projet_application;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Permet de parser une phrase. La phrase est transformée en une liste de morceaux. Les mots se trouvant entre # sont
 * des morceaux variables, le reste sont des morceaux. En fonction de la position du mot entre # dans la phrase,
 * on récupère le ou les morceaux l'entourant pour les ajouter à la liste contenant tous les morceaux.
 */
public class Parseur implements Parsing{

    protected Pattern regex;
    /**
     * @param pattern string représentant une regex pour repérer les morceauVariable
     */
    Parseur(String pattern){
        this.regex = Pattern.compile(pattern);  // compilation de la regex pour trouver les mots entre # ou des couples de mots pour la traduction
    }


    /**
     *
     * @param sentence : une phrase à parser -> elle contient des mots entre #
     * @return : une liste contenant des instances des classes Morceaux et MorceauVariable -> les mots entre # seront
     * des instances de MorceauVariable et le reste de la phrase des instances de Morceaux
     */
    @Override
    public List<Morceaux> parse(String sentence){

        Matcher m = this.regex.matcher(sentence);    // récupération des mots entre #

        int lenghtSent = sentence.length(); // longueur totale de la phrase (utile pour la constitution des morceaux)
        int indexStart = 0; // indice utile pour la récupération des morceaux

        List<Morceaux> sentChunk = new ArrayList<>();   // liste dans laquelle on va stocker les morceaux qui représenteront la phrase

        while(m.find()){    // parcours les matchs trouvés dans la phrase précédemment
            if (m.start()==0) { // si le match est au début de la phrase
                if (m.end() == lenghtSent) { // et que la fin du match est à la fin de la phrase (en gros y a qu'un mot et il est entre #)
                    MorceauVariable mv = new MorceauVariable(sentence, 1, m.end()-1); // création du morceau
//                    System.out.println(mv.reponseAttendue());
                    sentChunk.add(mv); // ajout du morceau à la liste

                } else if (m.end()<lenghtSent) {    // si la fin du match est avant la fin de la phrase
                    // si il y a un autre # dans la phrase après le 2ème du match courant
                    if (sentence.substring(m.end()+1, lenghtSent).contains("#")) { // dans le cas où il y a encore un mot à matcher après celui-ci
                        MorceauVariable mv = new MorceauVariable(sentence, 1, m.end()-1); // création du morceau
                        sentChunk.add(mv); // ajout du morceau à la ligne
                        indexStart = m.end() + 1;   // on met à jour l'indice de départ pour les prochains morceaux
//                        System.out.println(mv.reponseAttendue());
                    } else { // si il n'y a pas d'autre mot à matcher ensuite
                        MorceauVariable mv = new MorceauVariable(sentence, 1, m.end()-1); // création du morceau
                        Morceaux mc = new Morceaux(sentence, m.end()+1, lenghtSent); // et on prend ce qu'il y a après le morceau pour avoir le reste de la phrase
//                        System.out.println(mv.reponseAttendue());
//                        System.out.println(mc.reponseAttendue());
                        sentChunk.add(mv); // on les ajoute dans l'ordre dans la liste
                        sentChunk.add(mc);
                    }
                }
            } else { // si le 1er match n'est pas au début de la phrase
                if (m.end() == lenghtSent) { // si la fin du match est à la fin de la phrase

                    Morceaux mc = new Morceaux(sentence, indexStart, m.start()-1); // création des morceaux
                    MorceauVariable mv = new MorceauVariable(sentence, m.start()+1, m.end()-1);
//                    System.out.println(mc.reponseAttendue());
//                    System.out.println(mv.reponseAttendue());
                    sentChunk.add(mc); // ajout des morceaux à la liste
                    sentChunk.add(mv);
                    //indexStart = m.end()+1;
                } else if (m.end()<lenghtSent) { // si la fin du match est avant la fin de la phrase
                    if (sentence.substring(m.end()+1, lenghtSent).contains("#")){ // si la phrase contient un autre mot à matcher
                        /*System.out.println("mc "+sentence.substring(indexStart, m.start()-1));
                        System.out.println("mv "+sentence.substring(m.start()+1, m.end()-1));*/
                        Morceaux mc = new Morceaux(sentence, indexStart, m.start()-1); // création des morceaux
                        MorceauVariable mv = new MorceauVariable(sentence, m.start()+1, m.end()-1);
//                        System.out.println(mc.reponseAttendue());
//                        System.out.println(mv.reponseAttendue());
                        indexStart = m.end()+1; // mise à jour de l'indice de départ des autres morceaux
                        sentChunk.add(mc); // ajout des morceaux dans la liste
                        sentChunk.add(mv);
                    } else { // s'il n'y en a pas d'autres
//                        System.out.println(sentence.substring(indexStart, m.start()));
//                        System.out.println(sentence.substring(m.start()+1, m.end()-1));
//                        System.out.println(sentence.substring(m.end()+1, lenghtSent));
                        Morceaux mc1 = new Morceaux(sentence, indexStart, m.start()-1); // création des morceaux
                        MorceauVariable mv = new MorceauVariable(sentence, m.start()+1, m.end()-1);
//                        try {
//                            mv = new MorceauVariable(sentence, m.start()+1, m.end()-1);
//                        } catch (StringIndexOutOfBoundsException exception) {
//                            mv = new MorceauVariable(sentence, m.start(), m.end());
//                        }
                        Morceaux mc2 = new Morceaux(sentence, m.end()+1, lenghtSent);

                        sentChunk.add(mc1); // ajout dans la liste
                        sentChunk.add(mv);
                        sentChunk.add(mc2);
                    }
                }
            }
        }
        return sentChunk; // on retourne la liste contenant tous les morceaux représentant la phrase
    }

    /**
     * vérification que le nombre de mots entre # dans la phrase du contributeur correspond bien au niveau de l'exercice
     * @param newSent phrase que le contributeur veut ajouter à l'exercice au format pourProf
     * @param lvl niveau de l'exercice auquel la phrase sera ajoutée
     * @return true ou false suivant si le nombre de match correspond bien au niveau de l'exercice
     */
    @Override
    public boolean verifNbMw(String newSent, int lvl) {
        Matcher verify = this.regex.matcher(newSent);
        int nbMatch = 0;
        while (verify.find()){
            nbMatch++;
        }
        return nbMatch == lvl;
    }

    @Override
    public boolean verifWords(String newWords) {
        return this.regex.matcher(newWords).find();
    }

}
