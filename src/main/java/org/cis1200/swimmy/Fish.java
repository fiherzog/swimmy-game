package org.cis1200.swimmy;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class Fish {
    private int x, y;
    private int width, height;
    private double yVel;
    private double gravity;
    private boolean dead;
    private static final String IMG_FILE = "files/koi_fish.png";
    private static BufferedImage img;

    // Constructing the fish
    public Fish(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        gravity = 0.7;
        yVel = 0;

        try {
            if (img == null) {
                img = ImageIO.read(new File(IMG_FILE));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
    }

    // Drawing the fish!
    public void draw(Graphics g) {
        g.drawImage(img, x, y, width, height, null);
    }

    // Updating the position of the fish every time the up key is pressed
    public void update() {
        yVel += gravity;
        y += (int) yVel;
    }

    // Getters
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getW() {
        return width;
    }
    public int getH() {
        return height;
    }

    // Setters
    public void setGravity() {
        yVel -= 15;
    }
    public void setXpos(int xPos) {
        x = xPos;
    }
    public void setYpos(int yPos) {
        y = yPos;
    }

}
