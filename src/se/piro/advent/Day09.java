package se.piro.advent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Created by Rolf Staflin 2016-12-09 07:54
 */
public class Day09 {

    private static void print(String s) {
        System.out.println(s);
    }

    public static void main(String[] args) {
        Day09 day = new Day09();
        day.go();
    }
    /*

        ADVENT contains no markers and decompresses to itself with no changes, resulting in a decompressed length of 6.
        A(1x5)BC repeats only the B a total of 5 times, becoming ABBBBBC for a decompressed length of 7.
        (3x3)XYZ becomes XYZXYZXYZ for a decompressed length of 9.
        A(2x2)BCD(2x2)EFG doubles the BC and EF, becoming ABCBCDEFEFG for a decompressed length of 11.
        (6x1)(1x3)A simply becomes (1x3)A - the (1x3) looks like a marker, but because it's within a data section of another marker, it is not treated any differently from the A that comes after it. It has a decompressed length of 6.
        X(8x2)(3x3)ABCY becomes X(3x3)ABC(3x3)ABCY (for a decompressed length of 18), because the decompressed data from the (8x2) marker (the (3x3)ABC) is skipped and not processed further.

     */
    public void go() {

        print(decompress("ADVENT"));
        print("ADVENT");
        print(decompress("A(1x5)BC"));
        print("ABBBBBC");
        print(decompress("(3x3)XYZ"));
        print("XYZXYZXYZ");
        print(decompress("A(2x2)BCD(2x2)EFG"));
        print("ABCBCDEFEFG");
        print(decompress("(6x1)(1x3)A"));
        print("(1x3)A");
        print(decompress("X(8x2)(3x3)ABCY"));
        print("X(3x3)ABC(3x3)ABCY");

        print("Length     (20) " + decompressedLength("X(8x2)(3x3)ABCY"));
        print("Length (241920) " + decompressedLength("(27x12)(20x12)(13x14)(7x10)(1x12)A"));
        print("Length    (445) " + decompressedLength("(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN"));

        try (Stream<String> stream = Files.lines(Paths.get("data/day09.txt"))) {
            stream.forEach(this::handleLine);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void handleLine(String line) {
        String decompressed = decompress(line);
        print("Length " + decompressed.length());
        print("Line   " + line);
        print("Decomp " + decompressed);

        print("---------------- part 2 -----------------");
        print("Length " + decompressedLength(line));

    }

    private String decompress(String s) {
        if (!s.contains("(")) {
            return s;
        }
        int open = s.indexOf('(');
        int x = s.indexOf('x');
        int close = s.indexOf(')');

        StringBuilder output = new StringBuilder();
        output.append(s.substring(0, open));
        int length = Integer.parseInt(s.substring(open + 1, x));
        int copies = Integer.parseInt(s.substring(x + 1, close));

        for(int i = 0; i < copies; i++) {
            output.append(s.substring(close + 1, close + length + 1));
        }

        output.append(decompress(s.substring(close + length + 1)));
        return output.toString();
    }

    private long decompressedLength(String s) {
        if (!s.contains("(")) {
            return s.length();
        }
        int open = s.indexOf('(');
        int x = s.indexOf('x');
        int close = s.indexOf(')');

        int length = Integer.parseInt(s.substring(open + 1, x));
        int copies = Integer.parseInt(s.substring(x + 1, close));

        long result = open;
        result += copies * decompressedLength(s.substring(close + 1, close + length + 1));
        result += decompressedLength(s.substring(close + length + 1));

        return result;
    }

}
