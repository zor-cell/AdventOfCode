package aoc24;

import main.Day;

import java.util.*;

public class Day10 implements Day {
    public class Position {
        public int i;
        public int j;

        public Position(int i, int j) {
            this.i = i;
            this.j = j;
        }

        public boolean inBounds(int rows, int cols) {
            return i >= 0 && i <= rows - 1 && j >= 0 && j <= cols - 1;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Position position)) return false;
            return i == position.i && j == position.j;
        }

        @Override
        public int hashCode() {
            return Objects.hash(i, j);
        }
    }

    @Override
    public void run(Scanner scanner) {
        String s = "89010123\n" +
                "78121874\n" +
                "87430965\n" +
                "96549874\n" +
                "45678903\n" +
                "32019012\n" +
                "01329801\n" +
                "10456732";
        //scanner = new Scanner(s);

        List<char[]> map = new ArrayList<>();
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            char[] cur = new char[line.length()];
            for(int i = 0;i < line.length();i++) {
                cur[i] = line.charAt(i);
            }

            map.add(cur);
        }

        List<Position> pos = new ArrayList<>();
        int rows = map.size();
        int cols = map.getFirst().length;
        for(int i = 0;i < rows;i++) {
            for(int j = 0;j < cols;j++) {
                char c = map.get(i)[j];
                if(c == '0') {
                    pos.add(new Position(i, j));
                }
            }
        }

        int count = 0;
        for(Position p : pos) {
            Set<Position> reached = new HashSet<>();
            int score = dfs(map, reached, p);
            count += score;
        }

        System.out.println(count);
    }

    private int dfs(List<char[]> map, Set<Position> reached, Position pos) {
        char c = map.get(pos.i)[pos.j];
        int rows = map.size();
        int cols = map.getFirst().length;
        int[][] dirs = {
                {-1, 0},
                {0, 1},
                {1, 0},
                {0, -1}
        };

        int sum = 0;
        for(int[] d : dirs) {
            Position neighbor = new Position(pos.i + d[0], pos.j + d[1]);
            if(!neighbor.inBounds(rows, cols)) continue;

            char nc = map.get(neighbor.i)[neighbor.j];
            if(c + 1 == nc) {
                if(nc == '9') {
                    //part 1
                    //if(!reached.contains(neighbor)) sum++;
                    //reached.add(neighbor);

                    //part 2
                    sum++;
                } else {
                    sum += dfs(map, reached, neighbor);
                }
            }
        }

        return sum;
    }
}
