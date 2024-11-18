package poet;

import graph.Graph;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class GraphPoet {

    private final Graph<String> graph = Graph.empty();

    public GraphPoet(File corpus) throws IOException {
        List<String> lines = Files.readAllLines(corpus.toPath());
        String text = String.join(" ", lines);
        String[] words = text.toLowerCase().split("\\s+");

        for (int i = 0; i < words.length - 1; i++) {
            String word1 = words[i];
            String word2 = words[i + 1];
            int weight = graph.targets(word1).getOrDefault(word2, 0);
            graph.set(word1, word2, weight + 1);
        }
        checkRep();
    }

    public String poem(String input) {
        String[] words = input.split("\\s+");
        StringBuilder poem = new StringBuilder();

        for (int i = 0; i < words.length - 1; i++) {
            String word1 = words[i].toLowerCase();
            String word2 = words[i + 1].toLowerCase();

            poem.append(words[i]).append(" ");
            String bridge = findBridge(word1, word2);
            if (bridge != null) {
                poem.append(bridge).append(" ");
            }
        }
        poem.append(words[words.length - 1]);
        return poem.toString();
    }

    private String findBridge(String word1, String word2) {
        String bridge = null;
        int maxWeight = 0;

        for (String candidate : graph.targets(word1).keySet()) {
            if (graph.targets(candidate).containsKey(word2)) {
                int weight = graph.targets(word1).get(candidate) + graph.targets(candidate).get(word2);
                if (weight > maxWeight) {
                    maxWeight = weight;
                    bridge = candidate;
                }
            }
        }
        return bridge;
    }

    private void checkRep() {
        for (String vertex : graph.vertices()) {
            assert vertex != null;
            for (String target : graph.targets(vertex).keySet()) {
                assert target != null;
                assert graph.targets(vertex).get(target) > 0;
            }
        }
    }

    @Override
    public String toString() {
        return "GraphPoet with graph: " + graph;
    }
}
