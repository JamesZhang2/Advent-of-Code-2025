import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Day6 {
    public static void main(String[] args) {
        List<List<Integer>> nums = new ArrayList<>();
        List<Character> ops = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // The number of lines of numbers is variable - it's not always 3.
        try {
            String[] line = br.readLine().strip().split(" +");
            while (line.length > 1) {
                if (line[0].equals("*") || line[0].equals("+")) {
                    // operations
                    for (String opStr : line) {
                        assert opStr.length() == 1;
                        ops.add(opStr.charAt(0));
                    }
                } else {
                    List<Integer> parsed = new ArrayList<>();
                    for (String numStr : line) {
                        parsed.add(Integer.parseInt(numStr));
                    }
                    nums.add(parsed);
                }
                line = br.readLine().strip().split(" +");
            }
            assert ops.size() == nums.getFirst().size();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(part1(nums, ops));
    }

    private static long part1(List<List<Integer>> nums, List<Character> ops) {
        long ans = 0;
        for (int i = 0; i < nums.getFirst().size(); i++) {
            if (ops.get(i) == '+') {
                long sum = 0;
                for (List<Integer> row : nums) {
                    sum += row.get(i);
                }
                ans += sum;
            } else {
                long prod = 1;
                for (List<Integer> row : nums) {
                    prod *= row.get(i);
                }
                ans += prod;
            }
        }
        return ans;
    }
}
