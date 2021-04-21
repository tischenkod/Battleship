package battleship;

import java.security.InvalidParameterException;

import static java.lang.Math.*;
import static java.lang.Math.abs;

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

    public HitResult hit() {
        Coordinates c = getHit();
        HitResult hr = cells[c.y - 1][c.x - 1].hit();
        if (hr.equals(HitResult.SANK) && (--shipCount == 0))
            return HitResult.WON;
        return hr;
    }

    public void addShips() {
        for (ShipType shipType: ShipType.values()) {
            System.out.println(this);
            while (true) {
                System.out.printf("%nEnter the coordinates of the %s (%d cells):%n",
                        shipType.name,
                        shipType.length);
                String[] placement = Input.nextLine().
                        replaceAll("\\s{2,}", " ").
                        trim().
                        toLowerCase().
                        split(" ");
                if (placement.length != 2 ||
                        !placement[0].matches("[a-j](10|[1-9])") ||
                        !placement[1].matches("[a-j](10|[1-9])")) {
                    System.out.println("Error:\nInput must match the pattern \"[a-jA-J]([1-9]|10)]\" \"[a-jA-J]([1-9]|10)]\"");
                    continue;
                }

                int x1 = Integer.parseInt(placement[0].substring(1));
                int y1 = 1 + placement[0].charAt(0) - 'a';
                int x2 = Integer.parseInt(placement[1].substring(1));
                int y2 = 1 + placement[1].charAt(0) - 'a';

                if (x1 == x2) {
                    if (abs(y1 - y2) + 1 != shipType.length) {
                        System.out.println("Error:\nInvalid ship length");
                        continue;
                    }
                    try {
                        place(new Ship(shipType), x1, min(y1, y2), Direction.VERTICAL);
                        break;
                    } catch (Exception exception){
                        System.out.println("Error:\n" + exception.getMessage());
                    }
                } else {
                    if (y1 == y2) {
                        if (abs(x1 - x2) + 1 != shipType.length) {
                            System.out.println("Error:\nInvalid ship length");
                            continue;
                        }
                        try {
                            place(new Ship(shipType), min(x1, x2), y1, Direction.HORIZONTAL);
                            break;
                        } catch (Exception exception){
                            System.out.println("Error:\n" + exception.getMessage());
                        }
                    } else {
                        System.out.println("Error:\nShip must be oriented vertically or horizontally");
                    }
                }
            }
        }
        System.out.println(this);
    }

    private Coordinates getHit() {
        while (true) {
            try {
                return new Coordinates(Input.nextLine());
            } catch (InvalidParameterException ip) {
                System.out.println(ip.getMessage());
            }
        }
    }

}
