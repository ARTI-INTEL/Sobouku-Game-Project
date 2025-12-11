package sokoban;

public interface Tile {

    int getX();
    int getY();

    void setX(int x);
    void setY(int y);

    boolean isWalkable();   // player can stand on it?
    boolean isPushable();   // crates can be pushed onto it?
    char getSymbol();       // for debugging / printing
}
