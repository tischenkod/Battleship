package battleship;

public class Ship {
    String name;
    int length;
    boolean[] damaged;

    public Ship(ShipType shipType) {
        this.name = shipType.name;
        this.length = shipType.length;
        damaged = new boolean[length];
    }

    public char toChar(int pos) {
        return damaged[pos] ? 'X' : 'O';
    }
}
