package main;

import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

  //Game screen parameters
  final int tileSize = 48; //16 by 16 tile but scaled by 3 for normal computer resolution

  final int maxCol = 16;
  final int maxRow = 12;
  final int screenWidth = maxCol * tileSize;
  final int screenHeight = maxRow * tileSize; //16 by 12 screen scaled with tile size
  //World map parameters
  final int maxWorldCol = 50;
  final int maxWorldRow = 50;
  final int worldWidth = maxWorldCol * tileSize;
  final int worldHeight = maxWorldRow * tileSize;


  //Framerate
  int FPS = 30;

  //Dependencies
  KeyHandler keyH;

  Thread gameThread;

  Player player;
  TileManager tileManager;

  CollisionDetector collisionDetector;

  public GamePanel() {
    keyH = new KeyHandler();
    tileManager  = new TileManager(this);
    player = new Player(this, keyH);
    collisionDetector = new CollisionDetector(this);

    this.setPreferredSize(new Dimension(screenWidth, screenHeight));
    this.setBackground(Color.black);
    this.setDoubleBuffered(true);
    this.addKeyListener(keyH);
    this.setFocusable(true);
  }

  public void startGameThread() {
    gameThread = new Thread(this);
    gameThread.start();
  }

  public void update() {
    player.update();
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2 = (Graphics2D) g;

    tileManager.draw(g2);

    player.draw(g2);

    g2.dispose();
  }

  @Override
  public void run() {
    double drawInterval = 1000000000.0 / FPS;
    double nextDrawTime = System.nanoTime() + drawInterval;
    double remainingTime;

    while (gameThread != null) {
      update();
      repaint();

      try {
        remainingTime = nextDrawTime - System.nanoTime();
        remainingTime /= 1000000;
        if (remainingTime < 0) {
          remainingTime = 0;
        }
        Thread.sleep((long) remainingTime);
        nextDrawTime += drawInterval;
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }


    }
  }

  public int getTileSize() {
    return tileSize;
  }

  public int getMaxCol() {
    return maxCol;
  }

  public int getMaxRow() {
    return maxRow;
  }

  public int getScreenWidth() {
    return screenWidth;
  }

  public int getScreenHeight() {
    return screenHeight;
  }

  public int getMaxWorldCol() {
    return maxWorldCol;
  }

  public int getMaxWorldRow() {
    return maxWorldRow;
  }

  public int getWorldWidth() {
    return worldWidth;
  }

  public int getWorldHeight() {
    return worldHeight;
  }

  public Player getPlayer() {
    return player;
  }

  public CollisionDetector getCollisionDetector() {
    return collisionDetector;
  }
}
