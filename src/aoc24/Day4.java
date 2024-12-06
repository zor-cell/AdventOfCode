package aoc24;

import main.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Day4 implements Day {
    @Override
    public void run(Scanner scanner) {
        List<char[]> list = new ArrayList<>();

        String s = "MMMSXXMASM\n" +
        "MSAMXMSMSA\n" +
        "AMXSXMAAMM\n" +
        "MSAMASMSMX\n" +
        "XMASAMXAMM\n" +
        "XXAMMXXAMA\n" +
        "SMSMSASXSS\n" +
        "SAXAMASAAA\n" +
        "MAMMMXMMMM\n" +
        "MXMXAXMASX";

        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();

            var cur = new char[line.length()];
            for(int i = 0;i < line.length();i++) {
                cur[i] = line.charAt(i);
            }
            list.add(cur);
        }
        int rows = list.size();
        int cols = list.getFirst().length;

        int count = 0;
        for(int i = 0;i < rows;i++) {
            for(int j = 0;j < cols;j++) {
                char cur = list.get(i)[j];

                if(cur == 'A') {
                    if(i - 1 < 0 || j - 1 < 0 || j + 1 > cols - 1 || i + 1 > rows - 1) {
                        continue;
                    }
                    char ul = list.get(i - 1)[j - 1];
                    char ur = list.get(i - 1)[j + 1];
                    char dl = list.get(i + 1)[j - 1];
                    char dr = list.get(i + 1)[j + 1];

                    boolean a = (ul == 'M' && dr == 'S') || (ul == 'S' && dr == 'M');
                    boolean b = (dl == 'M' && ur == 'S') || (dl == 'S' && ur == 'M');

                    if(a && b) count++;
                }
            }
        }
        System.out.println(count);

        /*
        1
        int[][] dirs = {
                {-1, -1},
                {-1, 0},
                {-1, 1},
                {0, 1},
                {1, 1},
                {1, 0},
                {1, -1},
                {0, -1},
        };

        int count = 0;
        String xmas = "XMAS";
        for(int i = 0;i < rows;i++) {
            for(int j = 0;j < cols;j++) {
                for(int[] d : dirs) {
                    boolean found = true;
                    for(int k = 0;k < xmas.length();k++) {
                        int ci = i + d[0] * k;
                        int cj = j + d[1] * k;
                        if(ci < 0 || ci > rows - 1 || cj < 0 || cj > cols - 1) {
                            found = false;
                            break;
                        }

                        char cur = list.get(ci)[cj];
                        char xmasCur = xmas.charAt(k);
                        if(cur != xmasCur) {
                            found = false;
                            break;
                        }
                    }
                    if(found) count++;
                }
            }
        }
        System.out.println(count);*/
    }
}
