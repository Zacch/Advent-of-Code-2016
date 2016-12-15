package se.piro.advent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Rolf Staflin 2016-12-15 09:04
 */
public class Day15 extends Day {

    List<Disc> discs = new ArrayList<>();

    public static void main(String[] args) {
        Day15 day = new Day15();
        day.go();
    }

    private void go() {
        readFile();
        iterate();

        print("---- Part 2 -----");
        discs = new ArrayList<>();
        readFile();
        discs.add(new Disc(11, 0));
        iterate();
    }

    private void readFile() {
        try (Stream<String> stream = Files.lines(Paths.get("data/day15.txt"))) {
            stream.forEach(this::handleLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void iterate() {
        int time = 0;
        boolean success = false;
        while (!success) {
            success = true;
            for (int i = 0; success && i < discs.size(); i++) {
                Disc disc = discs.get(i);
                if ((disc.position + i + 1) % disc.size > 0) {
                    success = false;
                }
            }
            if (!success) {
                discs.forEach(d -> d.position = (d.position + 1) % d.size);
                time++;
            }
        }

        print("Drop the coin at time " + time);
    }

    private void handleLine(String line) {
        String[] tokens = line.split("[ \\.]");
        int size = Integer.parseInt(tokens[3]);
        int startPosition = Integer.parseInt(tokens[11]);
        print("Size " + size + ", start " + startPosition);
        Disc disc = new Disc(size, startPosition);
        discs.add(disc);
    }

    private class Disc {
        int size;
        int position;

        public Disc(int size, int position) {
            this.size = size;
            this.position = position;
        }
    }
}