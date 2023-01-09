package inalco.projet_application;

import java.util.List;

public class NotationDebutant implements StrategieNotation {

    /**
     * constructeur de la classe
     * indique quel type de notation est appliquée à un exercice
     */
    public NotationDebutant(){
        System.out.println("La notation débutante sera appliquée\n- bonne réponse = +1 \n- non répondue = 0 \n- mauvaise réponse -0,5");
    }

    /**
     * @param elementsCorriges liste contenant des éléments corrigés dont le résultat a été évalué
     * @return la note obtenue en fonction des résultats
     */
    @Override
    public float note(List<ElementCorrige> elementsCorriges) {
        float note = 0;
        for (ElementCorrige ec : elementsCorriges){
            switch (ec.getResultat()){
                case CORRECT -> note ++;
                case INCORRECT -> note = (float) (note-0.5);
                case NON_RÉPONDU -> note = note;
            }
        }
        return note;
    }

    //    public static void main(String[] args) throws IOException {
//        Correction correction = new Correction();
//        NotationStandard ns = new NotationStandard();
//
//        ns.note(correction.elementsCorriges);
//    }
}
