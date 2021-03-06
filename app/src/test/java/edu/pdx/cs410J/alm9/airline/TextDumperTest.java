package edu.pdx.cs410J.alm9.airline;

import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;

import edu.pdx.cs410j.alm9.airline.Airline;
import edu.pdx.cs410j.alm9.airline.Flight;
import edu.pdx.cs410j.alm9.airline.TextDumper;

public class TextDumperTest {

    private Flight flight1 = new Flight(
            1,
            "PDX",
            Flight.PARSEFORMAT.parse("11/11/1111 11:11 AM"),
            "ABQ",
            Flight.PARSEFORMAT.parse("12/12/1212 12:12 PM")
    );
    private Flight flight2 = new Flight(
            2,
            "PDX",
            Flight.PARSEFORMAT.parse("11/11/1111 11:11 AM"),
            "ABQ",
            Flight.PARSEFORMAT.parse("12/12/1212 12:12 PM")
    );

    public TextDumperTest() throws ParseException {
    }

    @Test
    public void nullArgThrowsException() {
        TextDumper sut = new TextDumper("");
        try {
            sut.dump(null);
        } catch (IOException e) {
            return;
        }
        assert (false);
    }

    @Test
    public void validArgDoesNotThrowException() {
        Airline airline = new Airline("Airline");
        TextDumper sut = new TextDumper("airline.txt");
        airline.addFlight(flight1);

        try {
            sut.dump(airline);
        } catch (IOException e) {
            assert (false);
        }
    }

    @Test
    public void airlineWithMultipleFlights() {
        Airline airline = new Airline("MultipleFlights");
        TextDumper sut = new TextDumper("multiple-flights.txt");

        airline.addFlight(flight1);
        airline.addFlight(flight2);

        try {
            sut.dump(airline);
        } catch (IOException e) {
            assert (false);
        }
    }

    @Test
    public void airlineWithLongName() {
        Airline airline = new Airline("'Long Name'");
        TextDumper sut = new TextDumper("long-name.txt");

        airline.addFlight(flight1);
        airline.addFlight(flight2);

        try {
            sut.dump(airline);
        } catch (IOException e) {
            assert (false);
        }
    }
}