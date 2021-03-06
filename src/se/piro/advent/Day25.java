package se.piro.advent;

/**
 * Created by Rolf Staflin 2016-12-25 06:51
 */
public class Day25 extends Day {

    int nextFreeLocation = 0;
    String[] memory = new String[1024];

    public static void main(String[] args) {
        Day25 day = new Day25();
        print("Lowest possible value for a: " + day.go());
    }

    private int go() {
        readFile("data/day25.txt");
        int a;
        for (a = 1; a < Integer.MAX_VALUE; a++) {
            CPU cpu = new CPU();
            cpu.memory = memory;
            cpu.a = a;
            try {
                cpu.run();
                break;
            } catch (OutputException e) {
                // print("Value " + a + " doesn't work.");
            }
        }
        return a;
    }

    protected void handleLine(String s) {
        memory[nextFreeLocation++] = s;
    }

    class OutputException extends RuntimeException {}

    @SuppressWarnings("Duplicates")
    class CPU {
        int a;
        int b;
        int c;
        int d;

        int ip;
        String[] memory;
        int nextExpectedOutput = 0;
        int outputLength = 0;

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
            int count = 0;
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
                } else if ("tgl".equals(instruction[0])) {
                    toggle(instruction);
                } else if ("out".equals(instruction[0])) {
                    output(instruction);
                }
                if (++count == 1000000) {
                    print("Returning after output of " + outputLength + " correct values.");
                    return;
                }
            }
        }

        private void output(String[] instruction) {
            int value = getValue(instruction[1]);
            // print("Output " + value);
            if (value != nextExpectedOutput) {
                throw new OutputException();
            }
            outputLength++;
            nextExpectedOutput = 1 - nextExpectedOutput;
        }

        private void toggle(String[] instruction) {
            int value = getValue(instruction[1]);
            int targetAddress = ip + value - 1;
            String target = memory[targetAddress];
            if (target == null || target.isEmpty()) {
                return;
            }
            String[] targetInstruction = target.split(" ");
            if (targetInstruction.length == 2) {
                if ("inc".equals(targetInstruction[0])) {
                    targetInstruction[0] = "dec";
                } else {
                    targetInstruction[0] = "inc";
                }
            } else {
                if ("jnz".equals(targetInstruction[0])) {
                    targetInstruction[0] = "cpy";
                } else {
                    targetInstruction[0] = "jnz";
                }
            }
            memory[targetAddress] = String.join(" ", targetInstruction);
        }


        private void copy(String[] instruction) {
            int value = getValue(instruction[1]);
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
            int value = getValue(instruction[1]);
            if (value != 0) {
                int distance;
                switch (instruction[2].charAt(0)) {
                    case 'a':
                        distance = a;
                        break;
                    case 'b':
                        distance = b;
                        break;
                    case 'c':
                        distance = c;
                        break;
                    case 'd':
                        distance = d;
                        break;
                    default:
                        distance = Integer.parseInt(instruction[2]);
                }
                ip += distance - 1;
            }
        }

        private int getValue(String register) {
            switch (register.charAt(0)) {
                case 'a':
                    return a;
                case 'b':
                    return b;
                case 'c':
                    return c;
                case 'd':
                    return d;
                default:
                    return Integer.parseInt(register);
            }
        }
    }
}
