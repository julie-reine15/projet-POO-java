package inalco.projet_application;

import java.util.List;

public interface Parsing {
    List<Morceaux> parse(String sentence);

    boolean verifNbMw(String newSent, int lvl);
    boolean verifWords(String newSent);
}
