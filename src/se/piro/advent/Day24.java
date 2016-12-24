package se.piro.advent;

import java.util.*;

/**
 * Created by Rolf Staflin 2016-12-24 10:41
 */
public class Day24 extends Day {

    private static final int SIZE_Y = 50;
    private static final int SIZE_X = 200;
    private final Position NOWHERE = new Position(-1, -1, -1);

    boolean[][] maze = new boolean[SIZE_X][SIZE_Y];    // True = wall
    Position[] pointsOfInterest = new Position[10];

    int highestPoi = 0;
    int highestY = 0;
    private int[][] distances;

    boolean part2 = false;

    public static void main(String[] args) {
        Day24 day = new Day24();
        day.go();
    }

    private void go() {
        for (int i = 0; i < 10; i++) {
            pointsOfInterest[i] = NOWHERE;
        }
        readFile("data/day24.txt");
/*
        handleLine("###########");
        handleLine("#0.1.....2#");
        handleLine("#.#######.#");
        handleLine("#4.......3#");
        handleLine("###########");
        printMaze();
*/
        // Calculate all distances
        distances = new int[highestPoi + 1][highestPoi + 1];
        for (int i = 0; i < highestPoi + 1; i++) {
            distances[i][i] = 0;
            for (int j = i + 1; j < highestPoi + 1; j++) {
                distances[i][j] = findShortestPath(pointsOfInterest[i], pointsOfInterest[j]);
                distances[j][i] = distances[i][j];
            }
        }

        print("Shortest path through all points has length " + travellingSalesman());
        part2 = true;
        print("Shortest path through all points and back to the start has length " + travellingSalesman());
    }

    @Override
    protected void handleLine(String line) {
        for (int x = 0; x < line.length(); x++) {
            final char c = line.charAt(x);
            maze[x][highestY] = (c == '#');
            if (Character.isDigit(c)) {
                int digit = c - '0';
                highestPoi = Math.max(highestPoi, digit);
                pointsOfInterest[digit] = new Position(x, highestY);
            }
        }
        highestY++;
    }


    int findShortestPath(Position start, Position goal) {
        start.distance = 0;
        Map<Position, Integer> visited = new HashMap<>(SIZE_X * SIZE_Y);
        Set<Position> thisGeneration = new HashSet<>(SIZE_X * 2);

        visited.put(start, 0);
        thisGeneration.add(start);

        while (!thisGeneration.isEmpty()) {
            Set<Position> nextGeneration = new HashSet<>(SIZE_X * 2);
            for (Position position : thisGeneration) {
                List<Position> moves = getMoves(position, visited);
                for (Position move : moves) {
                    if (move.equals(goal)) {
                      //  printMaze(visited, goal);
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

    private List<Position> getMoves(Position position, Map<Position, Integer> visited) {
        List<Position> moves = new ArrayList<>();
        if (position.y > 0) moves.add(new Position(position.x, position.y - 1, position.distance + 1));
        if (position.x > 0) moves.add(new Position(position.x - 1, position.y, position.distance + 1));
        if (position.y  + 1 < SIZE_Y) moves.add(new Position(position.x, position.y + 1, position.distance + 1));
        if (position.x + 1 < SIZE_X) moves.add(new Position(position.x + 1, position.y, position.distance + 1));
        moves.removeIf(p -> visited.keySet().contains(p) || maze[p.x][p.y]);
        return moves;
    }


    private int travellingSalesman() {
        LinkedList<Integer> pointsToTry = new LinkedList<>();
        for (int i = 1; i <= highestPoi; i++) {
            pointsToTry.add(i);
        }
        return recurse(pointsToTry, 0);
    }

    private int recurse(LinkedList<Integer> pointsToTry, int currentPoint) {
        if (pointsToTry.size() == 1) {
            return distances[currentPoint][pointsToTry.get(0)] + (part2 ? distances[0][pointsToTry.get(0)] : 0);
        }
        LinkedList<Integer> copy = new LinkedList<>();
        copy.addAll(pointsToTry);
        int shortestDistance = Integer.MAX_VALUE;
        for (Integer firstPoint : pointsToTry) {
            copy.remove(firstPoint);
            int distance = distances[currentPoint][firstPoint] + recurse(copy, firstPoint);
            shortestDistance = Math.min(distance, shortestDistance);
            copy.add(firstPoint);
        }
        return shortestDistance;
    }


    private void printMaze(Map<Position, Integer> visited, Position goal) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int y = 0; y < highestY; y++) {
            for (int x = 0; x < SIZE_X; x++) {
                Position position = new Position(x, y);
                if (position.equals(goal)) {
                    stringBuilder.append('*');
                } else if (visited.keySet().contains(position)) {
                    int distance = visited.get(position);
                    if (distance < 10) {
                        stringBuilder.append((char)('0' + distance));
                    } else if (distance < 36) {
                        stringBuilder.append((char)('a' + (distance - 9)));
                    } else {
                        stringBuilder.append((char)('A' + (distance - 36)));
                    }

                } else {
                    stringBuilder.append(maze[x][y] ? '#' : '.');
                }
            }
            Day.print(stringBuilder.toString());
            stringBuilder = new StringBuilder();
        }
    }

    void printMaze() {
        for (int y = 0; y < highestY; y++) {
            StringBuilder stringBuilder = new StringBuilder(SIZE_X);
            for (int x = 0; x < SIZE_X; x++) {
                Position point = new Position(x, y);
                boolean poiFound = false;
                for (int i = 0; i < pointsOfInterest.length; i++) {
                    if (point.equals(pointsOfInterest[i])) {
                        stringBuilder.append(Integer.toString(i));
                        poiFound = true;
                        break;
                    }
                }
                if (!poiFound) {
                    stringBuilder.append(maze[x][y] ? '#' : ' ');
                }
            }
            print(stringBuilder.toString());
        }
        print("");
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
            return 31 * x + y;
        }

        @Override
        public String toString() {
            return "[" + x + ", " + y + " (" + distance + ")]";
        }
    }
}