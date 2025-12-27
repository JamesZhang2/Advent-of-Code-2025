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
//        System.out.println("Clockwise? " + clockwise(points));  // My input is counterclockwise
//        System.out.println("Has size-1 steps? " + hasSizeOneSteps(points));  // My input does not have size-1 steps
        System.out.println(points.size());
//        System.out.println(part2(points));
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

    private static long part2(List<int[]> points) {
        long ans = -1;
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                long area = getRectSize(points.get(i), points.get(j));
                if (area < ans) {
                    // don't bother checking
                    continue;
                }
                if (isValid(points, i, j)) {
//                    System.out.println(i + " " + j);
                    ans = area;
                }
            }
        }
        return ans;
    }

    // Requires: input is counterclockwise and does not have size-1 steps
    // Requires: i < j
    // This test still isn't good enough. The answer I got was too low, so there are probably false negatives.
    // I did manage to pass the simple example in the problem description though.
    private static boolean isValid(List<int[]> points, int i, int j) {
        if (j == i + 1) {
            return true;
        }

        char dir = dir(points.get(i), points.get(j));
        int xi = points.get(i)[0];
        int xj = points.get(j)[0];
        int yi = points.get(i)[1];
        int yj = points.get(j)[1];

        if (xi > xj && yi > yj && dir == 'D') return false;

        if (xi > xj && yi < yj && dir == 'L') return false;
        if (xi > xj && yi < yj && dir == 'D') return false;

        if (xi < xj && yi > yj && dir == 'D') return false;
        if (xi < xj && yi > yj && dir == 'R') return false;

        if (xi < xj && yi < yj && dir != 'R') return false;

        int xMin = Math.min(xi, xj);
        int xMax = Math.max(xi, xj);
        int yMin = Math.min(yi, yj);
        int yMax = Math.max(yi, yj);

        for (int k = 0; k < points.size(); k++) {
            int[] prevPoint = points.get(k);
            int[] nextPoint = points.get((k + 1) % points.size());
            if (prevPoint[0] == nextPoint[0]
                    && xMin < prevPoint[0] && prevPoint[0] < xMax) {
                int segmentYMax = Math.max(prevPoint[1], nextPoint[1]);
                int segmentYMin = Math.min(prevPoint[1], nextPoint[1]);
                if (hasOverlap(yMin, yMax, segmentYMin, segmentYMax)) {
                    return false;
                }
            }
            if (prevPoint[1] == nextPoint[1]
                    && yMin < prevPoint[1] && prevPoint[1] < yMax) {
                int segmentXMax = Math.max(prevPoint[0], nextPoint[0]);
                int segmentXMin = Math.min(prevPoint[0], nextPoint[0]);
                if (hasOverlap(xMin, xMax, segmentXMin, segmentXMax)) {
                    return false;
                }
            }
        }
        return true;
    }

    // Returns true if the two intervals have nontrivial overlap (more than at one point), false otherwise
    private static boolean hasOverlap(int aMin, int aMax, int bMin, int bMax) {
        return Math.min(aMax, bMax) > Math.max(aMin, bMin);
    }

    private static long getRectSize(int[] p1, int[] p2) {
        return (long) (Math.abs(p1[0] - p2[0]) + 1) * (Math.abs(p1[1] - p2[1]) + 1);
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
