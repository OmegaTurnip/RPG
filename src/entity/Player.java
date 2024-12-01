package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler kh;
    int tileSize;

    final int screenX;
    final int screenY;

    public Player(GamePanel gp, KeyHandler kh) {
        this.gp = gp;
        this.kh = kh;
        screenX = (gp.getScreenWidth() / 2) - (gp.getTileSize() / 2); //Slight offset because coordinates correspond to top left corner of the tile
        screenY = (gp.getScreenHeight() / 2) - (gp.getTileSize() / 2);
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.getTileSize() * 23; //Put player in the center of the world
        worldY = gp.getTileSize() * 21;
        speed = 6;
        tileSize = gp.getTileSize();
        direction = 'd';
        collisionBox = new Rectangle(8, 16, 24, 24);
    }

    public void update() {
        move();
    }

    public void draw(Graphics g2) {
        BufferedImage image = null;

        switch (direction) {
            case 'u' -> image = (spriteState) ? up1 : up2;
            case 'l' -> image = (spriteState) ? left1 : left2;
            case 'r' -> image = (spriteState) ?  right1 : right2;
            case 'd' -> image = (spriteState) ? down1 : down2;
        }

        g2.drawImage(image, screenX, screenY, tileSize, tileSize, null);
    }

    public void move() {
        int mx = 0;
        int my = 0;
        int currSpeed = 0;
        if (kh.getKey(KeyEvent.VK_W) ||
            kh.getKey(KeyEvent.VK_A) ||
            kh.getKey(KeyEvent.VK_S) ||
            kh.getKey(KeyEvent.VK_D)) spriteCounter++;

        if (kh.getKey(KeyEvent.VK_W)) {
            direction = 'u';
        }
        if (kh.getKey(KeyEvent.VK_S)) {
            direction = 'd';
        }
        if (kh.getKey(KeyEvent.VK_A)) {
            direction = 'l';
        }
        if (kh.getKey(KeyEvent.VK_D)) {
            direction = 'r';
        }
        if (kh.getKey(KeyEvent.VK_SPACE)) {
            currSpeed += 2;
        }

        collisionOn = false;
        gp.getCollisionDetector().checkTile(this);

        if (!collisionOn) {
            if (kh.getKey(KeyEvent.VK_W)) {
                my -= 1;
            }
            if (kh.getKey(KeyEvent.VK_S)) {
                my += 1;
            }
            if (kh.getKey(KeyEvent.VK_A)) {
                mx -= 1;
            }
            if (kh.getKey(KeyEvent.VK_D)) {
                mx += 1;
            }
        }

        int[] movement = normaliseMovement(mx, my, speed + currSpeed);

        worldX += movement[0];
        worldY += movement[1];

        if (spriteCounter > 10 - currSpeed*2) {
            spriteState = !spriteState;
            spriteCounter = 0;
        }
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
        } catch (IOException e) {
            throw new IllegalArgumentException("Player sprite files cannot be read!");
        }
    }

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }
}
