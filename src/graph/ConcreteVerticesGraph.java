package graph;

import java.util.*;

public class ConcreteVerticesGraph {
    private final Set<String> vertices;
    private final Map<String, Map<String, Integer>> edges;

    public ConcreteVerticesGraph() {
        this.vertices = new HashSet<>();
        this.edges = new HashMap<>();
    }

    // 1. add(String vertex)
    public boolean add(String vertex) {
        if (vertices.contains(vertex)) {
            return false;
        }
        vertices.add(vertex);
        edges.put(vertex, new HashMap<>());
        return true;
    }

    // 2. set(String source, String target, int weight)
    public int set(String source, String target, int weight) {
        if (weight < 0) {
            throw new IllegalArgumentException("Weight cannot be negative");
        }

        if (!vertices.contains(source) || !vertices.contains(target)) {
            throw new IllegalArgumentException("Source or target vertex does not exist");
        }

        Map<String, Integer> sourceEdges = edges.get(source);
        int previousWeight = sourceEdges.getOrDefault(target, 0);
        
        if (weight == 0) {
            sourceEdges.remove(target);
        } else {
            sourceEdges.put(target, weight);
        }
        return previousWeight;
    }

    // 3. remove(String vertex)
    public boolean remove(String vertex) {
        if (!vertices.contains(vertex)) {
            return false;
        }

        vertices.remove(vertex);
        edges.remove(vertex);

        for (Map<String, Integer> targetEdges : edges.values()) {
            targetEdges.remove(vertex);
        }

        return true;
    }

    // 4. vertices()
    public Set<String> vertices() {
        return Collections.unmodifiableSet(vertices);
    }

    // 5. sources(String target)
    public Map<String, Integer> sources(String target) {
        Map<String, Integer> sources = new HashMap<>();
        for (String vertex : vertices) {
            Integer weight = edges.get(vertex).get(target);
            if (weight != null) {
                sources.put(vertex, weight);
            }
        }
        return sources;
    }

    // 6. targets(String source)
    public Map<String, Integer> targets(String source) {
        return Collections.unmodifiableMap(edges.get(source));
    }

    // 7. toString()
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Vertices:\n");
        for (String vertex : vertices) {
            sb.append("  ").append(vertex).append(" -> ").append(edges.get(vertex)).append("\n");
        }
        return sb.toString();
    }

    // 8. checkRep()
    public void checkRep() {
        for (String vertex : vertices) {
            if (vertex == null) {
                throw new IllegalStateException("Vertex is null");
            }
            Map<String, Integer> vertexEdges = edges.get(vertex);
            if (vertexEdges == null) {
                throw new IllegalStateException("Edges are null for vertex " + vertex);
            }
            for (String target : vertexEdges.keySet()) {
                if (!vertices.contains(target)) {
                    throw new IllegalStateException("Target vertex " + target + " does not exist");
                }
            }
        }
    }

    // Edge Class (Nested within ConcreteVerticesGraph)
    public class Edge {
        private final String source;
        private final String target;
        private final int weight;

        public Edge(String source, String target, int weight) {
            if (source == null || target == null || weight < 0) {
                throw new IllegalArgumentException("Invalid edge parameters");
            }
            this.source = source;
            this.target = target;
            this.weight = weight;
        }

        public String getSource() {
            return source;
        }

        public String getTarget() {
            return target;
        }

        public int getWeight() {
            return weight;
        }

        @Override
        public String toString() {
            return source + " -> " + target + " (" + weight + ")";
        }
    }
}
