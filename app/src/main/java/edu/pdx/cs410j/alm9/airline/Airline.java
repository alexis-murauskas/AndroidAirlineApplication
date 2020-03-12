package edu.pdx.cs410j.alm9.airline;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class Airline {
    private String name;
    private Collection<Flight> flights = new ArrayList<>();

    public Airline() {
    }

    /**
     * Sets the name of the Airline.
     * @param name An airline name.
     */
    public Airline(String name) {
        this.name = name;
    }

    /**
     * @return The name of the airline.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Adds a new flight to the airline.
     * @param flight A generic object that represents a flight.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addFlight(Flight flight) {
        this.flights.add(flight);
        this.flights = sortFlights(this.flights);
    }

    /**
     * Adds a new flight to the airline.
     * @param model A generic object that represents a flight.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Flight addFlight(InputModel model) throws ParseException {
        Flight flight = new Flight(
                Integer.parseInt(model.flightNumber),
                model.source,
                Flight.PARSEFORMAT.parse(model.departureTime),
                model.destination,
                Flight.PARSEFORMAT.parse(model.arrivalTime)
        );

        this.flights.add(flight);
        this.flights = sortFlights(this.flights);
        return flight;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private Collection<Flight> sortFlights(Collection<Flight> flights) {
        FlightComparator comparator = new FlightComparator<Flight>();
        return (Collection<Flight>) flights.stream().sorted(comparator).collect(Collectors.toList());
    }

    /**
     * Retrieves all the saved flights for the airline.
     * @return A collection of flights.
     */
    public Collection<Flight> getFlights() {
        return this.flights;
    }
}
