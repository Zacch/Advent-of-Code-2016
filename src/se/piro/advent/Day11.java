package se.piro.advent;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

/**
 * Created by Rolf Staflin 2016-12-11 11:11
 */
public class Day11 {

    State ILLEGAL_STATE = new State();

    SortedMap<Integer, LinkedList<State>> statesToExpand = new TreeMap<>();

    // A map from the string representation of a state to the length of its history.
    Map<String, Integer> expandedStates = new HashMap<>();
    State solution = null;
    int shortestLength = 34;

    public void print(String s) {
        System.out.println(s);
    }

    public static void main(String[] args) {
        Day11 day = new Day11();
        day.go();
    }

    Day11() {
        ILLEGAL_STATE = new State();
        ILLEGAL_STATE.elevatorFloor = -1;
    }


    private void go() {
        final State startState = makeStartState();
        limitedDepthFirstSearchWithHeuristics(startState);

        print("Shortest solution is " + shortestLength + " moves:");
        print(solution.toString());
        for (int i = solution.history.size() - 1; i >= 0; i--) {
            print(solution.history.get(i).toString());
        }
    }

    long laps = 0;

    void limitedDepthFirstSearchWithHeuristics(State state) {
        if (++laps % 100000 == 0) {
            print(laps + " laps. Expanded " + expandedStates.size() + " solutions.");
        }
        List<Move> validMoves = state.calculateValidMoves();
        List<State> statesToRecurseOver = new ArrayList<>();
        for (Move move : validMoves) {
            State newState = state.makeMove(move);

            if (newState.isEndState()) {
                recordEndState(newState);
                return;
            } else if (newState.isLegal() && newState.history.size() < shortestLength - 1) {
                Integer savedStateHistoryLength = expandedStates.get(newState.toString());
                if (savedStateHistoryLength == null || savedStateHistoryLength > newState.history.size()) {
                    expandedStates.put(newState.toString(), newState.history.size());
                    statesToRecurseOver.add(newState);
                }
            }
        }
        List<State> sortedStates = statesToRecurseOver.stream().sorted(comparing(State::getScore)).collect(Collectors.toList());
        sortedStates.forEach(this::limitedDepthFirstSearchWithHeuristics);
    }

    private void recordEndState(State goalState) {
        final int solutionLength = goalState.history.size();
        print("Reached goal after " + solutionLength + " moves!");
        if (solutionLength < shortestLength) {
            shortestLength = solutionLength;
            solution = goalState;

            List<Integer> valuesToRemove = new ArrayList<>();
            // Clean out uninteresting states from statesToExpand
            for (int score: statesToExpand.keySet()) {
                final LinkedList<State> states = statesToExpand.get(score);
                states.removeIf(state -> state.history.size() > shortestLength - 2);
                if (states.isEmpty()) {
                    valuesToRemove.add(score);
                }
            }
            for (int score : valuesToRemove) {
                statesToExpand.remove(score);
            }

            print(solution.toString());
            for (int i = solution.history.size() - 1; i >= 0; i--) {
                print(solution.history.get(i).toString());
            }

        }
    }

    //--------------------------------------------------------------------------

    class State {
        int elevatorFloor = 1;
        Floor f4 = new Floor();
        Floor f3 = new Floor();
        Floor f2 = new Floor();
        Floor f1 = new Floor();

        LinkedList<State> history = new LinkedList<>();

        // The score is used for the heuristic - try states with lower scores first.
        // The scorer here is the number of objects on each floor times the distance between the floor and floor 4.
        int getScore() {
            return  (f3.things.size() +
                2 * f2.things.size() +
                3 * f1.things.size());
        }

        boolean isEndState() {
            return elevatorFloor == 4 && f1.isEmpty() && f2.isEmpty() && f3.isEmpty();
        }

        boolean isLegal() {
            return elevatorFloor >= 1 && elevatorFloor <= 4 && f1.isLegal() && f2.isLegal() && f3.isLegal() && f4.isLegal();
        }

        State copy() {
            State copy = new State();
            copy.elevatorFloor = elevatorFloor;
            copy.f1.things.addAll(f1.things);
            copy.f2.things.addAll(f2.things);
            copy.f3.things.addAll(f3.things);
            copy.f4.things.addAll(f4.things);
            copy.history.addAll(history);
            return copy;
        }

        Floor currentFloor() {
            return getFloor(elevatorFloor);
        }

        Floor getFloor(int floor) {
            switch (floor) {
                case 1: return f1;
                case 2: return f2;
                case 3: return f3;
                case 4: return f4;
                default: throw new RuntimeException("Illegal!");
            }
        }

        List<Move> calculateValidMoves() {
            List<Move> possibleMoves = new ArrayList<>();
            LinkedList<ElevatorLoad> loads = currentFloor().allPossibleLoads();

            if (elevatorFloor < 4) {
                // Figure out which loads can move up
                for (ElevatorLoad load : loads) {
                    Floor nextFloor = getFloor(elevatorFloor + 1);
                    if (nextFloor.validWith(load)) {
                        possibleMoves.add(new Move(Direction.Up, load));
                    }
                }
            }

            if (elevatorFloor > 1) {
                // Figure out which loads can move down
                for (ElevatorLoad load : loads) {
                    Floor lowerFloor = getFloor(elevatorFloor - 1);
                    if (lowerFloor.validWith(load)) {
                        possibleMoves.add(new Move(Direction.Down, load));
                    }
                }
            }

            return possibleMoves;
        }

        State makeMove(Move move) {
            State newState = this.copy();
            newState.history.add(this);

            newState.currentFloor().elevatorLeaving(move.load);
            if (move.direction == Direction.Up) {
                newState.elevatorFloor++;
            } else {
                newState.elevatorFloor--;
            }
            newState.currentFloor().elevatorArriving(move.load);

            return newState;
        }

        @Override
        public String toString() {
            return "State " + getScore() + " {" +
                    "e = " + elevatorFloor +
                    ", f4 " + f4 +
                    ", f3 " + f3 +
                    ", f2 " + f2 +
                    ", f1 " + f1 +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            State state = (State) o;

            if (elevatorFloor != state.elevatorFloor) return false;
            if (!f4.equals(state.f4)) return false;
            if (!f3.equals(state.f3)) return false;
            if (!f2.equals(state.f2)) return false;
            return f1.equals(state.f1);
        }

        @Override
        public int hashCode() {
            int result = elevatorFloor;
            result = 31 * result + f4.hashCode();
            result = 31 * result + f3.hashCode();
            result = 31 * result + f2.hashCode();
            result = 31 * result + f1.hashCode();
            return result;
        }
    }

    /////////////

    class Floor {
        List<Thing> things = new ArrayList<>();

        boolean isEmpty() {
            return things.isEmpty();
        }

        boolean isLegal() {
            return validList(things);
        }

        LinkedList<ElevatorLoad> allPossibleLoads() {
            LinkedList<ElevatorLoad> result = new LinkedList<>();
            int size = things.size();
            if (size >= 2) {
                for (int i = 0; i + 1 < size; i++) {
                    for (int j = i + 1; j < size; j++) {
                        final ElevatorLoad load = new ElevatorLoad(things.get(i), things.get(j));
                        if (validWithout(load)) {
                            result.add(load);
                        }
                    }
                }
            }

            for (Thing thing : things) {
                final ElevatorLoad load = new ElevatorLoad(thing, NOTHING);
                if (validWithout(load)) {
                    result.add(load);
                }
            }

            return result;
        }

        boolean validWith(ElevatorLoad load) {
            List<Thing> allThings = new ArrayList<>();
            allThings.addAll(things);
            allThings.add(load.first);
            if (load.second != NOTHING) {
                allThings.add(load.second);
            }
            return validList(allThings);
        }

        private boolean validWithout(ElevatorLoad load) {
            List<Thing> remainingThings = new ArrayList<>();
            remainingThings.addAll(things);
            remainingThings.remove(load.first);
            if (load.second != NOTHING) {
                remainingThings.remove(load.second);
            }
            return validList(remainingThings);
        }

        private boolean validList(List<Thing> list) {
            List<Thing> generators = new ArrayList<>();
            List<Thing> microchips = new ArrayList<>();
            for (Thing thing : list) {
                if (thing.type == Type.Generator) {
                    generators.add(thing);
                } else {
                    microchips.add(thing);
                }
            }
            if (generators.isEmpty()) {
                return true;
            }

            for (Thing chip : microchips) {
                if (!generators.contains(new Thing(chip.material, Type.Generator))) {
                    return false;
                }
            }

            return true;
        }

        void elevatorLeaving(ElevatorLoad load) {
            things.remove(load.first);
            if (load.second != NOTHING) {
                things.remove(load.second);
            }
        }

        void elevatorArriving(ElevatorLoad load) {
            things.add(load.first);
            if (load.second != NOTHING) {
                things.add(load.second);
            }
            if (!isLegal()) {
                throw new IllegalArgumentException("elevatorArriving(ElevatorLoad " + load + ")");
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Floor other = (Floor) o;

            if (things.size() != other.things.size()) {
                return false;
            }

            for (Thing thing : things) {
                if (!other.things.contains(thing)) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public int hashCode() {
            return things.hashCode();
        }

        @Override
        public String toString() {
            if (things.isEmpty()) {
                return "[]";
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append('[');
            for (Thing thing: things) {
                stringBuilder.append(thing.toString()).append(' ');
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            stringBuilder.append(']');
            return stringBuilder.toString();
        }
    }

    //---------------------------
    public final Thing NOTHING = new Thing(Material.ILLEGAL, Type.ILLEGAL);

    public class Thing {
        Type type;
        Material material;

        public Thing(Material material, Type type) {
            this.material = material;
            this.type = type;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Thing thing = (Thing) o;

            if (type != thing.type) return false;
            return material == thing.material;
        }

        @Override
        public int hashCode() {
            return 31 * type.hashCode() + material.hashCode();
        }

        @Override
        public String toString() {
            return material.toString().substring(0, 2) + type.toString().substring(0, 1);
        }
    }

    class ElevatorLoad {
        Thing first;
        Thing second;

        public ElevatorLoad(Thing first, Thing second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public String toString() {
            return "ElevatorLoad{" +
                    "first=" + first +
                    ", second=" + second +
                    '}';
        }
    }

    class Move {
        Direction direction;
        ElevatorLoad load;

        public Move(Direction direction, ElevatorLoad load) {
            this.direction = direction;
            this.load = load;
        }
    }

    enum Direction {
        Up,
        Down
    }
    enum Type {
        Generator,
        Microchip,
        ILLEGAL
    }

    enum Material {
        Hydrogen,
        Lithium,
        Thulium,
        Plutonium,
        Strontium,
        Promethium,
        Ruthenium,
        ILLEGAL
    }

    @SuppressWarnings("unused")
    private State makeTestStartState() {
        State startState = new State();

        //The first floor contains a hydrogen-compatible microchip and a lithium-compatible microchip.
        startState.f1.things.add(new Thing(Material.Hydrogen, Type.Microchip));
        startState.f1.things.add(new Thing(Material.Lithium, Type.Microchip));

        //The second floor contains a hydrogen generator.
        startState.f2.things.add(new Thing(Material.Hydrogen, Type.Generator));

        //The third floor contains a lithium generator.
        startState.f3.things.add(new Thing(Material.Lithium, Type.Generator));

        //The fourth floor contains nothing relevant.
        return startState;
    }

    private State makeStartState() {
        State startState = new State();

        /*
            The first floor contains  a thulium generator, a thulium-compatible microchip,
                                      a plutonium generator, and a strontium generator.
         */
        startState.f1.things.add(new Thing(Material.Thulium, Type.Generator));
        startState.f1.things.add(new Thing(Material.Thulium, Type.Microchip));
        startState.f1.things.add(new Thing(Material.Plutonium, Type.Generator));
        startState.f1.things.add(new Thing(Material.Strontium, Type.Generator));

        // The second floor contains a plutonium-compatible microchip and a strontium-compatible microchip.

        startState.f2.things.add(new Thing(Material.Plutonium, Type.Microchip));
        startState.f2.things.add(new Thing(Material.Strontium, Type.Microchip));

        /*
            The third floor contains  a promethium generator, a promethium-compatible microchip,
                                      a ruthenium generator, and a ruthenium-compatible microchip.
         */
        startState.f3.things.add(new Thing(Material.Promethium, Type.Generator));
        startState.f3.things.add(new Thing(Material.Promethium, Type.Microchip));
        startState.f3.things.add(new Thing(Material.Ruthenium, Type.Generator));
        startState.f3.things.add(new Thing(Material.Ruthenium, Type.Microchip));

        // The fourth floor contains nothing relevant.
        return startState;
    }
}
