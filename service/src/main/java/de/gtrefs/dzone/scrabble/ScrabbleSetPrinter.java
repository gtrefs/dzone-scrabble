package de.gtrefs.dzone.scrabble;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    static Function<PrintStream, ScrabbleSetPrinter> emptyMessage(String message,
                                                                  Function<PrintStream, ScrabbleSetPrinter> next) {
        return out -> set -> {
          if(set.isEmpty())
              out.println(message);
          else
              next.apply(out).accept(set);
        };
    }

    static Function<PrintStream, ScrabbleSetPrinter> errorForNegativeAmount(String message,
                                                                            Function<PrintStream, ScrabbleSetPrinter> next) {
        return out -> set -> {
            final List<Tile> tilesWithNegativeAmount = set.getTilesWithNegativeAmount();
            if(!tilesWithNegativeAmount.isEmpty()){
                out.println(String.format(message, joinToString(tilesWithNegativeAmount)));
            }else{
                next.apply(out).printSet(set);
            }
        };
    }

    static String joinToString(List<Tile> tilesWithNegativeAmount) {
        return tilesWithNegativeAmount.stream().collect(mapping(Tile::toString, Collectors.joining(", ")));
    }
}
