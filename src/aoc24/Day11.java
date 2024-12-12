package aoc24;

import com.sun.source.tree.BinaryTree;
import main.Day;

import java.math.BigInteger;
import java.util.*;

public class Day11 implements Day {
    public record StoredCount(BigInteger count, int iteration) {}

    @Override
    public void run(Scanner scanner) {
        //List<BigInteger> stones = new ArrayList<>();
        Map<Integer, BigInteger> map = new HashMap<>();

        String s = "125 17";
        //scanner = new Scanner(s);

        String line = scanner.nextLine();
        String[] split = line.split(" ");
        int index = 0;
        for(String num : split) {
            BigInteger cur = new BigInteger(num);
            //stones.add(cur);
            map.put(index++, cur);
        }

        Map<StoredCount, Long> memo = new HashMap<>();
        long count = 0;
        for(BigInteger num : map.values()) {
            long cur = computeScore(memo, num, 75);
            count += cur;
        }
        System.out.println(count);

        //1
        /*for(int r = 0;r < 25;r++) {
            List<BigInteger> updated = new ArrayList<>(stones);
            int offsetIndex = 0;
            for(int i = 0;i < stones.size();i++) {
                BigInteger cur = stones.get(i);
                String curString = cur.toString();

                if(cur.equals(BigInteger.ZERO)) {
                    updated.set(i + offsetIndex,  BigInteger.ONE);
                } else if(curString.length() % 2 == 0) {
                    BigInteger left = new BigInteger(curString.substring(0, curString.length() / 2));
                    BigInteger right = new BigInteger(curString.substring(curString.length() / 2));

                    updated.set(i + offsetIndex, left);
                    updated.add(i + offsetIndex + 1, right);
                    offsetIndex++;
                } else {
                    BigInteger num = cur.multiply(new BigInteger("2024"));
                    updated.set(i + offsetIndex, num);
                }
            }
            stones = updated;
            System.out.println("Iteration " + r + ": " + stones.size());
        }*/

        //System.out.println(stones.size());
    }

    private long computeScore(Map<StoredCount, Long> memo, BigInteger cur, int layer) {
        if(layer == 0) {
            return 1;
        }

        String curString = cur.toString();
        StoredCount stored = new StoredCount(cur, layer);
        if(memo.containsKey(stored)) {
            return memo.get(stored);
        }

        BigInteger next;
        if(cur.equals(BigInteger.ZERO)) {
            next = BigInteger.ONE;
        } else if(curString.length() % 2 == 0) {
            BigInteger left = new BigInteger(curString.substring(0, curString.length() / 2));
            BigInteger right = new BigInteger(curString.substring(curString.length() / 2));

            long leftC = computeScore(memo, left, layer - 1);
            long rightC = computeScore(memo, right, layer - 1);
            memo.put(stored, leftC + rightC);
            return leftC + rightC;
        } else {
            next = cur.multiply(new BigInteger("2024"));
        }

        long score = computeScore(memo, next, layer - 1);
        memo.put(stored, score);
        return score;
    }
}


