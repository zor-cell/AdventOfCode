package aoc24;

import main.Day;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 implements Day {
    @Override
    public void run(Scanner scanner) {
        String input = "";
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            input += line;
        }

        int result = 0;

        int l = 0;
        int r = input.indexOf("don't()", l);
        boolean doing = true;
        while(r >= 0) {
            String match = input.substring(l, r);

            l = r;
            if(doing) {
                System.out.println(match);
                result += matchMult(match);
                r = input.indexOf("do()", l);
            } else {
                r = input.indexOf("don't()", l);
            }

            doing = !doing;
        }
        if(doing) {
            String last = input.substring(l, input.length() - 1);
            System.out.println(last);
            result += matchMult(last);
        }

        System.out.println(result);
    }

    private int matchMult(String toMatch) {
        int temp = 0;

        Pattern pattern = Pattern.compile("mul\\(\\d{1,3},\\d{1,3}\\)");
        Matcher matcher = pattern.matcher(toMatch);
        while(matcher.find()) {
            String match = matcher.group();
            temp += mult(match);
        }

        return temp;
    }

    private int mult(String mul) {
        String nums = mul.split("\\(")[1];
        String[] split = nums.split(",");
        int a = Integer.parseInt(split[0]);
        int b = Integer.parseInt(split[1].substring(0, split[1].length() - 1));

        return a * b;
    }
}
