package de.gtrefs.dzone.scrabble;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.PrintStream;

import static de.gtrefs.dzone.scrabble.ScrabbleSetDescription.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RemoveTilesFromScrabbleSetFeature {

    @Mock
    PrintStream printStream;

    ScrabbleSetDescription description;

    ScrabbleSetPrinter scrabbleSetPrinter;

    ScrabbleService service;

    @Before
    public void setUp(){
        description = errorMessageForOverdrawnScrabbleSet("Invalid input. More %s's have been taken from the bag than possible.")
                .orElse(messageForEmptyScrabbleSet("Scrabble set is empty."))
                .orElse(orderTilesByAmount());
        scrabbleSetPrinter = new ScrabbleSetPrinter(description, printStream);
        service = new ScrabbleService(scrabbleSetPrinter);
    }

    @Test
    public void removing_PQAREIOURSTHGWIOAE__from_the_scrabble_set_should_be_valid(){
        final ScrabbleSet set = ScrabbleSet.englishSet();

        service.print(service.removeTiles("PQAREIOURSTHGWIOAE_", set));

        String expected = "10: E\n7: A, I\n6: N, O\n5: T\n4: D, L, R\n3: S, U\n2: B, C, F, G, M, V, Y\n1: H, J, K, P, W, X, Z, _\n0: Q";
        verify(printStream).println(expected);
    }

    @Test
    public void removing_LQTOONOEFFJZT_from_the_scrabble_set_should_be_valid(){
        final ScrabbleSet set = ScrabbleSet.englishSet();

        service.print(service.removeTiles("LQTOONOEFFJZT", set));

        String expected = "11: E\n9: A, I\n6: R\n5: N, O\n4: D, S, T, U\n3: G, L\n2: B, C, H, M, P, V, W, Y, _\n1: K, X\n0: F, J, Q, Z";
        verify(printStream).println(expected);
    }

    @Test
    public void removing_more_tiles_from_a_bag_than_possible_should_be_an_error(){
        final ScrabbleSet set = ScrabbleSet.englishSet();

        service.print(service.removeTiles("AXHDRUIOR_XHJZUQEE", set));

        verify(printStream).println("Invalid input. More X's have been taken from the bag than possible.");
    }
}
