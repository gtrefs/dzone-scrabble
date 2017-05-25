package de.gtrefs.dzone.scrabble;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import static java.util.stream.Collectors.*;

@FunctionalInterface
public interface ScrabbleSetPrinter extends Consumer<ScrabbleSet> {

    Comparator<Map.Entry<Long, String>> byAmount = (e1, e2) -> Long.compare(e2.getKey(), e1.getKey());

    default void accept(ScrabbleSet set){
        printSet(set);
    }

    void printSet(ScrabbleSet set);

    static Function<PrintStream, ScrabbleSetPrinter> orderedByAmountOfTiles() {
        return out -> set -> set.countTiles()
               .entrySet()
               .stream()
               .collect(groupingBy(Map.Entry::getValue, mapping(e -> e.getKey().toString(), joining(", "))))
               .entrySet()
               .stream()
               .sorted(byAmount)
               .forEach(entry -> out.println(amountAndTile(entry)));
    }

    static String amountAndTile(Map.Entry<Long, String> entry) {
        return entry.getKey()+": "+entry.getValue();
    }

    static Function<PrintStream, ScrabbleSetPrinter> messageForEmptyScrabbleSet(String message,
                                                                                Function<PrintStream, ScrabbleSetPrinter> next) {
        return out -> set -> {
            boolean condition = set.isEmpty();
            orElse(next, out, set, condition, message);
        };
    }

    static Function<PrintStream, ScrabbleSetPrinter> errorForOverdrawnTiles(String message,
                                                                            Function<PrintStream, ScrabbleSetPrinter> next) {
        return out -> set -> {
            boolean condition = set.hasTilesWhichHaveBeenOverDrawn();
            String formattedMessage = String.format(message, joinToString(set.getTilesWhichHaveBeenOverdrawn()));
            orElse(next, out, set, condition, formattedMessage);
        };
    }

    static void orElse(Function<PrintStream, ScrabbleSetPrinter> next, PrintStream out, ScrabbleSet set, Boolean condition, String formattedMessage) {
        if(condition){
            out.println(formattedMessage);
        }else{
            next.apply(out).printSet(set);
        }
    }

    static String joinToString(List<Tile> tilesWithNegativeAmount) {
        return tilesWithNegativeAmount.stream().collect(mapping(Tile::toString, joining(", ")));
    }
}
