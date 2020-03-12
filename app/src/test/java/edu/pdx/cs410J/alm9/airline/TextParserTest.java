package edu.pdx.cs410J.alm9.airline;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import edu.pdx.cs410j.alm9.airline.TextParser;

import org.junit.Test;

public class TextParserTest {

    public static String prefix = "src/test/resources/edu/pdx/cs410J/alm9/airline//parser-";

    @Test
    public void nonExistentFileNameDoesNotThrowException() {
        TextParser parser = new TextParser("does-not-exist.txt");
        try {
            assertThat(parser.parse(), nullValue());
        } catch (IllegalArgumentException e) {
            assert(false);
        }
    }

    @Test
    public void correctFileNameDoesNotThrowException() {
        TextParser parser = new TextParser(prefix+"airline.txt");
        try {
            parser.parse();
        } catch (IllegalArgumentException e) {
            assert(false);
        }
    }

    @Test
    public void singleWordAirlineNameReturnsAirline() {
        TextParser parser = new TextParser(prefix+"airline.txt");
        try {
            assertThat(parser.parse().getName(), is("Airline"));
        } catch (IllegalArgumentException e) {
            assert(false);
        }
    }

    @Test
    public void multiWordAirlineNameReturnsAirline() {
        TextParser parser = new TextParser(prefix+"long-name.txt");
        try {
            assertThat(parser.parse().getName(), is("'Long Airline'"));
        } catch (IllegalArgumentException e) {
            assert(false);
        }
    }

    @Test
    public void multipleFlightsAreAddedSuccessfully() {
        TextParser parser = new TextParser(prefix+"airline.txt");
        try {
            assertThat(parser.parse().getFlights().size(), not(0));
        } catch (IllegalArgumentException e) {
            assert(false);
        }
    }

    @Test
    public void airlineCanHaveNoFlightsAdded() {
        TextParser parser = new TextParser(prefix+"long-name.txt");
        try {
            assertThat(parser.parse().getFlights().size(), is(0));
        } catch (IllegalArgumentException e) {
            assert(false);
        }
    }

    @Test
    public void malformattedFileThrowsException() {
        TextParser parser = new TextParser(prefix+"malformatted.txt");
        try {
            parser.parse();
        } catch (IllegalArgumentException e) {
            return;
        }
        assert(false);
    }
}
