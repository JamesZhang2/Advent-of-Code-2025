import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Day6 {
    public static void main(String[] args) {
//        System.out.println(part1());
        System.out.println(part2());
    }

    private static long part1() {
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

        return part1Helper(nums, ops);
    }

    private static long part1Helper(List<List<Integer>> nums, List<Character> ops) {
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

    private static long part2() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<char[]> nums = new ArrayList<>();
        char[] ops = null;
        // The number of lines of numbers is variable - it's not always 3.
        try {
            String line = br.readLine();  // don't strip
            while (!line.isEmpty()) {
                char[] chars = line.toCharArray();
                if (chars[0] == '*' || chars[0] == '+') {
                    // operations
                    ops = chars;
                } else {
                    // numbers
                    nums.add(chars);
                }
                line = br.readLine();
            }
            assert ops.length == nums.getFirst().length;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        long ans = 0;
        int idx = 0;
        while (idx < ops.length) {
            if (ops[idx] == '+') {
                long sum = 0;
                boolean allEmpty = false;
                while (!allEmpty && idx < ops.length) {
                    allEmpty = true;
                    long num = 0;
                    for (char[] row : nums) {
                        if (row[idx] != ' ') {
                            num = num * 10 + (row[idx] - '0');
                            allEmpty = false;
                        }
                    }
                    if (!allEmpty) {
                        sum += num;
                    }
                    idx++;
                }
                ans += sum;
            } else if (ops[idx] == '*') {
                long prod = 1;
                boolean allEmpty = false;
                while (!allEmpty && idx < ops.length) {
                    allEmpty = true;
                    long num = 0;
                    for (char[] row : nums) {
                        if (row[idx] != ' ') {
                            num = num * 10 + (row[idx] - '0');
                            allEmpty = false;
                        }
                    }
                    if (!allEmpty) {
                        prod *= num;
                    }
                    idx++;
                }
                ans += prod;
            } else {
                // impossible
                assert false;
            }
        }
        return ans;
    }
}
