/*
 * Implemented as Tutorial of Masters Program 
 * M.E. - Computer Engineering 
 * Digital Language Processing
 * SCET, Surat
 */
package scet.vintesh.dlp.assembler.ds;

import java.util.StringTokenizer;
import scet.vintesh.dlp.assembler.PassOneEntryPoint;

/**
 *
 * @author Vintesh
 */
public class RoutineCollections {

    /**
     * For performing the routine Call of AD type of statements
     *
     * @param adEntry - OPTABLE Entry of AD statement
     * @param completeLine - the whole line written in the input file for
     * processing operands
     */
    public static void processRoutineCall(OPTABLE adEntry, String completeLine) {
        String routineID = adEntry.getRoutinToBeCalled();
        switch (routineID) {
            case "R1":
                start(completeLine);
                break;
            case "R2":
                end();
                break;
            case "R3":
                origin(completeLine);
                break;
            case "R4":
                equ(completeLine);
                break;
            case "R5":
                ltorg();
                break;
            default:
                System.out.println("No Routine Is Found. Correct the CODE.");
        }
    }

    /**
     * Performing processing for START instruction
     *
     * @param line - line from the inputFile
     */
    private static void start(String line) {
        StringTokenizer tokenizer = new StringTokenizer(line, " \t");
        // Removing START from the line as token
        tokenizer.nextToken();
        // Getting the 2nd Operand from the line & parse it as address & update the location cou
        PassOneEntryPoint.setLocationCounter(Integer.parseInt(tokenizer.nextToken()));
    }

    /**
     * Performing processing for END instruction
     */
    private static void end() {
        LITTABLE.assignAddressesToLiterals();
        SYMTABLE.assignAddressesToLiterals();
    }

    /**
     * Performing processing for ORIGIN instruction which just updates the LC
     * value
     *
     * @param line - line from the inputFile
     */
    private static void origin(String line) {
        StringTokenizer tokenizer = new StringTokenizer(line, " \t");
        // Removing ORIGIN from the line as token
        tokenizer.nextToken();
        // Getting the 2nd Operand from the line & parse it as address & update the location counter & 
        // -1 as it is incremented by 1 in the while loop of the main program of PassOneEntry Point
        PassOneEntryPoint.setLocationCounter(Integer.parseInt(tokenizer.nextToken()) - 1);
    }

    /**
     * Performing processing for LTORG instruction which allocates memory to the
     * literals of LITTAB
     */
    private static void ltorg() {
        LITTABLE.assignAddressesToLiterals();
    }

    /**
     * Performing processing for EQU statement that is assigning the value to
     * symbol on LHS side of EQU with the value of RHS side of symbol
     */
    private static void equ(String line) {
        StringTokenizer tokenizer = new StringTokenizer(line, " \t");
        // Fetching the LHS side symbol
        String destinationSymbol = tokenizer.nextToken().trim();
        // Removing EQU from the line as token
        tokenizer.nextToken();
        // Fetching the RHS side symbol
        String sourceSymbol = tokenizer.nextToken().trim();

        SYMTABLE sourceSymbolEntry = SYMTABLE.getSymbolEntry(sourceSymbol);
        // If sourceSymbol entry available then fetch its' address & assign them to the destination symbol
        if (sourceSymbolEntry != null) {
            SYMTABLE.addEntryToSymbolTable(sourceSymbol, 1);
            SYMTABLE destinationSymbolEntry = SYMTABLE.getSymbolEntry(destinationSymbol);
            // If destination entry is available then set the address or create new Entry & set Address
            if (destinationSymbolEntry != null) {
                destinationSymbolEntry.setAddress(sourceSymbolEntry.getAddress());
            } else {
                SYMTABLE.addEntryToSymbolTable(destinationSymbol, sourceSymbolEntry.getAddress(), 1);
            }
        }

        // @TODO: If Source Symbol not exists then have to code for that
    }
}
