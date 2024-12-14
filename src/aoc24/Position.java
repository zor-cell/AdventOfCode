package aoc24;

import java.util.Objects;

public class Position {
    public int i;
    public int j;

    public Position(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public boolean inBounds(int rows, int cols) {
        return i >= 0 && i <= rows - 1 && j >= 0 && j <= cols - 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position position)) return false;
        return i == position.i && j == position.j;
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, j);
    }
}
