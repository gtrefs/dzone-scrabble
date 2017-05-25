package de.gtrefs.dzone.scrabble;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public interface ScrabbleSetDescription extends Function<ScrabbleSet, Optional<String>> {

    static ScrabbleSetDescription orderTilesByAmount() {
        return set -> {
            String tiles = set.countTiles()
                              .entrySet()
                              .stream()
                              .collect(groupingBy(Map.Entry::getValue, mapping(e -> e.getKey().toString(), joining(", "))))
                              .entrySet()
                              .stream()
                              .sorted((e1, e2) -> Long.compare(e2.getKey(), e1.getKey()))
                              .map(e -> e.getKey() + ": " + e.getValue())
                              .collect(Collectors.joining("\n"));
            return Optional.of(tiles);
        };
    }

    static ScrabbleSetDescription errorMessageForOverdrawnScrabbleSet(String errorMessage) {
        return set -> {
            if(!set.hasTilesWhichHaveBeenOverDrawn()) return Optional.empty();
            final String value = set.getTilesWhichHaveBeenOverdrawn().stream()
                                    .map(Tile::toString)
                                    .collect(joining(", "));
            return Optional.of(String.format(errorMessage, value));
        };
    }

    static ScrabbleSetDescription messageForEmptyScrabbleSet(String message) {
        return set -> set.isEmpty()?Optional.of(message):Optional.empty();
    }

    default ScrabbleSetDescription orElse(ScrabbleSetDescription other){
      return set -> {
          final Optional<String> value = apply(set);
          return value.isPresent()?value:other.apply(set);
      };
    };
}
