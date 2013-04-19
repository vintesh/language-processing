/*
 * Implemented as Tutorial of Masters Program 
 * M.E. - Computer Engineering 
 * Design of Language Processors
 * SCET, Surat
 */
package scet.vintesh.dlp.util;

import java.io.*;

/**
 *
 * @author Vintesh
 */
public class FileHandler {

    private static char[] bufferContent = new char[5000];

    public static char[] readFile(String fileName) {
        try {
            FileReader fr = new FileReader(new File(fileName));
            int inputContentSize = fr.read(bufferContent);
            fr.close();
            char[] inputBufferContent = new char[inputContentSize];
            System.arraycopy(bufferContent, 0, inputBufferContent, 0, inputContentSize);
            //System.out.println("Content Read: " + String.valueOf(inputBufferContent));
            return inputBufferContent;
        } catch (Exception ex) {
            System.out.println("Exception in FileReading.");
        }
        return null;
    }

    public static void writeFile(String fileName, char[] data) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(new File(fileName));
            fw.write(data);
            fw.close();
        } catch (IOException ex) {
            System.out.println("File Write Exception.");
        }
    }
}
