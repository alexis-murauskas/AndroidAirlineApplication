package edu.pdx.cs410j.alm9.airline;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.ParseException;

/**
 * The main class that parses the command line and communicates with the
 * Airline server using REST.
 */
public class Project4 {

    public static final String MISSING_ARGS = "Missing command line arguments";

    protected static String readme = "Alexis Murauskas - CS410J - Winter2020 - Project4\n\n" +
            "usage: java edu.pdx.cs410J.<login-id>.Project4 [options] <args>\n\n" +
            "args are (in this order):\n" +
            "airline The name of the airline\n" +
            "flightNumber The flight number\n" +
            "src Three-letter code of departure airport\n" +
            "depart Departure date and time (24-hour time)\n" +
            "dest Three-letter code of arrival airport\n" +
            "arrive Arrival date and time (24-hour time)\n\n" +
            "options are (options may appear in any order):\n" +
            "-host hostname Host computer on which the server runs\n" +
            "-port port Port on which the server is listening\n" +
            "-search Search for flights\n" +
            "-print Prints a description of the new flight\n" +
            "-README Prints a README for this project and exits";

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void main(String... args) {
        InputModel model = null;

        if (args == null || args.length < 2) {
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
        if (model.readme) {
            System.out.println(readme);
            System.exit(0);
        }

        System.exit(0);
    }
}