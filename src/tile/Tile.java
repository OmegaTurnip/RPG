package tile;

import java.awt.image.BufferedImage;

public class Tile {

    BufferedImage image;
    private boolean collision;

    public Tile(BufferedImage image) {
        this.image = image;
        this.collision = false;
    }
    public Tile(BufferedImage image, boolean collision) {
        this.image = image;
        this.collision = collision;
    }

    public boolean isCollision() {
        return collision;
    }
}
