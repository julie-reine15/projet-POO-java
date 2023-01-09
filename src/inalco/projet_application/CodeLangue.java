package inalco.projet_application;

public class CodeLangue {
    /**
     * code des 3 langues disponible dans l'application (code iso 2)
     */
    enum CODE_LANGUE {
        FR,
        NL,
        RU
    }

    /**
     * constructeur de la classe
     */
    private CodeLangue(){}

    /**
     * permet de transformer le code langue d'une string à une instance de l'énumération CODE_LANGUE
     * @param langue le code langue au format string
     * @return le code langue en instane de CODE_LANGUE
     * @throws NoSuchOptionException
     */
    public static CODE_LANGUE valueOf(String langue) throws NoSuchOptionException{
        CODE_LANGUE cl = null;

        if (langue.equals(CODE_LANGUE.FR.toString())){
            cl = CODE_LANGUE.FR;
        } else if (langue.equals(CODE_LANGUE.NL.toString())){
            cl = CODE_LANGUE.NL;
        } else if (langue.equals(CODE_LANGUE.RU.toString())){
            cl = CODE_LANGUE.RU;
        } else {
            throw new NoSuchOptionException("choix indisponible");
        }
        return cl;
    }
}
