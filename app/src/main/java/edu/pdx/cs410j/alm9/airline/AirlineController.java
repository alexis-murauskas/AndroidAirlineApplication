package edu.pdx.cs410j.alm9.airline;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AirlineController {
    private List<Airline> airlines;
    private static String filePrefix = "airline";

    public AirlineController() {
        this.airlines = new ArrayList<>();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void readAirlines(File path) {
        int tag = 0;
        File file = new File(path, filePrefix + tag);

        while (file.isFile()) {
            try {
                Airline rv = TextParser.parse(file);
                airlines.add(rv);
                ++tag;
                file = new File(path, filePrefix + tag);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void writeAirlines(File path) {
        for (Airline airline : airlines) {
            try {
                int pos = airlines.indexOf(airline);
                File file = new File(path, filePrefix + pos);
                TextDumper.dump(airline, file);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public void addAirline(Airline airline) {
        airlines.add(airline);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addFlight(InputModel model) throws ParseException {
        Airline found = findAirline(model.airline);

        if (found == null) {
            found = new Airline(model.airline);
            addAirline(found);
        }

        found.addFlight(model);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Airline findAirline(String name) {
        if (airlines.isEmpty())
            return null;

        Optional<Airline> rv = airlines.stream()
                .filter(a -> a.getName().equals(name))
                .findAny();

        return rv.orElse(null);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Airline findAirline(String name, String src, String dest) {
        if (airlines.isEmpty())
            return null;

        Optional<Airline> airline = airlines.stream()
                .filter(a -> a.getName().equals(name))
                .findAny();

        if (!airline.isPresent())
            return null;

        Airline found = airline.get();
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