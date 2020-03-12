package edu.pdx.cs410j.alm9.airline;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.ParseException;

public class Project4 {
    public static String readme = "Alexis Murauskas - CS410j - Winter2020 - Project4\n\n" +
            "usage: java edu.pdx.cs410j.<login-id>.Project4 [options] <args>\n\n" +
            "args are (in this order):\n" +
            "airline The name of the airline\n" +
            "flightNumber The flight number\n" +
            "src Three-letter code of departure airport\n" +
            "depart Departure date and time (24-hour time)\n" +
            "dest Three-letter code of arrival airport\n" +
            "arrive Arrival date and time (24-hour time)\n\n" +
            "options are (options may appear in any order):\n" +
            "-xmlFile file Where to read/write the airline info\n" +
            "-pretty file Pretty print the airline’s flights to" +
            "a text file or standard out (file -)\n" +
            "-textFile file Where to read/write the airline info\n" +
            "-print Prints a description of the new flight\n" +
            "-README Prints a README for this project and exits\n\n" +
            "Date and time should be in the format: mm/dd/yyyy hh:mm\n";

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void main(String[] args) {
        InputModel model = null;
        Airline airline = null;
        String fileName = null;

        if (args == null) {
            System.err.println("Missing command line arguments");
            System.exit(1);
        }

        // Attempt to parse input
        try {

            model = AirlineCommand.parse(args);

        } catch (NumberFormatException e) {
            System.err.println("Flight code isn't an integer");
            System.exit(1);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Arguments could not be parsed");
            System.exit(1);
        } catch (ParseException e) {
            System.err.println("Time is malformatted");
            System.exit(1);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

        // Print README and exit
        if (model.options.contains("-README")) {
            System.out.println(readme);
            System.exit(0);
        }

        // Read in file if option is present
        try {
            if (model.options.contains("-textFile")) {
                int index = model.options.indexOf("-textFile");
                fileName = model.options.get(index + 1);
                TextParser parser = new TextParser(fileName);
                airline = parser.parse();
            }
            if (model.options.contains("-xmlFile")) {
                int index = model.options.indexOf("-xmlFile");
                fileName = model.options.get(index + 1);
                XmlParser parser = new XmlParser(fileName);
                airline = parser.parse();
            }
        } catch (Exception e) {
            System.err.println("File contents are malformatted");
            System.exit(1);
        }

        // Add new flight if requested
        try {
            if (airline == null)
                airline = new Airline(model.airline);

            Flight added = airline.addFlight(model);
        } catch (Exception e) {
            System.err.println("Missing command line arguments");
            System.exit(1);
        }

        // Write out to file
        try {
            if (model.options.contains("-textFile")) {
                TextDumper dumper = new TextDumper(fileName);
                dumper.dump(airline);
            }

            if (model.options.contains("-xmlFile")) {
                XmlDumper dumper = new XmlDumper(fileName);
                dumper.dump(airline);
            }

            if (model.options.contains("-pretty")) {
                int index = model.options.indexOf("-pretty");
                String output = model.options.get(index + 1);
                PrettyPrinter pretty = new PrettyPrinter(output);
                pretty.dump(airline);
            }
        } catch (Exception e) {
            System.err.println("Airline could not be written to: " + fileName);
            System.exit(1);
        }

        System.exit(0);
    }
}
