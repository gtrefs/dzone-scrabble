package de.gtrefs.dzone.scrabble;

import java.io.PrintStream;

public class ScrabbleSetPrinter {

    private final ScrabbleSetDescription description;
    private final PrintStream out;

    public ScrabbleSetPrinter(ScrabbleSetDescription description, PrintStream out) {
        this.description = description;
        this.out = out;
    }

    public void printSet(ScrabbleSet scrabbleSet) {
        out.println(description.apply(scrabbleSet).orElse("Cannot describe scrabble set."));
    }
}
