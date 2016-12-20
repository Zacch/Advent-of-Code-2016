package se.piro.advent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.stream.Stream;

/**
 * Created by Rolf Staflin 2016-12-09 17:43
 */
public class Day {
    static final NumberFormat FORMAT = NumberFormat.getInstance();

    static void print(String s) {
        System.out.println(s);
    }

    protected void readFile(String filename) {
        try (Stream<String> stream = Files.lines(Paths.get(filename))) {
            stream.forEach(this::handleLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void handleLine(String line) {
    }
}
