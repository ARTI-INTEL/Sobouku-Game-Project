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

    public boolean move(int dx, int dy, Level level) {

        Tile nextTile = level.getTile(x + dx, y + dy);

        // Check if crate is at next position
        Crate crateToPush = getCrateAt(level.crates, x + dx, y + dy);

        if (crateToPush != null) {
            // Try to push the crate
            if (push(crateToPush, dx, dy, level)) {
                x += dx;
                y += dy;
                moves++;
                return true;
            }
            return false; // blocked
        }

        // Normal movement if next tile is NOT a wall
        if (!(nextTile instanceof Wall)) {
            x += dx;
            y += dy;
            moves++;
            return true;
        }

        return false; // wall
    }

    private boolean push(Crate crate, int dx, int dy, Level level) {

        int newCX = crate.x + dx;
        int newCY = crate.y + dy;

        // Blocked by wall or another crate?
        if (level.getTile(newCX, newCY) instanceof Wall) {
            return false;
        }

        if (getCrateAt(level.crates, newCX, newCY) != null) {
            return false;
        }

        // Move crate exactly one tile
        crate.x = newCX;
        crate.y = newCY;

        return true;
    }

    private Crate getCrateAt(Map<Integer,Crate> crates, int x, int y) {
        for (Crate c : crates.values()) {
            if (c.x == x && c.y == y) return c;
        }
        return null;
    }

    public void resetMoveCount() {
        // TODO Auto-generated method stub
        //reset moves to 0
        moves = 0;
    }

    public int checkNumOfMoves() {
        // TODO Auto-generated method stub
        return moves;
    }

    public int getX() {
        // TODO Auto-generated method stub
        return x;
    }

    public int getY() {
        // TODO Auto-generated method stub
        return y;  

    }

    public void move(String dir) {
        // TODO Auto-generated method stub
        if (dir.equals("up")) y--;
        else if (dir.equals("down")) y++;
        else if (dir.equals("left")) x--;
        else if (dir.equals("right")) x++;

        move(x, y, null);

        throw new UnsupportedOperationException("Unimplemented method 'move'");
    }

    public void push(Crate c, String dir) {
        // TODO Auto-generated method stub
        
        if (dir.equals("up")) y--;
        else if (dir.equals("down")) y++;
        else if (dir.equals("left")) x--;
        else if (dir.equals("right")) x++;

        move(x, y, null);
        
        throw new UnsupportedOperationException("Unimplemented method 'push'");
    }
}
