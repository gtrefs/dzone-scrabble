package de.gtrefs.dzone.scrabble;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.io.PrintStream;

import static de.gtrefs.dzone.scrabble.ScrabbleSetPrinter.errorForOverdrawnTiles;
import static de.gtrefs.dzone.scrabble.ScrabbleSetPrinter.orderedByAmountOfTiles;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ScrabbleSetPrinterShould {

    @Mock
    PrintStream outputStream;

    @Test
    public void order_ScrabbleSet_by_amount_of_tiles() throws IOException {
        final ScrabbleSet scrabbleSet = new ScrabbleSet(Tile.of('a'), Tile.of('a'), Tile.of('a'), Tile.of('b'));
        final ScrabbleSetPrinter writer = orderedByAmountOfTiles().apply(outputStream);

        writer.printSet(scrabbleSet);

        InOrder inOrder = inOrder(outputStream);
        inOrder.verify(outputStream).println("3: a");
        inOrder.verify(outputStream).println("1: b");
    }

    @Test
    public void group_tiles_by_amount() throws IOException {
        final ScrabbleSet scrabbleSet = new ScrabbleSet(Tile.of('a'), Tile.of('a'), Tile.of('b'), Tile.of('b'));
        final ScrabbleSetPrinter writer = orderedByAmountOfTiles().apply(outputStream);

        writer.printSet(scrabbleSet);

        InOrder inOrder = inOrder(outputStream);
        inOrder.verify(outputStream).println("2: a, b");
    }

    @Test
    public void print_empty_statement_if_scrabble_set_is_empty(){
        final ScrabbleSet scrabbleSet = new ScrabbleSet();
        ScrabbleSetPrinter.messageForEmptyScrabbleSet("Scrabble set is empty.", orderedByAmountOfTiles())
                .apply(outputStream)
                .printSet(scrabbleSet);

        verify(outputStream).println("Scrabble set is empty.");
    }

    @Test
    public void print_error_message_if_scrabble_set_has_negative_amount(){
        final ScrabbleSet scrabbleSet = new ScrabbleSet(Tile.of('a'));
        final ScrabbleSetPrinter printer = errorForOverdrawnTiles("Invalid input. More %s's have been " +
                "taken from the bag than possible.", orderedByAmountOfTiles()).apply(outputStream);

        printer.printSet(scrabbleSet.remove(ScrabbleSet.decode("aa")));

        verify(outputStream).println("Invalid input. More a's have been taken from the bag than possible.");
    }

    @Test
    public void error_for_negative_amount_combinator_should_call_other_function_if_valid(){
        final ScrabbleSetPrinter printer = errorForOverdrawnTiles("Invalid input. More %s's have been " +
                "taken from the bag than possible.", orderedByAmountOfTiles()).apply(outputStream);

        printer.printSet(ScrabbleSet.decode("aaa"));

        verify(outputStream).println("3: a");
    }
}
