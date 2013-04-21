/*
 * Implemented as Tutorial of Masters Program 
 * M.E. - Computer Engineering 
 * Design of Language Processors
 * SCET, Surat
 */
package scet.vintesh.dlp.slr;

import java.util.ArrayList;
import java.util.Stack;
import scet.vintesh.dlp.slr.ds.CellEntry;
import scet.vintesh.dlp.slr.ds.ParseLog;
import scet.vintesh.dlp.slr.ds.ProductionRule;
import scet.vintesh.dlp.slr.ds.SLRTableEntries;

/**
 *
 * @author Vintesh
 */
public class ParsingAlgorithm {
    
    public static boolean parseString(String stringToCheck, ArrayList<ProductionRule> rules, ArrayList<SLRTableEntries> slrTableEntries) {
        // Stack Initialization
        Stack<String> stack = new Stack<>();
        stack.push("$");
        stack.push("0");

        // Append $ to string which needs to be parsed
        stringToCheck += "$";
        
        while (true) {
            String stackTop = stack.get(stack.size() - 1);
            String stringTop = String.valueOf(stringToCheck.charAt(0));
            
            CellEntry cellEntry = SLRTableEntries.getCellEntry(stackTop, stringTop);
            
            switch (cellEntry.getEntryType()) {
                case SHIFT_T:
                    // Removing that symbol from the String Content
                    String firstCharOfString = String.valueOf(stringToCheck.charAt(0));
                    stringToCheck = stringToCheck.substring(1);
                    
                    stack.push(firstCharOfString);
                    stack.push(String.valueOf(cellEntry.getNumber()));
                    break;
                case REDUCE:
                    ProductionRule productionRule = ProductionRule.getProductionByNumber(cellEntry.getNumber());
                    
                    if (productionRule.getNumber() == 5) {
                        int x = 0;
                        x++;
                    }
                    int j = 0;
                    for (int i = stack.size() - 1; i > 1; i--, j++) {
                        // MATCHES Will not work with the Regular Expression for ESCAPE SEQUENCE CHARACTERS
                        // Don't USE ---- if (productionRule.getRightSide().matches(stack.get(i) + "[.]")) {
                        if (productionRule.getRightSide().startsWith(stack.get(i))) {
                            break;
                        }
                    }
                    while (j >= 0) {
                        stack.pop();
                        j--;
                    }
                    
                    stack.push(productionRule.getLeftSide());
                    
                    String columnName = stack.get(stack.size() - 1);
                    String rowNumber = stack.get(stack.size() - 2);
                    CellEntry cellEntryForNextMove = SLRTableEntries.getCellEntry(rowNumber, columnName);
                    
                    stack.push(String.valueOf(cellEntryForNextMove.getNumber()));
                    break;
                case ACCEPT:
                    System.out.println("String parsed Successfully...");
                    System.out.println(new ParseLog(stack.toString(), stringToCheck, cellEntry.toString()));
                    return true;
                case BLANK:
                    throw new IllegalStateException("BLANK CELL ENTRY : String is not parsed by the given grammer..");
            }
            System.out.println(new ParseLog(stack.toString(), stringToCheck, cellEntry.toString()));
        }
    }

    /**
     * Tester
     *
     * @param args
     */
    public static void main(String[] args) {
        parseString(null, null, null);
    }
}
