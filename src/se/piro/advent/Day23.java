package se.piro.advent;

/**
 * Created by Rolf Staflin 2016-12-12 08:50
 */
public class Day23 extends Day {


    int nextFreeLocation = 0;
    String[] memory = new String[1024];

    public static void main(String[] args) {
        Day23 day = new Day23();
        print("Part 1: " + day.go(7));
        day = new Day23();
        print("Part 2: " + day.go(12));
    }

    private int go(int a) {
        readFile("data/day23.txt");

        CPU cpu = new CPU();
        cpu.a = a;
        cpu.memory = memory;
        print(cpu.toString());
        cpu.run();
        print(cpu.toString());
        return cpu.a;
    }

    protected void handleLine(String s) {
        memory[nextFreeLocation++] = s;
    }

    @SuppressWarnings("Duplicates")
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
            int count = 0;
            while (ip < nextFreeLocation) {
                String line = memory[ip++];
               // print(line + " (ip " + (ip - 1) + ")");
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
                }
                if (count++ % 10000 == 0) {
                    print("" + count + ": " + line + " --> " + this);
                }
                //print(line + " --> " + this);
            }
        }

        private void toggle(String[] instruction) {
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
    }
}
