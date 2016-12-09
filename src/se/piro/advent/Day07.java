package se.piro.advent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Rolf Staflin 2016-12-08 17:25
 */
public class Day07 {
    private static void print(String s) {
        System.out.println(s);
    }

    public static void main(String[] args) {
        Day07 day07 = new Day07();
        day07.go();
    }

    private int count;
    private int count2;

    public void go() {

        IP2 ip2 = new IP2("aba[bab]xyz");
        print(ip2.fullData + " supports SSL? (yes) " + ip2.supportsSSL());
        ip2 = new IP2("xyx[xyx]xyx");
        print(ip2.fullData + " supports SSL? (no) " + ip2.supportsSSL());
        ip2 = new IP2("aaa[kek]eke");
        print(ip2.fullData + " supports SSL? (yes) " + ip2.supportsSSL());
        ip2 = new IP2("zazbz[bzb]cdb");
        print(ip2.fullData + " supports SSL? (yes) " + ip2.supportsSSL());


        IP ip = new IP("abcd[bdb]xyyx");
        print(ip.fullData + " --> " + ip.hasABBA());
        ip = new IP("abba[mnop]qrst");
        print(ip.fullData + " --> " + ip.hasABBA());
        ip = new IP("abcd[bddb]xyyx");
        print(ip.fullData + " --> " + ip.hasABBA());

        ip = new IP("aaaa[qwer]tyui");
        print(ip.fullData + " --> " + ip.hasABBA());

        ip = new IP("ioxxoj[asdfgh]zxcvbn");
        print(ip.fullData + " --> " + ip.hasABBA());

        ip2 = new IP2("luqpeubugunvgzdqk[jfnihalscclrffkxqz]wvzpvmpfiehevybbgpg[esjuempbtmfmwwmqa]rhflhjrqjbbsadjnyc");
        print(ip.fullData + " --> " + ip.hasABBA());

        count = 0;
        count2 = 0;
        try (Stream<String> stream = Files.lines(Paths.get("data/day07.txt"))) {
            stream.forEach(this::handleLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
        print("Count: " + count);
        print("Count 2: " + count2);

    }

    private void handleLine(String line) {
        IP lineIp = new IP(line);
        if (lineIp.hasABBA()) {
//            print("Line: " + line);
            count++;
        }

        IP2 lineIp2 = new IP2(line);
        if (lineIp2.supportsSSL()) {
            count2++;
        }
    }

    private class IP2 {
        private String[] parts;
        public String fullData;

        private List<String> supernetSequences = new ArrayList<>();
        private List<String> hypernetSequences = new ArrayList<>();

        public IP2(String data) {
            fullData = data;
            parts = data.split("[\\[\\]]");
            for (int i = 0; i < parts.length; i++) {
                if ((i & 1) == 0) {
                    supernetSequences.add(parts[i]);
                } else {
                    hypernetSequences.add(parts[i]);
                }
            }
        }

        boolean found = false;

        public boolean supportsSSL() {
            List<String> abas = findAbas();
            found = false;
            abas.forEach(aba -> {
                    if (hasBab(aba)) {
                        found = true;
                    }
            });
            return found;
        }

        private List<String> findAbas() {
            List<String> result = new ArrayList<>();
            supernetSequences.forEach(sequence -> {
                for (int i = 0; i <= sequence.length() - 3; i++) {
                    if (sequence.charAt(i) == sequence.charAt(i + 2) &&
                        sequence.charAt(i) != sequence.charAt(i + 1)) {
                        result.add(sequence.substring(i, i + 3));
                    }
                }
            });
            return result;
        }

        private boolean hasBab(String aba) {
            String bab = "" + aba.charAt(1) + aba.charAt(0) + aba.charAt(1);
            for (String sequence : hypernetSequences) {
                if (sequence.contains(bab)) {
                    return true;
                }
            }
            return false;
        }
    }

    private class IP {
        public String fullData;
        public String prefix;
        public String bracketContent;
        public String suffix;

        public IP(String data) {
            fullData = data;
            prefix = data.substring(0, data.indexOf('['));
            bracketContent = data.substring(data.indexOf('[') + 1, data.indexOf(']'));
            suffix = data.substring(data.indexOf(']') + 1);
        }

        public boolean hasABBA() {
            boolean inBrackets = false;

            boolean abbaFound = false;
            for (int i = 0; i <= fullData.length() - 4; i++) {
                if (fullData.charAt(i) == '[') {
                    inBrackets = true;
                } else if (fullData.charAt(i) == ']') {
                    inBrackets = false;
                } else if (fullData.charAt(i) == fullData.charAt(i + 3) &&
                            fullData.charAt(i + 1) == fullData.charAt(i + 2) &&
                            fullData.charAt(i) != fullData.charAt(i + 1)) {
                    final String theAbba = fullData.substring(i, i + 4);
                    print("Found ABBA: " + theAbba + ", inBrackets == " + inBrackets);
                    if (inBrackets) {
                        return false;
                    } else {
                        abbaFound = true;
                    }
                }
            }
            return abbaFound;
        }

        public boolean oldHasABBA() {
            return (hasAbbaRecursive(prefix) || hasAbbaRecursive(suffix)) && !hasAbbaRecursive(bracketContent);
        }

        private boolean hasAbbaRecursive(String input) {
            if (input.length() < 4) {
                return false;
            }
            if (input.charAt(0) == input.charAt(3) &&
                    input.charAt(1) == input.charAt(2) &&
                    input.charAt(0) != input.charAt(1)) {
                print(" " + input.substring(0, 4));
                return true;
            }
            return hasAbbaRecursive(input.substring(1));
        }
    }
}