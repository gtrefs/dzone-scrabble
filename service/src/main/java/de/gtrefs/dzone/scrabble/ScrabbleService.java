package de.gtrefs.dzone.scrabble;

public class ScrabbleService {
    private final ScrabbleSetPrinter printer;

    public ScrabbleService(ScrabbleSetPrinter printer) {
        this.printer = printer;
    }

    public ScrabbleSet removeTiles(String encodedScrabbleSet, ScrabbleSet set) {
        return set.remove(ScrabbleSet.decode(encodedScrabbleSet));
    }

    public void print(ScrabbleSet set) {
        printer.printSet(set);
    }
}