package org.cis1200.swimmy;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Pipe extends Obstacle {
    private int speed = 2;
    private static final String IMG_FILE_UP = "files/pipe_up.png";
    private static final String IMG_FILE_DOWN = "files/pipe.png";
    private static BufferedImage imgN;
    private static BufferedImage imgS;
    private String orientation;

    // Constructing the pipe
    public Pipe(int x, int y, int width, int height, String orientation) {
        super(x, y, width, height, 2, "Pipe");
        this.orientation = orientation;
        if (orientation.equals("south")) {
            try {
                if (imgS == null) {
                    imgS = ImageIO.read(new File(IMG_FILE_DOWN));
                }
            } catch (IOException e) {
                System.out.println("Internal Error:" + e.getMessage());
            }
        } else if (orientation.equals("north")) {
            try {
                if (imgN == null) {
                    imgN = ImageIO.read(new File(IMG_FILE_UP));
                }
            } catch (IOException e) {
                System.out.println("Internal Error:" + e.getMessage());
            }
        }
    }

    @Override
    public String getType() {
        return "Pipe";
    }


    // Drawing out the pipe depending on its orientation
    public void draw(Graphics g) {
        if (this.orientation.equals("north")) {
            g.drawImage(imgN, this.getX(), this.getY(), this.getW(), this.getH(), null);
        } else if (this.orientation.equals("south")) {
            g.drawImage(imgS, this.getX(), this.getY(), this.getW(), this.getH(), null);
        }
    }
    // Getting the type of the pipe: north or south
    public boolean isNorthPipe() {
        return orientation == "north";
    }

}
