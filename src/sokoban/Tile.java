package sokoban;

public abstract class Tile {
    protected final int x;
    protected final int y;
    protected int numOfCrates = 0;

    protected Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public void placeCrate() { numOfCrates++; }
    public void removeCrate() { if (numOfCrates>0) numOfCrates--; }
    public boolean hasCrate() { return numOfCrates > 0; }

    public abstract boolean isWalkable();
}
