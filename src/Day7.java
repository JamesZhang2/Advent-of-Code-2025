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

        System.out.println(part1(chars));
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
}
