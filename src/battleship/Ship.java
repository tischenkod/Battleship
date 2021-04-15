package battleship;

public class Ship {
    int name;
    int length;
    boolean[] damaged;

    public Ship(int name, int length) {
        this.name = name;
        this.length = length;
        damaged = new boolean[length];
    }

    public char toChar(int pos) {
        return damaged[pos] ? 'X' : 'O';
    }
}
