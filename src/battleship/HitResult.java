package battleship;

public enum HitResult {
    WON("You sank the last ship. You won. Congratulations!"),
    SANK("You sank a ship! Specify a new target:"),
    HIT("You hit a ship! Try again:"),
    MISS("You missed!");

    String message;

    HitResult(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
