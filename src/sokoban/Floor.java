package sokoban;

public class Floor implements Tile {

    private int x, y;

    public Floor(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override public int getX() { return x; }
    @Override public int getY() { return y; }
    @Override public void setX(int x) { this.x = x; }
    @Override public void setY(int y) { this.y = y; }

    @Override public boolean isWalkable() { return true; }
    @Override public boolean isPushable() { return true; }

    @Override public char getSymbol() { return '.'; }
}
