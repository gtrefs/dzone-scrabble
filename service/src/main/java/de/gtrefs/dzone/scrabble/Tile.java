package de.gtrefs.dzone.scrabble;

public class Tile {
    private final char letter;

    public static Tile of(char character) {
        return new Tile(character);
    }

    public Tile(char letter) {
        this.letter = letter;
    }

    public char getLetter(){
        return letter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return letter == tile.letter;
    }

    @Override
    public int hashCode() {
        return (int) letter;
    }

    @Override
    public String toString() {
        return  String.valueOf(letter);
    }

}
