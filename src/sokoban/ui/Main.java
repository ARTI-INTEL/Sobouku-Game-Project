package sokoban.ui;

import javax.swing.SwingUtilities;
import sokoban.*;
import java.util.*;

public final class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                WarehouseKeeper keeper = new WarehouseKeeper(2,2);
                List<Level> levels = LevelLoader.createLevels(keeper);
                SokobanFrame frame = new SokobanFrame(levels);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
