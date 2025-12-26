import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day11 {
    // The string s maps to the following map: node name v maps to number of paths from v to s.
    // For example, dp.get("out") is a map that maps v to number of paths from v to "out".
    private static final Map<String, Map<String, Long>> dp = new HashMap<>();

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

//        System.out.println(part1(graph));
        System.out.println(part2(graph));
    }

    private static long part1(Map<String, List<String>> graph) {
        // Hopefully there aren't any cycles on a path from you to out, otherwise the answer would be infinity.
        // DP on graph
        // Could do bottom-up with topological sort. I think top-down is easier.
        return pathsToTarget(graph, "you", "out");
    }

    private static long part2(Map<String, List<String>> graph) {
        // either we can reach fft from dac or dac from fft, but not both, because that would imply a cycle.
        if (pathsToTarget(graph, "dac", "fft") != 0) {
//            System.out.println("Can go from dac to fft");
            return pathsToTarget(graph, "svr", "dac")
                    * pathsToTarget(graph, "dac", "fft")
                    * pathsToTarget(graph, "fft", "out");
        } else {
            assert pathsToTarget(graph, "fft", "dac") != 0;
//            System.out.println("Can go from fft to dac");
            return pathsToTarget(graph, "svr", "fft")
                    * pathsToTarget(graph, "fft", "dac")
                    * pathsToTarget(graph, "dac", "out");
        }
    }

    private static long pathsToTarget(Map<String, List<String>> graph, String src, String target) {
        if (!dp.containsKey(target)) {
            dp.put(target, new HashMap<>());
        }
        if (dp.get(target).containsKey(src)) {
            return dp.get(target).get(src);
        }
        if (src.equals(target)) {
            // Assuming no cycles
            dp.get(target).put(target, 1L);
            return 1L;
        }
        if (!graph.containsKey(src)) {
            // reached a node with outdegree 0
            dp.get(target).put(src, 0L);
            return 0L;
        }
        long ans = 0;
        for (String neigh : graph.get(src)) {
            ans += pathsToTarget(graph, neigh, target);
        }
        dp.get(target).put(src, ans);
        return ans;
    }
}
