package edu.pdx.cs410j.alm9.airline;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AirlineController {
    private List<Airline> airlines;

    public AirlineController() {
        airlines = new ArrayList<>();
    }

    public void addAirline(Airline airline) {
        airlines.add(airline);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addFlight(InputModel model) throws ParseException {
        Airline found = findAirline(model.airline);

        if (found == null) {
            found = new Airline(model.airline);
            airlines.add(found);
        }

        found.addFlight(model);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Airline findAirline(String name) {
        if (airlines.isEmpty())
            return null;

        Optional rv = airlines.stream()
                .filter(a -> a.getName().equals(name))
                .findAny();

        if (rv.isPresent())
            return (Airline)  rv.get();

        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Airline findAirline(String name, String src, String dest) {
        if (airlines.isEmpty())
            return null;

        Optional airline = airlines.stream()
                .filter(a -> a.getName().equals(name))
                .findAny();

        if (!airline.isPresent())
            return null;

        Airline found = (Airline) airline.get();
        List<Flight> flights = found
                .getFlights()
                .stream()
                .filter(f -> f.getSource().equals(src) && f.getDestination().equals(dest))
                .collect(Collectors.toList());

        Airline rv = new Airline(name);
        for (Flight flight : flights)
            rv.addFlight(flight);

        return rv;
    }
}