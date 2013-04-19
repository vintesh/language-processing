/*
 * Implemented as Tutorial of Masters Program 
 * M.E. - Computer Engineering 
 * Design of Language Processors
 * SCET, Surat
 */
package scet.vintesh.dlp.slr.ds;

import com.csvreader.CsvReader;
import java.util.ArrayList;

/**
 *
 * @author Vintesh
 */
public class SLRTableEntries {

    private CellEntry cellEntry;
    private int rowNumber;
    private String columnName;
    private static ArrayList<SLRTableEntries> slrTable = new ArrayList<>();
    /**
     * For the External Files from which SLR Table will be constructed.
     */
    // For Configurations - 1 
    private static String CSVFilePathOfTable;
    private static ArrayList<String> columnNamesInFile = new ArrayList<>();

    public static void initConfig() {
        // Configurations - 1
        CSVFilePathOfTable = "files/slr/table1.csv";
        columnNamesInFile.add("n");
        columnNamesInFile.add("id");
        columnNamesInFile.add("*");
        columnNamesInFile.add("+");
        columnNamesInFile.add("$");
        columnNamesInFile.add("|");
        columnNamesInFile.add("E");
        columnNamesInFile.add("T");
        columnNamesInFile.add("F");
    }

    public SLRTableEntries(CellEntry cellEntry, int rowNumber, String columnName) {
        this.cellEntry = cellEntry;
        this.rowNumber = rowNumber;
        this.columnName = columnName;
    }

    public static void printSLRTable() {
        System.out.println(String.format("%-5s | %-5s | %-9s |", "ROW", "COLUMN", "CELL ENTRY"));
        for (SLRTableEntries entry : slrTable) {
            System.out.println(entry);
        }
    }

    public static void constructTable() {
        initConfig();

        try {
            CsvReader entries = new CsvReader(CSVFilePathOfTable);
            entries.readHeaders();
            for (int i = 0; entries.readRecord(); i++) {
                for (String columnName : columnNamesInFile) {
                    CellEntry cellEntry = new CellEntry(entries.get(columnName));
                    System.out.println(slrTable.add(new SLRTableEntries(cellEntry, i, columnName)));
                }
            }
            entries.close();
        } catch (Exception e) {
            System.out.println("SLR Table Construction Error ... Check the Syntex of the Table");
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return String.format("%-5s | %-5s | %-9s |", rowNumber, columnName, cellEntry);
    }

    /**
     * Tester
     */
    public static void main(String[] args) {
        SLRTableEntries.constructTable();
        SLRTableEntries.printSLRTable();
    }
}
