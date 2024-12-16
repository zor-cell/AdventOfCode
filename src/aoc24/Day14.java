package aoc24;

import main.Day;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day14 implements Day {
    class Robot {
        public Vector2 pos;
        public Vector2 vel;

        public Robot(Vector2 pos, Vector2 vel) {
            this.pos = pos;
            this.vel = vel;
        }
    }

    @Override
    public void run(Scanner scanner) {
        String s = "p=0,4 v=3,-3\n" +
                "p=6,3 v=-1,-3\n" +
                "p=10,3 v=-1,2\n" +
                "p=2,0 v=2,-1\n" +
                "p=0,0 v=1,3\n" +
                "p=3,0 v=-2,-2\n" +
                "p=7,6 v=-1,-3\n" +
                "p=3,0 v=-1,-2\n" +
                "p=9,3 v=2,3\n" +
                "p=7,3 v=-1,2\n" +
                "p=2,4 v=2,-3\n" +
                "p=9,5 v=-3,-3";
        //scanner = new Scanner(s);

        List<Robot> robots = new ArrayList<>();
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            var lineSplit = line.split(" ");

            var v1 = lineSplit[0].split("=");
            var nums1 = v1[1].split(",");
            int x1 = Integer.parseInt(nums1[0]);
            int y1 = Integer.parseInt(nums1[1]);
            Vector2 pos = new Vector2(x1, y1);

            var v2 = lineSplit[1].split("=");
            var nums2 = v2[1].split(",");
            int x2 = Integer.parseInt(nums2[0]);
            int y2 = Integer.parseInt(nums2[1]);
            Vector2 vel = new Vector2(x2, y2);

            Robot robot = new Robot(pos, vel);
            robots.add(robot);
        }

        Vector2 border = new Vector2(101, 103);
        for(int i = 0;i < 100;i++) {
            for (Robot robot : robots) {
                Vector2 updated = robot.pos.add(robot.vel);

                updated.x = Math.floorMod(updated.x, border.x);
                updated.y = Math.floorMod(updated.y, border.y);

                robot.pos = updated;
            }
        }

        int midx = border.x / 2;
        int midy = border.y / 2;
        long[] quarters = new long[4];
        for(Robot robot : robots) {
            if(robot.pos.x < midx && robot.pos.y < midy) {
                quarters[0]++;
            } else if(robot.pos.x >= midx + 1 && robot.pos.y < midy) {
                quarters[1]++;
            } else if(robot.pos.x < midx && robot.pos.y >= midy + 1) {
                quarters[2]++;
            } else if(robot.pos.x >= midx + 1 && robot.pos.y >= midy + 1) {
                quarters[3]++;
            }
        }

        long count = 1;
        for(long q : quarters) {
            count *= q;
        }
        System.out.println(count);
    }
}
