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
    int mapTileNum[][];

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
            tiles[1] = new Tile(ImageIO.read(getClass().getResourceAsStream("/tile/wall.png")));
            tiles[2] = new Tile(ImageIO.read(getClass().getResourceAsStream("/tile/water01.png")));
            tiles[3] = new Tile(ImageIO.read(getClass().getResourceAsStream("/tile/earth.png")));
            tiles[4] = new Tile(ImageIO.read(getClass().getResourceAsStream("/tile/tree.png")));
            tiles[5] = new Tile(ImageIO.read(getClass().getResourceAsStream("/tile/sand.png")));
            tiles[6] = new Tile(ImageIO.read(getClass().getResourceAsStream("/tile/floor01.png")));

        } catch (IOException e) {
            throw new RuntimeException(e);
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
        }
    }

    public void draw(Graphics2D g2) {

        int worldX = 0;
        int worldY = 0;
        int screenX = 0;
        int screenY = 0;

        for (int row = 0; row < gp.getMaxWorldRow(); row++) {
            for (int col = 0; col < gp.getMaxWorldCol(); col++) {
                worldX = col * gp.getTileSize();
                worldY = row * gp.getTileSize();
                screenX = worldX - gp.getPlayer().worldX + gp.getPlayer().getScreenX();
                screenY = worldY - gp.getPlayer().worldY + gp.getPlayer().getScreenY();
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
}
