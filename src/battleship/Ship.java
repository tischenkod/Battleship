package battleship;

import java.util.Arrays;

public class Ship {
    String name;
    int length;
    boolean[] damaged;

    public Ship(ShipType shipType) {
        this.name = shipType.name;
        this.length = shipType.length;
        damaged = new boolean[length];
        Arrays.fill(damaged, false);
    }

    public char toChar(int pos) {
        return damaged[pos] ? 'X' : 'O';
    }

    public void hit(int pos) {
        damaged[pos] = true;
    }
}
