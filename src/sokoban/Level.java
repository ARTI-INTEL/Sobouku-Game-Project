package sokoban;

import java.util.*;

public final class Level {
    private final int number;
    private final int width;
    private final int height;
    private final int numOfCrates;

    private final WarehouseKeeper keeper;
    private final List<Tile> tiles = new ArrayList<>();
    final Map<Integer, Crate> crates = new HashMap<>();

    public Level(int number, int width, int height, int numOfCrates, WarehouseKeeper keeper) {
        this.number = number;
        this.width = width;
        this.height = height;
        this.numOfCrates = numOfCrates;
        this.keeper = keeper;
    }

    public void start() {
        // any startup logic (keeper already set)
    }

    public boolean complete() {
        for (Crate c : crates.values()) {
            if (!c.isOnDiamond()) return false;
        }
        return true;
    }

    public void restart() {
        // naive restart: send keeper back to spawn and reset counts
        keeper.resetPosition();
        keeper.resetMoveCount();
        // crates/tiles should be reloaded by LevelLoader if needed
    }

    public void load(List<Tile> tilesDefinition, List<Crate> crateList) {
        tiles.clear();
        tiles.addAll(tilesDefinition);
        crates.clear();
        for (Crate c : crateList) crates.put(c.getCrateNum(), c);
    }

    public int getNumber() { return number; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getNumOfCrates() { return numOfCrates; }

    public List<Tile> getTiles() { return Collections.unmodifiableList(tiles); }
    public Collection<Crate> getCrates() { return Collections.unmodifiableCollection(crates.values()); }

    // Methods requested by UI
    public WarehouseKeeper getKeeper() { return keeper; }
    public int getKeeperMoves() { return keeper.checkNumOfMoves(); }

    // Helper: find tile at x,y or null
    public Tile tileAt(int x, int y) {
        for (Tile t : tiles) if (t.getX()==x && t.getY()==y) return t;
        return null;
    }

    public Tile getTile(int i, int j) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTile'");
    }
}
