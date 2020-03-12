package edu.pdx.cs410j.alm9.airline;

/**
 * Used for sorting flights
 */
public interface AbstractComparable extends Comparable<Flight> {
    int compareTo(Flight t);
}
