/*
 * Implemented as Tutorial of Masters Program 
 * M.E. - Computer Engineering 
 * Digital Language Processing
 * SCET, Surat
 */
package scet.vintesh.dlp.assembler.ds;

/**
 *
 * @author Vintesh
 */
public class RoutineCollections {

    public static void processRoutineCall(String routineID) {
        switch (routineID) {
            case "R1":
                Routine1();
                break;
            default:
                System.out.println("No Routine Is Found. Correct the CODE.");
        }
    }
    // For the instruction START

    private static void Routine1() {
    }
}
