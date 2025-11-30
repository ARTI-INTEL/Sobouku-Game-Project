package sokoban;

import java.util.List;
import java.util.Map;

public class WarehouseKeeper {

    public int x;
    public int y;

    private int startX;
    private int startY;

    private int moves = 0;

    public WarehouseKeeper(int x, int y) {
        this.x = x;
        this.y = y;
        this.startX = x;
        this.startY = y;
    }

    public int getMoves() {
        return moves;
    }

    public void resetPosition() {
        this.x = startX;
        this.y = startY;
        this.moves = 0;
    }

    /**
     * FIXED: Rewritten for safety and correct logic.
     * - Checks for null tiles (moving off-grid).
     * - Uses isWalkable() for better polymorphism.
     */
    public boolean move(int dx, int dy, Level level) {

        int nextX = x + dx;
        int nextY = y + dy;
        Tile nextTile = level.getTile(nextX, nextY);

        // Can't move off-grid
        if (nextTile == null) {
            return false;
        }

        // Check if crate is at next position
        Crate crateToPush = getCrateAt(level.crates, nextX, nextY);

        if (crateToPush != null) {
            // Try to push the crate
            if (push(crateToPush, dx, dy, level)) {
                x = nextX;
                y = nextY;
                moves++;
                return true;
            }
            return false; // blocked
        }

        // Normal movement if next tile is walkable
        if (nextTile.isWalkable()) {
            x = nextX;
            y = nextY;
            moves++;
            return true;
        }

        return false; // wall
    }

    /**
     * FIXED: Rewritten to handle game logic correctly.
     * - Checks for null/non-walkable target tiles.
     * - Updates crate's 'onDiamond' status.
     * - Updates Tile's internal crate count.
     */
    private boolean push(Crate crate, int dx, int dy, Level level) {

        int newCX = crate.x + dx;
        int newCY = crate.y + dy;

        Tile targetTile = level.getTile(newCX, newCY);

        // Blocked by off-grid, wall, or another crate?
        if (targetTile == null || !targetTile.isWalkable()) {
            return false;
        }

        if (getCrateAt(level.crates, newCX, newCY) != null) {
            return false;
        }

        // Get old tile BEFORE moving crate
        Tile oldTile = level.getTile(crate.x, crate.y);

        // Move crate exactly one tile
        crate.x = newCX;
        crate.y = newCY;

        // Update tile states
        if (oldTile != null) {
            oldTile.removeCrate();
        }
        targetTile.placeCrate(); // We know targetTile is not null

        // Update crate's 'onDiamond' status (fixes Level.complete())
        crate.setOnDiamond(targetTile instanceof Diamond);

        return true;
    }

    private Crate getCrateAt(Map<Integer,Crate> crates, int x, int y) {
        for (Crate c : crates.values()) {
            if (c.x == x && c.y == y) return c;
        }
        return null;
    }

    /**
     * FIXED: Implemented.
     */
    public void resetMoveCount() {
        moves = 0;
    }

    /**
     * FIXED: Implemented.
     */
    public int checkNumOfMoves() {
        return moves;
    }

    /**
     * FIXED: Implemented.
     */
    public int getX() {
        return x;
    }

    /**
     * FIXED: Implemented.
     */
    public int getY() {
        return y;  
    }

    /**
     * FIXED: Replaced old, broken methods with a functional one.
     * This now requires the Level to be passed in.
     */
    public boolean move(String dir, Level level) {
        if (level == null) {
             throw new IllegalArgumentException("Level cannot be null for move");
        }
        
        int dx = 0;
        int dy = 0;

        switch (dir.toLowerCase()) {
            case "up": dy = -1; break;
            case "down": dy = 1; break;
            case "left": dx = -1; break;
            case "right": dx = 1; break;
            default:
                return false; // Unknown direction
        }
        
        return move(dx, dy, level);
    }
}