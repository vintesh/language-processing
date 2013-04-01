/*
 * Implemented as Tutorial of Masters Program 
 * M.E. - Computer Engineering 
 * Digital Language Processing
 * SCET, Surat
 */
package scet.vintesh.dlp.assembler.ds;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import scet.vintesh.dlp.assembler.util.FileHandler;

/**
 *
 * @author Vintesh
 */
public class OPTABLE {

    public enum StatementType {

        AD, DL, IS;

        /**
         * Already implemented as VALUE OF METHOD in the ENUM
         *
         * @param string - of which you want to get equivalent ENUM Value.
         * @return - ENUM of the Equivalent String passed as Parameter.
         */
        @Deprecated
        public StatementType fromString(String string) {
            if (string.trim().equals("AD")) {
                return AD;
            } else if (string.trim().equals("DL")) {
                return DL;
            } else if (string.trim().equals("IS")) {
                return IS;
            } else {
                throw new IllegalStateException("Wrong type of STATEMENT declared in the OPTABLE File.");
            }
        }
    }
    private String opCode;
    private StatementType typeOfStatement;
    private String machineCode;
    private int length = -1;
    private String routinToBeCalled = "-";
    private static ArrayList<OPTABLE> instance;
    private static final String pathOfOptableFile = "files/OPTABLE.txt";

    /**
     * Creation of the Arraylist of the OPTABLE enctries. It is must that there
     * is no extra \t or space or \n in the TEXT file in which the OPTABLE is
     * declared. It will throw the Exception. If so then correct it.
     */
    static {
        instance = new ArrayList<>();
        String opcodeFileContent = new String(FileHandler.readFile(pathOfOptableFile));
        StringTokenizer tokenizer = new StringTokenizer(opcodeFileContent, "\n");
        while (tokenizer.hasMoreTokens()) {
            String oneEntry = tokenizer.nextToken();
            String[] columnsEntry = oneEntry.split("\t");
            if (columnsEntry.length == 3) {
                insertOptableEntry(columnsEntry[0].trim(), StatementType.valueOf(columnsEntry[1].trim()), columnsEntry[2].trim(), 0);
            } else {
                // if the last operand is INTEGER then add it as length or else 
                int length;
                try {
                    length = Integer.parseInt(columnsEntry[3].trim());
                    instance.add(new OPTABLE(columnsEntry[0].trim(), StatementType.valueOf(columnsEntry[1].trim()), columnsEntry[2].trim(), length));
                } catch (Exception e) {
                    // If exception occurs then it indicates the ROUTINE Address to be called. So add it as ...
                    instance.add(new OPTABLE(columnsEntry[0].trim(), StatementType.valueOf(columnsEntry[1].trim()), columnsEntry[2].trim(), columnsEntry[3].trim()));
                }

            }
        }

    }

    /**
     * For Testing of some code.
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("ANS: " + StatementType.valueOf("AD"));
        printOptable();
    }

    private OPTABLE(String opcode, StatementType typeOfStatement, String machineCode, int length) {
        this.opCode = opcode;
        this.typeOfStatement = typeOfStatement;
        this.machineCode = machineCode;
        this.length = length;
    }

    private OPTABLE(String opcode, StatementType typeOfStatement, String machineCode, String routineToBeCalled) {
        this.opCode = opcode;
        this.typeOfStatement = typeOfStatement;
        this.machineCode = machineCode;
        this.routinToBeCalled = routineToBeCalled;
    }

    public static boolean insertOptableEntry(String opcode, StatementType typeOfStatement, String machineCode, int length) {
        OPTABLE optableEntry = new OPTABLE(opcode, typeOfStatement, machineCode, length);
        return instance.add(optableEntry);
    }

    public static OPTABLE getOptableEntry(String opcode) {
        for (Iterator<OPTABLE> it = instance.iterator(); it.hasNext();) {
            OPTABLE optableObj = it.next();
            if (optableObj.getOpcode().equals(opcode)) {
                return optableObj;
            }
        }
        throw new IllegalStateException("Symbol is not Recognized.... Check the Input File Code: " + opcode);
    }

    public static boolean isEntryExistInOptable(String opcodeToCheck) {
        for (Iterator<OPTABLE> it = instance.iterator(); it.hasNext();) {
            OPTABLE optableObj = it.next();
            if (optableObj.getOpcode().equals(opcodeToCheck)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the type of the Statement. i.e. one of AD, IS or DL
     *
     * @return - ENUM of the type of the statement.
     */
    public StatementType getTypeOfStatement() {
        return typeOfStatement;
    }

    public String getOpcode() {
        return opCode;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public int getLength() {
        return length;
    }

    public static ArrayList<OPTABLE> getInstance() {
        return instance;
    }

    public static void printOptable() {
        for (Iterator<OPTABLE> it = instance.iterator(); it.hasNext();) {
            System.out.println(it.next());
        }
    }

    @Override
    public String toString() {
        return "OPTAB: " + opCode + "|" + typeOfStatement + "|" + machineCode + "|" + length + "|" + routinToBeCalled;
    }
}
