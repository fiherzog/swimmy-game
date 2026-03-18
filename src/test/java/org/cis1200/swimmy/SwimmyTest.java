package org.cis1200.swimmy;
import java.io.*;
import java.util.List;
import java.util.LinkedList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;
import java.time.Duration;

public class SwimmyTest {
    // Used to test FileUtilities
    final String stringsToFileTest = "./files/strings_to_file.txt";

    // Test whether Arena is initalized correctly
    @Test
    public void testArena() {
        JLabel status = new JLabel("Running... :)");
        Arena court = new Arena(status);
        assertNotNull(court);
        assertEquals(0.0, court.getPoints(), 0.001);
        assertEquals(0.0, court.getHighScore(), 0.001);
        assertFalse(court.isPlaying());
        assertFalse(court.isPaused());
        assertTrue(court.isStart());
        assertFalse(court.isGameOver());
        assertNotNull(court.getObstacles());
        assertEquals(0, court.getObstacles().size());
    }

    // Testing whether obstacles properly added
    @Test
    public void testAddObstacle() {
        JLabel status = new JLabel("Running... :)");
        Arena court = new Arena(status);
        Obstacle obstacle = new Pipe(100, 200, 70, 500, "north");
        court.addObstacle(obstacle);
        List<Obstacle> obstacles = court.getObstacles();
        assertEquals(1, obstacles.size());
        assertTrue(obstacles.contains(obstacle));
    }

    // Testing whether collisions work
    @Test
    public void detectCollision() {
        JLabel status = new JLabel("Running... :)");
        Arena court = new Arena(status);
        Pipe pipeN = new Pipe(10, 10,23, 28, "north");
        Fish fish = new Fish(10, 10, 23, 28);
        assertTrue(court.collision(fish, pipeN));
    }

    @Test
    public void hookCollision() {
        JLabel status = new JLabel("Running... :)");
        Arena court = new Arena(status);
        Fish fish = new Fish(100, 250, 50, 50);
        Pendulum hook = new Pendulum(100, 100, -3);
        assertTrue(court.pendulumCollision(fish, hook));
    }

    // Testing whether pipes and hooks are removed accordingly when off the game screen
    @Test
    public void pipeRemoved() {
        JLabel status = new JLabel("Running... :)");
        Arena court = new Arena(status);
        Pipe pipeN = new Pipe(-3, 10,23, 28, "north");
        assertFalse(court.getObstacles().contains(pipeN));
    }

    @Test
    public void hookRemoved() {
        JLabel status = new JLabel("Running... :)");
        Arena court = new Arena(status);
        Pendulum hook = new Pendulum(-20, 10,-3);
        assertFalse(court.getObstacles().contains(hook));
    }

    // Testing whether points are awarded accordingly
    @Test
    public void pointsIncrease() {
        JLabel status = new JLabel("Running... :)");
        Arena court = new Arena(status);
        Fish fish = new Fish(10, 10, 23, 28);
        Pipe pipeN = new Pipe(10, 10,23, 28, "north");
        court.addPoints(fish, pipeN);
        assertEquals(0.5, court.getPoints());
    }


    // Testing the File Utilities Class
    @Test
    public void testFileToReaderFilePathNull() {
        assertTimeoutPreemptively(Duration.ofSeconds(10), () -> {
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                FileUtilities.fileToReader(null);
            });
        });
    }

    @Test
    public void testFileToReaderInvalidFilePath() {
        assertTimeoutPreemptively(Duration.ofSeconds(10), () -> {
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                FileUtilities.fileToReader("invalid");
            });
        });
    }

    @Test
    public void writeStringsToFileNoAppendTest() {
        assertTimeoutPreemptively(Duration.ofSeconds(10), () -> {
            BufferedWriter bw = new BufferedWriter(
                    new FileWriter(stringsToFileTest, false)
            );
            bw.write("og line here");
            bw.close();

            List<String> strings = new LinkedList<>();
            strings.add("Line1");
            strings.add("Line2");
            strings.add("Line3");
            FileUtilities.writeStringsToFile(strings, stringsToFileTest, false);

            try (BufferedReader br = new BufferedReader(new FileReader(stringsToFileTest))) {
                assertEquals("Line1", br.readLine());
                assertEquals("Line2", br.readLine());
                assertEquals("Line3", br.readLine());
            }
        });
    }

    @Test
    public void writeStringsToFileAppend() {
        assertTimeoutPreemptively(Duration.ofSeconds(10), () -> {
            BufferedWriter bw = new BufferedWriter(
                    new FileWriter(stringsToFileTest, false)
            );
            bw.write("og line here\n");
            bw.close();

            List<String> strings = new LinkedList<>();
            strings.add("Line1");
            strings.add("Line2");
            strings.add("Line3");
            FileUtilities.writeStringsToFile(strings, stringsToFileTest, true);

            try (BufferedReader br = new BufferedReader(new FileReader(stringsToFileTest))) {
                assertEquals("og line here", br.readLine());
                assertEquals("Line1", br.readLine());
                assertEquals("Line2", br.readLine());
                assertEquals("Line3", br.readLine());
            }
        });
    }

    // Testing Line Iterator Class
    @Test
    public void testHasNextAndNext() {
        String words = "0, The end should come here.\n"
                + "1, This comes from data with no duplicate words!";
        StringReader sr = new StringReader(words);
        BufferedReader br = new BufferedReader(sr);
        LineIterator li = new LineIterator(br);
        assertTrue(li.hasNext());
        assertEquals("0, The end should come here.", li.next());
        assertTrue(li.hasNext());
        assertEquals("1, This comes from data with no duplicate words!", li.next());
        assertFalse(li.hasNext());
    }

    @Test
    public void testOneSentence() {
        String words = "0. The end should come here.";
        StringReader sr = new StringReader(words);
        BufferedReader br = new BufferedReader(sr);
        LineIterator li = new LineIterator(br);
        assertTrue(li.hasNext());
        assertEquals("0. The end should come here.", li.next());
        assertFalse(li.hasNext());
    }

    @Test
    public void testDuplicateWords() {
        String words = "0, The end should come here.\n"
                + "here. 0, The end should come here.";
        StringReader sr = new StringReader(words);
        BufferedReader br = new BufferedReader(sr);
        LineIterator li = new LineIterator(br);
        assertTrue(li.hasNext());
        assertEquals("0, The end should come here.", li.next());
        assertTrue(li.hasNext());
        assertEquals("here. 0, The end should come here.", li.next());
        assertFalse(li.hasNext());
    }

    @Test
    public void testEmptyFile() {
        LineIterator li = new LineIterator("files/empty.txt");
        assertFalse(li.hasNext());
    }

    @Test
    public void testNullFilePath() {
        String filePath = null;
        assertThrows(IllegalArgumentException.class, () -> {
            new LineIterator(filePath);
        });
    }

    @Test
    public void nonExistentFile() {
        String filePath = "notReal.txt";
        assertThrows(IllegalArgumentException.class, () -> {
            new LineIterator(filePath);
        });
    }

    @Test
    public void testNullReader() {
        String words = "0. The end should come here.";
        StringReader sr = new StringReader(words);
        BufferedReader br = null;
        assertThrows(IllegalArgumentException.class, () -> {
            new LineIterator(br);
        });
    }


}
