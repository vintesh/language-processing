/*
 * Implemented as Tutorial of Masters Program 
 * M.E. - Computer Engineering 
 * Digital Language Processing
 * SCET, Surat
 */
package scet.vintesh.dlp.assembler;

import java.util.StringTokenizer;
import scet.vintesh.dlp.assembler.ds.*;
import scet.vintesh.dlp.assembler.util.FileHandler;

/**
 *
 * @author Vintesh
 */
public class PassOneEntryPoint {

    private static final String pathOfInputFile = "files/INPUTFILE.txt";
    public static final String pathOfOutputFile = "files/OUTPUT_PASS1.txt";
    private static String inputFileContent = new String(FileHandler.readFile(pathOfInputFile));
    private static StringBuffer outputFileContent = new StringBuffer();
    private static int locationCounter;

    public static int getLocationCounter() {
        return locationCounter;
    }

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

                // Check is it Literal 
                if (!isFieldLiteral(nextField)) {
                    // Check is it Constant
                    if (isFieldConstant(nextField)) {
                        continue;
                    }
                } else {
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
                        if (optableEntry.getTypeOfStatement().equals(OPTABLE.StatementType.IS)) {
                            outputFileContent.append(String.valueOf(locationCounter));
                        }
                        outputFileContent.append("\t(").append(optableEntry.getTypeOfStatement()).append(",").append(optableEntry.getMachineCode()).append(")");

                        switch (optableEntry.getTypeOfStatement().toString()) {
                            case "IS":
                                isImperativeStatementEncountered = true;
                                break;
                            case "DL":
//                                locationCounter--;
                                processDLStatement(label, optableEntry, fieldSaperator);
                                break;
                            case "AD":
//                                locationCounter--;
                                processADStatement(nextField, oneLine);
                                continue;
                        }

                    }
                }
            }

            locationCounter++;
            outputFileContent.append("\n");
        }

        // Writing the Output in the File
        FileHandler.writeFile(pathOfOutputFile, outputFileContent.toString().toCharArray());

        // Print DataStructures after Pass 1
        printAllDS();
    }

    /**
     * Prints All DataStuctures which are processed.
     */
    private static void printAllDS() {
        System.out.println("--------- OP_TABLE ---------");
        OPTABLE.printOptable();
        System.out.println("---------- SYMBOL_TABLE ---------");
        SYMTABLE.printSymbolTable();
        System.out.println("---------- LIT_TABLE ---------");
        LITTABLE.printLittable();
        System.out.println("---------- POOL_TABLE ---------");
        POOLTABLE.printPooltable();
    }

    /**
     * Processing AD Statement. i.e. calling appropriate Routine for respected
     * instruction which is specified in the OPTABLE given.
     *
     * @param adStatement - AD Statement Identifier e.g. START/END etc.
     * @param completeLine - Line in which AD occurs.
     */
    private static void processADStatement(String adStatement, String completeLine) {
        OPTABLE adEntry = OPTABLE.getOptableEntry(adStatement);
        RoutineCollections.processRoutineCall(adEntry, completeLine);
    }

    /**
     * Processing DL(DS/DC) statements
     *
     * @param label - Label Value
     * @param optableEntry - DS/DC
     * @param fieldSaperator - constant value or address incremental value
     * @throws NumberFormatException
     */
    private static void processDLStatement(String label, OPTABLE optableEntry, StringTokenizer fieldSaperator) throws NumberFormatException {
        SYMTABLE.setAddress(label, locationCounter);
        switch (optableEntry.getOpcode()) {
            case "DS":
                // -1, because LC is incremented by 1 for all the instructions
                int constant = Integer.parseInt(fieldSaperator.nextToken());
                setLocationCounter(locationCounter + constant - 1);
                outputFileContent.append("\t(C,").append(constant).append(")");
                break;
            case "DC":
                break;
        }
    }

    /**
     * Processing encountered Label
     *
     * @param isImperativeStatementEncountered - if Label is encountered as the
     * 1st or 2nd operand in the statement.
     * @param label - LABEL value
     */
    private static void processLabel(boolean isImperativeStatementEncountered, String label) {
        if (isImperativeStatementEncountered) {
            if (!SYMTABLE.isEntryExistInSymbolTable(label)) {
                SYMTABLE.addEntryToSymbolTable(label, 1);
            }
            outputFileContent.append("\t(S,").append(SYMTABLE.getSymbolEntry(label).getEntryNo()).append(")");
        } else {
            if (SYMTABLE.isEntryExistInSymbolTable(label)) {
                SYMTABLE.getSymbolEntry(label).setAddress(locationCounter);
            } else if (!SYMTABLE.isEntryExistInSymbolTable(label)) {
                SYMTABLE.addEntryToSymbolTable(label, 1);
            }
        }
    }

    /**
     * Checks whether the field is Constant or not. If yes then replace it with
     * its appropriate Representation.
     *
     * @param nextField - field / operand
     * @return - true if field is constant, false or else
     */
    private static boolean isFieldConstant(String nextField) {
        try {
            int constant = Integer.parseInt(nextField);
            outputFileContent.append("\t(C,").append(constant).append(")");
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * Checks whether the field is Literal or not. If yes then replace it with
     * its appropriate Representation & Add it to it's LITERAL TABLE
     *
     * @param nextField - field
     * @return - true if field is literal & added to the LITTAB, false or else
     */
    private static boolean isFieldLiteral(String nextField) {
        if (nextField.startsWith("'")) {
            LITTABLE.addEntry(nextField);
            return true;
        }
        return false;
    }
}
