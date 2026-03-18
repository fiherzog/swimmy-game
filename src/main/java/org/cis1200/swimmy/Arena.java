package org.cis1200.swimmy;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Arena extends JPanel {
    private final JLabel status;
    private Fish fish;
    private ArrayList<Obstacle> obstacles;
    public static final int COURT_WIDTH = 600;
    public static final int COURT_HEIGHT = 700;
    public static final int INTERVAL = 35;

    // Game States
    private boolean playing = false;
    private boolean paused = false;
    private boolean start = true;
    private boolean gameOver = false;
    private double points = 0.0;
    private double highScore = 0.0;

    // Images of the game
    private static BufferedImage img, pausedImg, introImg;
    private static final String IMG_FILE = "files/ocean.png";
    private static final String PAUSED_FILE = "files/paused.png";
    private static final String INTRO_FILE = "files/intro.png";
    private static final String FILE_PATH = "./files/data.txt";


    public Arena(JLabel status) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setFocusable(true);

        // Loading in my Images
        loadImage(IMG_FILE);
        loadImage(PAUSED_FILE);
        loadImage(INTRO_FILE);

        // create timers to continuously run game
        Timer pipeDelay = new Timer(4000, e -> addPipes());
        pipeDelay.start();
        Timer hookDelay = new Timer(7000, e -> addHook());
        hookDelay.start();
        Timer timer = new Timer(INTERVAL, e -> tick());
        timer.start();

        // when different keys are pressed, account for different states
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                // When the user presses up, the fish should move up
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    if (!gameOver) {
                        playing = true;
                        start = false;
                        fish.setGravity();
                    }
                }
                // When the user presses the space button, the game should pause
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    paused = !paused;
                }
                // When the user presses the quit button, game state needs to be saved
                if (e.getKeyCode() == KeyEvent.VK_Q) {
                    saveGameData();
                    System.exit(0);
                }
                // If the user wants to continue playing, we need to reload in the
                // game that they left off at.
                if (e.getKeyCode() == KeyEvent.VK_C) {
                    loadGameData();
                    start = false;
                    playing = true;
                }
            }
        });
        this.obstacles = new ArrayList<Obstacle>();
        this.status = status;
    }

    // Load image helper method
    private void loadImage(String filename) {
        try {
            BufferedImage image = ImageIO.read(new File(filename));
            if (filename.equals(IMG_FILE)) {
                img = image;
            } else if (filename.equals(PAUSED_FILE)) {
                pausedImg = image;
            } else if (filename.equals(INTRO_FILE)) {
                introImg = image;
            }
        } catch (IOException e) {
            System.err.println("Error loading image: " + filename);
        }
    }

    // Save data helper method
    public void saveGameData() {
        List<String> data = new LinkedList<>();
        data.add("" + points);
        data.add("" + highScore);
        data.add("" + fish.getY());
        if (obstacles.isEmpty()) {
            data.add("NO_OBSTACLES");
        } else {
            // if obstacles exist, save data
            for (Obstacle obstacle : obstacles) {
                String obstacleType = obstacle.getType();
                String pipeType;
                if ("Pipe".equals(obstacleType)) {
                    Pipe pipe = (Pipe) obstacle;
                    if (pipe.isNorthPipe()) {
                        pipeType = "NorthPipe";
                    } else {
                        pipeType = "SouthPipe";
                    }
                    data.add(pipeType + ":" + pipe.getX() + "," + pipe.getY());
                } else if ("Pendulum".equals(obstacleType)) {
                    data.add("Pendulum:" + obstacle.getX() + "," + obstacle.getY());
                }
            }
        }
        FileUtilities.writeStringsToFile(data, FILE_PATH, false);
    }

    // Load the Game data
    public void loadGameData() {
        LineIterator read = new LineIterator(FILE_PATH);
        if (read.hasNext()) {
            points = Double.valueOf(read.next());
            highScore = Double.valueOf(read.next());
            fish.setYpos(Integer.parseInt(read.next()));
        }
        obstacles.clear();
        while (read.hasNext()) {
            List<String> parts = LineIterator.parseRecord(read.next());
            String type = parts.get(0);
            int x = Integer.parseInt(parts.get(1));
            int y = Integer.parseInt(parts.get(2));

            // Add obstacle based on type
            if (type.equals("NorthPipe")) {
                obstacles.add(new Pipe(x, y, 70, 500, "north"));
            } else if (type.equals("SouthPipe")) {
                obstacles.add(new Pipe(x, y, 70, 500, "south"));
            } else if (type.equals("Pendulum")) {
                obstacles.add(new Pendulum(x, y, -8));
            }
        }
    }


    // Resetting the game state
    public void reset() {
        playing = false;
        start = true;
        paused = false;
        gameOver = false;
        obstacles.clear();
        status.setText("Running... :) ");
        JLabel status = new JLabel("Running... :)");
        Arena court = new Arena(status);
        fish = new Fish(180, 400, 100, 70);
        Pipe pipeN = new Pipe(500, -200,70, 500, "north");
        Pipe pipeS = new Pipe(500, 550, 70, 500, "south");
        obstacles.add(pipeN);
        obstacles.add(pipeS);
        points = 0;
        // Updating the highScore from previous plays
        LineIterator read = new LineIterator(FILE_PATH);
        if (read.hasNext()) {
            String pointsIgnore = read.next();
            highScore = Double.valueOf(read.next());
        }
        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }

    // Add the correct points
    public void addPoints(Fish f, Pipe p) {
        if (f.getX() == p.getX()) {
            points += 0.5;
        }
    }

    // Check for collisions
    public boolean collision(Fish f, Pipe p) {
        return f.getX() < p.getX() + p.getW() - 15
                && f.getX() + f.getW() - 15 > p.getX()
                && f.getY() < p.getY() + p.getH() - 15
                && f.getY() + f.getH() - 15 > p.getY();
    }

    public boolean pendulumCollision(Fish f, Pendulum p) {
        int fXcenter = f.getX();
        int fYcenter = f.getY();

        int pXcenter = -15 + p.getX();
        int pYcenter = -18 + p.getY() + (int) (250 * Math.cos(Math.PI / 4));
        int xDistance = Math.abs(fXcenter - pXcenter);
        int yDistance = Math.abs(fYcenter - pYcenter);
        return xDistance <= (f.getW() / 2 + 30) && yDistance <= (f.getH() / 2 + 30);
    }

    // While playing, ensure that the coral is moving and gravity in place
    void tick() {
        if (playing && !paused) {
            // ensuring the fish experiences gravity
            fish.update();
            // is there a way to do this with dynamic dispatch
            ArrayList<Obstacle> obstaclesToRemove = new ArrayList<>();
            // Iterating through the pipes to updating their position,
            // updating the number of points, delete the necessary pipes
            // and checking for collisions.
            for (Obstacle obstacle : obstacles) {
                obstacle.update();
                if (obstacle.getX() + obstacle.getW() < 0) {
                    obstaclesToRemove.add(obstacle);
                }
                if (fish.getY() > COURT_WIDTH + 50 ||
                        fish.getY() < 0 || obstacle.getType().equals("Pendulum")
                        && pendulumCollision(fish, (Pendulum) obstacle)) {
                    gameOver = true;
                    List<String> data = new LinkedList<>();
                    data.add("" + 0.0);
                    data.add("" + highScore);
                    FileUtilities.writeStringsToFile(data, FILE_PATH, false);
                    playing = false;
                    status.setText("You Died :( ");
                    break;
                }
                if (obstacle.getType().equals("Pipe") && collision(fish, (Pipe) obstacle)) {
                    gameOver = true;
                    List<String> data = new LinkedList<>();
                    data.add("" + 0.0);
                    data.add("" + highScore);
                    FileUtilities.writeStringsToFile(data, FILE_PATH, false);
                    playing = false;
                    status.setText("You Died :( ");
                    break;
                }

                // Allow the user to get points
                if (obstacle.getType().equals("Pipe")) {
                    Pipe pipe = (Pipe) obstacle;
                    if (fish.getX() == pipe.getX()) {
                        points += 0.5;
                    }
                }

            }
            obstacles.removeAll(obstaclesToRemove);
        }
        repaint();
    }

    // Add an obstacle to the list
    public void addObstacle(Obstacle obstacle) {
        obstacles.add(obstacle);
    }

    // After the time specified, be sure to add in pipes
    void addPipes() {
        if (playing && !paused) {
            Pipe pipeN = new Pipe(700, 0,70, 500, "north");
            Pipe pipeS = new Pipe(700, 400, 70, 500, "south");
            int nY = (int) (pipeN.getY() - pipeN.getH() / 4 - Math.random() * pipeN.getH() / 2);
            pipeN.setY(nY);
            int opening = COURT_HEIGHT / 4;
            int sY = nY + pipeS.getH() + opening;
            pipeS.setY(sY);
            addObstacle(pipeN);
            addObstacle(pipeS);
            repaint();
        }
    }
    // After time specified, add in hooks
    void addHook() {
        if (playing && !paused) {
            Pendulum pendulum = new Pendulum(600, 0, -8);
            addObstacle(pendulum);
            repaint();
        }
    }

    // Painting all the components necessary on screen
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // drawing the background, fish and obstacles
        g.drawImage(img, 0, 0, COURT_WIDTH, COURT_HEIGHT, null);
        fish.draw(g);
        for (Obstacle obstacle : obstacles) {
            obstacle.draw(g);
        }
        g.setColor(Color.BLUE);
        g.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 25));
        // Drawing different images according to the different game states
        if (paused) {
            g.drawImage(pausedImg, 130, 155, 330, 430, null);
        }
        if (start) {
            g.drawImage(introImg, 130, 155, 350, 450, null);
        }
        if (!playing && !start) {
            g.drawString("Game Over :( ", COURT_WIDTH / 2 - 70, COURT_HEIGHT / 2);
            g.drawString("Points Scored: " + String.valueOf((int) points),
                    COURT_WIDTH / 2 - 100, COURT_HEIGHT / 2 + 30);
        } else {
            if (points > highScore) {
                highScore = points;
            }
            g.drawString("Score:" + String.valueOf((int) points), 10, 35);
            g.drawString("High score:" + String.valueOf((int) highScore), 10, 65);
        }
    }

    // Methods for Unit Testing
    public double getPoints() {
        return points;
    }

    public ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }

    public double getHighScore() {
        return highScore;
    }

    public boolean isPlaying() {
        return playing;
    }

    public boolean isPaused() {
        return paused;
    }

    public boolean isStart() {
        return start;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    // Setting the dimensions of my game
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
}
