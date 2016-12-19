package se.piro.advent;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Rolf Staflin 2016-12-17 16:48
 */
public class Day17 extends Day {

    static final int SIZE = 4;
    static final String INPUT = "vkjiggvb";

    int shortestPathFound = Integer.MAX_VALUE;
    int longestPathFound = -1;

    String shortestPath = "";
    String longestPath = "";

    MessageDigest messageDigest;

    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        Day17 day = new Day17();
        day.go();
        print("Shortest path, length " + (day.shortestPathFound - INPUT.length()) + ": " + day.shortestPath.substring(INPUT.length()));
        print("Longest path, length " + (day.longestPathFound - INPUT.length()) + ": " + day.longestPath.substring(INPUT.length()));
    }

    public Day17() throws NoSuchAlgorithmException {
        messageDigest = MessageDigest.getInstance("MD5");
    }

    private void go() throws UnsupportedEncodingException {
        recurseToExit(new Position(0, 0), INPUT);
    }

    void recurseToExit(Position position, String input) throws UnsupportedEncodingException {

        if (position.isGoal()) {
            checkSolution(position, input);
        } else {
            Exits exits = new Exits(input, position);
            print(position.toString() + exits.toString() + " " + input.substring(INPUT.length()));
            if (exits.down)  { recurseToExit(position.down(),  input + 'D'); }
            if (exits.right) { recurseToExit(position.right(), input + 'R'); }
            if (exits.up)    { recurseToExit(position.up(),    input + 'U'); }
            if (exits.left)  { recurseToExit(position.left(),  input + 'L'); }
            print(position.toString() + exits.toString() + " " + input.substring(INPUT.length()) + " done.");
        }
    }

    private void checkSolution(Position position, String input) {
        print(position.toString() + " GOAL! Length " + (input.length() - INPUT.length()) + ". Path: " + input.substring(INPUT.length()));
        if (input.length() < shortestPathFound) {
            print("Shortest solution so far!");
            shortestPathFound = input.length();
            shortestPath = input;
        }
        if (input.length() > longestPathFound) {
            print("Longest solution so far!");
            longestPathFound = input.length();
            longestPath = input;
        }
    }


    class Position {
        int x;
        int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
            if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) {
                throw new IllegalArgumentException("Illegal position: " + toString());
            }
        }

        boolean isGoal() { return (x == SIZE - 1) && (y == SIZE - 1); }

        public Position up()    { return new Position(x, y - 1); }
        public Position down()  { return new Position(x, y + 1); }
        public Position left()  { return new Position(x - 1, y); }
        public Position right() { return new Position(x + 1, y); }

        @Override
        public String toString() {
            return "[" + x + ", " + y + ']';
        }
    }

    class Exits {
        boolean up;
        boolean down;
        boolean left;
        boolean right;

        public Exits(String input, Position position) throws UnsupportedEncodingException {
            String hash = hash(input);
            up = isOpen(hash, 0);
            down = isOpen(hash, 1);
            left = isOpen(hash, 2);
            right = isOpen(hash, 3);
            if (position.x == 0) { left = false; }
            if (position.x == SIZE - 1) { right = false; }
            if (position.y == 0) { up = false; }
            if (position.y == SIZE - 1) { down = false; }
        }

        private boolean isOpen(String hash, int index) {
            return hash.charAt(index) > 'a';
        }

        private String hash(String input) throws UnsupportedEncodingException {
            byte[] bytesOfMessage = input.getBytes("UTF-8");
            byte[] theDigest = messageDigest.digest(bytesOfMessage);

            StringBuilder stringBuilder = new StringBuilder(theDigest.length * 2);
            for (byte b : theDigest) {
                stringBuilder.append(Integer.toHexString((b & 0xf0) >> 4));
                stringBuilder.append(Integer.toHexString((b & 0x0f)));
            }
            return stringBuilder.toString();
        }

        @Override
        public String toString() {
            return "{" + (up ? "U" : ".") + (down ? "D" : ".") + (left ? "L" : ".") + (right ? "R" : ".") + '}';
        }
    }
}


// DDUD RLRR UDRD