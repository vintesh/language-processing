/*
 * Implemented as Tutorial of Masters Program 
 * M.E. - Computer Engineering 
 * Design of Language Processors
 * SCET, Surat
 */
package scet.vintesh.dlp.slr.ds;

/**
 *
 * @author Vintesh
 */
public class CellEntry {

    private EntryType entryType;
    private int number = -1;

    public CellEntry(EntryType entryType, int number) {
        this.entryType = entryType;
        this.number = number;
    }

    public CellEntry(String str) {
        if (str.equalsIgnoreCase("Z")) {
            this.entryType = EntryType.BLANK;
        } else if (str.equalsIgnoreCase("A")) {
            this.entryType = EntryType.ACCEPT;
        } else if (str.matches("[sS].")) {
            this.number = Integer.parseInt(String.valueOf(str.substring(1)));
            this.entryType = EntryType.SHIFT_T;
        } else if (str.matches("[rR].")) {
            this.number = Integer.parseInt(String.valueOf(str.substring(1)));
            this.entryType = EntryType.REDUCE;
        } else if (str.matches("[0-9]*")) {
            this.number = Integer.parseInt(String.valueOf(str.substring(0)));
            this.entryType = EntryType.SHIFT_NT;
        }
    }

    public EntryType getEntryType() {
        return entryType;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return String.format("%-10s | %-2d |", entryType, number);
    }
}