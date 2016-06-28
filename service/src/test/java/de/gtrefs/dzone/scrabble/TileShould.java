package de.gtrefs.dzone.scrabble;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class TileShould {

    @Test
    public void be_identified_by_its_character(){
        final Tile tilea = new Tile('a');
        final Tile tilea2 = new Tile('a');

        assertThat(tilea, is(tilea2));
    }

    @Test
    public void be_different_from_other_tile_with_different_letter(){
        final Tile tilea = new Tile('a');
        final Tile tileb = new Tile('b');

        assertThat(tilea, is(not(tileb)));
    }

    @Test
    public void have_a_data_constructor(){
        final Tile tilea1 = new Tile('a');
        final Tile tilea2 = Tile.of('a');

        assertThat(tilea1, is(tilea2));
    }
}