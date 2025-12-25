import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
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

//        System.out.println(part1(fresh, available));
        System.out.println(part2(fresh));
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

    private static long part2(List<long[]> fresh) {
        // Interval merging
        List<long[]> merged = new LinkedList<>();  // sorted non-overlapping intervals
        for (long[] interval : fresh) {
            int idx = 0;
            while (idx < merged.size() && merged.get(idx)[1] < interval[0]) {
                idx++;
            }
            if (idx >= merged.size()) {
                // insert at the end
                merged.add(interval);
                continue;
            }
            if (merged.get(idx)[0] > interval[1]) {
                // no overlap
                merged.add(idx, interval);
            } else {
                // has overlap
                long newLower = Math.min(merged.get(idx)[0], interval[0]);
                int j = idx + 1;  // the first index that does not overlap the incoming interval
                while (j < merged.size() && merged.get(j)[0] <= interval[1]) {
                    j++;
                }
                long newUpper = Math.max(merged.get(j - 1)[1], interval[1]);

                // remove old intervals
                for (int i = idx; i < j; i++) {
                    merged.remove(idx);
                }

                // add new interval
                merged.add(idx, new long[]{newLower, newUpper});
            }
        }

        long ans = 0;
        for (long[] interval : merged) {
            ans += interval[1] - interval[0] + 1;
        }
        return ans;
    }
}
