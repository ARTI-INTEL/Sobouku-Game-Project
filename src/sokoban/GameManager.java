package sokoban;

import java.util.*;

public final class GameManager {
    private final List<Level> levels = new ArrayList<>();
    private int currentLevelIndex = -1;
    private final MoveDatabase db = new MoveDatabase();

    public void addLevel(Level l) { levels.add(l); }

    public void startLevel(int index) {
        if (index < 0 || index >= levels.size()) throw new IndexOutOfBoundsException("Level not found");
        currentLevelIndex = index;
        levels.get(index).start();
    }

    public void levelCompleted() {
        Level lvl = levels.get(currentLevelIndex);
        if (lvl == null) return;
        int moves = lvl.getKeeperMoves();
        db.record(lvl.getNumber(), moves);
    }
}
