package entity;

import java.awt.image.BufferedImage;

public abstract class Entity {

    public int worldX, worldY, speed;
    BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    char direction; //Can be u, l, r, or d (Up Left Right Down)
    int spriteCounter = 0;
    boolean spriteState = true;

    /**
     * Normalise a 2-dim vector of movement coordinates,
     * so that moving diagonally does not make the player faster.
     * @param mx Movement in x direction
     * @param my Movement in y direction
     * @param speed Speed of movement
     * @return Array of length 2 of the normalised movement coordinates
     */
    public int[] normaliseMovement(int mx, int my, int speed) {
        double distance = Math.hypot(mx, my);
        mx = (int) Math.rint(speed * (mx / distance));
        my = (int) Math.rint(speed * (my / distance));
        int[] result = {mx, my};
        return result;
    }
}
