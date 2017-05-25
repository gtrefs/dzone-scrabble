package de.gtrefs.dzone.scrabble;

import org.junit.Test;

import java.util.function.Function;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ScrabbleSetMessageShould {

    @Test
    public void be_empty_message_for_empty_scrabble_set() throws Exception {
        final ScrabbleSet empty = ScrabbleSet.empty();

        final String message = messageForEmptyScrabbleSet("Scrabble set is empty.").apply(empty);

        assertThat(message, is("Scrabble set is empty."));
    }

    @Test
    public void be_given_empty_message_for_emtpy_scrabble_set() throws Exception {
        final String message = messageForEmptyScrabbleSet("It is empty.").apply(ScrabbleSet.empty());

        assertThat(message, is("It is empty."));
    }

    private ScrabbleSetContentDescription messageForEmptyScrabbleSet(String message) {
        return set -> message;
    }

    private interface ScrabbleSetContentDescription extends Function<ScrabbleSet, String>{

    }
}
