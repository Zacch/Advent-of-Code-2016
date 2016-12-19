package se.piro.advent;

import java.util.Arrays;

/**
 * Created by Rolf Staflin 2016-12-18 10:42
 */
public class Day18 extends Day {

    static final String INPUT = ".^^.^^^..^.^..^.^^.^^^^.^^.^^...^..^...^^^..^^...^..^^^^^^..^.^^^..^.^^^^.^^^.^...^^^.^^.^^^.^.^^.^.";
    static final int COLUMNS = INPUT.length();

    int rows;
    Boolean[][] floor;

    public static void main(String[] args) {
        print("Part 1");
        Day18 day = new Day18(40);
        day.go();

        print("Part 2");
        day = new Day18(400000);
        day.go();
    }

    public Day18(int rows) {
        this.rows = rows;
        floor = new Boolean[rows][COLUMNS];
    }

    private void go() {

        Arrays.setAll(floor[0], i -> INPUT.charAt(i) == '^');
        for (int i = 1; i < rows; i++) {
            Boolean[] row = floor[i];
            Boolean[] previousRow = floor[i - 1];
            row[0] = isTrap(false, previousRow[0], previousRow[1]);
            for (int j = 1; j < COLUMNS - 1; j++) {
                row[j] = isTrap(previousRow[j - 1], previousRow[j], previousRow[j + 1]);
            }
            row[COLUMNS - 1] = isTrap(previousRow[COLUMNS - 2], previousRow[COLUMNS - 1], false);
        }
        printFloor();
    }

    private Boolean isTrap(Boolean left, Boolean center, Boolean right) {
        if (left && center && !right) { return true; }
        if (!left && center && right) { return true; }
        if (left && !center && !right) { return true; }
        if (!left && !center && right) { return true; }
        return false;
    }

    private void printFloor() {
        int safeTiles = 0;
        for (int row = 0; row < rows; row++) {
            StringBuilder stringBuilder = new StringBuilder();
            Arrays.stream(floor[row]).forEach(tile -> stringBuilder.append(tile ? '^' : '.'));
            String line = stringBuilder.toString();
            print(line);
            safeTiles += line.chars().filter(c -> c == '.').count();
        }
        print("Safe tiles: " + safeTiles);
    }
}
