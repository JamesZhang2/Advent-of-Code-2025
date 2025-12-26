import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Day9 {
    public static void main(String[] args) {
        List<int[]> points = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        try {
            line = br.readLine();
            while (!line.isEmpty()) {
                String[] coordsStr = line.split(",");
                int[] coords = new int[]{Integer.parseInt(coordsStr[0]), Integer.parseInt(coordsStr[1])};
                points.add(coords);
                line = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        System.out.println(part1(points));
        System.out.println("Clockwise? " + clockwise(points));  // My input is counterclockwise
        System.out.println("Has size-1 steps? " + hasSizeOneSteps(points));  // My input does not have size-1 steps
    }

    private static long part1(List<int[]> points) {
        // Naive method
        long ans = -1;
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                ans = Math.max(ans, getRectSize(points.get(i), points.get(j)));
            }
        }
        return ans;
    }

    private static long getRectSize(int[] p1, int[] p2) {
        return (long)(Math.abs(p1[0] - p2[0]) + 1) * (Math.abs(p1[1] - p2[1]) + 1);
    }

    // Returns true if the points enclose a region clockwise, false if they enclose a region counterclockwise.
    private static boolean clockwise(List<int[]> points) {
        int deg = 0;  // clockwise is positive
        char dir = dir(points.get(0), points.get(1));
        for (int i = 1; i <= points.size(); i++) {
            char newDir = dir(points.get(i % points.size()), points.get((i + 1) % points.size()));
            switch (dir) {
                case 'U':
                    if (newDir == 'L') {
                        deg -= 90;
                    } else if (newDir == 'R') {
                        deg += 90;
                    } else {
                        assert false;
                    }
                    break;
                case 'D':
                    if (newDir == 'L') {
                        deg += 90;
                    } else if (newDir == 'R') {
                        deg -= 90;
                    } else {
                        assert false;
                    }
                    break;
                case 'R':
                    if (newDir == 'U') {
                        deg -= 90;
                    } else if (newDir == 'D') {
                        deg += 90;
                    } else {
                        assert false;
                    }
                    break;
                case 'L':
                    if (newDir == 'U') {
                        deg += 90;
                    } else if (newDir == 'D') {
                        deg -= 90;
                    } else {
                        assert false;
                    }
                    break;
                default:
                    assert false;
            }
            dir = newDir;
        }

        assert deg == 360 || deg == -360;
        System.out.println("Degrees: " + deg);
        return deg == 360;
    }

    // Direction of motion from prev to cur, with the standard Cartesian coordinates (+x to the right, +y upwards)
    private static char dir(int[] prev, int[] cur) {
        if (prev[0] == cur[0]) {
            return prev[1] < cur[1] ? 'U' : 'D';
        } else {
            return prev[0] < cur[0] ? 'R' : 'L';
        }
    }

    private static boolean hasSizeOneSteps(List<int[]> points) {
        for (int i = 0; i < points.size(); i++) {
            if (Math.abs(points.get(i)[0] - points.get((i + 1) % points.size())[0]) == 1
                || Math.abs(points.get(i)[1] - points.get((i + 1) % points.size())[1]) == 1) {
                return true;
            }
        }
        return false;
    }
}
