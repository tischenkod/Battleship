package battleship;

import java.security.InvalidParameterException;

import static java.lang.String.format;

public class Coordinates {
    int x;
    int y;

    public Coordinates(String s) {
        s = s.trim().toLowerCase();
        if (!s.matches("[a-j](10|[1-9])"))
            throw new InvalidParameterException(format("Error: String %s, doesn't match pattern \"[a-j](10|[1-9])\"", s));

        x = Integer.parseInt(s.substring(1));
        y = 1 + s.charAt(0) - 'a';
    }

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
