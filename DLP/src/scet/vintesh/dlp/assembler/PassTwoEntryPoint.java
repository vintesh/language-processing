/*
 * Implemented as Tutorial of Masters Program 
 * M.E. - Computer Engineering 
 * Design of Language Processors
 * SCET, Surat
 */
package scet.vintesh.dlp.assembler;

import java.util.StringTokenizer;
import scet.vintesh.dlp.assembler.ds.SYMTABLE;
import scet.vintesh.dlp.util.FileHandler;

/**
 *
 * @author Vintesh
 */
public class PassTwoEntryPoint {

    public static final String pathOfOutputFile = "files/assembler/OUTPUT_PASS2.txt";
    private static String inputFileContent = new String(FileHandler.readFile(PassOneEntryPoint.pathOfOutputFile));
    private static StringBuffer outputFileContent = new StringBuffer();

    public static void main(String[] args) {

        StringTokenizer mainStringTokenizer = new StringTokenizer(inputFileContent, "\n");
        while (mainStringTokenizer.hasMoreTokens()) {
            String oneLine = mainStringTokenizer.nextToken().trim();
            StringTokenizer fieldSaperator = new StringTokenizer(oneLine, " \t");

            while (fieldSaperator.hasMoreTokens()) {
                String nextField = fieldSaperator.nextToken().trim();

                // Skip the AD & DL Statements
                if (!(nextField.startsWith("(AD") || nextField.startsWith("(DL"))) {
                    // Appending Address
                    outputFileContent.append(nextField).append("\t");

                    // Appending Mnemonic Code
                    nextField = fieldSaperator.nextToken();
                    outputFileContent.append(nextField.substring(4, 6)).append("\t");

                    // If any more OPERAND are available represent the Equivalent Code
                    if (fieldSaperator.hasMoreTokens()) {
                        nextField = fieldSaperator.nextToken().trim();
                        processSymbol(nextField);

                        // If any more OPERAND are available represent the Equivalent Code
                        if (fieldSaperator.hasMoreTokens()) {
                            nextField = fieldSaperator.nextToken().trim();
                            processSymbol(nextField);
                        } else {
                            outputFileContent.append("\n");
                        }
                    }
                }
                break;
            }
        }

        // Writing the Output in the File
        FileHandler.writeFile(pathOfOutputFile, outputFileContent.toString().toCharArray());
    }

    private static void processSymbol(String nextField) throws NumberFormatException {
        // If it is symbol then fetch from the symbol table & get the equivalent address
        if (nextField.startsWith("(S")) {
            String entryNo = nextField.substring(3, nextField.length() - 1);
            entryNo = entryNo.replace(')', ' ').trim();
            SYMTABLE entryOfSymbolTable = SYMTABLE.getEntryByEntryNo(Integer.parseInt(entryNo));
            outputFileContent.append(entryOfSymbolTable.getAddress()).append("\n");
        } else {
            // Or else put the Inbuilt Operands
            outputFileContent.append(nextField).append("\t");
        }
    }
}
