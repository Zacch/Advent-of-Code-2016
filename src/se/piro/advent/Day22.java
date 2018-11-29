package se.piro.advent;

import java.util.*;
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
        part2_2018();
    }


    class State_2018 {
        int empty_x;
        int empty_y;
        int payload_x;
        int payload_y;
        int length;

        public State_2018() { }

        public State_2018(State_2018 other) {
            this.empty_x = other.empty_x;
            this.empty_y = other.empty_y;
            this.payload_x = other.payload_x;
            this.payload_y = other.payload_y;
            this.length = other.length + 1;
        }

        public State_2018 left() {
            State_2018 result = new State_2018(this);
            if (empty_x > 0) {
                result.empty_x--;
                if (grid[result.empty_x][result.empty_y].used > 85) {
                    return this;
                }
                if (result.empty_x == result.payload_x && result.empty_y == result.payload_y) {
                    result.payload_x++;
                }
            }
            return result;
        }

        public State_2018 right() {
            State_2018 result = new State_2018(this);
            if (empty_x < GRID_SIZE_X - 1) {
                result.empty_x++;
                if (grid[result.empty_x][result.empty_y].used > 85) {
                    return this;
                }
                if (result.empty_x == result.payload_x && result.empty_y == result.payload_y) {
                    result.payload_x--;
                }
            }
            return result;
        }

        public State_2018 up() {
            State_2018 result = new State_2018(this);
            if (empty_y > 0) {
                result.empty_y--;
                if (grid[result.empty_x][result.empty_y].used > 85) {
                    return this;
                }
                if (result.empty_x == result.payload_x && result.empty_y == result.payload_y) {
                    result.payload_y++;
                }
            }
            return result;
        }

        public State_2018 down() {
            State_2018 result = new State_2018(this);
            if (empty_y < GRID_SIZE_Y - 1) {
                result.empty_y++;
                if (grid[result.empty_x][result.empty_y].used > 85) {
                    return this;
                }
                if (result.empty_x == result.payload_x && result.empty_y == result.payload_y) {
                    result.payload_y--;
                }
            }
            return result;
        }

        @Override
        public String toString() {
            return "State_2018{" +
                    "empty (" + empty_x + ", " + empty_y +
                    "), payload (" + payload_x + ", " + payload_y +
                    "), length " + length + "}";
        }

        @Override
        public boolean equals(Object object) {
            if (object instanceof State_2018) {
                State_2018 other = (State_2018) object;
                return empty_x == other.empty_x &&
                        empty_y == other.empty_y &&
                        payload_x == other.payload_x &&
                        payload_y == other.payload_y;
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return ("" + empty_x + ", " + empty_y + ", " + payload_x + ", " + payload_y).hashCode();
        }
    }

    private void part2_2018() {
        print("____________________________");
        print("Real implementation in 2018:");
        Set<State_2018> visitedStates = new HashSet<>();
        Set<State_2018> statesToTry = new HashSet<>();
        Vector<State_2018> queue = new Vector<>();
        State_2018 startState = new State_2018();
        Arrays.stream(grid).forEach(r -> Arrays.stream(r).forEach(n -> {
            if (n.used == 0) {
                startState.empty_x = n.x;
                startState.empty_y = n.y;
            }
        }));
        startState.payload_x = GRID_SIZE_X - 1;
        startState.payload_y = 0;
        statesToTry.add(startState);
        queue.add(startState);
        boolean done;
        do {
            State_2018 nextState = queue.firstElement();
            queue.remove(0);
            statesToTry.remove(nextState);
            done = search_2018(nextState, visitedStates, statesToTry, queue);
        } while (!done);
    }

    private boolean search_2018(State_2018 startState, Set<State_2018> visitedStates, Set<State_2018> statesToTry, Vector<State_2018> queue) {
        if (startState.payload_x == 0 && startState.payload_y == 0) {
            print("Breadth-first search found solution in " + startState.length + " moves.");
            return true;
        }

        visitedStates.add(startState);
        State_2018 left = startState.left();
        if (!visitedStates.contains(left) && !statesToTry.contains(left)) {
            queue.add(left);
            statesToTry.add(left);
        }
        State_2018 right = startState.right();
        if (!visitedStates.contains(right) && !statesToTry.contains(right)) {
            queue.add(right);
            statesToTry.add(right);
        }
        State_2018 up = startState.up();
        if (!visitedStates.contains(up) && !statesToTry.contains(up)) {
            queue.add(up);
            statesToTry.add(up);
        }
        State_2018 down = startState.down();
        if (!visitedStates.contains(down) && !statesToTry.contains(down)) {
            queue.add(down);
            statesToTry.add(down);
        }
        return false;
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
