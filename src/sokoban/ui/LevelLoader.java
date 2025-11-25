package sokoban.ui;

import java.util.*;
import sokoban.*;

public final class LevelLoader {
    private LevelLoader() {}

    public static List<Level> createLevels(WarehouseKeeper keeperTemplate) {
        List<Level> list = new ArrayList<>();
        for (int i=1;i<=5;i++) {
            WarehouseKeeper k = new WarehouseKeeper(2,2);
            Level lvl = createDemoLevel(i, k);
            list.add(lvl);
        }
        return list;
    }

    public static Level createDemoLevel(int levelNumber, WarehouseKeeper keeper) {
        int w = 8, h = 8;
        Level lvl = new Level(levelNumber, w, h, 2, keeper);
        List<Tile> tiles = new ArrayList<>();
        for (int y=0;y<h;y++) for (int x=0;x<w;x++) tiles.add(new Floor(x,y));
        // borders as walls
        for (int i=0;i<w;i++) {
            tiles.set(idx(i,0,w), new Wall(i,0,1));
            tiles.set(idx(i,h-1,w), new Wall(i,h-1,1));
            tiles.set(idx(0,i,w), new Wall(0,i,1));
            tiles.set(idx(w-1,i,w), new Wall(w-1,i,1));
        }
        // place some diamonds, vary by levelNumber
        tiles.set(idx(5,5,w), new Diamond(5,5,null));
        tiles.set(idx(6,5,w), new Diamond(6,5,null));
        List<Crate> crates = new ArrayList<>();
        crates.add(new Crate(0,3,3));
        crates.add(new Crate(1,4,3));
        lvl.load(tiles, crates);
        return lvl;
    }

    private static int idx(int x,int y,int width) { return y*width + x; }
}
