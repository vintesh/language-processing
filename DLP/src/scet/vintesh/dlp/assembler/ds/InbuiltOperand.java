/*
 * Implemented as Tutorial of Masters Program 
 * M.E. - Computer Engineering 
 * Design of Language Processors
 * SCET, Surat
 */
package scet.vintesh.dlp.assembler.ds;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Vintesh
 */
public class InbuiltOperand {

    public static Map<String, String> map = new HashMap<>();

    static {
        // FOR THE REGISTERS
        map.put("AREG", "01");
        map.put("BREG", "02");
        map.put("CREG", "03");
        map.put("DREG", "04");
        // For the CONDITIONS
        map.put("LT", "01");
        map.put("LE", "02");
        map.put("EQ", "03");
        map.put("GT", "04");
        map.put("GE", "05");
        map.put("ANY", "06");
    }

    public static boolean isInbuiltOperand(String oprandToCheck) {
        for (String string : InbuiltOperand.map.keySet()) {
            if (string.equals(oprandToCheck)) {
                return true;
            }
        }
        return false;
    }
}
