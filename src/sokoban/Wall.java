package sokoban;

public class Wall implements Tile {

    private int x, y;
    private int length;

    public Wall(int x, int y, int length) {
        this.x = x;
        this.y = y;
        this.length = length;
    }

    @Override public int getX() { return x; }
    @Override public int getY() { return y; }
    @Override public void setX(int x) { this.x = x; }
    @Override public void setY(int y) { this.y = y; }

    @Override public boolean isWalkable() { return false; }
    @Override public boolean isPushable() { return false; }

    @Override public char getSymbol() { return '#'; }
}
