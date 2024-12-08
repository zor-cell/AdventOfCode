package aoc24;

import main.Day;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

public class Day7 implements Day {

    @Override
    public void run(Scanner scanner) {
        String s = "190: 10 19\n" +
                "3267: 81 40 27\n" +
                "83: 17 5\n" +
                "156: 15 6\n" +
                "7290: 6 8 6 15\n" +
                "161011: 16 10 13\n" +
                "192: 17 8 14\n" +
                "21037: 9 7 18 13\n" +
                "292: 11 6 16 20";
        //scanner = new Scanner(s);

        List<BigInteger> results = new ArrayList<>();
        List<BigInteger[]> nums = new ArrayList<>();
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] split1 = line.split(":");
            BigInteger result = new BigInteger(split1[0]);

            String[] split2 = split1[1].substring(1).split(" ", -1);
            List<BigInteger> num = Arrays.stream(split2).map(BigInteger::new).toList();
            BigInteger[] a = new BigInteger[num.size()];
            for(int i = 0;i < num.size();i++) {
                a[i] = num.get(i);
            }

            results.add(result);
            nums.add(a);
        }



        BigInteger count = BigInteger.valueOf(0);
        String[] operators = {"+", "||",  "*"};
        for(int i = 0;i < results.size();i++) {
            BigInteger res = results.get(i);
            BigInteger[] num = nums.get(i);

            for(String op : operators) {
                boolean found = calculate(res, num, op, 0, new BigInteger("0"));
                if(found) {
                    count = count.add(res);
                    break;
                }
            }
        }

        System.out.println(count);
    }

    private boolean calculate(BigInteger target, BigInteger[] nums, String operator, int i, BigInteger sum) {
        BigInteger cur = new BigInteger("0");

        if(i > nums.length - 1) return false;
        else if(i == 0) {
            cur = calc(nums[i], nums[i + 1], operator);
            i++;
        } else {
            cur = calc(sum, nums[i], operator);
        }

        if(cur.equals(target)) return true;
        else if(cur.compareTo(target) > 0) return false;

        boolean a = calculate(target, nums, "+", i + 1, cur);
        boolean c = calculate(target, nums, "||", i + 1, cur);
        boolean b = calculate(target, nums, "*", i + 1, cur);


        return a || b || c;
    }

    private BigInteger calc(BigInteger a, BigInteger b, String op) {
        if(op.equals("+")) {
            return a.add(b);
        } else if(op.equals("*")) {
            return a.multiply(b);
        } else if(op.equals("||")) {
            return new BigInteger(a.toString() + b);
        }

        return new BigInteger("0");
    }
}
