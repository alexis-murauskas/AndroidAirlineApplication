package edu.pdx.cs410J.alm9.airline;

import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import edu.pdx.cs410j.alm9.airline.Airline;
import edu.pdx.cs410j.alm9.airline.Flight;
import edu.pdx.cs410j.alm9.airline.XmlDumper;

public class XmlDumperTest {

    @Test
    public void validAirlineSucceeds() throws IOException {
        Airline airline = new Airline("Airline");

        XmlDumper dumper = new XmlDumper("airline.xml");
        dumper.dump(airline);
    }

    @Test (expected = IOException.class)
    public void invalidAirlineFails() throws IOException {
        XmlDumper dumper = new XmlDumper("airline.xml");
        dumper.dump(null);
    }

    @Test
    public void validAirlineWithFlightSucceeds() throws IOException, ParseException {
        SimpleDateFormat formatter = Flight.PARSEFORMAT;
        Flight flight = new Flight(
                1,
                "PDX",
                formatter.parse("11/11/1111 11:11 AM"),
                "ABQ",
                formatter.parse("12/12/1212 12:12 PM")
        );

        Airline airline = new Airline("Airline");
        airline.addFlight(flight);

        XmlDumper dumper = new XmlDumper("airline-with-flight.xml");
        dumper.dump(airline);
    }
}
