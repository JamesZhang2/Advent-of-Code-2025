import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Day1 {
    public static void main(String[] args) {
//        System.out.println(part1());
        System.out.println(part2());
    }

    private static int part1() {
        int ans = 0;
        int dial = 50;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        try {
            line = br.readLine().strip();
            while (!line.isEmpty()) {
                int delta = (line.charAt(0) == 'L' ? -1 : 1) * Integer.parseInt(line.substring(1));
                dial = (dial + delta + 100) % 100;
                if (dial == 0) {
                    ans++;
                }
                line = br.readLine().strip();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ans;
    }

    private static int part2() {
        int ans = 0;
        int dial = 50;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        try {
            line = br.readLine().strip();
            while (!line.isEmpty()) {
                int delta = (line.charAt(0) == 'L' ? -1 : 1) * Integer.parseInt(line.substring(1));
                int oldDial = dial;
                dial += delta;
                ans += getPass0(oldDial, dial);
//                System.out.printf("old: %d, new: %d, getPass0: %d\n", oldDial, dial, getPass0(oldDial, dial));
                line = br.readLine().strip();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ans;
    }

    private static int getPass0(int oldDial, int newDial) {
        if (oldDial < newDial) {
            // interval groups: [-100, -1], [0, 99], [100, 199], etc.
            int oldGroup = Math.floorDiv(oldDial, 100);
            int newGroup = Math.floorDiv(newDial, 100);
            return newGroup - oldGroup;
        } else {
            // interval groups: [-99, 0], [1, 100], [101, 200], etc.
            int oldGroup = Math.ceilDiv(oldDial, 100);
            int newGroup = Math.ceilDiv(newDial, 100);
            return oldGroup - newGroup;
        }
    }
}
