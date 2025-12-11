package sokoban.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import sokoban.*;

public class SokobanFrame extends JFrame {

    private final BoardPanel board;
    private final JLabel status;
    private final List<Level> levels;
    private int current = 0;

    public SokobanFrame(List<Level> levels) {
        super("Sokoban");
        this.levels = levels;

        Level start = levels.get(0);
        this.board = new BoardPanel(start);
        this.status = new JLabel("Moves: 0");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(board, BorderLayout.CENTER);
        add(status, BorderLayout.SOUTH);
        add(createToolBar(), BorderLayout.NORTH);
        setJMenuBar(createMenuBar());

        pack();
        setLocationRelativeTo(null);
        board.requestFocusInWindow();

        // Update move counter
        Timer t = new Timer(200, e ->
                status.setText("Moves: " + board.getLevel().getKeeper().checkNumOfMoves()));
        t.start();
    }

    private JToolBar createToolBar() {
        JToolBar tb = new JToolBar();
        tb.setFloatable(false);

        // Restart button
        JButton restart = new JButton("Restart Level");
        restart.addActionListener(e -> {
            board.getLevel().restart();
            board.repaint();
        });

        // Next level button (with completion check)
        JButton next = new JButton("Next Level");
        next.addActionListener(e -> {

            Level lvl = board.getLevel();

            // Check if complete
            if (!lvl.complete()) {
                JOptionPane.showMessageDialog(
                        this,
                        "You must complete the level before moving to the next!",
                        "Level Not Completed",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            // Move to the next level
            current = (current + 1) % levels.size();
            Level nextLvl = levels.get(current);

            board.setLevel(nextLvl);
            board.repaint();
            board.requestFocusInWindow();

            JOptionPane.showMessageDialog(
                    this,
                    "Level " + nextLvl.getNumber() + " Loaded!",
                    "Next Level",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        tb.add(restart);
        tb.add(next);
        return tb;
    }

    private JMenuBar createMenuBar() {
        JMenuBar mb = new JMenuBar();
        JMenu menu = new JMenu("Sokoban Game");

        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(e -> System.exit(0));

        menu.add(exit);
        mb.add(menu);
        return mb;
    }
}

