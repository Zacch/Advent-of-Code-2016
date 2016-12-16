package se.piro.advent;

/**
 * Created by Rolf Staflin 2016-12-16 06:07
 */
public class Day16 extends Day {

    static final String INPUT = "01111001100111011";

    public static void main(String[] args) {
        Day16 day = new Day16();
        print("Part 1");
        day.go(272);
        print("\nPart 2");
        day.go(35651584);
    }

    private void go(int diskSize) {
        StringBuilder buffer = new StringBuilder(diskSize * 2);
        buffer.append(INPUT);
        while (buffer.length() < diskSize) {
            String data = buffer.toString();
            buffer.append('0');
            for (int i = data.length() - 1; i >= 0 && buffer.length() < diskSize; i--) {
                buffer.append(data.charAt(i) == '1' ? '0' : '1');
            }
        }
        String data = buffer.toString();
        if (data.length() < 1000) {
            print("Disk data: " + data);
        }

        String checksum = getChecksum(data);
        print("Checksum: " + checksum);
    }

    private String getChecksum(String input) {
        StringBuilder checksum = new StringBuilder(input.length() / 2);
        String data = input;
        boolean done = false;
        while (!done) {
            for (int i = 0; i < data.length(); i += 2) {
                checksum.append(data.charAt(i) == data.charAt(i + 1) ? '1' : '0');
            }
            if ((checksum.length() % 2) == 1) {
                done = true;
            } else {
                data = checksum.toString();
                checksum = new StringBuilder(data.length() / 2);
            }
        }
        return checksum.toString();
    }
}

// 0111110101
// 0111110101