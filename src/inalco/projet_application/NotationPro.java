package inalco.projet_application;

import java.util.List;

public class NotationPro implements StrategieNotation {

    /**
     * constructeur de la classe
     * indique quel type de notation est appliquée à un exercice
     */
    public NotationPro(){
        System.out.println("La notation pro sera appliquée\n- bonne réponse = +0,25 \n- non répondue = -0,25 point\n- mauvaise réponse -1");
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
                case CORRECT -> note = (float) (note + 0.25);
                case INCORRECT -> note--;
                case NON_RÉPONDU -> note = (float) (note - 0.25);
            }
        }
        return note;
    }
}
