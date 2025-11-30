package sokoban.ui;

import java.util.*;
import sokoban.*;

public final class LevelLoader {

    private LevelLoader() {}

    public static List<Level> createLevels(WarehouseKeeper keeperTemplate) {
        List<Level> list = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            WarehouseKeeper k = new WarehouseKeeper(2, 2);
            Level lvl = createLevel(i, k);
            list.add(lvl);
        }
        return list;
    }

    public static Level createLevel(int levelNumber, WarehouseKeeper keeper) {

        // Fixed 8x8 board
        final int w = 8, h = 8;
        Level lvl = new Level(levelNumber, w, h, 0, keeper);  

        List<Tile> tiles = new ArrayList<>();
        List<Crate> crates = new ArrayList<>();

        // Fill all floor
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                tiles.add(new Floor(x, y));
            }
        }

        // Surround with walls
        for (int i = 0; i < w; i++) {
            tiles.set(idx(i, 0, w), new Wall(i, 0, 1));
            tiles.set(idx(i, h - 1, w), new Wall(i, h - 1, 1));
            tiles.set(idx(0, i, w), new Wall(0, i, 1));
            tiles.set(idx(w - 1, i, w), new Wall(w - 1, i, 1));
        }

        // Load different levels
        switch (levelNumber) {

            // -----------------------------------------------------------
            case 1: // SIMPLE 1 CRATE LEVEL
                keeper.x = 2;
                keeper.y = 2;

                tiles.set(idx(5, 5, w), new Diamond(5, 5, null));
                crates.add(new Crate(0, 3, 3)); 
                break;

            // -----------------------------------------------------------
            case 2: // SMALL MAZE (1 CRATE)
                keeper.x = 2;
                keeper.y = 2;

                // Inner walls
                tiles.set(idx(3,2,w), new Wall(3,2,1));
                tiles.set(idx(3,3,w), new Wall(3,3,1));
                tiles.set(idx(3,4,w), new Wall(3,4,1));

                tiles.set(idx(5,3,w), new Wall(5,3,1));

                tiles.set(idx(6,5,w), new Diamond(6,5,null));
                crates.add(new Crate(0, 4, 4));
                break;

            // -----------------------------------------------------------
            case 3: // TWO CRATES, MEDIUM
                keeper.x = 2;
                keeper.y = 2;

                tiles.set(idx(6,2,w), new Wall(6,2,1));
                tiles.set(idx(6,3,w), new Wall(6,3,1));
                tiles.set(idx(6,4,w), new Wall(6,4,1));

                tiles.set(idx(5,5,w), new Diamond(5,5,null));
                tiles.set(idx(6,5,w), new Diamond(6,5,null));

                crates.add(new Crate(0, 3, 3));
                crates.add(new Crate(1, 4, 3));
                break;

            // -----------------------------------------------------------
            case 4: // HARD – 2 CRATES
                keeper.x = 2;
                keeper.y = 2;

                // Maze walls
                tiles.set(idx(2,3,w), new Wall(2,3,1));
                tiles.set(idx(3,3,w), new Wall(3,3,1));
                tiles.set(idx(4,3,w), new Wall(4,3,1));

                tiles.set(idx(2,5,w), new Wall(2,5,1));
                tiles.set(idx(3,5,w), new Wall(3,5,1));
                tiles.set(idx(4,5,w), new Wall(4,5,1));

                // Diamonds
                tiles.set(idx(5,6,w), new Diamond(5,6,null));
                tiles.set(idx(6,6,w), new Diamond(6,6,null));

                // Only 2 crates now
                crates.add(new Crate(0, 3, 4));
                crates.add(new Crate(1, 5, 4));
                break;


            // -----------------------------------------------------------
            case 5: // VERY HARD – MAZE + MULTIPLE CRATES (4 crates)
                keeper.x = 1;
                keeper.y = 1;

                // Maze pattern
                tiles.set(idx(2,1,w), new Wall(2,1,1));
                tiles.set(idx(2,2,w), new Wall(2,2,1));
                tiles.set(idx(2,3,w), new Wall(2,3,1));

                tiles.set(idx(4,1,w), new Wall(4,1,1));
                tiles.set(idx(4,2,w), new Wall(4,2,1));
                // tiles.set(idx(4,3,w), new Wall(4,3,1));

                tiles.set(idx(3,5,w), new Wall(3,5,1));
                tiles.set(idx(3,6,w), new Wall(3,6,1));

                // Diamonds
                tiles.set(idx(6,4,w), new Diamond(6,4,null));
                tiles.set(idx(6,5,w), new Diamond(6,5,null));

                // ONLY 2 crates now
                crates.add(new Crate(0, 4, 4));
                crates.add(new Crate(1, 5, 4));
                break;
        }

        lvl.load(tiles, crates);
        return lvl;
    }

    private static int idx(int x, int y, int width) {
        return y * width + x;
    }
}
