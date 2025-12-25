import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Day3 {
    public static void main(String[] args) {
        int[][] batteries;
        List<String> lines = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        try {
            line = br.readLine().strip();
            while (!line.isEmpty()) {
                lines.add(line);
                line = br.readLine().strip();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        batteries = new int[lines.size()][lines.getFirst().length()];
        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < lines.get(i).length(); j++) {
                batteries[i][j] = lines.get(i).charAt(j) - '0';
            }
        }

//        System.out.println(part1(batteries));
        System.out.println(part2(batteries));
    }

    private static int part1(int[][] batteries) {
        int ans = 0;
        for (int[] bank : batteries) {
            ans += getMaxJoltagePart1(bank);
        }
        return ans;
    }

    private static int getMaxJoltagePart1(int[] bank) {
        // Just a simple double loop would do
        int max = -1;
        for (int i = 0; i < bank.length; i++) {
            for (int j = i + 1; j < bank.length; j++) {
                max = Math.max(bank[i] * 10 + bank[j], max);
            }
        }
        return max;
    }

    private static long part2(int[][] batteries) {
        long ans = 0;
        for (int[] bank : batteries) {
            ans += getMaxJoltagePart2(bank);
        }
        return ans;
    }

    private static long getMaxJoltagePart2(int[] bank) {
        // For 12 batteries we definitely need DP.
        // dp[i][j] is max joltage starting from i, with j more to choose.
        // There must be at least j batteries left.
        long[][] dp = new long[bank.length][13];

        for (int i = bank.length - 1; i >= 0; i--) {
            for (int j = 1; j <= Math.min(12, bank.length - i); j++) {
                if (i == bank.length - 1) {
                    // base case
                    dp[i][j] = bank[i];
                    continue;
                }

                if (j == bank.length - i) {
                    // base case, must choose all
                    dp[i][j] = bank[i] * (long)Math.pow(10, j - 1) + dp[i + 1][j - 1];
                    continue;
                }

                // inductive case: can choose whether to take next or not
                dp[i][j] = Math.max(bank[i] * (long)Math.pow(10, j - 1) + dp[i + 1][j - 1], dp[i + 1][j]);
            }
        }

        return dp[0][12];
    }
}
