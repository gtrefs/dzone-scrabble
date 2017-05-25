package de.gtrefs.dzone.scrabble;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.io.PrintStream;

import static de.gtrefs.dzone.scrabble.ScrabbleSetDescription.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ScrabbleSetPrinterShould {

    @Mock
    PrintStream outputStream;

    @Test
    public void print_scrabble_set_description() throws IOException {
        final ScrabbleSet scrabbleSet = new ScrabbleSet(Tile.of('a'), Tile.of('a'), Tile.of('a'), Tile.of('b'));
        final ScrabbleSetDescription description = messageForEmptyScrabbleSet("")
                .orElse(errorMessageForOverdrawnScrabbleSet(""))
                .orElse(orderTilesByAmount());
        final ScrabbleSetPrinter writer = new ScrabbleSetPrinter(description, outputStream);

        writer.printSet(scrabbleSet);

        verify(outputStream).println("3: a\n1: b");
    }

    @Test
    public void group_tiles_by_amount() throws IOException {
        final ScrabbleSet scrabbleSet = new ScrabbleSet(Tile.of('a'), Tile.of('a'), Tile.of('b'), Tile.of('b'));
        final ScrabbleSetPrinter writer = new ScrabbleSetPrinter(orderTilesByAmount(), outputStream);

        writer.printSet(scrabbleSet);

        verify(outputStream).println("2: a, b");
    }

    @Test
    public void print_empty_statement_if_scrabble_set_is_empty(){
        final ScrabbleSet scrabbleSet = new ScrabbleSet();
        final ScrabbleSetDescription description = messageForEmptyScrabbleSet("Scrabble set is empty.").orElse(orderTilesByAmount());
        final ScrabbleSetPrinter writer = new ScrabbleSetPrinter(description, outputStream);

        writer.printSet(scrabbleSet);

        verify(outputStream).println("Scrabble set is empty.");
    }
}
