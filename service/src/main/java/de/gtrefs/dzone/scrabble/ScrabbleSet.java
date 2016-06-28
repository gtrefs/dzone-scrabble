package de.gtrefs.dzone.scrabble;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ScrabbleSet {

    private final static String englishScrabbleSet = "AAAAAAAAABBCCDDDDEEEEEEEEEEEEFFGGGHHIIIIIIIIIJKLLLL" +
            "MMNNNNNNOOOOOOOOPPQRRRRRRSSSSTTTTTTUUUUVVWWXYYZ__";

    private final Map<Tile, Long> amountByTiles;

    public static ScrabbleSet decode(String decodedScrabbleSet) {
        final List<Tile> tiles = decodedScrabbleSet.chars()
                .mapToObj(i -> (char)i)
                .map(Tile::of)
                .collect(Collectors.toList());
        return new ScrabbleSet(tiles);
    }

    public static ScrabbleSet englishSet() {
        return ScrabbleSet.decode(englishScrabbleSet);
    }

    public ScrabbleSet(Tile tile) {
        this(Collections.singletonList(tile));
    }

    public ScrabbleSet() {
        this(Collections.<Tile>emptyList());
    }

    public ScrabbleSet(Tile... tiles) {
        this(Arrays.asList(tiles));
    }

    public ScrabbleSet(List<Tile> tiles) {
        this.amountByTiles = tiles.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    private ScrabbleSet(Map<Tile, Long> amountByTile){
        this.amountByTiles = amountByTile;
    }

    public boolean isEmpty() {
        return amountByTiles.isEmpty();
    }

    public Map<Tile,Long> countTiles() {
        return Collections.unmodifiableMap(amountByTiles);
    }

    public ScrabbleSet remove(ScrabbleSet other) {
        final Map<Tile, Long> newSet = new HashMap<>(amountByTiles);
        other.countTiles().forEach((otherTile, otherAmount) ->
            newSet.computeIfPresent(otherTile, (tile, amount) -> amount - otherAmount));
        return new ScrabbleSet(newSet);
    }

    public List<Tile> getTilesWithNegativeAmount() {
        return amountByTiles.entrySet()
                .stream()
                .filter(e -> e.getValue() < 0L)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}