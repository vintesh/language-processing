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
    private int number;

    public CellEntry(EntryType entryType, int number) {
        this.entryType = entryType;
        this.number = number;
    }

    public CellEntry(String str) {
        if (str.equalsIgnoreCase("Z")) {
            this.entryType = EntryType.BLANK;
        } else if (str.equalsIgnoreCase("A")) {
            this.entryType = EntryType.ACCEPT;
        } else if (str.substring(0, str.length() - 1).equalsIgnoreCase("S")) {
            this.number = Integer.parseInt(String.valueOf(str.charAt(str.length() - 1)));
            this.entryType = EntryType.SHIFT;
        } else if (str.substring(0, str.length() - 1).equalsIgnoreCase("R")) {
            this.number = Integer.parseInt(String.valueOf(str.charAt(str.length() - 1)));
            this.entryType = EntryType.REDUCE;
        }
    }

    @Override
    public String toString() {
        return String.format("%-6s | %-2d |", entryType, number);
    }
}