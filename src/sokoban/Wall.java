package sokoban;

public final class Wall extends Tile {
    private final int length;
    public Wall(int x, int y, int length) { super(x, y); this.length = length; }
    public int getLength() { return length; }
    @Override public boolean isWalkable() { return false; }
}
