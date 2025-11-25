package sokoban;

public final class Crate {
    private final int crateNum;
    private int x;
    private int y;
    private boolean onDiamond = false;

    public Crate(int crateNum, int startX, int startY) {
        this.crateNum = crateNum;
        this.x = startX;
        this.y = startY;
    }

    public int getCrateNum() { return crateNum; }
    public int getX() { return x; }
    public int getY() { return y; }
    public boolean isOnDiamond() { return onDiamond; }
    public void setOnDiamond(boolean v) { onDiamond = v; }

    public void move(String direction) {
        switch(direction.toLowerCase()) {
            case "up": y -= 1; break;
            case "down": y += 1; break;
            case "left": x -= 1; break;
            case "right": x += 1; break;
            default: throw new IllegalArgumentException("Unknown direction: "+direction);
        }
    }
}
