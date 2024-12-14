package aoc24;

import main.Day;

import java.util.*;

public class Day12 implements Day {
    public class Region {
        public int area;
        public int perimeter;

        public Region(int area, int perimeter) { //perimeter = sides in 2
            this.area = area;
            this.perimeter = perimeter;
        }
    }

    public record PositionPair(Position cur, Position prev) {}

    @Override
    public void run(Scanner scanner) {
        String s = "RRRRIICCFF\n" +
                "RRRRIICCCF\n" +
                "VVRRRCCFFF\n" +
                "VVRCCCJFFF\n" +
                "VVVVCJJCFE\n" +
                "VVIVCCJJEE\n" +
                "VVIIICJJEE\n" +
                "MIIIIIJJEE\n" +
                "MIIISIJEEE\n" +
                "MMMISSJEEE";
        scanner = new Scanner(s);

        List<char[]> map = new ArrayList<>();
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            char[] cur = new char[line.length()];
            for(int i = 0;i < line.length();i++) {
                cur[i] = line.charAt(i);
            }
            map.add(cur);
        }
        int rows = map.size();
        int cols = map.getFirst().length;

        boolean[][] visited = new boolean[rows][cols];
        Map<Integer, Region> regions = new HashMap<>();

        int id = 0;
        for(int i = 0;i < rows;i++) {
            for(int j = 0;j < cols;j++) {
                if(visited[i][j]) continue;

                Position pos = new Position(i, j);
                regions.put(id, new Region(0, 0));

                compute(map, visited, regions, pos, id++);
            }
        }

        long count = 0;
        for(Region region : regions.values()) {
            count += (long) region.area * region.perimeter;
        }

        System.out.println(count);

    }

    //1
    void compute(List<char[]> map, boolean[][] visited, Map<Integer, Region> regions, Position start, int id) {
        Queue<PositionPair> queue = new LinkedList<>(); //cur, prev
        queue.add(new PositionPair(start, null));

        int rows = map.size();
        int cols = map.getFirst().length;
        PositionPair pair = null;
        while(!queue.isEmpty()) {
            pair = queue.poll();
            Position pos = pair.cur;
            Position prev = pair.prev;

            if(visited[pos.i][pos.j]) {
                continue;
            }

            char posC = map.get(pos.i)[pos.j];

            //add area for current id region
            Region region = regions.get(id);
            region.area++;
            visited[pos.i][pos.j] = true;

            //add perimeter for all not-same neighbors
            int[][] dirs = {
                    {-1, 0},
                    {0, 1},
                    {1, 0},
                    {0, -1}
            };
            for(int[] dir : dirs) {
                Position next = new Position(pos.i + dir[0], pos.j + dir[1]);
                if(isPerimeter(map, next, posC, rows, cols)) {
                    if(prev == null) {
                        region.perimeter++;
                        continue;
                    }
                    Position prevNext = new Position(prev.i + dir[0], prev.j + dir[1]);
                    if(!isPerimeter(map, prevNext, posC, rows, cols)) {
                        region.perimeter++;
                    }
                } else {
                    var newPair = new PositionPair(next, pos);
                    if(!visited[next.i][next.j] && !queue.contains(newPair)) queue.add(newPair);
                }
            }
        }

        if(pair == null) return;
        //last was a corner
        int[][] dirs = {
                {-1, 0},
                {0, 1},
                {1, 0},
                {0, -1}
        };
        int free = 0;
        Position pos = pair.cur;
        char posC = map.get(pos.i)[pos.j];
        for(int[] dir : dirs) {
            Position next = new Position(pos.i + dir[0], pos.j + dir[1]);
            if(isPerimeter(map, next, posC, rows, cols)) {
                free++;
            }
        }
        if(free == 2) {
            regions.get(id).perimeter--; //subtract faulty corner case
        }
    }

    private boolean isPerimeter(List<char[]> map, Position pos, char c1, int rows, int cols) {
        if(!pos.inBounds(rows, cols)) return true;

        char c = map.get(pos.i)[pos.j];
        return c != c1;
    }

    //1
    /*void compute(List<char[]> map, boolean[][] visited, Map<Integer, Region> regions, Position pos, int id) {
        if(visited[pos.i][pos.j]) {
            return;
        }

        int rows = map.size();
        int cols = map.getFirst().length;
        char posC = map.get(pos.i)[pos.j];

        //add area for current id region
        Region region = regions.get(id);
        region.area++;
        visited[pos.i][pos.j] = true;

        //add perimeter for all not-same neighbors
        int[][] dirs = {
                {-1, 0},
                {0, 1},
                {1, 0},
                {0, -1}
        };
        for(int[] dir : dirs) {
            Position next = new Position(pos.i + dir[0], pos.j + dir[1]);
            if(!next.inBounds(rows, cols)) {
                region.perimeter++;
                continue;
            }

            char nextC = map.get(next.i)[next.j];
            if(posC != nextC) {
                region.perimeter++;
            } else {
                compute(map, visited, regions, next, id);
            }
        }
    }*/
}
