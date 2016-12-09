package se.piro.advent;

import java.util.List;

/**
 * Created by Rolf Staflin 2016-12-06 00:11
 */
public class Day02 {

    /*
    1 2 3
    4 5 6
    7 8 9
     */

    static int[][] keypad = {{7, 4, 1}, {8, 5, 2}, {9, 6, 3}};

    /*
            1
          2 3 4
        5 6 7 8 9
          A B C
            D
     */

    static char[][] keypad2 = {
            {' ', ' ', '5', ' ', ' '},
            {' ', 'A', '6', '2', ' '},
            {'D', 'B', '7', '3', '1'},
            {' ', 'C', '8', '4', ' '},
            {' ', ' ', '9', ' ', ' '}};

    public int x = 1;
    public int y = 1;

    public static void main(String[] args) {
        Day02 day02 = new Day02();
        String input = "RLRDDRLLDLRLUDDULLDRUUULDDLRLUDDDLDRRDUDDDLLURDDDLDDDRDURUDRDRRULUUDUDDRRRLRRRRRLRULRLLRULDRUUD" +
                "RLRRURDDRLRULDLDULLLRULURRUULLRLLDDDDLLDURRUDLDLURDRDRDLUUUDDRDUUDDULLUURRDRLDDULURRRUDLLULULDLLURURUD" +
                "RRRRUDRLRDLRRLDDRDDLULDLLLURURDUDRRRRUULURLRDULDRLUDRRUDDUULDURUDLDDURRRDLULLUUDRLLDUUDLDRUDDRLLLLLLDU" +
                "DUDDLRDLRRDRUDDRRRLLRRDLLRLDDURUURRRDDLDUULLDLDLRURDLLLDDRUUDRUDDDDULRLLDUULRUULLLULURRRLLULDLDUDLDLUR" +
                "UDUDULLDLLUUDRRDRLUURURURURDLURUUDLDRLUDDUUDULDULULLLDLDDULLULLDULRRDRULLURRRULLDDDULULURLRDURLLURUDDU" +
                "LLRUDLRURURRDRDUULDRUUDURDURDDLRDUUULDUUDRDURURDRRRURLLDDLLLURURULULUDLRDLDRDRURLRLULRDLU," +
                "UDLDURRULDRDDLDUULUDLDUULUURDDRUDRURRRUDRURLLDDRURLDLRDUUURDLLULURDDUDDDRRRURLLDLDLULRDULRLULDLUUDLLRL" +
                "DLRUUULDDUURDLDDRRDLURLDUDDRURDRRURDURRRLUULURDDLRDLDRRRLDUDRLRLLRLDDUULDURUUULLLRRRRRRRDRRRDRLUULDLDD" +
                "LULDRDUDLLUDRRUDRUUDULRLUURDDDDRRUUDLURULLLURDULUURDRDDURULRUDRRDLRDUUUUUDDDRDRDDRUDRDDDRLRUUDRDRDDDLU" +
                "DRDRLDRDDRULURDRLDRUDUDRUULRLLUDRDRLLLLDUDRRLLURDLLLDRRUDDUDRLRLDUDRLURRUUULURDDRUURRLDRLRRRUUDLULDDDR" +
                "DLDUUURLLUULDDRRUDLDDRUDUDUURURDDRDULLLLLULRRRDLRRRDDDLURDDDDLUULLLRDDURRRRLURRLDDLRUULULRDRDDDDLDUUUU" +
                "UUDRRULUUUDD," +
                "UURDRRUDLURRDDDLUDLRDURUDURDLLLLRDLRLRDDRDRDUUULRDLLDLULULRDUDDRRUUDURULDLUDLRDRUDLDDULLLDDRDLLDULLLUR" +
                "LLRDDLDRDULRRDDULRDURLLRUDRLRRLUDURLDRDLDLRLLLURLRRURDLDURDLUDULRDULLLDRDDRDLDRDULUULURDRRRLDRRUULULLD" +
                "DRRLDLRUURLRUURLURRLLULUUULRLLDDUDDLRLDUURURUDLRDLURRLLURUDLDLLUDDUULUUUDDDURDLRRDDDLDRUDRLRURUUDULDDL" +
                "UUDDULLDDRRDDRRRUDUDUDLDLURLDRDLLLLDURDURLRLLLUUDLRRRRUDUDDLDLRUURRLRRLUURRLUDUDRRRRRRRLDUDDRUDDLUDLRD" +
                "DDRLDUULDRDRRDLDRURDLDRULRLRLUDRDLRRUURUUUUDLDUUULLLRRRRRDLRRURDDLLLLUULDLLRULLUDLLDLLUDLRLRRLRURDDRRL," +
                "URDRDLLRDDDLLLDDLURLRURUURRRLUURURDURRLLUDURRLRLDLUURDLULRRDRUDDLULDLDRLDLRLRRLLLDDDUDDDLRURURRLLDRRRU" +
                "RUDLRDDLLDULDDLDRLUUUDRRRULDUULRDDDLRRLLURDDURLULRDUDURRLLDLLRLDUDDRRDDLRLLLDUDRLUURRLLDULRLDLUUUUUDUL" +
                "UDLULUDDUURRURLDLDRRLDLRRUDUDRRDLDUDDLULLDLLRDRURDRDRRLDDDDRDDRLLDDDLLUDRURLURDRRRRRUDDDUDUDDRDUUDRRUD" +
                "UDRLULDDURULUURUUUURDRULRLRULLDDRRRUULRRRRURUDLDLRDLLDRLURLRUULLURDUDULRRURLRLLRRLLLURULRRRLDDUULLUUUL" +
                "RRDRULUUUUDRDRRDLRURLRLLRLRRRDRDRLDLUURUURULLDLULRRLRRDRULRRLLLDDURULLDLDLDLUUURDLDLUUDULRLLUDDRRDLLDL" +
                "DLDURLUURRDDRRURDRLUDRLUUUDLDULDLUDRLDUDDLLRUDULLLLLDRRLLUULLUUURRDDUURDLLRDDLRLLU," +
                "LDUDRRDLUUDDRLLUUULURLDUDLUDLRLDRURLULRLLDDLRRUUUDDDDRDULDDUUDLRUULDRULLRDRUDDURLDUUURRUDUDRDRDURRDLUR" +
                "RRDRLDLRRRLLLRLURUURRDLLRDLDDLLRDUDDRDUULRULRRURLUDDUDDDUULLUURDULDULLLLRUUUDDRRRLDDDLDLRRDRDRDLUULRLU" +
                "LDRULDLRDRRUDULUDLLUDUULRDLRRUUDDLLDUDDRULURRLULDLDRRULDDRUUDDLURDLRDRLULRRLURRULDUURDLUDLLDRLDULLULDL" +
                "LRDRDLLLUDLRULLRLDRDDDLDDDLRULDLULLRUUURRLLDUURRLRLDUUULDUURDURRULULRUUURULLLRULLURDDLDRLLRDULLUDLDRRR" +
                "LLLLDUULRRLDURDURDULULDUURLDUDRLRURRDLUUULURRUDRUUUDRUR";

        String[] strings = input.split(",");
        day02.decode(strings);
        print("\n");
        day02.decodePart2(strings);
    }

    static void print(String s) {
        System.out.println(s);
    }

    public String toString() {
        return "[" + x + ", " + y + "] -> " + keypad[x][y];
    }

    public String toString2() {
        return "[" + x + ", " + y + "] -> " + keypad2[x][y];
    }

    private void decode(String[] stringList) {
        for (String string: stringList) {
            print(string);
            for (char c: string.toCharArray()) {
                switch (c) {
                    case 'L':
                        x = Math.max(0, x - 1);
                        break;
                    case 'R':
                        x = Math.min(2, x + 1);
                        break;
                    case 'U':
                        y = Math.min(2, y + 1);
                        break;
                    case 'D':
                        y = Math.max(0, y - 1);
                        break;
                }
                //  print("  " + c + " -> " + toString());
            }
            print(toString());
        }
    }
    private void decodePart2(String[] stringList) {
        x = 0;
        y = 2;
        for (String string: stringList) {
            print(string);
            for (char c: string.toCharArray()) {
                switch (c) {
                    case 'L':
                        int newX = Math.max(0, x - 1);
                        if (keypad2[newX][y] != ' ') {
                            x = newX;
                        }
                        break;
                    case 'R':
                        newX = Math.min(4, x + 1);
                        if (keypad2[newX][y] != ' ') {
                            x = newX;
                        }
                        break;
                    case 'U':
                        int newY = Math.min(4, y + 1);
                        if (keypad2[x][newY] != ' ') {
                            y = newY;
                        }
                        break;
                    case 'D':
                        newY = Math.max(0, y - 1);
                        if (keypad2[x][newY] != ' ') {
                            y = newY;
                        }
                        break;
                }
                // print("  " + c + " -> " + toString2());
            }
            print(toString2());
        }
    }
}
