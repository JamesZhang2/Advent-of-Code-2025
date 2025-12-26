import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Day12 {
    public static void main(String[] args) {
        // Only paste the part after the 6 shapes into stdin
        List<int[]> specs = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        try {
            line = br.readLine();
            while (!line.isEmpty()) {
                String[] parts = line.split(": ");
                int width = Integer.parseInt(parts[0].substring(0, 2));
                int length = Integer.parseInt(parts[0].substring(3, 5));
                assert 10 <= width && width <= 99 && 10 <= length && length <= 99;
                int[] spec = new int[8];
                spec[0] = width;
                spec[1] = length;
                String[] quantities = parts[1].split(" ");
                for (int i = 2; i < 8; i++) {
                    spec[i] = Integer.parseInt(quantities[i - 2]);
                }
                specs.add(spec);
                line = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(part1(specs));
    }

    private static int part1(List<int[]> specs) {
        int[] verdicts = new int[3];  // 0 for definitely fits, 1 for definitely doesn't fit, 2 for don't know
        for (int i = 0; i < specs.size(); i++) {
            int width = specs.get(i)[0];
            int length = specs.get(i)[1];
            int[] quantities = new int[6];
            System.arraycopy(specs.get(i), 2, quantities, 0, 6);
            if (definitelyFits(width, length, quantities)) {
                verdicts[0]++;
            } else if (definitelyDoesNotFit(width, length, quantities)) {
                verdicts[1]++;
            } else {
                verdicts[2]++;
            }
        }
        System.out.println("Definitely fits: " + verdicts[0]);
        System.out.println("Definitely doesn't fit: " + verdicts[1]);
        System.out.println("Don't know: " + verdicts[2]);
        if (verdicts[2] == 0) {
            return verdicts[0];
        } else {
            return -1;
        }
    }

    // Returns true if the presents definitely can fit, false otherwise (including when we don't know)
    private static boolean definitelyFits(int width, int length, int[] quantities) {
        int sum = 0;
        for (int q : quantities) {
            sum += q;
        }
        return (width / 3) * (length / 3) >= sum;
    }

    // Returns true if the presents definitely cannot fit, false otherwise (including when we don't know)
    private static boolean definitelyDoesNotFit(int width, int length, int[] quantities) {
        int[] areas = {7, 7, 5, 7, 7, 6};
        int areaSum = 0;
        for (int i = 0; i < quantities.length; i++) {
            areaSum += quantities[i] * areas[i];
        }
        return areaSum > width * length;
    }
}
