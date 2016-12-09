package se.piro.advent;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Rolf Staflin 2016-12-05 22:14
 */
public class Day01 {

    public static void main(String[] args) {
        Day01 day01 = new Day01();
        System.out.print(day01.distance("R2, L1, R2, R1, R1, L3, R3, L5, L5, L2, L1, R4, R1, R3, L5, L5, R3, " +
                "L4, L4, R5, R4, R3, L1, L2, R5, R4, L2, R1, R4, R4, L2, L1, L1, R190, R3, L4, R52, R5, R3, L5, R3, R2, " +
                "R1, L5, L5, L4, R2, L3, R3, L1, L3, R5, L3, L4, R3, R77, R3, L2, R189, R4, R2, L2, R2, L1, R5, R4, R4, " +
                "R2, L2, L2, L5, L1, R1, R2, L3, L4, L5, R1, L1, L2, L2, R2, L3, R3, L4, L1, L5, L4, L4, R3, R5, L2, R4, " +
                "R5, R3, L2, L2, L4, L2, R2, L5, L4, R3, R1, L2, R2, R4, L1, L4, L4, L2, R2, L4, L1, L1, R4, L1, L3, L2, " +
                "L2, L5, R5, R2, R5, L1, L5, R2, R4, R4, L2, R5, L5, R5, R5, L4, R2, R1, R1, R3, L3, L3, L4, L3, L2, L2, " +
                "L2, R2, L1, L3, R2, R5, R5, L4, R3, L3, L4, R2, L5, R5"));
    }

    private class Taxi {

        public int x;
        public int y;
        public int heading;
        private Set<String> visitedLocations = new TreeSet<>();

        public String toString() {
            return "[x: " + x + ", y: " + y + ", heading: " + heading + ", distance from origo: " + distanceFromOrigo() + "]";
        }

        public int distanceFromOrigo() {
            return Math.abs(x) + Math.abs(y);
        }

        public void move(String direction) {
            if (direction == null || direction.length() == 0) {
                return;
            }
            switch (direction.charAt(0)) {
                case 'L':
                    heading = (heading + 3) % 4;
                    break;
                case 'R':
                    heading = (heading + 1) % 4;
                    break;
                default:
                    throw new IllegalArgumentException("Bad move direction: \"" + direction + "\"");
            }

            int distance = Integer.parseInt(direction.substring(1));
            int deltaY = 0, deltaX = 0;
            switch (heading) {
                case 0:  // North
                    deltaY = 1;
                    break;
                case 1:  // East
                    deltaX = 1;
                    break;
                case 2:  // South
                    deltaY = -1;
                    break;
                case 3:  // West
                    deltaX = -1;
                    break;
                default:
                    throw new RuntimeException("Incorrect heading: " + heading);
            }
            while (--distance >= 0) {
                x += deltaX;
                y += deltaY;
                String place = "[" + x + "," + y + "]";
                if (visitedLocations.contains(place)) {
                    System.out.println("------------------ Revisit! " + place + " " + toString());
                } else {
                    visitedLocations.add(place);

                }
            }
        }
    }

    private int distance(String directionsString) {
        String[] directions = directionsString.split(",");
        Set<String> places = new TreeSet<>();
        Taxi taxi = new Taxi();
        for (String direction: directions) {
            direction = direction.trim();
            taxi.move(direction);
            System.out.println(direction + " -> " + taxi.toString());
        }
        return taxi.distanceFromOrigo();
    }
}
