package graph;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import graph.Graph;

public abstract class GraphInstanceTest {

    private Graph<String> graph;

    /**
     * Returns a new, empty instance of the Graph.
     * Subclasses must override this method to provide specific implementations.
     */
    protected abstract Graph<String> emptyInstance();

    @Before
    public void setUp() {
        graph = emptyInstance();
    }

    /**
     * Test that a new graph instance has no vertices.
     */
    @Test
    public void testInitialVerticesEmpty() {
        // Check that the graph has no vertices when it is first created.
        assertTrue("A new graph should have no vertices.", graph.vertices().isEmpty());
    }

    /**
     * Test adding vertices and checking their existence in the graph.
     */
    @Test
    public void testAddVertex() {
        // Add a vertex and check if it is successfully added.
        assertTrue("Adding a new vertex 'A' should return true.", graph.add("A"));
        assertTrue("Graph should contain vertex 'A' after adding it.", graph.vertices().contains("A"));

        // Try to add the same vertex again and check that it fails.
        assertFalse("Adding duplicate vertex 'A' should return false.", graph.add("A"));
    }

    /**
     * Test that adding a duplicate vertex does not create a second entry.
     */
    @Test
    public void testAddDuplicateVertex() {
        // Add a vertex and check if it is successfully added.
        assertTrue("Adding vertex 'A' the first time should return true.", graph.add("A"));
        
        // Try to add the same vertex again and ensure it does not get duplicated.
        assertFalse("Adding vertex 'A' a second time should return false.", graph.add("A"));

        // Check that the graph contains only one instance of the vertex.
        assertEquals("Graph should contain only one instance of 'A' after adding it twice.", 1, graph.vertices().size());
    }

    /**
     * Test setting edges between vertices and verifying correct weights.
     */
    @Test
    public void testSetEdge() {
        // Add vertices and set edges between them with weights.
        graph.add("A");
        graph.add("B");

        // Initially, there should be no edge, so the weight should be 0.
        assertEquals("Initially, there should be no edge, so the weight should be 0.", 0, graph.set("A", "B", 5));
        
        // Set the edge weight and verify it has been updated correctly.
        assertEquals("Updating edge weight from 'A' to 'B' should return the old weight of 0.", 0, graph.set("A", "B", 10));
        
        // Verify the edge exists with the correct weight.
        assertEquals("Source 'A' should have an edge with weight 10 to 'B'.", 10, graph.sources("B").get("A").intValue());
        assertEquals("Target 'B' should have an edge with weight 10 from 'A'.", 10, graph.targets("A").get("B").intValue());
    }

    /**
     * Test removing a vertex and verifying it no longer exists in the graph.
     */
    @Test
    public void testRemoveVertex() {
        // Add vertices and set an edge between them.
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 5);

        // Verify that vertex 'A' exists before removal.
        assertTrue("Graph should contain vertex 'A' before removal.", graph.vertices().contains("A"));
        
        // Remove vertex 'A' and check that it has been removed.
        assertTrue("Removing vertex 'A' should return true.", graph.remove("A"));
        assertFalse("Vertex 'A' should no longer exist in the graph.", graph.vertices().contains("A"));
        
        // Check that all edges from 'A' are removed after removal.
        assertEquals("Removing 'A' should remove all edges where 'A' was a source.", 0, graph.sources("B").size());
    }

    /**
     * Test sources and targets for directed edges.
     */
    @Test
    public void testSourcesAndTargets() {
        // Add vertices and set edges between them with weights.
        graph.add("A");
        graph.add("B");
        graph.add("C");
        graph.set("A", "B", 5);
        graph.set("B", "C", 10);

        // Check the edges from source 'A' to target 'B' and from 'B' to 'C'.
        assertEquals("Target 'B' should have an edge with weight 5 from 'A'.", 5, graph.targets("A").get("B").intValue());
        assertEquals("Target 'C' should have an edge with weight 10 from 'B'.", 10, graph.targets("B").get("C").intValue());

        // Check the reverse edges (sources).
        assertEquals("Source 'A' should have an edge with weight 5 to 'B'.", 5, graph.sources("B").get("A").intValue());
        assertEquals("Source 'B' should have an edge with weight 10 to 'C'.", 10, graph.sources("C").get("B").intValue());
    }
}
