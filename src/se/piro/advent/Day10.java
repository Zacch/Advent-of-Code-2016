package se.piro.advent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Created by Rolf Staflin 2016-12-09 17:44
 */
public class Day10 extends Day {

    private Bot[] bots = new Bot[1000];
    private Output[] outputs = new Output[1000];

    public static void main(String[] args) {
        Day10 day = new Day10();
        day.go();
    }

    private void go() {
        try (Stream<String> stream = Files.lines(Paths.get("data/day10.txt"))) {
            stream.forEach(this::handleLine);
        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean eventProcessed = true;
        while (eventProcessed) {
            eventProcessed = false;
            for (Bot bot : bots) {
                if (bot != null) {
                    eventProcessed |= bot.act();
                }
            }
        }
        print("The product of the chips in outputs 0, 1 and 2 is " +
                (outputs[0].chip * outputs[1].chip * outputs[2].chip));
    }

    private void handleLine(String line) {
        if (line.startsWith("value ")) {
            // "value 61 goes to bot 49"
            String[] args = line.substring(6).split(" goes to bot ");
            int chip = Integer.parseInt(args[0]);
            int botNr = Integer.parseInt(args[1]);
            Bot bot = bots[botNr];
            if (bot == null) {
                bot = new Bot(botNr, this);
                bots[botNr] = bot;
            }
            bot.receive(chip);
        } else if (line.startsWith("bot ")) {
            // "bot 109 gives low to bot 137 and high to bot 82"
            String[] tokens = line.split(" ");
            int botNr = Integer.parseInt(tokens[1]);
            int lowNr = Integer.parseInt(tokens[6]);
            int highNr = Integer.parseInt(tokens[11]);

            Bot bot = getBot(botNr);

            Destination lowDestination = tokens[5].equals("bot") ? getBot(lowNr) : getOutput(lowNr);
            Destination highDestination = tokens[10].equals("bot") ? getBot(highNr) : getOutput(highNr);

            bot.low = lowDestination;
            bot.high = highDestination;
        } else {
            throw new IllegalArgumentException("Unknown line format: " + line);
        }
    }

    private Destination getOutput(int outputNr) {
        if (outputs[outputNr] == null) {
            outputs[outputNr] = new Output(outputNr, this);
        }
        return outputs[outputNr];
    }

    private Bot getBot(int botNr) {
        if (bots[botNr] == null) {
            bots[botNr] = new Bot(botNr, this);
        }
        return bots[botNr];
    }

    abstract class Destination {

        int number;
        Day10 owner;

        abstract void receive(int chip);
    }

    class Output extends Destination {

        int chip;

        Output(int outputNr, Day10 owner) {
            this.number = outputNr;
            this.owner = owner;
        }

        @Override
        public void receive(int chip) {
            // print("Output " + number + " received " + chip);
            if (this.chip > 0) {
                throw new RuntimeException("Output " + number + " has chip " + this.chip + " and received " + chip);
            }
            this.chip = chip;
        }
    }

    class Bot extends Destination {

        Destination low;
        Destination high;

        int chip1;
        int chip2;

        Bot(int botNr, Day10 owner) {
            this.number = botNr;
            this.owner = owner;
        }

        boolean act() {
            if (chip1 > 0 && chip2 > 0) {
                low.receive(Math.min(chip1, chip2));
                high.receive(Math.max(chip1, chip2));
                chip1 = 0;
                chip2 = 0;
                return true;
            }
            return false;
        }

        @Override
        public void receive(int chip) {
        //    print("Bot " + number + " received " + chip);
            if (chip1 == 0) {
                chip1 = chip;
            } else if (chip2 == 0) {
                chip2 = chip;
            } else {
                throw new RuntimeException("Bot " + number + " has too many chips! " + chip1 + ", " + chip2 + ", " + chip);
            }

            if (Math.max(chip1, chip2) == 61 && Math.min(chip1, chip2) == 17) {
                print("Bot " + number + " has chip " + chip1 + " and " + chip2 + "!");
            }
        }
    }
}
