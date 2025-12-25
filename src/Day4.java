import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Day4 {
    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<String> lines = new ArrayList<>();
        String line;
        try {
            line = br.readLine().strip();
            while (!line.isEmpty()) {
                lines.add(line);
                line = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        char[][] diagram = new char[lines.size()][lines.getFirst().length()];
        for (int i = 0; i < lines.size(); i++) {
            diagram[i] = lines.get(i).toCharArray();
        }

//        System.out.println(part1(diagram));
        System.out.println(part2(diagram));
    }

    private static int part1(char[][] diagram) {
        int ans = 0;
        for (int r = 0; r < diagram.length; r++) {
            for (int c = 0; c < diagram[0].length; c++) {
                ans += accessible(diagram, r, c) ? 1 : 0;
            }
        }
        return ans;
    }

    private static int part2(char[][] diagram) {
        // Simulation
        List<int[]> removable = new ArrayList<>();
        int ans = 0;
        do {
            removable.clear();
            for (int r = 0; r < diagram.length; r++) {
                for (int c = 0; c < diagram[0].length; c++) {
                    if (accessible(diagram, r, c)) {
                        removable.add(new int[]{r, c});
                    }
                }
            }
            ans += removable.size();
            for (int[] pair : removable) {
                diagram[pair[0]][pair[1]] = '.';
            }
        } while (!removable.isEmpty());
        return ans;
    }

    private static boolean accessible(char[][] diagram, int r, int c) {
        if (diagram[r][c] == '.') {
            return false;
        }
        int neigh = 0;
        for (int deltaR = -1; deltaR <= 1; deltaR++) {
            for (int deltaC = -1; deltaC <= 1; deltaC++) {
                if (r + deltaR < 0 || r + deltaR >= diagram.length
                        || c + deltaC < 0 || c + deltaC >= diagram[0].length) {
                    continue;
                }
                if (deltaR == 0 && deltaC == 0) {
                    continue;
                }
                neigh += diagram[r + deltaR][c + deltaC] == '@' ? 1 : 0;
            }
        }
        return neigh < 4;
    }
}
