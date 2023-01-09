package inalco.projet_application;

import java.util.List;

/**
 *
 */
public class Notation {
    private StrategieNotation sNotation; // type de notation
    protected Notation notation; // instance de la classe notation

    /**
     * détermine comment sera noté un exercice (dépend du niveau de l'utilisateur)
     * @param snot un type de notation
     */
    public void setSnot(StrategieNotation snot) {
        this.sNotation = snot;
    }

    /**
     * applique la notation à une liste d'éléments corrigés suivant le type de notation choisie
     * @param elementCorrigeList liste contenant des éléments corrigés dont le résultat a été évalué
     * @return la note obtenue en fonction des résultats
     */
    public float applyNotation(List<ElementCorrige> elementCorrigeList){
        return sNotation.note(elementCorrigeList);
    }
}
