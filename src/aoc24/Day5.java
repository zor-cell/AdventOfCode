package aoc24;

import main.Day;

import java.util.*;
import java.util.stream.Collectors;

public class Day5 implements Day {
    @Override
    public void run(Scanner scanner) {
        String s = "47|53\n" +
                "97|13\n" +
                "97|61\n" +
                "97|47\n" +
                "75|29\n" +
                "61|13\n" +
                "75|53\n" +
                "29|13\n" +
                "97|29\n" +
                "53|29\n" +
                "61|53\n" +
                "97|53\n" +
                "61|29\n" +
                "47|13\n" +
                "75|47\n" +
                "97|75\n" +
                "47|61\n" +
                "75|61\n" +
                "47|29\n" +
                "75|13\n" +
                "53|13\n" +
                "\n" +
                "75,47,61,53,29\n" +
                "97,61,53,29,13\n" +
                "75,29,13\n" +
                "75,97,47,61,53\n" +
                "61,13,29\n" +
                "97,13,75,29,47";
        //scanner = new Scanner(s);

        List<int[]> rules = new ArrayList<>();
        List<List<Integer>> allPages = new ArrayList<>();

        boolean isRules = true;
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if(line.isEmpty()) {
                isRules = false;
                continue;
            }

            if(isRules) {
                var cur = new int[2];
                String[] split = line.split("\\|");
                cur[0] = Integer.parseInt(split[0]);
                cur[1] = Integer.parseInt(split[1]);
                rules.add(cur);
            } else {
                List<Integer> cur = Arrays.stream(line.split(",")).map(Integer::parseInt).toList();
                allPages.add(cur);
            }
        }

        int sum = 0;
        for(var pages : allPages) {
            var map = new HashMap<Integer, Integer>(); //page, position
            for(int i = 0;i < pages.size();i++) {
                map.put(pages.get(i), i);
            }

            var ruleMap = new HashMap<Integer, Integer>(); //page, is before another page that exists count
            for(int i = 0;i < pages.size();i++) {
                ruleMap.put(pages.get(i), 0);
            }

            boolean good = true;
            for(int[] rule : rules) {
                int l = rule[0];
                int r = rule[1];

                if(!map.containsKey(l) || !map.containsKey(r)) continue;

                int cur = ruleMap.get(l);
                ruleMap.put(l, cur+1);

                if(map.get(l) > map.get(r)) {
                    good = false;
                }
            }
            if(good) {
                //1
                /*int middle = pages.get(pages.size() / 2);
                sum += middle;*/
            } else {
                List<Integer> copy = new ArrayList<>(pages);

                copy.sort((a, b) -> {
                    return ruleMap.get(b) - ruleMap.get(a);
                });
                int middle = copy.get(copy.size() / 2);
                sum += middle;
            }
        }
        System.out.println(sum);
    }
}
