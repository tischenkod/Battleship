package battleship;

import java.security.InvalidParameterException;
import java.util.Scanner;

import static java.lang.Math.abs;
import static java.lang.Math.min;

public class Game {
    BattleField battleField;
    Scanner scanner;

    public Game() {
        battleField = new BattleField();
        scanner = new Scanner(System.in);

        addShips();
    }

    private void addShips() {
        for (ShipType shipType: ShipType.values()) {
            System.out.println(battleField);
            while (true) {
                System.out.printf("%nEnter the coordinates of the %s (%d cells):%n",
                        shipType.name,
                        shipType.length);
                String[] placement = scanner.nextLine().
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
                Direction direction;

                if (x1 == x2) {
                    if (abs(y1 - y2) + 1 != shipType.length) {
                        System.out.println("Error:\nInvalid ship length");
                        continue;
                    }
                    try {
                        battleField.place(new Ship(shipType), x1, min(y1, y2), Direction.VERTICAL);
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
                            battleField.place(new Ship(shipType), min(x1, x2), y1, Direction.HORIZONTAL);
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
        System.out.println(battleField);
    }

    Coordinates askForShot() {

        return new Coordinates(scanner.nextLine());
    }

    public void run() {
        System.out.println("The game starts!");
        System.out.println(battleField.toString(true));
        System.out.println("Take a shot!");
        while (battleField.getShipCount() > 0) {
            Coordinates c;
            while (true) {
                try {
                    c = askForShot();
                    break;
                } catch (InvalidParameterException ip) {
                    System.out.println(ip.getMessage());
                }
            }
            HitResult hr = battleField.hit(c);
            System.out.println(battleField);
            System.out.println(hr);
        }
    }
}
