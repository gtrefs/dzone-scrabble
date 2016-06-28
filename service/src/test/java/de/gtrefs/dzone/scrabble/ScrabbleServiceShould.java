package de.gtrefs.dzone.scrabble;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ScrabbleServiceShould {

    @Mock
    ScrabbleSetPrinter printer;

    @Mock
    ScrabbleSet scrabbleSet;

    @Test
    public void use_printer_to_print_scrabble_set(){
        final ScrabbleService service = new ScrabbleService(printer);
        final ScrabbleSet set = new ScrabbleSet();

        service.print(set);

        verify(printer).printSet(set);
    }

    @Test
    public void use_scrabble_set_to_remove_tiles(){
        final ScrabbleService service = new ScrabbleService(printer);

        service.removeTiles("AAA", scrabbleSet);

        verify(scrabbleSet).remove(any(ScrabbleSet.class));
    }

}
