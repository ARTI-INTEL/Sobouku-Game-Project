package sokoban.ui;

import java.util.List;
import sokoban.Level;              // adjust if Level is in another package
import sokoban.ui.LevelLoader;
import sokoban.WarehouseKeeper;
import sokoban.ui.SokobanFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class MainMenu extends JFrame {

    public MainMenu() {
        setTitle("Sokoban – Main Menu");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("SOKOBAN", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        add(title, BorderLayout.NORTH);

        // Center panel (description)
        JTextArea info = new JTextArea();
        info.setEditable(false);
        info.setFont(new Font("Arial", Font.PLAIN, 16));
        info.setText(
            "Welcome to Sokoban!\n\n" +
            "Tile Guide:\n" +
            "Grey is Wall – Cannot be moved or passed.\n" +
            "White is Floor – Walkable space.\n" +
            "Yellow Square is Crate – Pushable block.\n" +
            "Pink Diamond – Goal point for crates.\n" +
            "Blue Circle is Player  – You!\n\n" +
            "Push crates onto all diamonds to solve the puzzle."
        );
        add(info, BorderLayout.CENTER);

        // Play button
        JButton playBtn = new JButton("Play");
        playBtn.setFont(new Font("Arial", Font.BOLD, 20));
        playBtn.setFocusable(false);

        playBtn.addActionListener(e -> {


            WarehouseKeeper keeper = new WarehouseKeeper(2, 2);
            List<Level> levels = LevelLoader.createLevels(keeper);

            SokobanFrame game = new SokobanFrame(levels);  // your game window
            dispose();                 // close main menu
            game.setVisible(true);     // show game window
        });


        add(playBtn, BorderLayout.SOUTH);
    }

    // Entry point – runs menu first
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainMenu().setVisible(true);
        });
    }
}
