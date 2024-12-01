package main;

import entity.Entity;

public class CollisionDetector {

    GamePanel gp;

    public CollisionDetector(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        int leftSide = entity.worldX + entity.getCollisionBox().x;
        int rightSide = entity.worldX + entity.getCollisionBox().x + entity.getCollisionBox().width;
        int topSide = entity.worldY + entity.getCollisionBox().y;
        int bottomSide = entity.worldY + entity.getCollisionBox().y + entity.getCollisionBox().height;

        int leftCol = leftSide / gp.tileSize;
        int rightCol = rightSide / gp.tileSize;

        int topRow = topSide / gp.tileSize;
        int bottomRow = bottomSide / gp.tileSize;

        switch (entity.getDirection()) {
            case 'u' -> checkRowCollision(entity, topSide, leftCol, rightCol, true);

            case 'l' -> checkColumnCollision(entity, leftSide, topRow, bottomRow, true);

            case 'r' -> checkColumnCollision(entity, rightSide, topRow, bottomRow, false);

            case 'd' -> checkRowCollision(entity, bottomSide, leftCol, rightCol, false);

        }
    }

    public void checkColumnCollision(Entity entity, int side, int topRow, int bottomRow, boolean isLeft) {
        int tileNum1, tileNum2;
        int col = (isLeft) ? (side - entity.speed) / gp.tileSize : (side + entity.speed) / gp.tileSize;
        tileNum1 = gp.tileManager.getMapTileNum()[topRow][col];
        tileNum2 = gp.tileManager.getMapTileNum()[bottomRow][col];
        if (gp.tileManager.getTiles()[tileNum1].isCollision() || gp.tileManager.getTiles()[tileNum2].isCollision()) {
            entity.setCollisionOn(true);
        }
    }
    public void checkRowCollision(Entity entity, int side, int leftCol, int rightCol, boolean isTop) {
        int tileNum1, tileNum2;
        int row = (isTop) ? (side - entity.speed) / gp.tileSize : (side + entity.speed) / gp.tileSize;
        tileNum1 = gp.tileManager.getMapTileNum()[row][leftCol];
        tileNum2 = gp.tileManager.getMapTileNum()[row][rightCol];
        if (gp.tileManager.getTiles()[tileNum1].isCollision() || gp.tileManager.getTiles()[tileNum2].isCollision()) {
            entity.setCollisionOn(true);
        }
    }
}
