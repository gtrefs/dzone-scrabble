package de.gtrefs.dzone.scrabble;

public class ScrabbleService {
    private final ScrabbleSetPrinter printer;

    public ScrabbleService(ScrabbleSetPrinter printer) {
        this.printer = printer;
    }

    public ScrabbleSet removeTiles(String encodedScrabbleSet, ScrabbleSet from) {
        return from.remove(ScrabbleSet.decode(encodedScrabbleSet));
    }

    public void printScrabbleSet(ScrabbleSet set) {
        printer.printSet(set);
    }
}
