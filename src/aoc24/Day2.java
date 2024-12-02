package aoc24;

import main.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Day2 implements Day {
    @Override
    public void run(Scanner scanner) {
        int safe = 0;

        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            var report = Arrays.stream(line.split(" "))
                    .map(Integer::parseInt)
                    .toList();

            boolean safeReport = true;
            int state = 0; //-1 decreasing, +1 increasing
            int errors = 1;
            for(int i = 0;i < report.size() - 1;i++) {
                if(state == 0) {
                    if(report.get(i) > report.get(i + 1)) {
                        state = -1;
                    } else if(report.get(i) < report.get(i + 1)) {
                        state = 1;
                    }
                } else if(state == -1) {
                    if(report.get(i) < report.get(i + 1)) {
                        if(errors <= 0) {
                            safeReport = false;
                            break;
                        } else errors--;
                    }
                } else { //state == 1
                    if(report.get(i) > report.get(i + 1)) {
                        if(errors <= 0) {
                            safeReport = false;
                            break;
                        } else errors--;
                    }
                }

                int diff = Math.abs(report.get(i) - report.get(i + 1));
                if(diff < 1 || diff > 3) {
                    if(errors <= 0) {
                        safeReport = false;
                        break;
                    } else errors--;
                }
            }

            if(safeReport) safe++;
        }

        System.out.println(safe);
    }
}
