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

        System.out.println(part1(points));
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
}
