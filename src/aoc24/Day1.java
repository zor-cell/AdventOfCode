package aoc24;


import main.Day;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Day1 implements Day {
    @Override
    public void run(Scanner scanner) {
        var list1 = new ArrayList<Integer>();
        var list2 = new ArrayList<Integer>();

        while(scanner.hasNextLine()) {
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            scanner.nextLine();

            list1.add(a);
            list2.add(b);
        }

        Collections.sort(list1);
        Collections.sort(list2);

        int distance = 0;
        for(int i = 0;i < list1.size();i++) {
            int r = Math.abs(list1.get(i) - list2.get(i));
            distance += r;
        }

        System.out.println(distance);

        int similarity = 0;
        for(int i = 0;i < list1.size();i++) {
            int cur = list1.get(i);
            int count = 0;
            for(int j = 0;j < list2.size();j++) {
                if(list2.get(j) == cur) {
                    count++;
                }
            }

            similarity += cur * count;
        }

        System.out.println(similarity);
    }
}
