package se.piro.advent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Created by Rolf Staflin 2016-12-09 17:44
 */
public class Day10 extends Day {

    public static void main(String[] args) {
        Day10 day = new Day10();

        try (Stream<String> stream = Files.lines(Paths.get("data/day10.txt"))) {
            stream.forEach(day::handleLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleLine(String line) {

    }
}
