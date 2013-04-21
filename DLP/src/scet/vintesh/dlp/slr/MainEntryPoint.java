/*
 * Implemented as Tutorial of Masters Program 
 * M.E. - Computer Engineering 
 * Design of Language Processors
 * SCET, Surat
 */
package scet.vintesh.dlp.slr;

import java.util.Scanner;
import scet.vintesh.dlp.slr.ds.ProductionRule;
import scet.vintesh.dlp.slr.ds.SLRTableEntries;

/**
 *
 * @author Vintesh
 */
public class MainEntryPoint {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Creating Parsing Table
        SLRTableEntries.constructTable();
        SLRTableEntries.printSLRTable();

        // Getting the string to check
        System.out.println("Enter the String: ");
//        String stringToCheck = scanner.nextLine();
        String stringToCheck = "i+i";

        // String Parsing
        boolean isValid = ParsingAlgorithm.parseString(stringToCheck, ProductionRule.getInstance(), SLRTableEntries.getSlrTable());
        System.out.println("is Valid String: " + isValid);
    }
}
