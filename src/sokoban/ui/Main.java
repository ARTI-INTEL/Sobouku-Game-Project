package sokoban.ui;

import javax.swing.SwingUtilities;

public final class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Open the new main menu
                new MainMenu().setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
