package de.gtrefs.dzone.scrabble;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ScrabbleSetShould {

    @Test
    public void be_empty_without_tiles(){
        final ScrabbleSet set = new ScrabbleSet();

        assertThat(set.isEmpty(),is(true));
    }

    @Test
    public void contain_given_tile(){
        final Tile tile = new Tile('a');

        final ScrabbleSet set = new ScrabbleSet(tile);

        assertThat(set.countTiles().containsKey(tile),is(true));
    }

    @Test
    public void not_contain_tile_if_not_in_set(){
        final Tile tile = new Tile('b');

        final ScrabbleSet set = new ScrabbleSet();

        assertThat(set.countTiles().containsKey(tile), is(false));
    }

    @Test
    public void count_tiles_by_letter(){
        final ScrabbleSet set = new ScrabbleSet(Arrays.asList(Tile.of('a'), Tile.of('b'), Tile.of('b'), Tile.of('a')));

        final Map<Tile, Long> expectedTileByAmount = new HashMap<>();
        expectedTileByAmount.put(Tile.of('a'), 2L);
        expectedTileByAmount.put(Tile.of('b'), 2L);

        final Map<Tile, Long> tileByAmount = set.countTiles();

        assertThat(tileByAmount, is(equalTo(expectedTileByAmount)));
    }

    @Test
    public void be_made_from_array_of_tiles(){
        final ScrabbleSet set = new ScrabbleSet(Tile.of('a'), Tile.of('b'), Tile.of('c'));

        assertThat(set.countTiles().containsKey(Tile.of('a')), is(true));
    }

    @Test
    public void be_made_from_String(){
        final String decodedScrabbleSet = "aaaabbbbccc";

        final Map<Tile, Long> count = ScrabbleSet.decode(decodedScrabbleSet).countTiles();

        assertThat(count.get(Tile.of('a')), is(4L));
        assertThat(count.get(Tile.of('b')), is(4L));
        assertThat(count.get(Tile.of('c')), is(3L));
    }


    @Test
    public void remove_tile_from_given_scrabble_set(){
        final ScrabbleSet set = ScrabbleSet.englishSet();

        final Long count = set.remove(ScrabbleSet.decode("A")).countTiles().get(Tile.of('A'));

        assertThat(count, is(8L));
    }

    @Test
    public void remove_nothing_from_empty(){
        final ScrabbleSet set = new ScrabbleSet();

        final ScrabbleSet newSet = set.remove(ScrabbleSet.decode("B"));

        assertThat(newSet.isEmpty(), is(true));
    }

    @Test
    public void have_an_empty_scrabble_set_factory_method() throws Exception {
        final ScrabbleSet set = ScrabbleSet.empty();

        assertThat(set.isEmpty(), is(true));
    }
    
    @Test
    public void remove_multiple_tiles_from_given_scrabble_set(){
        final ScrabbleSet set = ScrabbleSet.englishSet();

        final Long count = set.remove(ScrabbleSet.decode("AAA")).countTiles().get(Tile.of('A'));

        assertThat(count, is(6L));
    }

    @Test
    public void indicate_which_tiles_have_been_overdrawn(){
        final ScrabbleSet set = ScrabbleSet.decode("a").remove(ScrabbleSet.decode("aa"));

        final List<Tile> tilesWithNegativeAmount = set.getTilesWhichHaveBeenOverdrawn();

        assertThat(tilesWithNegativeAmount, hasItem(Tile.of('a')));
    }

    @Test
    public void list_of_tiles_with_negative_amount_should_not_contain_tiles_with_positive_amount(){
        final ScrabbleSet set = ScrabbleSet.decode("ab").remove(ScrabbleSet.decode("aa"));

        final List<Tile> tilesWithNegativeAmount = set.getTilesWhichHaveBeenOverdrawn();

        assertThat(tilesWithNegativeAmount, not(hasItem(Tile.of('b'))));
    }

    @Test
    public void indicate_whether_too_much_tiles_have_been_removed_from_set(){
        final ScrabbleSet set = ScrabbleSet.decode("a").remove(ScrabbleSet.decode("aa"));

        assertThat(set.hasTilesWhichHaveBeenOverDrawn(), is(true));
    }

}
