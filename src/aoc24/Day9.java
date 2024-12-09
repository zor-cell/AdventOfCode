package aoc24;

import main.Day;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Day9 implements Day {
    class FileSize implements Comparable {
        public int id;
        public int size;

        public FileSize(int id, int size) {
            this.id = id;
            this.size = size;
        }

        @Override
        public int compareTo(Object o) {
            if(o instanceof FileSize other) {
                //first id, then size
                return size - other.size;
            }

            return 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof FileSize fileSize)) return false;
            return id == fileSize.id && size == fileSize.size;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, size);
        }
    }

    @Override
    public void run(Scanner scanner) {
        String temp = "2333133121414131402";
        //scanner = new Scanner(temp);

        List<String> compressed = new ArrayList<>();
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            compressed.add(line);
        }

        List<Integer> ids = new ArrayList<>();
        List<FileSize> sizes = new ArrayList<>(); //for part 2
        for(int i = 0;i < compressed.size();i++) {
            String s = compressed.get(i);
            int id = 0;
            for(int j = 0;j < s.length();j++) {
                int times = Integer.parseInt(String.valueOf(s.charAt(j)));
                if(j % 2 == 0) {
                    //memory
                    repeat(ids, id, times);
                    sizes.add(new FileSize(id, times));
                    id++;
                } else {
                    //free
                    repeat(ids, -1, times);
                }
            }
        }

        List<Integer> allMoved = new ArrayList<>();
        int l = 0;
        int r = ids.size() - 1;
        int size = 0;
        while(l <= r) {
            if(ids.get(l) > -1) {
                if(size > 0) {
                    //allocate batch

                    size = 0;
                }

                allMoved.add(ids.get(l));
                l++;
            } else {
                size++;
            }
        }

        /*
                int l = 0;
        int r = ids.size() - 1;
        int size = 0;
        while(l <= r) {
            if(ids.get(l) > -1) {
                allMoved.add(ids.get(l));
                l++;
            } else {
                if(ids.get(r) > -1) {
                    allMoved.add(ids.get(r));
                    r--;
                    l++;
                } else {
                    r--;
                }
            }
        }
         */

        BigInteger checksum = BigInteger.ZERO;
        for(int i = 0;i < allMoved.size();i++) {
            BigInteger index = new BigInteger(String.valueOf(i));
            BigInteger id = new BigInteger(String.valueOf(allMoved.get(i)));
            BigInteger cur = index.multiply(id);
            checksum = checksum.add(cur);
        }

        System.out.println(checksum);
    }

    private String repeatString(String c, int times) {
        StringBuilder s = new StringBuilder();
        s.append(c.repeat(Math.max(0, times)));
        return s.toString();
    }

    private void repeat(List<Integer> list, int num, int times) {
        for(int i = 0;i < times;i++) {
            list.add(num);
        }
    }
}
