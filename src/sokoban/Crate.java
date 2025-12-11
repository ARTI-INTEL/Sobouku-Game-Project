package sokoban;

/**
 * Simple crate model for Sokoban.
 * Stores position and whether the crate is on a diamond.
 */
public final class Crate {

    private final int crateNum;

    private final int startX;
    private final int startY;

    private int x;
    private int y;
    private boolean onDiamond = false;

    public Crate(int crateNum, int startX, int startY) {
        this.crateNum = crateNum;

        this.startX = startX;
        this.startY = startY;

        this.x = startX;
        this.y = startY;
    }

    public int getCrateNum() { return crateNum; }

    public int getX() { return x; }
    public int getY() { return y; }

    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }

    public void move(String direction) {
        switch(direction.toLowerCase()) {
            case "up":    y -= 1; break;
            case "down":  y += 1; break;
            case "left":  x -= 1; break;
            case "right": x += 1; break;
            default: throw new IllegalArgumentException("Unknown direction: " + direction);
        }
    }

    public boolean isOnDiamond() { return onDiamond; }
    public void setOnDiamond(boolean onDiamond) { this.onDiamond = onDiamond; }

    /** Reset crate to original spawn position */
    public void resetPosition() {
        this.x = startX;
        this.y = startY;
        this.onDiamond = false;
    }

    @Override
    public String toString() {
        return "Crate#" + crateNum + "(@" + x + "," + y + ")" + (onDiamond ? "[D]" : "");
    }
}
