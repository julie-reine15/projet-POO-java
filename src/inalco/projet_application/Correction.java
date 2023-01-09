package inalco.projet_application;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class Correction {

    protected LinkedList<ElementCorrige> elementsCorriges = new LinkedList<>(); // liste des éléments corrigés

    public Correction(){
    }

    /**
     * fait la correction de l'exercice de traduction
     * @param ref le mot à traduire dans la langue cible
     * @param transl le mot à traduire dans la langue cible donné par l'utilisateur
     */
    public void correctionTranslation(Phrase ref, Phrase transl){

        // récupération des morceaux à comparer sous forme de string
        String reference = ref.morceauxVariables.get(0).reponseAttendue().trim();
        String translation = transl.morceauxVariables.get(0).reponseAttendue().trim();

//        System.out.println(reference);
//        System.out.println(translation);

        if (reference.equals(translation)){ // si les morceaux sont égaux = correct
            System.out.println(translation+" -> "+Reponse.REPONSE.valueOf("CORRECT"));
            ElementCorrige ec = new ElementCorrige(ref.morceauxVariables.get(0), transl.morceauxVariables.get(0));
            ec.setResultat(Reponse.REPONSE.CORRECT);
            this.elementsCorriges.add(ec);
        } else if (translation.equals("")) { // si l'utilisateur n'a pas répondu = non répondu
            System.out.println(translation+" -> "+Reponse.REPONSE.valueOf("NON_RÉPONDU"));
            System.out.println("La bonne réponse était -> "+reference);
            ElementCorrige ec = new ElementCorrige(ref.morceauxVariables.get(0), transl.morceauxVariables.get(0));
            ec.setResultat(Reponse.REPONSE.NON_RÉPONDU);
            this.elementsCorriges.add(ec);
        } else {
            System.out.println(translation+" -> "+Reponse.REPONSE.valueOf("INCORRECT")); // dans tous les autres cas = incorrect
            System.out.println("La bonne réponse était -> "+reference);
            ElementCorrige ec = new ElementCorrige(ref.morceauxVariables.get(0), transl.morceauxVariables.get(0));
            ec.setResultat(Reponse.REPONSE.INCORRECT);
            this.elementsCorriges.add(ec);
        }

    }

    /**
     * fait la correction de l'exercice des phrases à trous
     * @param reference la phrase de référence venant du fichier (format réponse attendue)
     * @param phraseUtilisateur la phrase correspondant à la réponse de l'utilisateur
     */
    public void correctionMw(Phrase reference, Phrase phraseUtilisateur){
//        System.out.println(reference.repAttendue(phraseEleve.phrase));

        // création d'itérateurs depuis les phrases pour itérer sur chaque morceau variable
        Iterator<MorceauVariable> iteRef = reference.getIteratorMv();
        Iterator<MorceauVariable> itePhEleve = phraseUtilisateur.getIteratorMv();
        int i = 0;

        while ((iteRef.hasNext() && itePhEleve.hasNext() && i < reference.morceauxVariables.size()) || reference.morceauxVariables.size()==0){
//            System.out.println(i);
//            System.out.println("correction !!!! ");

            // récupération des morceaux sous forme de string pour les comparer
            String morceauRef = reference.morceauxVariables.get(i).reponseAttendue().trim();
            String morceauUtilisateur = phraseUtilisateur.morceauxVariables.get(i).reponseAttendue().trim();

//            System.out.println(morceauRef);
//            System.out.println(morceauEleve);

            if (morceauRef.equals(morceauUtilisateur)){ // si les 2 morceaux sont identiques = correct
                System.out.println(morceauUtilisateur +" -> "+Reponse.REPONSE.valueOf("CORRECT"));
                ElementCorrige ec = new ElementCorrige(reference.morceauxVariables.get(i), phraseUtilisateur.morceauxVariables.get(i));
                ec.setResultat(Reponse.REPONSE.CORRECT);
                this.elementsCorriges.add(ec);

            } else if (morceauUtilisateur.equals("X")){ // si l'utilisateur a mis un X à la place du mot (consigne) = non répondu
                System.out.println(morceauUtilisateur +" -> "+Reponse.REPONSE.valueOf("NON_RÉPONDU"));
                System.out.println("La bonne réponse était -> "+reference.repAttendue(reference.phrase));
                ElementCorrige ec = new ElementCorrige(reference.morceauxVariables.get(i), phraseUtilisateur.morceauxVariables.get(i));
                ec.setResultat(Reponse.REPONSE.NON_RÉPONDU);
                this.elementsCorriges.add(ec);

            } else { // dans tous les autres cas = incorrect
                System.out.println(morceauUtilisateur +" -> "+Reponse.REPONSE.valueOf("INCORRECT"));
                System.out.println("La bonne réponse était -> "+reference.repAttendue(reference.phrase));
                ElementCorrige ec = new ElementCorrige(reference.morceauxVariables.get(i), phraseUtilisateur.morceauxVariables.get(i));
                ec.setResultat(Reponse.REPONSE.INCORRECT);
                this.elementsCorriges.add(ec);
            }
            i++;
        }
    }

    /**
     * récupère la liste des éléments corrigés après correction d'un exercice
     * @return une liste d'éléments corrigés
     */
    public List<ElementCorrige> getElementCorrige(){
        return this.elementsCorriges;
    }
}
