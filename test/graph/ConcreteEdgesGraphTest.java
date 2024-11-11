/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Map;
import java.util.Set;

public class ConcreteEdgesGraphTest {

    // Test cases for the Edge class within ConcreteEdgesGraph

    @Test
    public void testEdgeCreation() {
        ConcreteEdgesGraph.Edge edge = new ConcreteEdgesGraph.Edge("A", "B", 5);
        assertEquals("A", edge.getSource());
        assertEquals("B", edge.getTarget());
        assertEquals(5, edge.getWeight());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEdgeNegativeWeight() {
        new ConcreteEdgesGraph.Edge("A", "B", -1);
    }

    @Test
    public void testEdgeToString() {
        ConcreteEdgesGraph.Edge edge = new ConcreteEdgesGraph.Edge("A", "B", 5);
        assertEquals("A -> B (5)", edge.toString());
    }

    @Test
    public void testEdgeEquality() {
        ConcreteEdgesGraph.Edge edge1 = new ConcreteEdgesGraph.Edge("A", "B", 5);
        ConcreteEdgesGraph.Edge edge2 = new ConcreteEdgesGraph.Edge("A", "B", 5);
        ConcreteEdgesGraph.Edge edge3 = new ConcreteEdgesGraph.Edge("A", "C", 5);
        assertEquals(edge1, edge2);
        assertNotEquals(edge1, edge3);
    }

    // Test cases for the ConcreteEdgesGraph class

    @Test
    public void testAddVertex() {
        ConcreteEdgesGraph graph = new ConcreteEdgesGraph();
        assertTrue(graph.add("A"));
        assertFalse(graph.add("A")); // vertex already exists
    }

    @Test
    public void testSetEdge() {
        ConcreteEdgesGraph graph = new ConcreteEdgesGraph();
        graph.add("A");
        graph.add("B");
        
        assertEquals(0, graph.set("A", "B", 5)); // new edge
        assertEquals(5, graph.set("A", "B", 10)); // update edge weight
        assertEquals(10, graph.set("A", "B", 0)); // remove edge
    }

    @Test
    public void testRemoveVertex() {
        ConcreteEdgesGraph graph = new ConcreteEdgesGraph();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 5);

        assertTrue(graph.remove("A"));
        assertFalse(graph.remove("A")); // vertex already removed
        assertEquals(0, graph.sources("B").size()); // no edges should remain
    }

    @Test
    public void testVertices() {
        ConcreteEdgesGraph graph = new ConcreteEdgesGraph();
        graph.add("A");
        graph.add("B");

        Set<String> vertices = graph.vertices();
        assertEquals(2, vertices.size());
        assertTrue(vertices.contains("A"));
        assertTrue(vertices.contains("B"));
    }

    @Test
    public void testSources() {
        ConcreteEdgesGraph graph = new ConcreteEdgesGraph();
        graph.add("A");
        graph.add("B");
        graph.add("C");
        graph.set("A", "B", 5);
        graph.set("C", "B", 3);

        Map<String, Integer> sources = graph.sources("B");
        assertEquals(2, sources.size());
        assertEquals(5, (int) sources.get("A"));
        assertEquals(3, (int) sources.get("C"));
    }

    @Test
    public void testTargets() {
        ConcreteEdgesGraph graph = new ConcreteEdgesGraph();
        graph.add("A");
        graph.add("B");
        graph.add("C");
        graph.set("A", "B", 5);
        graph.set("A", "C", 3);

        Map<String, Integer> targets = graph.targets("A");
        assertEquals(2, targets.size());
        assertEquals(5, (int) targets.get("B"));
        assertEquals(3, (int) targets.get("C"));
    }

    @Test
    public void testToString() {
        ConcreteEdgesGraph graph = new ConcreteEdgesGraph();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 5);

        String expected = "Vertices: [A, B]\nEdges:\n  A -> B (5)\n";
        assertEquals(expected, graph.toString());
    }
}
