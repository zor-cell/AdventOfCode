package aoc24;

import main.Day;

import java.util.*;

record Pos(int i, int j, int di, int dj) {}

public class Day6 implements Day {
    @Override
    public void run(Scanner scanner) {
        List<char[]> map = new ArrayList<>();

        String s = "....#.....\n" +
                ".........#\n" +
                "..........\n" +
                "..#.......\n" +
                ".......#..\n" +
                "..........\n" +
                ".#..^.....\n" +
                "........#.\n" +
                "#.........\n" +
                "......#...";
        //scanner = new Scanner(s);

        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();

            char[] cur = new char[line.length()];
            for(int i = 0;i < line.length();i++) {
                cur[i] = line.charAt(i);
            }

            map.add(cur);
        }
        int rows = map.size();
        int cols = map.getFirst().length;

        int gi = -1;
        int gj = -1;
        int di = -1;
        int dj = 0;
        for(int i = 0;i < rows;i++) {
            for(int j = 0;j < cols;j++) {
                if(map.get(i)[j] == '^') {
                    gi = i;
                    gj = j;
                }
            }
        }

        Set<Pos> visited = new HashSet<>();
        simulate(gi, gj, di, dj, map, visited, true);

        int count = 0;
        for(Pos pos : visited) {
            int i = pos.i();
            int j = pos.j();

            if(i == gi && j == gj) continue;

            map.get(i)[j] = '#';
            int c = simulate(gi, gj, di, dj, map, visited, false);
            count += c;
            map.get(i)[j] = '.';
        }

        System.out.println(count);

        //1
        /*int count = 0;
        for(int i = 0;i < rows;i++) {
            for(int j = 0;j < cols;j++) {
                if(map.get(i)[j] == 'X') {
                    count++;
                }
            }
        }
        System.out.println(count);*/
    }

    private int simulate(int gi, int gj, int di, int dj, List<char[]> map, Set<Pos> visited, boolean setVisited) {
        int rows = map.size();
        int cols = map.getFirst().length;

        Set<Pos> currentVisited = new HashSet<>();

        //1
        //map.get(gi)[gj] = 'X';
        boolean isLoop = true;
        while(true) {
            int i = gi + di;
            int j = gj + dj;

            //out of bounds
            if(i < 0 || i > rows - 1 || j < 0 || j > cols - 1) {
                isLoop = false;
                break;
            }

            if(map.get(i)[j] == '#') {
                int[] d = rotate(di, dj);
                di = d[0];
                dj = d[1];

                continue;
            } else {
                //1
                //map.get(i)[j] = 'X';
                Pos curPos = new Pos(i, j, di, dj);
                //already visited in same direction -> loop
                if(currentVisited.contains(curPos)) {
                    break;
                }

                if(setVisited) visited.add(new Pos(i, j, 0, 0)); //direction irrelevant
                else currentVisited.add(curPos);
            }

            gi = i;
            gj = j;
        }

        return isLoop ? 1 : 0;
    }

    private int[] rotate(int di, int dj) {
        int[] d = new int[2];
        if(di == -1 && dj == 0) {
            d[0] = 0;
            d[1] = 1;
        } else if(di == 0 && dj == 1) {
            d[0] = 1;
            d[1] = 0;
        } else if(di == 1 && dj == 0) {
            d[0] = 0;
            d[1] = -1;
        } else {
            d[0] = -1;
            d[1] = 0;
        }

        return d;
    }
}
