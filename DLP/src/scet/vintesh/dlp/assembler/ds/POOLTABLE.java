/*
 * Implemented as Tutorial of Masters Program 
 * M.E. - Computer Engineering 
 * Digital Language Processing
 * SCET, Surat
 */
package scet.vintesh.dlp.assembler.ds;

import java.util.ArrayList;

/**
 *
 * @author Vintesh
 */
public class POOLTABLE {

    private static ArrayList<LITTABLE> pool = new ArrayList<>();

    public static boolean addEntry(LITTABLE littableEntry) {
        return pool.add(littableEntry);
    }

    public static void printPooltable() {
        for (LITTABLE littableEntry : pool) {
            System.out.println(littableEntry);
        }
    }
}
