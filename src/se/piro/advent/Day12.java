package se.piro.advent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Created by Rolf Staflin 2016-12-12 08:50
 */
public class Day12 {


    int nextFreeLocation = 0;
    String[] memory = new String[1024];

    public void print(String s) {
        System.out.println(s);
    }

    public static void main(String[] args) {
        Day12 day = new Day12();
        day.go();
    }

    private void go() {
        try (Stream<String> stream = Files.lines(Paths.get("data/day12.txt"))) {
            stream.forEach(this::handleLine);
        } catch (IOException e) {
            e.printStackTrace();
        }

        CPU cpu = new CPU();
        cpu.memory = memory;
        print(cpu.toString());
        cpu.run();
        print(cpu.toString());
        print("Part 1 done! a == " + cpu.a);


        print("----------------------- part 2 ---------------------------");
        cpu = new CPU();
        cpu.memory = memory;
        cpu.c = 1;
        print(cpu.toString());
        cpu.run();
        print(cpu.toString());
        print("Part 2 done! a == " + cpu.a);
    }

    private void handleLine(String s) {
        memory[nextFreeLocation++] = s;
    }

    class CPU {
        int a;
        int b;
        int c;
        int d;

        int ip;
        String[] memory;

        @Override
        public String toString() {
            return "CPU {" +
                    "a=" + a +
                    ", b=" + b +
                    ", c=" + c +
                    ", d=" + d +
                    ", ip=" + ip +
                    '}';
        }

        public void run() {
            while (ip < nextFreeLocation) {
                String line = memory[ip++];
                String[] instruction = line.split(" ");
                if ("cpy".equals(instruction[0])) {
                    copy(instruction);
                } else if ("dec".equals(instruction[0])) {
                    decrement(instruction);
                } else if ("inc".equals(instruction[0])) {
                    increment(instruction);
                } else if ("jnz".equals(instruction[0])) {
                    jumpIfNonZero(instruction);
                }
                //print(line + " --> " + this);
            }
        }

        private void copy(String[] instruction) {
            int value;
            switch (instruction[1].charAt(0)) {
                case 'a':
                    value = a;
                    break;
                case 'b':
                    value = b;
                    break;
                case 'c':
                    value = c;
                    break;
                case 'd':
                    value = d;
                    break;
                default:
                    value = Integer.parseInt(instruction[1]);
            }
            switch (instruction[2].charAt(0)) {
                case 'a':
                    a = value;
                    break;
                case 'b':
                    b = value;
                    break;
                case 'c':
                    c = value;
                    break;
                case 'd':
                    d = value;
                    break;
                default:
                    throw new IllegalArgumentException("Illegal destination " + instruction[2]);
            }
        }

        private void increment(String[] instruction) {
            switch (instruction[1].charAt(0)) {
                case 'a':
                    a++;
                    break;
                case 'b':
                    b++;
                    break;
                case 'c':
                    c++;
                    break;
                case 'd':
                    d++;
                    break;
                default:
                    throw new IllegalArgumentException("Illegal register " + instruction[1]);
            }
        }

        private void decrement(String[] instruction) {
            switch (instruction[1].charAt(0)) {
                case 'a':
                    a--;
                    break;
                case 'b':
                    b--;
                    break;
                case 'c':
                    c--;
                    break;
                case 'd':
                    d--;
                    break;
                default:
                    throw new IllegalArgumentException("Illegal register " + instruction[1]);
            }
        }

        private void jumpIfNonZero(String[] instruction) {

            boolean condition;
            switch (instruction[1].charAt(0)) {
                case 'a':
                    condition = (a != 0);
                    break;
                case 'b':
                    condition = (b != 0);
                    break;
                case 'c':
                    condition = (c != 0);
                    break;
                case 'd':
                    condition = (d != 0);
                    break;
                default:
                    condition = (Integer.parseInt(instruction[1]) != 0);
                    break;
            }
            if (condition) {
                int distance = Integer.parseInt(instruction[2]);
                ip += distance - 1;
            }
        }

    }
}
