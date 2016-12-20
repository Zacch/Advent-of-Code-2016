package se.piro.advent;

import java.util.Arrays;

/**
 * Created by Rolf Staflin 2016-12-19 08:39
 */
public class Day19 extends Day {

    public static void main(String[] args) {
        Day19 day = new Day19();
        print("Part 1");
        day.go(5); // 3012210
        print("\nPart 2");
        day.part2(3012210);
    }

    private void go(int numberOfElves) {
        int[] presents = new int[numberOfElves + 1];
        Arrays.setAll(presents, (p -> p = 1));

        int currentElf = 1;
        for (int elvesWithPresents = numberOfElves; elvesWithPresents > 1; elvesWithPresents--) {
            int nextElf = findNextElf(currentElf, numberOfElves, presents);
            // print("Elf " + currentElf + " steals from " + nextElf);
            presents[currentElf] += presents[nextElf];
            presents[nextElf] = 0;
            currentElf = findNextElf(nextElf, numberOfElves, presents);
        }
        print("The last elf is " + currentElf);
    }

    private int findNextElf(int elf, int numberOfElves, int[] presents) {

        for (int i = elf + 1; i <= numberOfElves; i++) {
            if (presents[i] > 0) return i;
        }
        for (int i = 1; i <= elf; i++) {
            if (presents[i] > 0) return i;
        }
        throw new RuntimeException("No next elf found!");
    }

    //-------------------------------------------------------
    private void part2(int numberOfElves) {
        long startTime = System.currentTimeMillis();
        Elf[] elves = new Elf[numberOfElves + 1];

        for (int i = 1; i <= numberOfElves; i++) {
            elves[i] = new Elf(i, 1);
        }
        int elvesLeft = numberOfElves;

        int currentElfIndex = 1;
        while (elvesLeft > 1) {
            if ((elvesLeft % 10000) == 0) {
                print("  working... " + elvesLeft + " elves left.");
            }
            int victimIndex = findElfAcross(currentElfIndex, elvesLeft);
            // print(elves[currentElfIndex] + " steals from " + elves[victimIndex]);
            elves[currentElfIndex].presents += elves[victimIndex].presents;
            elves[victimIndex].presents = 0;
            removeElf(victimIndex, elves, elvesLeft);
            if (currentElfIndex > victimIndex) {
                currentElfIndex--;
            }
            elvesLeft--;
            currentElfIndex++;
            if (currentElfIndex > elvesLeft) {
                currentElfIndex = 1;
            }
        }
        print("The last elf is " + elves[1]);
        print("Time taken: " + ((System.currentTimeMillis() - startTime) / 1000d) + " seconds.");
    }

    private int findElfAcross(int elf, int elvesLeft) {
        int offset = elvesLeft / 2;
        int result = (elf + offset) % elvesLeft;
        if (result == 0) {
            result = elvesLeft;
        }
        return result;
    }

    private void removeElf(int elf, Elf[] elves, int elvesLeft) {
        System.arraycopy(elves, elf + 1, elves, elf, elvesLeft - elf);
        elves[elvesLeft] = null;
    }

    class Elf {
        int number;
        int presents;

        public Elf(int number, int presents) {
            this.number = number;
            this.presents = presents;
        }

        @Override
        public String toString() {
            return "Elf " + number + " with " + presents + " presents";
        }
    }
}
