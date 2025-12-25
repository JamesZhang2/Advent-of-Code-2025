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

        System.out.println(part1(diagram));
    }

    private static int part1(char[][] diagram) {
        int ans = 0;
        for (int r = 0; r < diagram.length; r++) {
            for (int c = 0; c < diagram[0].length; c++) {
                if (diagram[r][c] == '.') {
                    continue;
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
                if (neigh < 4) {
                    ans++;
                }
            }
        }
        return ans;
    }
}
