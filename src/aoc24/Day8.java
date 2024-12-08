package aoc24;

import main.Day;

import java.util.*;

public class Day8 implements Day {
    @Override
    public void run(Scanner scanner) {
        List<char[]> map = new ArrayList<>();

        String s = "............\n" +
                "........0...\n" +
                ".....0......\n" +
                ".......0....\n" +
                "....0.......\n" +
                "......A.....\n" +
                "............\n" +
                "............\n" +
                "........A...\n" +
                ".........A..\n" +
                "............\n" +
                "............";
        //scanner = new Scanner(s);

        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();

            char[] cur = new char[line.length()];
            for(int i = 0;i < line.length();i++) {
                cur[i] = line.charAt(i);
            }

            map.add(cur);
        }

        Map<Character, List<Pos>> nodes = new HashMap<>();

        int rows = map.size();
        int cols = map.getFirst().length;
        for(int i = 0;i < rows;i++) {
            for(int j = 0;j < cols;j++) {
                char c = map.get(i)[j];
                if(c != '.') {
                    if(nodes.containsKey(c)) {
                        nodes.get(c).add(new Pos(i, j));
                    } else {
                        List<Pos> temp = new ArrayList<>();
                        temp.add(new Pos(i, j));
                        nodes.put(c, temp);
                    }
                }
            }
        }

        Set<Pos> antinodes = new HashSet<>();
        for(char c : nodes.keySet()) {
            List<Pos> positions = nodes.get(c);
            for(int i = 0;i < positions.size();i++) {
                Pos cur = positions.get(i);
                for(int j = 0;j < positions.size();j++) {
                    if(j == i) continue;
                    Pos other = positions.get(j);

                    antinodes.add(other);

                    Pos fromCur = cur.minus(other);
                    Pos fromOther = other.minus(cur);

                    Pos a1 = cur.plus(fromCur);
                    while(a1.inBounds(rows, cols)) {
                        antinodes.add(a1);
                        a1 = a1.plus(fromCur);
                    }

                    Pos a2 = other.plus(fromOther);
                    while(a2.inBounds(rows, cols)) {
                        antinodes.add(a2);
                        a2 = a2.plus(fromOther);
                    }
                }
            }
        }

        System.out.println(antinodes.size());
    }

    private class Pos {
        public int i;
        public int j;

        public Pos(int i, int j) {
            this.i = i;
            this.j = j;
        }

        public Pos minus(Pos pos) {
            return new Pos(i - pos.i, j - pos.j);
        }

        public Pos plus(Pos pos) {
            return new Pos(i + pos.i, j + pos.j);
        }

        public boolean inBounds(int rows, int cols) {
            return i >= 0 && i <= rows - 1 && j >= 0 && j <= cols - 1;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Pos pos)) return false;
            return i == pos.i && j == pos.j;
        }

        @Override
        public int hashCode() {
            return Objects.hash(i, j);
        }
    }
}
