/*
 * Implemented as Tutorial of Masters Program 
 * M.E. - Computer Engineering 
 * Design of Language Processors
 * SCET, Surat
 */
package scet.vintesh.dlp.slr.ds;

import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author Vintesh
 */
public class ParseLog {

    private String stackContent;
    private String inputString;
    private String actionPerformed;
    private static ArrayList<ParseLog> logList = new ArrayList<>();

    public ParseLog(String stackContent, String inputString, String actionPerformed) {
        this.stackContent = stackContent;
        this.inputString = inputString;
        this.actionPerformed = actionPerformed;
        logList.add(this);
    }

    public static void printLog() {
        System.out.println(String.format("%-30s | %-30s | %-30s |", "STACK", "INPUT STRING", "ACTION"));
        for (ParseLog parseLog : logList) {
            System.out.println(parseLog);
        }
    }

    @Override
    public String toString() {
        return String.format("%-30s | %-30s | %-30s |", stackContent, inputString, actionPerformed);
    }

    /**
     * Tester
     */
    public static void main(String[] args) {
        System.out.println(new ParseLog("hi", "vintesh", "thr?"));
        printLog();
    }
}
