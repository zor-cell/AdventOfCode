package main;

import aoc24.Day1;
import aoc24.Day2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
       try {
           Day currentDay = new Day2();
           String classString = currentDay.getClass().toString();
           String asString = classString.substring(classString.lastIndexOf('.') + 1).toLowerCase();

           File file = new File("input/"+ asString + ".txt");
           Scanner scanner = new Scanner(file);

           currentDay.run(scanner);
       } catch(FileNotFoundException e) {
           System.out.println(e.getMessage());
       }
    }
}