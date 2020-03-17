package edu.pdx.cs410j.alm9.airline;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Collectors;

public class TextDumper{

    /**
     * Given an airline object, dump will write it out to a file based on the name of the airline.
     * @param airline to be written out to a file
     * @throws IOException if the parameter is null, or if any errors occur while writing the object to file
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void dump(Airline airline, File file) throws IOException {
        if (airline == null)
            throw new IOException();

        try {
            FileWriter writer = new FileWriter(file);
            writer.write(airline.getName());
            String flights = airline.getFlights()
                    .stream()
                    .map(
                            f -> "\n" + f.getNumber()
                                    + ";" + f.getSource()
                                    + ";" + Flight.PARSEFORMAT.format(f.getDeparture()).replace(" ", ";")
                                    + ";" + f.getDestination()
                                    + ";" + Flight.PARSEFORMAT.format(f.getArrival()).replace(" ", ";")
                    )
                    .collect(Collectors.joining(""));

            writer.write(flights);
            writer.close();

        } catch (Exception e) {
            throw new IOException(e);
        }
    }
}
