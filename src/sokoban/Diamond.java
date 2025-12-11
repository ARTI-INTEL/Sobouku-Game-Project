package sokoban;

public class Diamond implements Tile {

    private int x, y;
    private Integer crateNum;  // crate assigned to this diamond, can be null

    public Diamond(int x, int y, Integer crateNum) {
        this.x = x;
        this.y = y;
        this.crateNum = crateNum;
    }

    @Override public int getX() { return x; }
    @Override public int getY() { return y; }
    @Override public void setX(int x) { this.x = x; }
    @Override public void setY(int y) { this.y = y; }

    @Override public boolean isWalkable() { return true; }
    @Override public boolean isPushable() { return true; }

    @Override public char getSymbol() { return 'D'; }

    public Integer getCrateNum() { return crateNum; }
}
