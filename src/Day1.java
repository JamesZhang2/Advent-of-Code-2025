import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Day1 {
    public static void main(String[] args) {
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

        System.out.println(part1(lines));
        System.out.println(part2(lines));
        System.out.println(part2Alt(lines));
    }

    private static int part1(List<String> lines) {
        int ans = 0;
        int dial = 50;

        for (String line : lines) {
            int delta = (line.charAt(0) == 'L' ? -1 : 1) * Integer.parseInt(line.substring(1));
            dial = (dial + delta + 100) % 100;
            if (dial == 0) {
                ans++;
            }
        }

        return ans;
    }

    private static int part2(List<String> lines) {
        int ans = 0;
        int dial = 50;

        for (String line : lines) {
            int delta = (line.charAt(0) == 'L' ? -1 : 1) * Integer.parseInt(line.substring(1));
            int oldDial = dial;
            dial += delta;
            ans += getPass0(oldDial, dial);
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

    // Currently this method returns an incorrect result
    private static int part2Alt(List<String> lines) {
        int ans = 0;
        int dial = 50;

        for (String line : lines) {
            int dir = line.charAt(0) == 'L' ? -1 : 1;
            int deltaAbs = Integer.parseInt(line.substring(1));
            ans += deltaAbs / 100;
            if (dir == -1 && dial != 0 && deltaAbs % 100 >= dial) {
                ans++;
            }
            if (dir == 1 && dial != 0 && deltaAbs % 100 + dial >= 100) {
                ans++;
            }
            dial = (dial + dir * deltaAbs + 100) % 100;
        }

        return ans;
    }
}
