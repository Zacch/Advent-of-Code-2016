package se.piro.advent;

import org.w3c.dom.ranges.Range;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by Rolf Staflin 2016-12-20 08:44
 */
public class Day20 extends Day {

    TreeSet<Range> ranges = new TreeSet<>();

    private static final long MAX_IP = 4294967295L;

    public static void main(String[] args) {
        Day20 day = new Day20();
        day.go();
    }

    private void go() {
        readFile("data/day20.txt");
        print("Lowest non-blocked IP: " + (ranges.first().end + 1));
        print("Number of allowed IPs: " + countGaps());
    }

    @Override
    protected void handleLine(String line) {
        Range range = new Range(line);
        ranges.add(range);
        Range lowerRange = ranges.lower(range);
        while ((lowerRange != null) && range.merge(lowerRange)) {
            ranges.remove(lowerRange);
            lowerRange = ranges.lower(range);
        }
        Range higherRange = ranges.higher(range);
        while ((higherRange != null) && (range.merge(higherRange))) {
            ranges.remove(higherRange);
            higherRange = ranges.higher(range);
        }

    }

    private long countGaps() {
        long sum = 0;
        long lastEnd = -1;
        for (Range range : ranges) {
            sum += (range.start - 1 - lastEnd);
            lastEnd = range.end;
        }
        sum += MAX_IP - lastEnd;
        return sum;
    }

    class Range implements Comparable<Range> {
        long start;
        long end;

        public Range(long start, long end) {
            this.start = start;
            this.end = end;
        }

        public Range(String line) {
            String[] parts = line.split("-");
            this.start = Long.parseLong(parts[0]);
            this.end = Long.parseLong(parts[1]);
        }

        boolean merge(Range other) {
            if (start > other.end + 1 || other.start > end + 1) {
                return false;
            }
            start = Math.min(start, other.start);
            end = Math.max(end, other.end);
            return true;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Range range = (Range) o;

            if (start != range.start) return false;
            if (end != range.end) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = (int) (start ^ (start >>> 32));
            result = 31 * result + (int) (end ^ (end >>> 32));
            return result;
        }

        @Override
        public int compareTo(Range other) {
            if (start < other.start) {
                return -1;
            }
            if (start > other.start) {
                return 1;
            }
            if (end < other.end) {
                return -1;
            }
            if (end > other.end) {
                return 1;
            }
            return 0;
        }

        @Override
        public String toString() {
            return "[" + FORMAT.format(start) + ", " + FORMAT.format(end) + ']';
        }
    }
}
