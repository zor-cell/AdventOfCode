package aoc24;

import main.Day;
import main.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day15 implements Day {
    @Override
    public void run(Scanner scanner) {
        String s = "#######\n" +
                "#...#.#\n" +
                "#.....#\n" +
                "#..OO@#\n" +
                "#..O..#\n" +
                "#.....#\n" +
                "#######\n" +
                "\n" +
                "<vv<<^^<<^^";
        scanner = new Scanner(s);

        List<char[]> map = new ArrayList<>();
        String moves = "";

        boolean readMap = true;
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if(line.isEmpty()) {
                readMap = false;
                continue;
            }

            if(readMap) {
                //char[] cur = new char[line.length()];
                /*for(int i = 0;i < line.length();i++) {
                    cur[i] = line.charAt(i);
                }*/
                char[] cur = new char[line.length() * 2];
                int si = 0;
                for(int i = 0;i < cur.length;i+=2) {
                    char c = line.charAt(si);
                    if(c == 'O') {
                        cur[i] = '[';
                        cur[i + 1] = ']';
                    } else {
                        cur[i] = c;
                        cur[i + 1] = c;
                    }

                    si++;
                }
                map.add(cur);
            } else {
                moves += line;
            }
        }

        Position pos = new Position(-1, -1);
        int rows = map.size();
        int cols = map.getFirst().length;
        for(int i = 0;i < rows;i++) {
            for(int j = 0;j < cols;j++) {
                if(map.get(i)[j] == '@') {
                    pos = new Position(i, j);
                    map.get(i)[j] = '.';
                }
            }
        }

        for(int i = 0;i < moves.length();i++) {
            char c = moves.charAt(i);
            int[] d = dirFromMove(c);

            map.get(pos.i)[pos.j] = '.';

            Position target = new Position(pos.i + d[0], pos.j + d[1]);
            char t = map.get(target.i)[target.j];
            if(t == '.') {
                pos = target;
            } else if(t == '#') {
                map.get(pos.i)[pos.j] = '@';
                continue;
            } else if(t == 'O') {
                Position startPush = new Position(target.i, target.j);
                while(map.get(target.i)[target.j] == 'O') {
                    target = new Position(target.i + d[0], target.j + d[1]);
                }

                if(map.get(target.i)[target.j] == '.') {
                    pos = startPush;
                    map.get(startPush.i)[startPush.j] = '@'; //remove first
                    map.get(target.i)[target.j] = 'O'; //set new obstacle
                    continue;
                } else if(map.get(target.i)[target.j] == '#') {
                    continue;
                }
            }

            map.get(target.i)[target.j] = '@';
        }

        long sum = 0;
        for(int i = 0;i < rows;i++) {
            for (int j = 0; j < cols; j++) {
                if(map.get(i)[j] == 'O') {
                    sum += 100L * i + j;
                }
            }
        }

        System.out.println(sum);
    }

    private void moveTo(List<char[]> map, char c, Position oldPos, Position newPos) {
        map.get(oldPos.i)[oldPos.j] = '.';
    }

    private int[] dirFromMove(char move) {
        int[] dir = new int[2];

        if(move == '^') {
            dir[0] = -1;
            dir[1] = 0;
        } else if(move == '>') {
            dir[0] = 0;
            dir[1] = 1;
        } else if(move == 'v') {
            dir[0] = 1;
            dir[1] = 0;
        } else if(move == '<') {
            dir[0] = 0;
            dir[1] = -1;
        }

        return dir;
    }
}
