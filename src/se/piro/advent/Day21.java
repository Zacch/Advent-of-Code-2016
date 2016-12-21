package se.piro.advent;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Created by Rolf Staflin 2016-12-21 09:40
 */
public class Day21 extends Day {

    LinkedList<Instruction> program = new LinkedList<>();
    LinkedList<Instruction> reversedProgram = new LinkedList<>();

    public static void main(String[] args) {
        Day21 day = new Day21();
        day.go();
    }

    private void go() {
        readFile("data/day21.txt");
        print("Output: " + run(program, "abcdefgh"));
        reverseProgram();
        print("Reversed Output: " + run(reversedProgram, "fbgdceah"));
    }

    @Override
    protected void handleLine(String line) {
        Instruction instruction = new Instruction();
        String[] tokens = line.split(" ");

        if ("swap".equals(tokens[0])) {
            if ("position".equals(tokens[1])) {
                // swap position X with position Y
                instruction.op_code = OP_CODE.SWAP_POSITIONS;
                instruction.int1 = Integer.parseInt(tokens[2]);
                instruction.int2 = Integer.parseInt(tokens[5]);
            } else {
                // swap letter b with letter e
                instruction.op_code = OP_CODE.SWAP_LETTERS;
                instruction.c1 = tokens[2].charAt(0);
                instruction.c2 = tokens[5].charAt(0);
            }
        } else if ("rotate".equals(tokens[0])) {
            if ("based".equals(tokens[1])) {
                // rotate based on position of letter X
                instruction.op_code = OP_CODE.ROTATE_LETTER;
                instruction.c1 = tokens[6].charAt(0);
            } else {
                // rotate left/right X steps
                instruction.op_code = ("left".equals(tokens[1])) ? OP_CODE.ROTATE_LEFT : OP_CODE.ROTATE_RIGHT;
                instruction.int1 = Integer.parseInt(tokens[2]);
            }
        } else if ("reverse".equals(tokens[0])) {
            // reverse positions X through Y
            instruction.op_code = OP_CODE.REVERSE;
            instruction.int1 = Integer.parseInt(tokens[2]);
            instruction.int2 = Integer.parseInt(tokens[4]);
        } else if ("move".equals(tokens[0])) {
            // move position X to position Y
            instruction.op_code = OP_CODE.MOVE;
            instruction.int1 = Integer.parseInt(tokens[2]);
            instruction.int2 = Integer.parseInt(tokens[5]);
        } else {
            print("Unknown instruction: " + tokens[0] + " in line " + line);
        }
        program.add(instruction);
    }

    private String run(Collection<Instruction> program, String input) {
       // print("Input: " + input);
        String data = input;
        for (Instruction instruction : program) {
            switch (instruction.op_code) {
                case SWAP_POSITIONS:
                    char c1 = data.charAt(instruction.int1);
                    char c2 = data.charAt(instruction.int2);
                    data = data.replace(c1, '_')
                        .replace(c2, c1)
                        .replace('_', c2);
                    break;
                case SWAP_LETTERS:
                    data = data.replace(instruction.c1, '_')
                            .replace(instruction.c2, instruction.c1)
                            .replace('_', instruction.c2);
                    break;
                case ROTATE_LEFT:
                    data = data.substring(instruction.int1) + data.substring(0, instruction.int1);
                    break;
                case ROTATE_RIGHT:
                    data = data.substring(data.length() - instruction.int1) + data.substring(0, data.length() - instruction.int1);
                    break;
                case ROTATE_LETTER:
                    int rotation = data.indexOf(instruction.c1);
                    rotation = (rotation + (rotation < 4 ? 1 : 2)) % data.length();
                    data = data.substring(data.length() - rotation) + data.substring(0, data.length() - rotation);
                    break;
                case REVERSE:
                    String reversed = new StringBuilder(data.substring(instruction.int1, instruction.int2 + 1)).reverse().toString();
                    data = data.substring(0, instruction.int1) + reversed + data.substring(instruction.int2 + 1, data.length());
                    break;
                case MOVE:
                    String s = data.substring(instruction.int1, instruction.int1 + 1);
                    data = data.replace(s, "");
                    data = data.substring(0, instruction.int2) + s + data.substring(instruction.int2, data.length());
                    break;
                case INVERSE_MOVE:
                    s = data.substring(instruction.int2, instruction.int2 + 1);
                    data = data.replace(s, "");
                    data = data.substring(0, instruction.int1) + s + data.substring(instruction.int1, data.length());
                    break;
                case INVERSE_ROTATE:
                    // Hard coded for input length of 8!
                    final int[] rotations = {1, 1, 6, 2, 7, 3, 0, 4};
                    int positionAfterRotation = data.indexOf(instruction.c1);
                    int leftRotation = rotations[positionAfterRotation];
                    data = data.substring(leftRotation) + data.substring(0, leftRotation);
                    break;
                default:
                    throw new IllegalArgumentException("Bad instruction: " + instruction);
            }
            // print(input + "   " + instruction.toString() + "   " + data);
        }
        return data;
    }

    private void reverseProgram() {
        for (Instruction instruction : program) {
            Instruction reversed = instruction.copy();

            switch (instruction.op_code) {
                case SWAP_POSITIONS:
                case SWAP_LETTERS:
                case REVERSE:
                    // Nothing to do here
                    break;
                case ROTATE_LEFT:
                    reversed.op_code = OP_CODE.ROTATE_RIGHT;
                    break;
                case ROTATE_RIGHT:
                    reversed.op_code = OP_CODE.ROTATE_LEFT;
                    break;
                case ROTATE_LETTER:
                    reversed.op_code = OP_CODE.INVERSE_ROTATE;
                    break;
                case MOVE:
                    reversed.op_code = OP_CODE.INVERSE_MOVE;
                    break;
                case INVERSE_MOVE:
                    reversed.op_code = OP_CODE.MOVE;
                    break;
                case INVERSE_ROTATE:
                    reversed.op_code = OP_CODE.ROTATE_LETTER;
                default:
                    throw new IllegalArgumentException("Bad instruction: " + instruction);
            }
            reversedProgram.addFirst(reversed);
        }
    }

    class Instruction {
        OP_CODE op_code = OP_CODE.NOP;

        int int1 = -1;
        int int2 = -1;
        char c1 = '_';
        char c2 = '_';

        @Override
        public String toString() {
            return "<" + op_code + " " + int1 + ", " + int2 + ", \'" + c1 + "\', \'" + c2 + "\'>";
        }

        public Instruction copy() {
            Instruction copy = new Instruction();
            copy.op_code = op_code;
            copy.c1 = c1;
            copy.c2 = c2;
            copy.int1 = int1;
            copy.int2 = int2;
            return copy;
        }
    }

    enum OP_CODE {
        NOP,            // No operation
        SWAP_POSITIONS, // swap position X with position Y
        SWAP_LETTERS,   // swap letter X with letter Y
        ROTATE_LEFT,    // rotate left X steps
        ROTATE_RIGHT,   // rotate right X steps
        ROTATE_LETTER,  // rotate based on position of letter X
        REVERSE,        // reverse positions X through Y
        MOVE,           // move position X to position Y
        INVERSE_MOVE,   // reverse of a MOVE
        INVERSE_ROTATE  // reverse of a ROTATE_LETTER
    }
}
