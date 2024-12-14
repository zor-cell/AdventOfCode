package aoc24;

import main.Day;

import java.awt.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day13 implements Day {
    public record Machine(int[] a, int[] b, int[] p) {};

    @Override
    public void run(Scanner scanner) {
        String s = "Button A: X+94, Y+34\n" +
                "Button B: X+22, Y+67\n" +
                "Prize: X=8400, Y=5400\n" +
                "\n" +
                "Button A: X+26, Y+66\n" +
                "Button B: X+67, Y+21\n" +
                "Prize: X=12748, Y=12176\n" +
                "\n" +
                "Button A: X+17, Y+86\n" +
                "Button B: X+84, Y+37\n" +
                "Prize: X=7870, Y=6450\n" +
                "\n" +
                "Button A: X+69, Y+23\n" +
                "Button B: X+27, Y+71\n" +
                "Prize: X=18641, Y=10279";
       // scanner = new Scanner(s);

        List<Machine> machines = new ArrayList<>();
        while(scanner.hasNextLine()) {
            String buttonA = scanner.nextLine();
            String buttonB = scanner.nextLine();
            String prize = scanner.nextLine();
            if(scanner.hasNextLine()) scanner.nextLine(); //newline

            String[] axy = buttonA.split(":")[1].split(",");
            int[] a = parseButton(axy[0], axy[1]);

            String[] bxy = buttonB.split(":")[1].split(",");
            int[] b = parseButton(bxy[0], bxy[1]);

            String[] pxy = prize.split(":")[1].split(",");
            int[] p = parsePrize(pxy[0], pxy[1]);

            Machine machine = new Machine(a, b, p);
            machines.add(machine);


        }

        BigInteger count = BigInteger.ZERO;
        for(Machine machine : machines) {
            //int cur = greedy(machine);
            //if(cur < Integer.MAX_VALUE) count += cur;

            var cur = math(machine);
            count = count.add(cur);
        }
        System.out.println(count);
    }

    private int greedy(Machine machine) {
        int[] a = machine.a;
        int ax = a[0];
        int ay = a[1];

        int[] b = machine.b;
        int bx = b[0];
        int by = b[1];

        int[] p = machine.p;
        int px = p[0];
        int py = p[1];

        int min = Integer.MAX_VALUE;
        for(int cntA = 0;cntA <= 100;cntA++) {
            for(int cntB = 0;cntB <= 100;cntB++) {
                int totalX = ax * cntA + bx * cntB;
                int totalY = ay * cntA + by * cntB;

                if(totalX > px || totalY > py) {
                    break;
                } else if(totalX == px && totalY == py) {
                    min = Math.min(min, cntA * 3 + cntB);
                }
            }
        }
        return min;
    }

    private BigInteger math(Machine machine) {
        int[] ma = machine.a;
        BigInteger ax = bInt(ma[0]);
        BigInteger ay = bInt(ma[1]);

        int[] mb = machine.b;
        BigInteger bx = bInt(mb[0]);
        BigInteger by = bInt(mb[1]);

        BigInteger inc = new BigInteger("10000000000000"); //add increment to prize
        int[] p = machine.p;
        BigInteger px = bInt(p[0]).add(inc);
        BigInteger py = bInt(p[1]).add(inc);

        //ax * a + bx * b = px I
        //ay * a + by * b = py II

        //I * -by
        var t1 = ax.multiply(by.negate());
        var t2 = bx.multiply(by.negate());
        var t3 = px.multiply(by.negate());

        //II * ay
        var t4 = ay.multiply(bx);
        var t5 = by.multiply(bx);
        var t6 = py.multiply(bx);

        //I + II
        var r1 = t1.add(t4);
        var r2 = t2.add(t5); //always 0
        var r3 = t3.add(t6);

        BigInteger[] a = r3.divideAndRemainder(r1);
        if(!a[1].equals(BigInteger.ZERO)) {
            return BigInteger.ZERO; //invalid button presses
        }

        //a in II
        BigInteger[] b = py.subtract(ay.multiply(a[0])).divideAndRemainder(by);
        if(!b[1].equals(BigInteger.ZERO)) {
            return BigInteger.ZERO; //invalid button presses
        }

        return a[0].multiply(new BigInteger("3")).add(b[0]);
    }

    private BigInteger bInt(int a) {
        return new BigInteger(String.valueOf(a));
    }

    private int[] parseButton(String x, String y) {
        int bx = Integer.parseInt(x.split("\\+")[1]);
        int by = Integer.parseInt(y.split("\\+")[1]);

        return new int[]{bx, by};
    }

    private int[] parsePrize(String x, String y) {
        int px = Integer.parseInt(x.split("=")[1]);
        int py = Integer.parseInt(y.split("=")[1]);
        return new int[] {px, py};
    }
}
