package graph;

import graph.ConcreteVerticesGraph;
import org.junit.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class ConcreteVerticesGraphTest {

    // Tests for ConcreteVerticesGraph class

    @Test
    public void testGraphAddVertex() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        assertTrue("New vertex should be added successfully.", graph.add("A"));
        assertFalse("Adding duplicate vertex should return false.", graph.add("A"));
    }

    @Test
    public void testGraphSetEdge() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        graph.add("A");
        graph.add("B");

        int prevWeight = graph.set("A", "B", 5);
        assertEquals("Previous weight should be 0 for a new edge.", 0, prevWeight);
        assertEquals("Edge weight should be set correctly.", 5, (int) graph.targets("A").get("B"));

        prevWeight = graph.set("A", "B", 10); // update weight
        assertEquals("Previous weight should be the old weight.", 5, prevWeight);
        assertEquals("Edge weight should be updated correctly.", 10, (int) graph.targets("A").get("B"));
    }

    @Test
    public void testGraphSetEdgeToRemove() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 5);

        int prevWeight = graph.set("A", "B", 0); // setting weight to 0 removes the edge
        assertEquals("Previous weight should be the old weight before removal.", 5, prevWeight);
        assertFalse("Edge should be removed when weight is set to 0.", graph.targets("A").containsKey("B"));
    }

    @Test
    public void testGraphRemoveVertex() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 5);

        assertTrue("Vertex should be removed successfully.", graph.remove("A"));
        assertFalse("Removed vertex should not be in the graph.", graph.vertices().contains("A"));
        assertFalse("Edges connected to the removed vertex should also be removed.", graph.sources("B").containsKey("A"));
    }

    @Test
    public void testGraphVertices() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        graph.add("A");
        graph.add("B");

        Set<String> vertices = graph.vertices();
        assertEquals("Graph should contain two vertices.", 2, vertices.size());
        assertTrue("Graph should contain vertex A.", vertices.contains("A"));
        assertTrue("Graph should contain vertex B.", vertices.contains("B"));
    }

    @Test
    public void testGraphSources() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 5);

        Map<String, Integer> sources = graph.sources("B");
        assertEquals("There should be one source for B.", 1, sources.size());
        assertEquals("Source weight should be correct.", 5, (int) sources.get("A"));
    }

    @Test
    public void testGraphTargets() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 5);

        Map<String, Integer> targets = graph.targets("A");
        assertEquals("There should be one target for A.", 1, targets.size());
        assertEquals("Target weight should be correct.", 5, (int) targets.get("B"));
    }

    @Test
    public void testGraphToString() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 5);

        String expected = "Vertices:\n  A -> {B=5}\n  B -> {}\n";
        assertEquals("Graph's toString should match the expected format.", expected, graph.toString());
    }

    // Tests for Edge class

    @Test
    public void testEdgeCreation() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        graph.add("A");
        graph.add("B");

        ConcreteVerticesGraph.Edge edge = graph.new Edge("A", "B", 10);
        assertEquals("Edge source should be A", "A", edge.getSource());
        assertEquals("Edge target should be B", "B", edge.getTarget());
        assertEquals("Edge weight should be 10", 10, edge.getWeight());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEdgeInvalidWeight() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        graph.new Edge("A", "B", -1); // Invalid weight
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEdgeNullSource() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        graph.new Edge(null, "B", 5); // Null source
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEdgeNullTarget() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        graph.new Edge("A", null, 5); // Null target
    }
}
