package sokoban;

import java.util.*;

public final class MoveDatabase {
    private final Map<Integer, Integer> bestMoves = new HashMap<>();

    public void record(int levelNumber, int moves) {
        Integer prev = bestMoves.get(levelNumber);
        if (prev == null || moves < prev) bestMoves.put(levelNumber, moves);
    }

    public OptionalInt getBest(int levelNumber) {
        Integer v = bestMoves.get(levelNumber);
        return v == null ? OptionalInt.empty() : OptionalInt.of(v);
    }
}
