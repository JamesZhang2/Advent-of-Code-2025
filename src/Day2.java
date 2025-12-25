import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Day2 {
    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            long[][] idPairs;
            String line = br.readLine();
            String[] pairs = line.split(",");
            idPairs = new long[pairs.length][2];
            for (int i = 0; i < pairs.length; i++) {
                int dashIdx = pairs[i].indexOf("-");
                idPairs[i][0] = Long.parseLong(pairs[i].substring(0, dashIdx));
                idPairs[i][1] = Long.parseLong(pairs[i].substring(dashIdx + 1));
            }

            System.out.println(part1(idPairs));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static long part1(long[][] idPairs) {
        long ans = 0;
        for (long[] pair : idPairs) {
            long next = nextInvalid(pair[0]);
            while (next <= pair[1]) {
                ans += next;
                next = nextInvalid(next + 1);
            }
        }
        return ans;
    }

    // May return num itself.
    private static long nextInvalid(long num) {
        int len = (num + "").length();
        if (len % 2 == 0) {
            long factor = (long)(Math.pow(10, len / 2));
            long firstHalf = num / factor;
            if (firstHalf * factor + firstHalf >= num) {
                // e.g. 4321 -> 4343
                return firstHalf * factor + firstHalf;
            } else {
                // e.g. 1234 -> 1313
                // Impossible to have overflow: 9999 is greater than any 4-digit number
                return (firstHalf + 1) * factor + (firstHalf + 1);
            }
        } else {
            // e.g. 12345 -> 100100
            long firstHalf = (long)(Math.pow(10, len / 2));
            long factor = (long)(Math.pow(10, (len + 1) / 2));
            return firstHalf * factor + firstHalf;
        }
    }
}
