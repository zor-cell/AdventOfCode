package aoc24;

import main.Day;

import java.math.BigInteger;
import java.util.*;

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
                //id desc ordering
                return other.id - id;
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
        sizes.sort(FileSize::compareTo);

        while(!sizes.isEmpty()) {
            for(int i = 0;i < sizes.size();i++) {
                FileSize cur = sizes.get(i);

                int size = 0;
                int j = 0;
                boolean found = false;
                while(j < ids.size()) {
                    if (ids.get(j) == -1) {
                        size++;
                        j++;
                        continue;
                    } else {
                        if(ids.get(j).equals(cur.id)) break; //index is more to the right than already
                    }

                    if(size > 0) {
                        if(cur.size <= size) {
                            for(int k = 0;k < ids.size();k++) {
                                if(ids.get(k).equals(cur.id)) {
                                    ids.set(k, -1); //remove ids
                                }
                            }

                            //add ids on the left
                            int startJ = j - size;
                            for(int k = startJ;k < startJ + cur.size;k++) {
                                ids.set(k, cur.id);
                            }

                            found = true;
                            break;
                        } else size = 0;
                    }

                    j++;
                }

                if(found) {
                    sizes.remove(i); //remove from sizes
                    break;
                } else {
                    sizes.remove(i); //remove from sizes
                    break;
                }
            }
        }

        //part 1
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
        for(int i = 0;i < ids.size();i++) {
            if(ids.get(i).equals(-1)) continue;

            BigInteger index = new BigInteger(String.valueOf(i));
            BigInteger id = new BigInteger(String.valueOf(ids.get(i)));
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
