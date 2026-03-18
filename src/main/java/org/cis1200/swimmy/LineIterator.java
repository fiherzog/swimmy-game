package org.cis1200.swimmy;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class LineIterator implements Iterator<String> {
    private final BufferedReader read;
    private String line;
    private final static char DOUBLE_QUOTES = '"';
    private final static char COMMA = ',';
    private final static char COLON = ':';

    /**
     * Constructs a {@code LineIterator} for reader.
     * <p>
     * If an IOException is thrown by the BufferedReader, then hasNext should
     * return false.
     * <p>
     *
     * @param reader - A reader to be turned to an Iterator
     * @throws IllegalArgumentException if reader is null
     */
    public LineIterator(BufferedReader reader) {
        if (reader == null) {
            throw new IllegalArgumentException();
        }
        this.read = reader;
        try {
            this.line = read.readLine();
        } catch (IOException e) {
            this.line = null;
            throw new IllegalArgumentException();
        }
    }

    /**
     * Creates a LineIterator from a provided filePath by creating a
     * FileReader and BufferedReader for the file.
     * <p>
     *
     * @param filePath - a string representing the file
     * @throws IllegalArgumentException if filePath is null or if the file
     *                                  doesn't exist
     */
    public LineIterator(String filePath) {
        this(FileUtilities.fileToReader(filePath));
    }

    /**
     * Returns true if there are lines left to read in the file, and false
     * otherwise.
     * <p>
     * If there are no more lines left, this method attempts to close the
     * BufferedReader. In case of an IOException during the closing process,
     * an error message is printed to the console indicating the issue.
     *
     * @return a boolean indicating whether the LineIterator can produce
     *         another line from the file
     */
    @Override
    public boolean hasNext() {
        return (line != null);
    }

    /**
     * Returns the next line from the file, or throws a NoSuchElementException
     * if there are no more strings left to return (i.e. hasNext() is false).
     * <p>
     * This method also advances the iterator in preparation for another
     * invocation. If an IOException is thrown during a next() call, your
     * iterator should make note of this such that future calls of hasNext()
     * will return false and future calls of next() will throw a
     * NoSuchElementException
     *
     * @return the next line in the file
     * @throws java.util.NoSuchElementException if there is no more data in the
     *                                          file
     */
    @Override
    public String next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        String s = line;
        try {
            line = read.readLine();
        } catch (IOException ex) {
            line = null;
        }
        return s;
    }

    /**
     * A method to read through the line that the Line Iterator gives
     * and separates the line according to the commas.
     * @param dataLine - the string representing the entire line read by the
     * Line Iterator
     * @return The sequence of fields of the record as a list of
     * {@code String}s.
     *
     */
    public static List<String> parseRecord(String dataLine) {
        List<String> line = new LinkedList<>();
        char[] ch = dataLine.toCharArray();
        StringBuilder builder = new StringBuilder();
        boolean quotationMode = false;
        for (char c : ch) {
            if (c == DOUBLE_QUOTES) {
                quotationMode = !quotationMode;
            } else {
                if (quotationMode) {
                    builder.append(c);
                } else if (!quotationMode && c == COMMA || c == COLON) {
                    line.add(String.valueOf(builder));
                    builder = new StringBuilder();
                } else {
                    builder.append(c);
                }
            }
        }
        line.add(String.valueOf(builder));
        return line;
    }
}
