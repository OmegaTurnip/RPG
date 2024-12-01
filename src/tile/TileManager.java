package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gp;
    Tile[] tiles;
    int[][] mapTileNum;

    String levelFile;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tiles = new Tile[10];
        mapTileNum = new int[gp.getMaxWorldRow()][gp.getMaxWorldCol()];
        getTileImage();
        levelFile = "/level/world01.txt"; //default level
        loadLevel(levelFile);
    }

    public void getTileImage() {
        try {
            tiles[0] = new Tile(ImageIO.read(getClass().getResourceAsStream("/tile/grass01.png")));
            tiles[1] = new Tile(ImageIO.read(getClass().getResourceAsStream("/tile/wall.png")), true);
            tiles[2] = new Tile(ImageIO.read(getClass().getResourceAsStream("/tile/water01.png")), true);
            tiles[3] = new Tile(ImageIO.read(getClass().getResourceAsStream("/tile/earth.png")));
            tiles[4] = new Tile(ImageIO.read(getClass().getResourceAsStream("/tile/tree.png")), true);
            tiles[5] = new Tile(ImageIO.read(getClass().getResourceAsStream("/tile/sand.png")));
            tiles[6] = new Tile(ImageIO.read(getClass().getResourceAsStream("/tile/floor01.png")));

        } catch (IOException e) {
            throw new IllegalArgumentException("Tile files cannot be read!");
        }

    }

    public void loadLevel(String levelFile) {

        try {
            InputStream is = getClass().getResourceAsStream(levelFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            for (int row = 0; row < gp.getMaxWorldRow(); row++) {
                String[] line = br.readLine().split(" ");
                for (int col = 0; col < gp.getMaxWorldCol(); col ++) {
                    mapTileNum[row][col] = Integer.parseInt(line[col]);
                }
            }

        } catch (IOException e) {
            throw new IllegalArgumentException("Level file cannot be read!");
        }
    }

    public void draw(Graphics2D g2) {

        int worldX;
        int worldY;
        int screenX;
        int screenY;

        for (int row = 0; row < gp.getMaxWorldRow(); row++) {
            for (int col = 0; col < gp.getMaxWorldCol(); col++) {
                worldX = col * gp.getTileSize(); //X and Y coordinates of the tile on the world scale
                worldY = row * gp.getTileSize();

                //So we have the worldCoordinates which are the absolute position, and the screen/camera position which is the relative position.
                //The world coordinates do not change, only the camera position. So we plot the world coordinate RELATIVE to our camera with
                //worldCoord - playerWorldCoord. We are saying, draw this tile relative to where the player is. If the world coord is 2,2, and the
                //player coord is 7,7, then draw the tile -5,-5 coordinates from the center of the screen.
                //Of course, we also add the player's screen position to this equation because we want to center our camera on the player, otherwise the camera
                //is in a 'default' center position. In other words, we want to center the world on the player; we want to offset our tiles from the player camera.
                screenX = gp.getPlayer().getScreenX() + worldX - gp.getPlayer().worldX;
                screenY = gp.getPlayer().getScreenY() + worldY - gp.getPlayer().worldY;

                //Render only the tiles you can see
                if (worldX + gp.getTileSize() > gp.getPlayer().worldX - gp.getPlayer().getScreenX() &&
                    worldX - gp.getTileSize() < gp.getPlayer().worldX + gp.getPlayer().getScreenX() &&
                    worldY + gp.getTileSize() > gp.getPlayer().worldY - gp.getPlayer().getScreenY() &&
                    worldY - gp.getTileSize() < gp.getPlayer().worldY + gp.getPlayer().getScreenY()) {
                    g2.drawImage(tiles[mapTileNum[row][col]].image, screenX, screenY, gp.getTileSize(), gp.getTileSize(), null);
                }
            }
        }
    }

    public void setLevelFile(String levelFile) {

        this.levelFile = levelFile;
        loadLevel(levelFile);
    }
    public int[][] getMapTileNum() {
        return mapTileNum;
    }

    public Tile[] getTiles() {
        return tiles;
    }
}
