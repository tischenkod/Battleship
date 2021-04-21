package battleship;

import java.security.InvalidParameterException;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class BattleField {
    int shipCount;

    Cell[][] cells;

    public BattleField() {
        shipCount = 0;
        cells = new Cell[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

    public int getShipCount() {
        return shipCount;
    }

    void place(Ship ship, int x, int y, Direction direction) {
        if (ship == null || x < 1 || x > 10 || y < 1 || y > 10) {
            throw new InvalidParameterException();
        }
        if (!isFree(max(1, x - 1), max(1, y - 1),
                min(10, direction == Direction.HORIZONTAL ? x + ship.length : x + 1),
                min(10, direction == Direction.HORIZONTAL ? y + 1 : y + ship.length))) {
            throw new InvalidParameterException();
        }

        for (int i = 0; i < ship.length; i++) {
            if (direction == Direction.HORIZONTAL) {
                cells[y - 1][x + i - 1].place(ship, i);
            } else {
                cells[y + i - 1][x - 1].place(ship, i);
            }
        }
        shipCount++;
    }

    private boolean isFree(int xFrom, int yFrom, int xTo, int yTo) {
        if (xFrom > xTo || yFrom > yTo) {
            throw new InvalidParameterException();
        }
        for (int x = xFrom; x <= xTo; x++) {
            for (int y = yFrom; y <= yTo; y++) {
                if (!cells[y - 1][x - 1].isFree()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return toString(false);
    }

    public String toString(boolean fog) {
        StringBuilder sb = new StringBuilder("  1 2 3 4 5 6 7 8 9 10");
        char letter = 'A';
        for (int i = 0; i < 10; i++) {
            sb.append('\n').append(letter++);
            for (int j = 0; j < 10; j++) {
                sb.append(' ').append(cells[i][j].toChar(fog));
            }
        }
        return sb.toString();
    }

    public HitResult hit(Coordinates c) {
        HitResult hr = cells[c.y - 1][c.x - 1].hit();
        if (hr.equals(HitResult.SANK) && (--shipCount == 0))
            return HitResult.WON;
        return hr;
    }
}
