/*
 * Implemented as Tutorial of Masters Program 
 * M.E. - Computer Engineering 
 * Digital Language Processing
 * SCET, Surat
 */
package scet.vintesh.dlp.assembler.ds;

import java.util.ArrayList;
import java.util.Iterator;
import scet.vintesh.dlp.assembler.PassOneEntryPoint;

/**
 *
 * @author Vintesh
 */
public class LITTABLE {

    private String literal;
    private int address;
    private static ArrayList<LITTABLE> instance = new ArrayList<>();

    public LITTABLE(String literal) {
        this.literal = literal;
        this.address = -1;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public int getAddress() {
        return address;
    }

    /**
     * Adds entry in the table viz list here in the class.
     *
     * @param literal - instance of the LITTAB = one entry of the table
     */
    public static void addEntry(String literal) {
        instance.add(new LITTABLE(literal));
    }

    public static void printLittable() {
        for (Iterator<LITTABLE> it = instance.iterator(); it.hasNext();) {
            System.out.println(it.next());
        }
    }

    /**
     * Assigns Addresses to the remaining Literals in the list. It also updates
     * the Pointer of POOLTAB.
     */
    public static void assignAddressesToLiterals() {
        // For Assigning the pointerEntry in the POOLTAB
        boolean isFirstEntryToAssignEntry = false;
        for (Iterator<LITTABLE> it = instance.iterator(); it.hasNext();) {
            LITTABLE entry = it.next();
            if (!entry.isEntrySet()) {
                // Making the POOLTAB entry By Giving pointer of the Entry of LITTAB
                if (!isFirstEntryToAssignEntry) {
                    POOLTABLE.addEntry(entry);
                    isFirstEntryToAssignEntry = true;
                }
                entry.address = PassOneEntryPoint.getLocationCounter();
                PassOneEntryPoint.setLocationCounter(PassOneEntryPoint.getLocationCounter() + 1);
            }
        }
    }

    /**
     * Checks whether Address is available in the entry. i.e. Checks whether
     * memory is allocated to the literal or not?
     *
     * @return - true if memory is allocated to the LITRAL or else false
     */
    private boolean isEntrySet() {
        return this.address == -1 ? false : true;
    }

    @Override
    public String toString() {
        return "LITTAB: " + literal + "|" + address;
    }
}
