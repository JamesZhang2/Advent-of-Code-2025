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

        System.out.println(part1(batteries));
    }

    private static int part1(int[][] batteries) {
        int ans = 0;
        for (int[] bank : batteries) {
            ans += getMaxJoltage(bank);
        }
        return ans;
    }

    private static int getMaxJoltage(int[] bank) {
        // Just a simple double loop would do
        int max = -1;
        for (int i = 0; i < bank.length; i++) {
            for (int j = i + 1; j < bank.length; j++) {
                max = Math.max(bank[i] * 10 + bank[j], max);
            }
        }
        return max;
    }
}
