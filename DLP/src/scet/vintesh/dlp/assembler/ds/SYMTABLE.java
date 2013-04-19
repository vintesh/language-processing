/*
 * Implemented as Tutorial of Masters Program 
 * M.E. - Computer Engineering 
 * Design of Language Processors
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
public class SYMTABLE {

    private int entryNo;
    private String symbol;
    private int address;
    private int length;
    private static int index = 1;
    private static ArrayList<SYMTABLE> instance = new ArrayList<>();

    private SYMTABLE(String symbol, int address, int length) {
        this.symbol = symbol;
        this.address = address;
        this.length = length;
        this.entryNo = index++;
    }

    public int getAddress() {
        return address;
    }

    public int getLength() {
        return length;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getEntryNo() {
        return entryNo;
    }

    public void setAddress(int locationCounter) {
        this.address = locationCounter;
    }

    public static SYMTABLE getEntryByEntryNo(int entryNo) {
        for (Iterator<SYMTABLE> it = instance.iterator(); it.hasNext();) {
            SYMTABLE symtable = it.next();
            if (symtable.getEntryNo() == entryNo) {
                return symtable;
            }
        }
        throw new IllegalStateException("No Symbol Entry Found in the Symbol Table");
    }

    public static boolean addEntryToSymbolTable(String symbol, int length) {
        // -1 if no address is identified. i.e. Handling of the FORWARD REFERENCE
        return addEntryToSymbolTable(symbol.trim(), -1, length);
    }

    public static boolean addEntryToSymbolTable(String symbol, int address, int length) {
        return instance.add(new SYMTABLE(symbol.trim(), address, length));
    }

    public static boolean isEntryExistInSymbolTable(String string) {
        for (Iterator<SYMTABLE> it = instance.iterator(); it.hasNext();) {
            SYMTABLE symtab = it.next();
            if (symtab.getSymbol().equals(string.trim())) {
                return true;
            }
        }
        return false;
    }

    public static void setAddress(String label, int locationCounter) {
        for (Iterator<SYMTABLE> it = instance.iterator(); it.hasNext();) {
            SYMTABLE symtab = it.next();
            if (symtab.getSymbol().equals(label)) {
                symtab.setAddress(locationCounter);
            }
        }
    }

    public static SYMTABLE getSymbolEntry(String string) {
        for (Iterator<SYMTABLE> it = instance.iterator(); it.hasNext();) {
            SYMTABLE symtabObj = it.next();
            if (symtabObj.getSymbol().equals(string.trim())) {
                return symtabObj;
            }
        }
        return null;

    }

    public static void assignAddressesToLiterals() {
        for (Iterator<SYMTABLE> it = instance.iterator(); it.hasNext();) {
            SYMTABLE entry = it.next();
            if (!entry.isEntryAddressSet()) {
                entry.address = PassOneEntryPoint.getLocationCounter();
                PassOneEntryPoint.setLocationCounter(PassOneEntryPoint.getLocationCounter() + 1);
            }
        }
    }

    public static ArrayList<SYMTABLE> getInstance() {
        return instance;
    }

    public static void printSymbolTable() {
        for (Iterator<SYMTABLE> it = instance.iterator(); it.hasNext();) {
            System.out.println(it.next());
        }
    }

    @Override
    public String toString() {
        return "SYMTAB: " + entryNo + "|" + symbol + "|" + address + "|" + length;
    }

    private boolean isEntryAddressSet() {
        if (this.address == -1) {
            return false;
        } else {
            return true;
        }
    }
}
