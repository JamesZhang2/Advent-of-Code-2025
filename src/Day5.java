import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Day5 {
    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<long[]> fresh = new ArrayList<>();
        List<Long> available = new ArrayList<>();
        String line;
        try {
            line = br.readLine().strip();
            while (!line.isEmpty()) {
                String[] strPair = line.split("-");
                long[] pair = new long[]{Long.parseLong(strPair[0]), Long.parseLong(strPair[1])};
                fresh.add(pair);
                line = br.readLine();
            }
            // Consume the empty line in the middle
            line = br.readLine();
            while (!line.isEmpty()) {
                available.add(Long.parseLong(line));
                line = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(part1(fresh, available));
    }

    private static int part1(List<long[]> fresh, List<Long> available) {
        // Naive solution
        int ans = 0;
        for (long id : available) {
            for (long[] range : fresh) {
                if (range[0] <= id && id <= range[1]) {
                    ans++;
                    break;
                }
            }
        }
        return ans;
    }
}
