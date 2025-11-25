package sokoban;

public final class WarehouseKeeper {
    private int x;
    private int y;
    private final int spawnX;
    private final int spawnY;
    private int numOfMoves = 0;

    public WarehouseKeeper(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        this.spawnX = startX;
        this.spawnY = startY;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public int checkNumOfMoves() { return numOfMoves; }
    public void resetMoveCount() { numOfMoves = 0; }

    public void resetPosition() { this.x = spawnX; this.y = spawnY; }
    public void resetPosition(int x, int y) { this.x = x; this.y = y; }

    public boolean move(String direction) {
        switch(direction.toLowerCase()) {
            case "up": y -= 1; break;
            case "down": y += 1; break;
            case "left": x -= 1; break;
            case "right": x += 1; break;
            default: return false;
        }
        numOfMoves++;
        return true;
    }

    public boolean push(Crate crate, String direction) {
        if (crate == null) return false;
        crate.move(direction);
        numOfMoves++;
        return true;
    }
}
