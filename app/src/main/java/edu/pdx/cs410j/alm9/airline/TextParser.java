package edu.pdx.cs410j.alm9.airline;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class TextParser {

    /**
     * TextParser reads in from a specified file. It expects that the file name is already properly formatted,
     * and assumes it is reading in an Airline object. This means that the first line is always the name
     * of the airline, followed by lines representing the associated flights. These are in standard CSV
     * format.
     * @return An airline object created from the file's contents
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Airline parse(File file) throws IOException {
        if (file == null)
            throw new IOException();

        Airline airline = null;

        try {
            String[] input;
            if (!file.isFile())
                return null;

            Scanner reader = new Scanner(file);

            if (reader.hasNextLine()) {
                String name = reader.nextLine();
                airline = new Airline(name);
            }

            while (reader.hasNextLine()) {
                String line = "placeholder;" + reader.nextLine();
                input = line.split(";");
                InputModel rv = AirlineCommand.parse(input);
                airline.addFlight(rv);
            }

            reader.close();
        }
        catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        return airline;
    }
}
