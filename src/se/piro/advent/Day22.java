package se.piro.advent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Rolf Staflin 2016-12-22 07:35
 */
public class Day22 extends Day {

    ArrayList<Integer> used = new ArrayList<>();
    ArrayList<Integer> free = new ArrayList<>();

    final static int GRID_SIZE_X = 34;
    final static int GRID_SIZE_Y = 30;
    Node[][] grid = new Node[GRID_SIZE_X][GRID_SIZE_Y];

    public static void main(String[] args) {
        Day22 day = new Day22();
        day.go();
    }

    private void go() {
        readFile("data/day22.txt");
        Collections.sort(used);
        Collections.sort(free);
        part1();

        part2();
    }

    private void part2() {
        print("\n------------ Part 2 ------------");
        print("The grid looks like this:\n");
        printGrid();
        int moves = 0;
        print("\nThe empty node is at (4,25)");
        print("Moving it left to (1,25) takes three moves");
        moves += 3;
        print("Moving it up to (1,0) takes 25 moves");
        moves += 25;
        print("Moving it right to (33,0) takes 32 moves");
        moves += 32;
        print("So far " + moves + " moves, and the goal is at (32, 0)");
        print("");
        print("Now, it takes five moves to transfer the goal one step to the left.");
        print("There are 32 steps to go, and 5 * 32 = " + (5 * 32));
        moves += 5 * 32;
        print("");
        print("So, in all it takes a minimum of " + moves + " moves!");
    }

    private void printGrid() {
        for (int y = 0; y < GRID_SIZE_Y; y++) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int x = 0; x < GRID_SIZE_X; x++) {
                Node node = grid[x][y];
                if (x == GRID_SIZE_X - 1 && y == 0) {
                    stringBuilder.append('G');
                } else if (node.used == 0) {
                    stringBuilder.append('_');
                } else if (node.used < 85) {
                    stringBuilder.append('.');
                } else {
                    stringBuilder.append('#');
                }
                stringBuilder.append(' ');
            }
            print(stringBuilder.toString());
        }
    }

    private void part1() {
        int nodes = free.size();
        int largestFree = free.get(nodes - 1);

        int pairs = 0;
        List<Integer> usedList = used.stream().filter(x -> x <= largestFree).collect(Collectors.toList());
        for (int size : usedList) {
            if (size == 0) {
                continue;
            }
            int index = Collections.binarySearch(free, size - 1);
            if (index < 0) {
                index = -(index + 1);
            }
            while (free.get(index) <= size) {
                index++;
            }
            pairs += nodes - index;
        }
        print("Found " + pairs + " pairs.");
    }

    @Override
    protected void handleLine(String line) {
        // Filesystem              Size  Used  Avail  Use%
        // /dev/grid/node-x0-y0     89T   65T    24T   73%
        String[] tokens = line.split("[T ]+");
        used.add(Integer.parseInt(tokens[2]));
        free.add(Integer.parseInt(tokens[3]));
        Node node = new Node(tokens);
        grid[node.x][node.y] = node;
    }

    class Node {
        int x;
        int y;
        int capacity;
        int used;
        int free;

        Node(String[] tokens) {
            capacity = Integer.parseInt(tokens[1]);
            used = Integer.parseInt(tokens[2]);
            free = Integer.parseInt(tokens[3]);
            String[] urlParts = tokens[0].split("[xy -]+");
            x = Integer.parseInt(urlParts[1]);
            y = Integer.parseInt(urlParts[2]);
        }

        @Override
        public String toString() {
            return "<" + x +
                    ", " + y +
                    "> " + used +
                    "/" + capacity + " (" + free +
                    ')';
        }
    }
}
