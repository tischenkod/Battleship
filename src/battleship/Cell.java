package battleship;

public class Cell {
    Ship ship;
    int pos;
    boolean damaged;

    public Cell() {
    }

    public boolean isFree() {
        return ship == null;
    }

    void place(Ship ship, int pos) {
        this.ship = ship;
        this.pos = pos;
        damaged = false;
    }

    public char toChar() {
        if (ship == null) {
            return damaged ? 'M' : '~';
        } else {
            return ship.toChar(pos);
        }
    }

    public boolean hit() {
        if (damaged)
            return false;
        damaged = true;
        if (ship == null)
            return false;
        ship.hit(pos);
        return true;
    }
}
