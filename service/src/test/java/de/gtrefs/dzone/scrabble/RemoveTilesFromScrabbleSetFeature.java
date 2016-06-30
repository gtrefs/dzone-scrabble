package de.gtrefs.dzone.scrabble;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.PrintStream;

import static de.gtrefs.dzone.scrabble.ScrabbleSetPrinter.messageForEmptyScrabbleSet;
import static de.gtrefs.dzone.scrabble.ScrabbleSetPrinter.errorForOverdrawnTiles;
import static de.gtrefs.dzone.scrabble.ScrabbleSetPrinter.orderedByAmountOfTiles;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RemoveTilesFromScrabbleSetFeature {

    @Mock
    PrintStream printStream;

    ScrabbleSetPrinter scrabbleSetPrinter;

    ScrabbleService service;

    @Before
    public void setUp(){
        scrabbleSetPrinter = errorForOverdrawnTiles("Invalid input. More %s's have been taken from the bag than possible.",
                messageForEmptyScrabbleSet("Scrabble set is empty.",
                orderedByAmountOfTiles())).apply(printStream);
        service = new ScrabbleService(scrabbleSetPrinter);
    }

    @Test
    public void removing_PQAREIOURSTHGWIOAE__from_the_scrabble_set_should_be_valid(){
        final ScrabbleSet set = ScrabbleSet.englishSet();

        service.print(service.removeTiles("PQAREIOURSTHGWIOAE_", set));

        InOrder inOrder = inOrder(printStream);
        inOrder.verify(printStream).println("10: E");
        inOrder.verify(printStream).println("7: A, I");
        inOrder.verify(printStream).println("6: N, O");
        inOrder.verify(printStream).println("5: T");
        inOrder.verify(printStream).println("4: D, L, R");
        inOrder.verify(printStream).println("3: S, U");
        inOrder.verify(printStream).println("2: B, C, F, G, M, V, Y");
        inOrder.verify(printStream).println("1: H, J, K, P, W, X, Z, _");
        inOrder.verify(printStream).println("0: Q");
    }

    @Test
    public void removing_LQTOONOEFFJZT_from_the_scrabble_set_should_be_valid(){
        final ScrabbleSet set = ScrabbleSet.englishSet();

        service.print(service.removeTiles("LQTOONOEFFJZT", set));

        InOrder inOrder = inOrder(printStream);
        inOrder.verify(printStream).println("11: E");
        inOrder.verify(printStream).println("9: A, I");
        inOrder.verify(printStream).println("6: R");
        inOrder.verify(printStream).println("5: N, O");
        inOrder.verify(printStream).println("4: D, S, T, U");
        inOrder.verify(printStream).println("3: G, L");
        inOrder.verify(printStream).println("2: B, C, H, M, P, V, W, Y, _");
        inOrder.verify(printStream).println("1: K, X");
        inOrder.verify(printStream).println("0: F, J, Q, Z");
    }

    @Test
    public void removing_more_tiles_from_a_bag_than_possible_should_be_an_error(){
        final ScrabbleSet set = ScrabbleSet.englishSet();

        service.print(service.removeTiles("AXHDRUIOR_XHJZUQEE", set));

        verify(printStream).println("Invalid input. More X's have been taken from the bag than possible.");
    }
}
