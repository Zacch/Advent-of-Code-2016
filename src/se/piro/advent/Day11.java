package se.piro.advent;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Rolf Staflin 2016-12-11 11:11
 */
public class Day11 {

    State ILLEGAL_STATE = new State();

    LinkedList<State> shortestSolution = null;
    int shortestLength = 16000;

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
        LinkedList<State> previousStates = new LinkedList<>();
        final State startState = makeStartState();
        previousStates.add(startState);

        //recurse(previousStates);
        widthFirstSearch(startState);

        print("Shortest solution is " + shortestLength + " moves:");
        for (int i = shortestSolution.size() - 1; i >= 0; i--) {
            print(shortestSolution.get(i).toString());
        }
    }

    Hashtable<State, LinkedList<State>> seenSolutions = new Hashtable<>();
    LinkedList<State> statesToTry = new LinkedList<>();

    void widthFirstSearch(State startState) {
        statesToTry.add(startState);
        seenSolutions.put(startState, new LinkedList<>());

        long startTime = System.currentTimeMillis();
        boolean solutionFound = false;
        long laps = 0;
        while (!solutionFound) {
            State state = statesToTry.pop();
            if (++laps % 100000 == 0) {
                long timeSpent = System.currentTimeMillis() - startTime;
                print("Testing " + state + " with history: " + seenSolutions.get(state).size() +
                        " Seen states: " + seenSolutions.size() + " States to try: " + statesToTry.size() +
                        " Laps: " + laps + " (" + laps * 1000 / timeSpent + "/s)");
            }
            List<Move> validMoves = state.calculateValidMoves();
            for (Move move : validMoves) {
                State newState = state.makeMove(move);
                if (newState.isEndState()) {
                    solutionFound = true;
                    LinkedList<State> newStatePath = new LinkedList<>();
                    newStatePath.addAll(seenSolutions.get(state));
                    newStatePath.add(state);
                    recordEndState(newStatePath, newState);
                } else if (newState.isLegal() && !seenSolutions.keySet().contains(newState)) {
                    LinkedList<State> newStatePath = new LinkedList<>();
                    newStatePath.addAll(seenSolutions.get(state));
                    newStatePath.add(state);
                    seenSolutions.put(newState, newStatePath);
                    statesToTry.add(newState);
                }
            }
        }
    }

    private void recurse(LinkedList<State> states) {

        final int stateSize = states.size();
        // print("Recurse: " + states.size());
        // We've already found a shorter solution.
        if (stateSize >= shortestLength) {
            return;
        }

        State currentState = states.peek();
        List<Move> validMoves = currentState.calculateValidMoves();
        for (Move move : validMoves) {
            State newState = currentState.makeMove(move);
            if (newState.isEndState()) {
                recordEndState(states, newState);
            } else if (newState.isLegal() && !states.contains(newState)) {
                LinkedList<State> newStates = new LinkedList<>();
                newStates.addAll(states);
                newStates.push(newState);
                recurse(newStates);
            }
        }
    }

    private void recordEndState(LinkedList<State> states, State goalState) {
        final int solutionLength = states.size();
        if (solutionLength < shortestLength) {
            shortestLength = solutionLength;
            shortestSolution = new LinkedList<>();
            shortestSolution.addAll(states);
            shortestSolution.push(goalState);
            print("Reached goal after " + solutionLength + " moves!");
        }
    }

    /////////////

    class State {
        int elevatorFloor = 1;
        Floor f4 = new Floor();
        Floor f3 = new Floor();
        Floor f2 = new Floor();
        Floor f1 = new Floor();


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
            return "State{" +
                    "elevatorFloor=" + elevatorFloor +
                    ", f4=" + f4 +
                    ", f3=" + f3 +
                    ", f2=" + f2 +
                    ", f1=" + f1 +
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
