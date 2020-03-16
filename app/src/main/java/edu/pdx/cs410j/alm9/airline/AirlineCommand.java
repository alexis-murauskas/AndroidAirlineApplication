package edu.pdx.cs410j.alm9.airline;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AirlineCommand {
    private static final int AIRLINE = 0;
    private static final int FLIGHT = 1;
    private static final int SRC = 2;
    private static final int DEPART = 3;
    private static final int DEST = 4;
    private static final int ARRIVE = 5;

    private static final int SEARCH_SRC = 1;
    private static final int SEARCH_DEST = 2;

    private static final int CODELEN = 3;

    private static final List<String> validOptions = Arrays.asList(
            "-getAirline",
            "-search"
    );

    /**
     * Parse is responsible for checking if the user input is complete and correct. If all the input
     * passes checks, then it is organized into an InputModel object that can be consumed by Main and/or
     * the Airline Controller.
     *
     * @param input String array of user input, including options and arguments.
     * @return Returns input organized into a model if all error checks pass.
     * @throws ArrayIndexOutOfBoundsException if something goes wrong in parsing options or Airline Name
     *                                        because of invalid user input
     * @throws IllegalArgumentException       if Airport Code is the incorrect length,
     *                                        contains non-alphabetic characters,
     *                                        or is not among known airport codes;
     *                                        if there are extraneous or invalid arguments;
     *                                        if the Airline Name does not match the target file
     * @throws DateTimeParseException         if the date and/or time are improperly formatted
     * @throws NumberFormatException          if the Flight Code is not numerical
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static InputModel parse(String[] input) throws ParseException {
        InputModel model = new InputModel();

        model = parseOptions(model, input);
        String[] arguments = trimArguments(model.options, input);
        model = parseArgs(model, arguments);

        return model;
    }

    /**
     * ParseOptions checks for optional flags from the user and adds them if they correctly denoted with
     * a leading hyphen and if they are among the listed valid options.
     *
     * @param input User input that has been forwarded by Parse.
     * @return A list of extracted options to be placed into the InputModel.
     */
    private static InputModel parseOptions(InputModel model, String[] input) {
        int count = 0;
        List<String> in = Arrays.asList(input);

        for (String option : validOptions) {
            boolean inputContains = in.contains(option);

            if (inputContains) {
                ++count;
                switch (validOptions.indexOf(option)) {
                    case 0:
                        model.getAirline = true;
                        break;
                    case 1:
                        model.search = true;
                        break;
                }
            }
        }

        model.options = count;
        return model;
    }

    /**
     * Trims arguments that have already been evaluated by the parser. This is intended for use with
     * extracting items of variable length, such as options and the Airline Name.
     *
     * @param size  Size of sub-list to be removed
     * @param input Current array of user input.
     * @return User input with already evaluated arguments removed.
     */
    private static String[] trimArguments(int size, String[] input) {
        return Arrays.copyOfRange(input, size, input.length);
    }

    /**
     * ParseArgs checks if flight arguments are present and then evaluates them to be placed into the
     * InputModel.
     *
     * @param input Current array of user input, possibly with options removed.
     * @return If all input items pass checks, an InputModel with the items included will be returned.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private static InputModel parseArgs(InputModel model, String[] input) throws ParseException {
        if (input.length == 0)
            return model;

        model.airline = input[AIRLINE];

        if (model.getAirline)
            return model;

        if (model.search) {
            model.source = checkAirportCode(input[SEARCH_SRC]);
            model.destination = checkAirportCode(input[SEARCH_DEST]);
            return model;
        }

        model.flightNumber = checkFlight(input[FLIGHT]);
        model.source = checkAirportCode(input[SRC]);
        model.departureTime = checkDateTime(input[DEPART]);
        model.destination = checkAirportCode(input[DEST]);
        model.arrivalTime = checkDateTime(input[ARRIVE]);
        compareDepartureArrivalTimes(model.departureTime, model.arrivalTime);

        return model;
    }

    /**
     * Attempts to parse a Flight Code as an integer. If it fails, the input is incorrect. Otherwise
     * it simply returns the same string.
     *
     * @param input Input representing a Flight Code.
     * @return The same input string.
     */
    private static String checkFlight(String input) {
        Integer.parseInt(input);
        return input;
    }

    /**
     * Checks an Airport Code to make sure that it only contains letters and has a length of 3.
     *
     * @param input Input representing an Airport Code.
     * @return The same input string.
     */
    private static String checkAirportCode(String input) {
        if (input.length() != CODELEN)
            throw new IllegalArgumentException("Airport code is too short");
        if (input.matches("[A-Za-z]]+"))
            throw new IllegalArgumentException("Airport code has number");
        /*if (AirportNames.getName(input) == null)
            throw new IllegalArgumentException("Airport code does not match a known airport");*/

        return input;
    }

    /**
     * Attempts to parse a DateTime string. If it succeeds, then the input has been correctly formatted.
     * Otherwise, it throws an exception.
     *
     * @param input Input representing a date and time.
     * @return The same input string.
     */
    private static String checkDateTime(String input) throws ParseException {
        Flight.PARSEFORMAT.parse(input);
        return input;
    }

    /**
     * Checks if the departure time comes before arrival time
     *
     * @param departure string input representing the departure time
     * @param arrival   string input representing the arrival time
     * @throws ParseException           if parsing is unsuccessful with the dates
     * @throws IllegalArgumentException if departure is after arrival
     */
    public static void compareDepartureArrivalTimes(String departure, String arrival) throws ParseException {
        Date departs = Flight.PARSEFORMAT.parse(departure);
        Date arrives = Flight.PARSEFORMAT.parse(arrival);

        if (departs.after(arrives))
            throw new IllegalArgumentException("Arrival time cannot come before departure time");
    }
}
