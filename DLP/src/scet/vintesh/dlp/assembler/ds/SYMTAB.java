/*
 * Implemented as Tutorial of Masters Program 
 * M.E. - Computer Engineering 
 * Digital Language Processing
 * SCET, Surat
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scet.vintesh.dlp.assembler.ds;

import java.util.ArrayList;

/**
 *
 * @author Vintesh
 */
public class SYMTAB {

    private String symbol;
    private int address;
    private int length;
    private static ArrayList<SYMTAB> instance = new ArrayList<>();

    private SYMTAB(String symbol, int address, int length) {
        this.symbol = symbol;
        this.address = address;
        this.length = length;
    }

    public static boolean addEntryToSymbolTable(String symbol, int length) {
        // -1 if no address is identified. i.e. Handling of the FORWARD REFERENCE
        return addEntryToSymbolTable(symbol, -1, length);
    }

    private static boolean addEntryToSymbolTable(String symbol, int address, int length) {
        return instance.add(new SYMTAB(symbol, address, length));
    }
}
