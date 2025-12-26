import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day11 {
    private static final Map<String, Long> dp = new HashMap<>();  // Maps node name to number of paths to out

    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Map<String, List<String>> graph = new HashMap<>();
        String line;
        try {
            line = br.readLine();
            while (!line.isEmpty()) {
                String[] words = line.split(" ");
                String src = words[0].substring(0, words[0].length() - 1);
                List<String> dests = new ArrayList<>();
                for (int i = 1; i < words.length; i++) {
                    dests.add(words[i]);
                }
                graph.put(src, dests);
                line = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(part1(graph));
    }

    private static long part1(Map<String, List<String>> graph) {
        // Hopefully there aren't any cycles on a path from you to out, otherwise the answer would be infinity.
        // DP on graph
        // Could do bottom-up with topological sort. I think top-down is easier.
        dp.put("out", 1L);
        return pathsToOut(graph, "you");
    }

    private static long pathsToOut(Map<String, List<String>> graph, String src) {
        if (dp.containsKey(src)) {
            return dp.get(src);
        }
        long ans = 0;
        for (String neigh : graph.get(src)) {
            ans += pathsToOut(graph, neigh);
        }
        dp.put(src, ans);
        return ans;
    }
}
