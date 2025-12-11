package sokoban;

import java.util.Collection;

/**
 * WarehouseKeeper handles player position and move/push logic.
 * Use move(String dir, Level level) to attempt a move.
 */
public class WarehouseKeeper {
    public int x;
    public int y;
    private final int spawnX;
    private final int spawnY;
    private int numOfMoves = 0;

    public WarehouseKeeper(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        this.spawnX = startX;
        this.spawnY = startY;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int checkNumOfMoves() { return numOfMoves; }
    public void resetMoveCount() { numOfMoves = 0; }

    public void resetPosition() {
        this.x = spawnX;
        this.y = spawnY;
    }

    /**
     * Attempt to move the keeper in a direction.
     * Returns true if the keeper moved (or pushed a crate and moved), false if blocked.
     */
    public boolean move(String dir, Level level) {
        int dx = 0, dy = 0;
        switch (dir.toLowerCase()) {
            case "up": dy = -1; break;
            case "down": dy = 1; break;
            case "left": dx = -1; break;
            case "right": dx = 1; break;
            default: return false;
        }

        int tx = x + dx;
        int ty = y + dy;

        // Check for wall on the target tile
        Tile targetTile = level.tileAt(tx, ty);
        if (targetTile == null) return false; // out of bounds or invalid

        if (!targetTile.isWalkable()) {
            // wall or not walkable -> blocked
            return false;
        }

        // Is there a crate at the destination?
        Crate crate = findCrateAt(level.getCrates(), tx, ty);
        if (crate == null) {
            // empty or diamond or floor — just move keeper
            x = tx;
            y = ty;
            numOfMoves++;
            // after moving, update crate-on-diamond flags (none changed)
            return true;
        }

        // There is a crate: attempt to push
        int nx = crate.getX() + dx;
        int ny = crate.getY() + dy;
        Tile beyond = level.tileAt(nx, ny);
        if (beyond == null) return false; // out of bounds

        // cannot push into a wall or non-pushable tile
        if (!beyond.isPushable() && !beyond.isWalkable()) return false;

        // cannot push into another crate
        if (findCrateAt(level.getCrates(), nx, ny) != null) return false;

        // Move crate exactly one tile
        crate.setX(nx);
        crate.setY(ny);

        // update crate on-diamond status
        if (beyond instanceof Diamond) crate.setOnDiamond(true);
        else crate.setOnDiamond(false);

        // Move keeper into the crate's previous spot
        x = tx;
        y = ty;
        numOfMoves++;
        return true;
    }

    private Crate findCrateAt(Collection<Crate> crates, int x, int y) {
        for (Crate c : crates) {
            if (c.getX() == x && c.getY() == y) return c;
        }
        return null;
    }
}
