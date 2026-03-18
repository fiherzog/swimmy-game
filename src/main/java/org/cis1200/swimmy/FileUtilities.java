package org.cis1200.swimmy;

import java.io.*;
import java.nio.file.Paths;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class FileUtilities {
    /**
     * Takes in a filename and creates a BufferedReader.
     *
     * @param filePath the path to the data file to be turned to a
     *                 BufferedReader
     * @return a BufferedReader of the provided file contents
     * @throws IllegalArgumentException if filePath is null or if the file
     *                                  doesn't exist
     */
    public static BufferedReader fileToReader(String filePath) {
        if (filePath == null) {
            throw new IllegalArgumentException();
        }
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filePath));
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
        return reader;
    }

    /**
     * Given a {@code List} of {@code String}s, writes them to a file (one
     * {@code String} per
     * line in the file). This method uses {@code BufferedWriter}, the flip side to
     * {@code BufferedReader}. It may be useful to look at the JavaDocs for
     * {@code FileWriter}.
     * <p>
     * You may assume none of the arguments or strings passed in will be null.
     * <p>
     * If the process of opening the file or writing the data triggers an
     * {@code IOException}, it
     * should catch it and stop writing.
     *
     * @param stringsToWrite A List of Strings to write to the file
     * @param filePath       the string containing the path to the file where
     *                       the data should be written
     * @param append         a boolean indicating whether the data
     *                       should be appended to the current file or should
     *                       overwrite its previous contents
     */
    public static void writeStringsToFile(
            List<String> stringsToWrite, String filePath,
            boolean append
    ) {
        File file = Paths.get(filePath).toFile();
        BufferedWriter bw;
        FileWriter fw;
        try {
            fw = new FileWriter(file, append);
            bw = new BufferedWriter(fw);
            for (String i : stringsToWrite) {
                bw.write(i + "\n");
            }
            bw.close();
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }
}
