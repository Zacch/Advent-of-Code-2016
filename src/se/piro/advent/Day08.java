package se.piro.advent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Created by Rolf Staflin 2016-12-08 22:08
 */
public class Day08 {

    private static void print(String s) {
        System.out.println(s);
    }

    Screen screen;

    public static void main(String[] args) {
        Day08 day08 = new Day08();
        day08.go();
    }

    public void go() {
        screen = new Screen(7, 3);
        screen.rect(3, 2);
        screen.rotateColumn(1, 1);
        screen.rotateRow(0, 4);
        screen.print();
        print("Pixels: " + screen.countLitPixels());
        print("--------------");

        screen = new Screen(7, 3);
        handleLine("rect 3x2");
        handleLine("rotate column x=1 by 1");
        handleLine("rotate row y=0 by 4");
        screen.print();
        print("Pixels: " + screen.countLitPixels());
        print("--------------");

        screen = new Screen(50, 6);
        try (Stream<String> stream = Files.lines(Paths.get("data/day08.txt"))) {
            stream.forEach(this::handleLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
        screen.print();
        print("Pixels: " + screen.countLitPixels());

    }

    private void handleLine(String line) {
        if (line.startsWith("rect ")) {
            String[] numbers = line.substring(5).split("x");
            screen.rect(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1]));
        } else if (line.startsWith("rotate column x=")) {
            String[] numbers = line.substring("rotate column x=".length()).split(" by ");
            screen.rotateColumn(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1]));
        } else if (line.startsWith("rotate row y=")) {
            String[] numbers = line.substring("rotate row y=".length()).split(" by ");
            screen.rotateRow(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1]));
        } else {
            throw new IllegalArgumentException("Bad input: " + line);
        }
    }
        public class Screen {

        int width;
        int height;

        boolean[] pixels;

        public Screen(int width, int height) {
            this.width = width;
            this.height = height;
            pixels = new boolean[width * height];
        }

        /*
        rect 3x2 creates a small rectangle in the top-left corner:

        ###....
        ###....
        .......

         */
        public void rect(int w, int h) {
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    pixels[y * width + x] = true;
                }
            }
        }

        /*
        rotate column x=1 by 1 rotates the second column down by one pixel:

        #.#....
        ###....
        .#.....

         */
        public void rotateColumn(int x, int distance) {
            boolean[] result = Arrays.copyOf(pixels, pixels.length);
            for (int y = 0; y < height; y++) {
                result[((y + distance) % height) * width + x] = pixels[y * width + x];
            }
            pixels = result;
        }

        /*
        rotate row y=0 by 4 rotates the top row right by four pixels:

        ....#.#
        ###....
        .#.....

        */
        public void rotateRow(int y, int distance) {
            boolean[] result = Arrays.copyOf(pixels, pixels.length);
            for (int x = 0; x < width; x++) {
                result[y * width + ((x + distance) % width)] = pixels[y * width + x];
            }
            pixels = result;
        }

        public void print() {
            for (int y = 0; y < height; y++) {
                StringBuilder stringBuilder = new StringBuilder(width * 2);
                for (int x = 0; x < width; x++) {
                    stringBuilder.append(pixels[y * width + x] ? "#" : ".");
                }
                System.out.println(stringBuilder.toString());
            }
        }

        public int countLitPixels() {
            int result = 0;
            for (boolean pixel : pixels) {
                if (pixel) {
                    result++;
                }
            }
            return result;
        }

    }
}
