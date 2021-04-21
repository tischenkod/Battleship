package battleship;

import java.security.InvalidParameterException;

public class Main {

    public static void main(String[] args) {
        Game game = new Game();
        System.out.println("The game starts!");
        Coordinates c;
        while (true) {
            try {
                c = game.askForShot();
                break;
            } catch (InvalidParameterException ip) {
                System.out.println(ip.getMessage());
            }
        }
        game.hit(c);
    }
}
