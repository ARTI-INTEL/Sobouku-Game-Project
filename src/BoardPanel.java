import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class BoardPanel extends JPanel implements KeyListener {
    private static final int TILE_SIZE = 48;
    private Level level;
    private final JLabel statusLabel;

    public BoardPanel(Level level, JLabel statusLabel) {
        this.level = level;
        this.statusLabel = statusLabel;
        Dimension pref = new Dimension(level.getWidth()*TILE_SIZE, level.getHeight()*TILE_SIZE);
        setPreferredSize(pref);
        setFocusable(true);
        addKeyListener(this);
    }

    public Level getLevel() { return level; }
    public void setLevel(Level level) {
        this.level = level;
        setPreferredSize(new Dimension(level.getWidth()*TILE_SIZE, level.getHeight()*TILE_SIZE));
        revalidate();
    }

    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (level == null) return;

        // draw background
        g.setColor(Color.GRAY);
        g.fillRect(0,0,getWidth(),getHeight());

        // draw tiles
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
                g.fillOval(x+12,y+12, TILE_SIZE-24, TILE_SIZE-24);
            } else {
                g.setColor(new Color(220,220,220));
                g.fillRect(x,y,TILE_SIZE,TILE_SIZE);
            }
            g.setColor(Color.DARK_GRAY);
            g.drawRect(x,y,TILE_SIZE,TILE_SIZE);
        }

        // draw crates
        for (Crate c : level.getCrates()) {
            int x = c.getX()*TILE_SIZE;
            int y = c.getY()*TILE_SIZE;
            g.setColor(new Color(170, 90, 50));
            g.fillRoundRect(x+6,y+6,TILE_SIZE-12,TILE_SIZE-12,8,8);
            g.setColor(Color.BLACK);
            g.drawRoundRect(x+6,y+6,TILE_SIZE-12,TILE_SIZE-12,8,8);
        }

        // draw keeper
        WarehouseKeeper k = level.getKeeper();
        int kx = k.getX()*TILE_SIZE;
        int ky = k.getY()*TILE_SIZE;
        g.setColor(new Color(50,100,200));
        g.fillOval(kx+8,ky+8,TILE_SIZE-16,TILE_SIZE-16);
        g.setColor(Color.BLACK);
        g.drawOval(kx+8,ky+8,TILE_SIZE-16,TILE_SIZE-16);

        statusLabel.setText("Moves: " + k.checkNumOfMoves());
    }

    private boolean isWall(int x, int y) {
        for (Tile t : level.getTiles()) if (t.getX()==x && t.getY()==y && !t.isWalkable()) return true;
        return false;
    }

    private Crate crateAt(int x, int y) {
        for (Crate c : level.getCrates()) if (c.getX()==x && c.getY()==y) return c;
        return null;
    }

    private void attemptMove(String dir) {
        WarehouseKeeper k = level.getKeeper();
        int tx = k.getX();
        int ty = k.getY();
        switch(dir) { case "up": ty--; break; case "down": ty++; break; case "left": tx--; break; case "right": tx++; break; }

        if (isWall(tx,ty)) {
            Toolkit.getDefaultToolkit().beep();
            return;
        }

        Crate c = crateAt(tx,ty);
        if (c == null) {
            k.move(dir);
        } else {
            int nx = c.getX();
            int ny = c.getY();
            switch(dir) { case "up": ny--; break; case "down": ny++; break; case "left": nx--; break; case "right": nx++; break; }
            if (isWall(nx,ny) || crateAt(nx,ny) != null) { Toolkit.getDefaultToolkit().beep(); return; }
            // push
            c.move(dir);
            k.push(c, dir);
        }

        repaint();
        if (level.complete()) {
            JOptionPane.showMessageDialog(this, "Level " + level.getNumber() + " complete! Moves: " + level.getKeeper().checkNumOfMoves());
        }
    }

    // KeyListener
    @Override public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP: attemptMove("up"); break;
            case KeyEvent.VK_DOWN: attemptMove("down"); break;
            case KeyEvent.VK_LEFT: attemptMove("left"); break;
            case KeyEvent.VK_RIGHT: attemptMove("right"); break;
        }
    }
    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}