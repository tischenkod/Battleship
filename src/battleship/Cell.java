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

    public char toChar(boolean fog) {
        if (ship == null) {
            return damaged ? 'M' : '~';
        } else {
            return !fog || damaged ? ship.toChar(pos) : '~';
        }
    }

    public HitResult hit() {
        if (damaged)
            return HitResult.MISS;
        damaged = true;
        if (ship == null)
            return HitResult.MISS;
        return ship.hit(pos);
    }
}
