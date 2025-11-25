package sokoban;

public final class Diamond extends Tile {
    private final Integer crateNum; // optional
    public Diamond(int x, int y, Integer crateNum) { super(x, y); this.crateNum = crateNum; }
    public Integer getCrateNum() { return crateNum; }
    @Override public boolean isWalkable() { return true; }
}
