package battleship;

public class Game {
    static final int playerCount = 2;

    BattleField[] players;
    int currentPlayer;

    public Game() {

        players = new BattleField[playerCount];
        currentPlayer = 0;

        for (int i = 0; i < playerCount; i++) {
            System.out.printf("Player %d, place your ships on the game field%n", i + 1);
            players[i] = new BattleField();
            players[i].addShips();
            if (i < playerCount - 1)
                askForNext();
        }
    }

    @Override
    public String toString() {
        return players[1 - currentPlayer].toString(true) +
                "\n---------------------\n" +
                players[currentPlayer].toString(false);
    }

    public void run() {
        while (!gameOver()) {
            askForNext();
            System.out.println(this);
            System.out.printf("Player %d, it's your turn:%n", currentPlayer + 1);
            System.out.println(players[1 - currentPlayer].hit());
            currentPlayer = 1 - currentPlayer;
        }
    }

    private void askForNext() {
        System.out.println("Press Enter and pass the move to another player\n...");
        Input.nextLine();
    }


    private boolean gameOver() {
        for (BattleField player: players)
            if (player.getShipCount() == 0)
                return true;
        return false;
    }
}
