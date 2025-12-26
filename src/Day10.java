import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Day10 {
    public static void main(String[] args) {
        List<String[]> lines = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        try {
            line = br.readLine().strip();
            while (!line.isEmpty()) {
                lines.add(line.split(" "));
                line = br.readLine().strip();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(part1(lines));
    }

    private static int part1(List<String[]> lines) {
        int ans = 0;
        for (String[] line : lines) {
            String targetStr = line[0].substring(1, line[0].length() - 1);
            int numLights = targetStr.length();
            boolean[] target = new boolean[numLights];
            for (int i = 0; i < targetStr.length(); i++) {
                target[i] = targetStr.charAt(i) == '#';
            }

            List<List<Integer>> buttons = new ArrayList<>();
            for (int i = 1; i < line.length - 1; i++) {
                String[] toggleLights = line[i].substring(1, line[i].length() - 1).split(",");
                List<Integer> button = new ArrayList<>();
                for (String toggleLight : toggleLights) {
                    button.add(Integer.parseInt(toggleLight));
                }
                buttons.add(button);
            }

            ans += getMinButtonPresses(target, buttons);
        }
        return ans;
    }

    private static int getMinButtonPresses(boolean[] target, List<List<Integer>> buttons) {
        // I think in general this problem may be (NP)-hard.
        // Determining whether an instance is solvable and providing one solution if it is solvable is easy
        // because it's just solving a linear system in Z/2Z, but finding the sparsest solution looks similar to
        // a cryptographic problem that is believed to be hard.
        // However, from my exploration, the maximum number of buttons is only 13, so we can easily brute force.
        // Note that pressing a button twice is the same as doing nothing, so there are only at most 2^13 possibilities.

        int min = Integer.MAX_VALUE;

        // Use binary representation for easy iteration
        for (int i = 0; i < (1 << buttons.size()); i++) {
            int popCount = 0;
            boolean[] result = new boolean[target.length];
            for (int j = 0; j < buttons.size(); j++) {
                if ((i & (1 << j)) != 0) {
                    // Push jth button
                    popCount++;
                    for (int light : buttons.get(j)) {
                        result[light] = !result[light];
                    }
                }
            }

            // check result at the end
            boolean match = true;
            for (int j = 0; j < result.length; j++) {
                if (result[j] != target[j]) {
                    match = false;
                    break;
                }
            }
            if (match) {
                min = Math.min(min, popCount);
            }
        }

        assert min < Integer.MAX_VALUE;  // We assume that the problem is solvable, otherwise we panic.
        return min;
    }
}
