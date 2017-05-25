package de.gtrefs.dzone.scrabble;

import org.junit.Test;

import java.util.Optional;

import static de.gtrefs.dzone.scrabble.ScrabbleSetDescription.errorMessageForOverdrawnScrabbleSet;
import static de.gtrefs.dzone.scrabble.ScrabbleSetDescription.messageForEmptyScrabbleSet;
import static de.gtrefs.dzone.scrabble.ScrabbleSetDescription.orderTilesByAmount;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ScrabbleSetDescriptionShould {

    @Test
    public void be_empty_message_for_empty_scrabble_set() throws Exception {
        final ScrabbleSet empty = ScrabbleSet.empty();

        assertThat(messageForEmptyScrabbleSet("Scrabble set is empty.").apply(empty).get(), is("Scrabble set is empty."));
        assertThat(messageForEmptyScrabbleSet("It is empty.").apply(empty).get(), is("It is empty."));
    }


    @Test
    public void be_overdrawn_message_if_set_is_overdrawn() throws Exception {
        final ScrabbleSet overdrawn = ScrabbleSet.decode("a").remove(ScrabbleSet.decode("aa"));
        final String errorMessage = "Invalid input. More %s's have been taken from the bag than possible.";

        final Optional<String> message = errorMessageForOverdrawnScrabbleSet(errorMessage).apply(overdrawn);

        assertThat(message.get(), is("Invalid input. More a's have been taken from the bag than possible."));
    }

    @Test
    public void only_be_empty_message_if_scrabble_set_is_empty() throws Exception {
        final  ScrabbleSet set = ScrabbleSet.englishSet();

        assertThat(messageForEmptyScrabbleSet("Empty").apply(set), is(Optional.empty()));
    }

    @Test
    public void replace_template_with_name_of_overdrawn_tile() throws Exception {
        final ScrabbleSet overdrawn = ScrabbleSet.decode("b").remove(ScrabbleSet.decode("bb"));
        final String errorMessage = "Invalid input. More %s's have been taken from the bag than possible.";

        final Optional<String> message = errorMessageForOverdrawnScrabbleSet(errorMessage).apply(overdrawn);

        assertThat(message.get(), is("Invalid input. More b's have been taken from the bag than possible."));
    }

    @Test
    public void replace_template_only_if_set_is_overdrawn() throws Exception {
        Optional<String> message = errorMessageForOverdrawnScrabbleSet("%s").apply(ScrabbleSet.englishSet());

        assertThat(message, is(Optional.empty()));
    }

    @Test
    public void order_tiles_with_same_amount_equally() {
        final ScrabbleSet set = new ScrabbleSet(Tile.of('a'), Tile.of('a'), Tile.of('b'), Tile.of('b'));

        Optional<String> message = orderTilesByAmount().apply(set);

        assertThat(message.get(), is("2: a, b"));
    }

    @Test
    public void order_first_3_tiles_a_and_then_2_tiles_b() throws Exception {
        final ScrabbleSet set = ScrabbleSet.decode("ababa");

        Optional<String> message = orderTilesByAmount().apply(set);

        assertThat(message.get(), is("3: a\n2: b"));
    }

    @Test
    public void print_empty_message_or_else_order_by_tiles_message() throws Exception {
        final ScrabbleSet set = ScrabbleSet.decode("aa");

        final ScrabbleSetDescription describe = messageForEmptyScrabbleSet("Scrabble set is empty.")
                .orElse(orderTilesByAmount());

        assertThat(describe.apply(set).get(), is("2: a"));
    }

}
