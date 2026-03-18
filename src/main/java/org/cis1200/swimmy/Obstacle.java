package org.cis1200.swimmy;
import java.awt.*;

public abstract class Obstacle {
    private int x, y;
    private int width, height;
    private int speed;
    private String type;
    public Obstacle(int x, int y, int width, int height, int speed, String type) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.type = type;
    }

    // Getters
    public int getX() {
        return x;
    }

    public int getW() {
        return width;
    }
    public int getY() {
        return y;
    }
    public int getH() {
        return height;
    }
    public int getSpeed() {
        return speed;
    }
    public String getType() {
        return type;
    }
    // Setters
    public void setY(int y) {
        this.y = y;
    }
    public void setX(int x) {
        this.x = x;
    }

    // Updating the speed to mimic the fish swimming
    public void update() {
        x -= speed;
    }
    public abstract void draw(Graphics g);

}
