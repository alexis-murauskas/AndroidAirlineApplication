package edu.pdx.cs410J.alm9.airline;

import edu.pdx.cs410j.alm9.airline.Airline;
import edu.pdx.cs410j.alm9.airline.Flight;
import edu.pdx.cs410j.alm9.airline.XmlParser;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

public class XmlParserTest {
    public static String prefix = "src/test/resources/edu/pdx/cs410J/alm9/airline//";

    @Test
    public void parserCanReadInValidFile()  {
        XmlParser parser = new XmlParser(prefix+"valid-airline.xml");
        parser.parse();
    }

    @Test
    public void nonexistentFileDoesNotThrowError()  {
        XmlParser parser = new XmlParser(prefix+"does-not-exist.xml");
        parser.parse();
    }

    @Test (expected = IllegalArgumentException.class)
    public void parserThrowsExceptionForInvalidFIle()  {
        XmlParser parser = new XmlParser(prefix+"invalid-airline.xml");
        parser.parse();
    }

    @Test
    public void airlineHasCorrectName()  {
        XmlParser parser = new XmlParser(prefix+"valid-airline.xml");
        Airline rv = parser.parse();

        assertThat(!rv.getName().isEmpty(), is(true));
        assertThat(rv.getName(), is("'Valid Airlines'"));
    }

    @Test
    public void airlineHasCorrectNumberOfFlights()  {
        XmlParser parser = new XmlParser(prefix+"valid-airline.xml");
        Airline rv = parser.parse();

        assertThat(rv.getFlights().size(), is(2));
    }

    @Test
    public void flightDataIsCorrect()  {
        XmlParser parser = new XmlParser(prefix+"valid-airline.xml");
        Airline rv = parser.parse();
        Flight flight = (Flight) rv.getFlights().toArray()[0];

        assertThat(flight.getNumber(), is(1437));
        assertThat(flight.getSource(), is("BJX"));
        assertThat(flight.getDepartureString(), is("9/25/20 5:00 PM"));
    }
}
