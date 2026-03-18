package org.cis1200.swimmy;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Pendulum extends Obstacle {
    private static final int WIDTH = 800;
    private static final double MIN_AMPLITUDE = 0.2;
    private static final double MAX_AMPLITUDE = 0.5;
    private static final int ROD_LENGTH = 250;
    private static final int BALL_RADIUS = 60;
    private static final int LINE_THICKNESS = 3;
    private static final double GRAVITY = 9.81;

    private double angle = Math.PI / 4;
    private double angleVelocity = 0.0;
    private double angleAcceleration = 0.0;
    private static final String IMG_FILE = "files/ball.png";
    private static BufferedImage img;

    public Pendulum(int x, int y, int speed) {
        super(x, y, 0, 0, speed, "Pendulum");
        try {
            if (img == null) {
                img = ImageIO.read(new File(IMG_FILE));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
    }

    @Override
    public String getType() {
        return "Pendulum";
    }

    @Override
    public void update() {
        double amplitude = MIN_AMPLITUDE + (MAX_AMPLITUDE - MIN_AMPLITUDE)
                * ((double) getX() / WIDTH);
        angleAcceleration = (-GRAVITY / ROD_LENGTH) * Math.sin(angle);
        angleVelocity += angleAcceleration;
        angle += angleVelocity * amplitude;
        setX(getX() + getSpeed());
    }

    @Override
    public void draw(Graphics g) {
        int ballX = getX();
        int ballY = (int) (getY() + ROD_LENGTH * Math.cos(angle));

        // Drawing rod
        Graphics2D line = (Graphics2D) g;
        line.setStroke(new BasicStroke(LINE_THICKNESS));
        line.drawLine(getX(), getY(), ballX, ballY);

        // Drawing ball
        g.drawImage(img, ballX - BALL_RADIUS / 2, ballY - BALL_RADIUS / 2,
                BALL_RADIUS, BALL_RADIUS, null);
    }
}

