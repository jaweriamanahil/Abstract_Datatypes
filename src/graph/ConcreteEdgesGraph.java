package graph;

import java.util.*;

public class ConcreteEdgesGraph implements Graph<String> {

    private final Set<String> vertices = new HashSet<>();
    private final List<Edge> edges = new ArrayList<>();

    // Ensures the internal representation invariants hold
    private void checkRep() {
        assert vertices != null : "Vertices set should not be null";
        assert edges != null : "Edges list should not be null";
        for (Edge edge : edges) {
            assert vertices.contains(edge.getSource()) : "Edge's source vertex must be in the vertices set";
            assert vertices.contains(edge.getTarget()) : "Edge's target vertex must be in the vertices set";
        }
    }

    @Override
    public boolean add(String vertex) {
        boolean added = vertices.add(vertex);
        checkRep();
        return added;
    }

    @Override
    public int set(String source, String target, int weight) {
        if (weight < 0) {
            throw new IllegalArgumentException("Weight cannot be negative");
        }

        Edge existingEdge = null;
        for (Edge edge : edges) {
            if (edge.getSource().equals(source) && edge.getTarget().equals(target)) {
                existingEdge = edge;
                break;
            }
        }

        int previousWeight = 0;
        if (existingEdge != null) {
            previousWeight = existingEdge.getWeight();
            edges.remove(existingEdge);
        }

        if (weight > 0) {
            edges.add(new Edge(source, target, weight));
        }
        checkRep();
        return previousWeight;
    }

    @Override
    public boolean remove(String vertex) {
        if (!vertices.contains(vertex)) {
            return false;
        }

        vertices.remove(vertex);
        edges.removeIf(edge -> edge.getSource().equals(vertex) || edge.getTarget().equals(vertex));
        checkRep();
        return true;
    }

    @Override
    public Set<String> vertices() {
        return Collections.unmodifiableSet(vertices);
    }

    @Override
    public Map<String, Integer> sources(String target) {
        Map<String, Integer> sources = new HashMap<>();
        for (Edge edge : edges) {
            if (edge.getTarget().equals(target)) {
                sources.put(edge.getSource(), edge.getWeight());
            }
        }
        return sources;
    }

    @Override
    public Map<String, Integer> targets(String source) {
        Map<String, Integer> targets = new HashMap<>();
        for (Edge edge : edges) {
            if (edge.getSource().equals(source)) {
                targets.put(edge.getTarget(), edge.getWeight());
            }
        }
        return targets;
    }

    /**
     * Returns a string representation of the graph.
     * Format:
     * Vertices: [v1, v2, ..., vn]
     * Edges:
     *   source -> target (weight)
     * Vertices are listed in a set format, and each edge appears on a new line.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Vertices: ").append(vertices).append("\nEdges:\n");
        for (Edge edge : edges) {
            sb.append("  ").append(edge).append("\n");
        }
        return sb.toString();
    }

    /**
     * Immutable Edge class representing a weighted, directed edge in the graph.
     */
    public static class Edge {
        private final String source;
        private final String target;
        private final int weight;

        /**
         * Initializes a new Edge with the given source, target, and weight.
         * @param source the starting vertex of the edge
         * @param target the ending vertex of the edge
         * @param weight the weight of the edge; must be non-negative
         * @throws IllegalArgumentException if source or target is null, or weight is negative
         */
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

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Edge)) {
                return false;
            }
            Edge other = (Edge) obj;
            return source.equals(other.source) && target.equals(other.target) && weight == other.weight;
        }

        @Override
        public int hashCode() {
            return Objects.hash(source, target, weight);
        }
    }
}
