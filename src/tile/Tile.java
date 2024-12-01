package tile;

import java.awt.image.BufferedImage;

public class Tile {

    BufferedImage image;
    private boolean collision = false;

    public Tile(BufferedImage image) {
        this.image = image;
    }
}
