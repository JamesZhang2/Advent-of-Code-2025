import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day8 {
    public static void main(String[] args) {
        List<int[]> points = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        try {
            line = br.readLine();
            while (!line.isEmpty()) {
                String[] coordsStr = line.split(",");
                int[] coords = new int[]{Integer.parseInt(coordsStr[0]),
                        Integer.parseInt(coordsStr[1]), Integer.parseInt(coordsStr[2])};
                points.add(coords);
                line = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        solve(points);
    }

    private static void solve(List<int[]> points) {
        // There are 1000 points in my puzzle input, so roughly 5 * 10^5 unordered pairs
        List<long[]> pairs = new ArrayList<>();  // distance^2, first idx, second idx
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                long distSqr = (long) (points.get(i)[0] - points.get(j)[0]) * (points.get(i)[0] - points.get(j)[0])
                        + (long) (points.get(i)[1] - points.get(j)[1]) * (points.get(i)[1] - points.get(j)[1])
                        + (long) (points.get(i)[2] - points.get(j)[2]) * (points.get(i)[2] - points.get(j)[2]);
                pairs.add(new long[]{distSqr, i, j});
            }
        }

        pairs.sort((a, b) -> {
            if (a[0] != b[0]) {
                return Long.compare(a[0], b[0]);
            } else if (a[1] != b[1]) {
                return Long.compare(a[1], b[1]);
            } else {
                return Long.compare(a[2], b[2]);
            }
        });

//        System.out.println(pairs.size());
//        System.out.println(pairs.get(0)[1] + " " + pairs.get(0)[2]);
//        System.out.println(pairs.get(1)[1] + " " + pairs.get(1)[2]);

        // Union find
        int[] labels = new int[points.size()];  // maps point idx to group number
        for (int i = 0; i < labels.length; i++) {
            labels[i] = i;
        }

        List<List<Integer>> groups = new ArrayList<>();  // maps group number to point idx
        for (int i = 0; i < labels.length; i++) {
            List<Integer> l = new ArrayList<>();
            l.add(i);
            groups.add(l);
        }

        for (int i = 0; i < pairs.size(); i++) {
            int first = (int)(pairs.get(i)[1]);
            int second = (int)(pairs.get(i)[2]);

            if (labels[first] == labels[second]) {
                // already in the same group - do nothing
                continue;
            }
            // Part 2
            if (groups.get(labels[first]).size() + groups.get(labels[second]).size() == points.size()) {
                System.out.println("Part 2 answer: " + (long)(points.get(first)[0]) * points.get(second)[0]);
            }
            if (groups.get(labels[first]).size() > groups.get(labels[second]).size()) {
                // merge second into first
                int labelOfSecond = labels[second];
                for (int member : groups.get(labels[second])) {
                    labels[member] = labels[first];
                    groups.get(labels[first]).add(member);
                }
                groups.get(labelOfSecond).clear();
            } else {
                // merge first into second
                int labelOfFirst = labels[first];
                for (int member : groups.get(labels[first])) {
                    labels[member] = labels[second];
                    groups.get(labels[second]).add(member);
                }
                groups.get(labelOfFirst).clear();
            }

            // Part 1
            // 1000 is the number of closest junction boxes that we're connecting
            if (i == 1000 - 1) {
//                for (int label : labels) {
//                    System.out.print(label + ",");
//                }
//                System.out.println();
//                System.out.println(groups);

                List<Integer> groupSizes = new ArrayList<>();
                for (int j = 0; j < points.size(); j++) {
                    groupSizes.add(groups.get(j).size());
                }
                groupSizes.sort((a, b) -> b - a);
//                System.out.println(groupSizes);
                System.out.println("Part 1 answer: " + (long)groupSizes.get(0) * groupSizes.get(1) * groupSizes.get(2));
            }
        }
    }
}
