/*
 * Implemented as Tutorial of Masters Program 
 * M.E. - Computer Engineering 
 * Digital Language Processing
 * SCET, Surat
 */
package scet.vintesh.dlp.assembler;

import java.util.StringTokenizer;
import scet.vintesh.dlp.assembler.ds.OPTABLE;
import scet.vintesh.dlp.assembler.ds.InbuiltOperand;
import scet.vintesh.dlp.assembler.ds.SYMTABLE;
import scet.vintesh.dlp.assembler.util.FileHandler;

/**
 *
 * @author Vintesh
 */
public class PassOneEntryPoint {

    private static final String pathOfInputFile = "files/INPUTFILE.txt";
    private static final String pathOfOutputFile = "files/OUTPUT_PASS1.txt";
    private static String inputFileContent = new String(FileHandler.readFile(pathOfInputFile));
    private static StringBuffer outputFileContent = new StringBuffer();
    private static int locationCounter;

    public static void setLocationCounter(int locationCounter) {
        PassOneEntryPoint.locationCounter = locationCounter;
    }

    public static void main(String[] args) {
        // Start Processing the Input File for generating the intermediate code
        StringTokenizer mainStringTokenizer = new StringTokenizer(inputFileContent, "\n");
        while (mainStringTokenizer.hasMoreTokens()) {
            String oneLine = mainStringTokenizer.nextToken().trim();
            StringTokenizer fieldSaperator = new StringTokenizer(oneLine, " ,\t");
            String label = "";

            boolean isImperativeStatementEncountered = false;

            while (fieldSaperator.hasMoreTokens()) {
                String nextField = fieldSaperator.nextToken().trim();

                // Check is it Constant
                if (isFieldConstant(nextField)) {
                    continue;
                }

                // Check is it LABEL
                if (!OPTABLE.isEntryExistInOptable(nextField) && !InbuiltOperand.isInbuiltOperand(nextField)) {
                    processLabel(isImperativeStatementEncountered, nextField);
                    label = nextField;
                } // If not LABEL then it is Assembly Statement
                else {
                    // Check whether it is inbuilt operand?
                    String operandIndex = InbuiltOperand.map.get(nextField);
                    if (operandIndex != null) {
                        outputFileContent.append("\t(").append(operandIndex).append(")");
                    } else {
                        // For Mnemonics
                        OPTABLE optableEntry = OPTABLE.getOptableEntry(nextField);
                        outputFileContent.append(String.valueOf(locationCounter));
                        outputFileContent.append("\t(").append(optableEntry.getTypeOfStatement()).append(",").append(optableEntry.getMachineCode()).append(")");
                        
                        switch (optableEntry.getTypeOfStatement().toString()) {
                            case "IS":
                                isImperativeStatementEncountered = true;
                                break;
                            case "DL":
                                processDLStatement(label, optableEntry, fieldSaperator);
                                break;
                            case "AD":
                                break;
                        }
                        
                    }
                }
            }

            locationCounter++;
            outputFileContent.append("\n");
        }
        // Writing the Output in the File
        FileHandler.writeFile(pathOfOutputFile, outputFileContent.toString().toCharArray());
        // Print DataStructures
        System.out.println("--------- OP_TABLE ---------");
        OPTABLE.printOptable();
        System.out.println("---------- SYMBOL_TABLE ---------");
        SYMTABLE.printSymbolTable();
    }

    private static void processDLStatement(String label, OPTABLE optableEntry, StringTokenizer fieldSaperator) throws NumberFormatException {
        SYMTABLE.setAddress(label, locationCounter);
        switch (optableEntry.getOpcode()) {
            case "DS":
                // -1, because LC is incremented by 1 for all the instructions
                int constant = Integer.parseInt(fieldSaperator.nextToken());
                locationCounter = locationCounter + constant - 1;
                outputFileContent.append("\t(C,").append(constant).append(")");
                break;
            case "DC":
                break;
        }
    }

    private static void processLabel(boolean isImperativeStatementEncountered, String nextField) {
        if (isImperativeStatementEncountered) {
            if (SYMTABLE.isEntryExistInSymbolTable(nextField)) {
                outputFileContent.append("\t(S,").append(SYMTABLE.getSymbolEntry(nextField).getEntryNo()).append(")");
            } else if (!SYMTABLE.isEntryExistInSymbolTable(nextField)) {
                SYMTABLE.addEntryToSymbolTable(nextField, -1, 1);
                outputFileContent.append("\t(S,").append(SYMTABLE.getSymbolEntry(nextField).getEntryNo()).append(")");
            }
        } else {
            if (SYMTABLE.isEntryExistInSymbolTable(nextField)) {
                SYMTABLE.getSymbolEntry(nextField).setAddress(locationCounter);
            } else if (!SYMTABLE.isEntryExistInSymbolTable(nextField)) {
                SYMTABLE.addEntryToSymbolTable(nextField, locationCounter, 1);
            }
        }
    }

    private static boolean isFieldConstant(String nextField) {
        try {
            int constant = Integer.parseInt(nextField);
            outputFileContent.append("\t(C,").append(constant).append(")");
            return true;
        } catch (Exception e) {
        }
        return false;
    }
}
