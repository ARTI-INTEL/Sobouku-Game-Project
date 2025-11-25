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
        super("Sokoban - Retro Games Ltd");
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

        // update status periodically
        Timer t = new Timer(200, e -> status.setText("Moves: " + board.getLevel().getKeeper().checkNumOfMoves()));
        t.start();
    }

    private JToolBar createToolBar() {
        JToolBar tb = new JToolBar();
        tb.setFloatable(false);
        JButton restart = new JButton("Restart");
        restart.addActionListener(e -> { board.getLevel().restart(); board.repaint(); });
        JButton next = new JButton("Next");
        next.addActionListener(e -> {
            current = (current+1) % levels.size();
            board.setLevel(levels.get(current));
        });
        tb.add(restart);
        tb.add(next);
        return tb;
    }

    private JMenuBar createMenuBar() {
        JMenuBar mb = new JMenuBar();
        JMenu menu = new JMenu("Game");
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(e -> System.exit(0));
        menu.add(exit);
        mb.add(menu);
        return mb;
    }
}
