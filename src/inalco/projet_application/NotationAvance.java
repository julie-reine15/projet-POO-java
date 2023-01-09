package inalco.projet_application;

import java.util.List;

public class NotationAvance implements StrategieNotation{

    /**
     * constructeur de la classe
     * indique quel type de notation est appliquée à un exercice
     */
    public NotationAvance(){
        System.out.println("La notation avancée sera appliquée\n- bonne réponse = +0,5\n- non répondue = 0\n- mauvaise réponse -0,5");
    }

    /**
     * @param elementCorrigeList liste contenant des éléments corrigés dont le résultat a été évalué
     * @return la note obtenue en fonction des résultats
     */
    @Override
    public float note(List<ElementCorrige> elementCorrigeList) {
        float note = 0;
        for (ElementCorrige ec : elementCorrigeList){
            switch (ec.getResultat()){
                case CORRECT -> note = (float) (note + 0.5);
                case INCORRECT, NON_RÉPONDU -> note = (float) (note-1.0);
            }
        }
        return note;
    }
}
