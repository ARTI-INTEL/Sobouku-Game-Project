package sokoban.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import sokoban.*;

public class BoardPanel extends JPanel implements KeyListener {
    private static final int TILE_SIZE = 48;
    private Level level;
    public boolean levelCompleted = false;

    public BoardPanel(Level level) {
        this.level = level;
        Dimension pref = new Dimension(level.getWidth()*TILE_SIZE, level.getHeight()*TILE_SIZE);
        setPreferredSize(pref);
        setFocusable(true);
        addKeyListener(this);
    }

    public Level getLevel() { return level; }
    public void setLevel(Level lvl) {
        this.level = lvl;
        setPreferredSize(new Dimension(lvl.getWidth()*TILE_SIZE, lvl.getHeight()*TILE_SIZE));
        revalidate();
        repaint();
        requestFocusInWindow();
    }

    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (level == null) return;
        // draw background
        g.setColor(Color.GRAY);
        g.fillRect(0,0,getWidth(),getHeight());

        for (Tile t : level.getTiles()) {
            int x = t.getX()*TILE_SIZE;
            int y = t.getY()*TILE_SIZE;
            if (t instanceof Wall) {
                g.setColor(new Color(80,80,80));
                g.fillRect(x,y,TILE_SIZE,TILE_SIZE);
            } else if (t instanceof Diamond) {
                g.setColor(new Color(200,200,220));
                g.fillRect(x,y,TILE_SIZE,TILE_SIZE);
                g.setColor(Color.MAGENTA);
                g.fillOval(x+12,y+12,TILE_SIZE-24,TILE_SIZE-24);
            } else {
                g.setColor(new Color(220,220,220));
                g.fillRect(x,y,TILE_SIZE,TILE_SIZE);
            }
            g.setColor(Color.DARK_GRAY);
            g.drawRect(x,y,TILE_SIZE,TILE_SIZE);
        }

        for (Crate c : level.getCrates()) {
            int x = c.getX()*TILE_SIZE;
            int y = c.getY()*TILE_SIZE;
            g.setColor(new Color(170,90,50));
            g.fillRoundRect(x+6,y+6,TILE_SIZE-12,TILE_SIZE-12,8,8);
            g.setColor(Color.BLACK);
            g.drawRoundRect(x+6,y+6,TILE_SIZE-12,TILE_SIZE-12,8,8);
        }

        WarehouseKeeper k = level.getKeeper();
        int kx = k.getX()*TILE_SIZE;
        int ky = k.getY()*TILE_SIZE;
        g.setColor(new Color(50,100,200));
        g.fillOval(kx+8,ky+8,TILE_SIZE-16,TILE_SIZE-16);
        g.setColor(Color.BLACK);
        g.drawOval(kx+8,ky+8,TILE_SIZE-16,TILE_SIZE-16);
    }

    private void attemptMove(String dir) {
        // We pass 'level' to the new move() method.
        boolean moveSuccessful = level.getKeeper().move(dir, level);

        if (moveSuccessful) {
            repaint();
            if (level.complete()) {
                // Fire off the completion message *after* Swing repaints
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(this, "Level " + level.getNumber() + " complete! Moves: " + level.getKeeper().checkNumOfMoves());
                });
            }
        } else {
            // Move failed (blocked by wall or crate), play a beep
            Toolkit.getDefaultToolkit().beep();
        }
    }


    @Override public void keyPressed(KeyEvent e) {
        // This logic remains the same
        switch(e.getKeyCode()) {
            case KeyEvent.VK_UP: attemptMove("up"); break;
            case KeyEvent.VK_DOWN: attemptMove("down"); break;
            case KeyEvent.VK_LEFT: attemptMove("left"); break;
            case KeyEvent.VK_RIGHT: attemptMove("right"); break;
            case KeyEvent.VK_W: attemptMove("up"); break;
            case KeyEvent.VK_S: attemptMove("down"); break;
            case KeyEvent.VK_A: attemptMove("left"); break;
            case KeyEvent.VK_D: attemptMove("right"); break;
        }
    }
    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}