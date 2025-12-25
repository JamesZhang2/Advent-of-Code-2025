import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Day7 {
    public static void main(String[] args) {
        List<char[]> chars = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        try {
            line = br.readLine();
            while (!line.isEmpty()) {
                chars.add(line.toCharArray());
                line = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(part2(chars));
    }

    private static int part1(List<char[]> chars) {
        int startIdx = -1;
        for (int i = 0; i < chars.getFirst().length; i++) {
            if (chars.getFirst()[i] == 'S') {
                startIdx = i;
            }
        }

        int ans = 0;
        Set<Integer> beams = new HashSet<>();
        beams.add(startIdx);
        for (int r = 1; r < chars.size(); r++) {
            Set<Integer> newBeams = new HashSet<>();
            for (int beam : beams) {
                if (chars.get(r)[beam] == '^') {
                    ans++;
                    newBeams.add(beam - 1);
                    newBeams.add(beam + 1);
                } else {
                    assert chars.get(r)[beam] == '.';
                    newBeams.add(beam);
                }
            }
            beams = newBeams;
        }
        return ans;
    }

    private static long part2(List<char[]> chars) {
        // DP
        int rows = chars.size();
        int cols = chars.getFirst().length;

        int startIdx = -1;
        for (int i = 0; i < cols; i++) {
            if (chars.getFirst()[i] == 'S') {
                startIdx = i;
            }
        }

        // dp[r][c] is the number of possible timelines for a particle at row r, column c.
        long[][] dp = new long[rows][cols];

        // last row has no splitters
        for (int c = 0; c < cols; c++) {
            dp[rows - 1][c] = 1;
        }

        for (int r = rows - 2; r >= 0; r--) {
            for (int c = 0; c < cols; c++) {
                if (chars.get(r + 1)[c] == '^') {
                    dp[r][c] = dp[r + 1][c - 1] + dp[r + 1][c + 1];
                } else {
                    dp[r][c] = dp[r + 1][c];
                }
            }
        }
        return dp[0][startIdx];
    }
}
