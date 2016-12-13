package se.piro.advent;

import java.util.*;

/**
 * Created by Rolf Staflin 2016-12-13 08:37
 */
public class Day13 extends Day {

    private final Position START = new Position(1, 1, 0);

    private static final int input = 1364;
    private static final int SIZE = 50;
    private final Position GOAL = new Position(31, 39);

/*
    // Test settings
    private static final int input = 10;
    private static final int SIZE = 10;
    private final Position GOAL = new Position(7, 4);
*/

    public static void main(String[] args) {
        Day13 day = new Day13();
        day.go();
    }

    private void go() {
        Floor floor = new Floor();
        int distance = widthFirstSearch(START, GOAL, floor);
        print(" ");
        print("The distance is " + distance);
        print(" ");

        print("Part 2");
        print(" ");
        print("The number of locations is " + countPositions(START, 50, floor));
    }

    int widthFirstSearch(Position start, Position goal, Floor floor) {
        Map<Position, Integer> visited = new HashMap<>(SIZE * SIZE);
        Set<Position> thisGeneration = new HashSet<>(SIZE * 2);

        visited.put(start, 0);
        thisGeneration.add(start);

        while (!thisGeneration.isEmpty()) {
            Set<Position> nextGeneration = new HashSet<>(SIZE * 2);
            for (Position position : thisGeneration) {
                List<Position> moves = getMoves(position, visited, floor);
                    for (Position move : moves) {
                    if (move.equals(goal)) {
                        floor.print(visited, goal);
                        return move.distance;
                    }
                    visited.put(move, move.distance);
                    nextGeneration.add(move);
                }
            }
            thisGeneration = nextGeneration;
        }
        return -1;
    }

    private List<Position> getMoves(Position position, Map<Position, Integer> visited, Floor floor) {
        List<Position> moves = new ArrayList<>();
        if (position.y > 0) moves.add(new Position(position.x, position.y - 1, position.distance + 1));
        if (position.x > 0) moves.add(new Position(position.x - 1, position.y, position.distance + 1));
        if (position.y  + 1 < SIZE) moves.add(new Position(position.x, position.y + 1, position.distance + 1));
        if (position.x + 1 < SIZE) moves.add(new Position(position.x + 1, position.y, position.distance + 1));
        moves.removeIf(p -> visited.keySet().contains(p) || floor.isWall(p));
        return moves;
    }

    int countPositions(Position start, int distance, Floor floor) {
        Map<Position, Integer> visited = new HashMap<>(SIZE * SIZE);
        Set<Position> thisGeneration = new HashSet<>(SIZE * 2);

        visited.put(start, 0);
        thisGeneration.add(start);

        while (distance-- > 0) {
            Set<Position> nextGeneration = new HashSet<>(SIZE * 2);
            for (Position position : thisGeneration) {
                List<Position> moves = getMoves(position, visited, floor);
                for (Position move : moves) {
                    visited.put(move, move.distance);
                    nextGeneration.add(move);
                }
            }
            thisGeneration = nextGeneration;
        }

        return visited.size();
    }

    //--------------------------------------------------------------------------
    class Floor {
        char[][] floor = new char[SIZE][SIZE];

        Floor() {
            for (int y = 0; y < SIZE; y++) {
                for (int x = 0; x < SIZE; x++) {
                    floor[x][y] = calculateContents(x, y);
                }
            }
        }

        private char calculateContents(long x, long y) {
            long hash = x*x + 3*x + 2*x*y + y + y*y + input;
            String binary = Long.toBinaryString(hash);
            long ones = binary.chars()
                    .mapToObj(i -> (char) i)
                    .filter(i -> i == '1')
                    .count();
            return (ones & 0x1) == 1  ? '#' : '.';
        }

        void print(Map<Position, Integer> positions, Position goal) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int y = 0; y < SIZE; y++) {
                for (int x = 0; x < SIZE; x++) {
                    Position position = new Position(x, y);
                    if (position.equals(goal)) {
                        stringBuilder.append('*');
                    } else if (positions.keySet().contains(position)) {
                        int distance = positions.get(position);
                        if (distance < 10) {
                            stringBuilder.append((char)('0' + distance));
                        } else if (distance < 36) {
                            stringBuilder.append((char)('a' + (distance - 9)));
                        } else {
                            stringBuilder.append((char)('A' + (distance - 36)));
                        }

                    } else {
                        stringBuilder.append(floor[x][y]);
                    }
                }
                Day.print(stringBuilder.toString());
                stringBuilder = new StringBuilder();
            }
        }

        boolean isWall(Position p) {
            return floor[p.x][p.y] == '#';
        }
    }

    //--------------------------------------------------------------------------
    class Position {
        int x;
        int y;

        int distance = 0;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Position(int x, int y, int distance) {
            this.x = x;
            this.y = y;
            this.distance = distance;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Position position = (Position) o;

            if (x != position.x) return false;
            return y == position.y;
        }

        @Override
        public int hashCode() {
            return 255 * x + y;
        }

        @Override
        public String toString() {
            return "<" + x + ", " + y + ", distance=" + distance + ">";
        }
    }
}
