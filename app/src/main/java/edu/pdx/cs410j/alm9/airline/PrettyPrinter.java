package edu.pdx.cs410j.alm9.airline;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Collectors;

public class PrettyPrinter {

    private static final int MSPERMIN = 60000;
    private String fileName;

    public PrettyPrinter(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Dumps the indicated airline to either a file or the console depending on the given fileName
     * and utilizes formatting to make it user-friendly.
     * @param airline to be pretty printed
     * @throws IOException if there is an error writing out the flights, or writing out to file
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void dump(Airline airline) throws IOException {
        if (airline == null)
            throw new IOException();

        String toWrite = "*** " + airline.getName() + " ***\n";
        toWrite += airline.getFlights()
                .stream()
                .map(
                        f -> "\n" + f.getNumber()
                                + " " + f.getSource()
                                + " " + f.getDepartureString()
                                + " " + f.getDestination()
                                + " " + f.getArrivalString()
                                + " (" + ((f.getArrival().getTime()-f.getDeparture().getTime())/MSPERMIN)
                                + " mins)"
                )
                .collect(Collectors.joining(""));

        try {
            if (this.fileName.equals("-")) {
                System.out.println(toWrite);
                return;
            }

            File file = new File(this.fileName);
            FileWriter writer = new FileWriter(file);
            writer.write(toWrite);
            writer.close();

        } catch (Exception e) {
            throw new IOException(e);
        }
    }
}
