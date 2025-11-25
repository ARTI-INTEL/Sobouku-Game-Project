package sokoban;

public final class Floor extends Tile {
    public Floor(int x, int y) { super(x, y); }
    @Override public boolean isWalkable() { return true; }
}
