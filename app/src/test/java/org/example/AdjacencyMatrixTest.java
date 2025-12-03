/*
 * Unit tests for AdjacencyList class
 */
package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class AdjacencyListTest {
    private AdjacencyList graph;

    @BeforeEach
    void setUp() {
        graph = new AdjacencyList(5);
    }

    @Test
    void testGraphCreation() {
        assertEquals(5, graph.getSize());
    }

    @Test
    void testAddWeightedEdge() {
        graph.addEdge(0, 1, 5);
        assertTrue(graph.hasEdge(0, 1));
        assertEquals(5, graph.getWeight(0, 1));
    }

    @Test
    void testAddUnweightedEdge() {
        graph.addEdge(0, 1);
        assertTrue(graph.hasEdge(0, 1));
        assertEquals(1, graph.getWeight(0, 1));
    }

    @Test
    void testNoEdgeInitially() {
        assertFalse(graph.hasEdge(0, 1));
        assertEquals(Double.POSITIVE_INFINITY, graph.getWeight(0, 1));
    }

    @Test
    void testRemoveEdge() {
        graph.addEdge(0, 1, 5);
        assertTrue(graph.hasEdge(0, 1));
        graph.removeEdge(0, 1);
        assertFalse(graph.hasEdge(0, 1));
    }

    @Test
    void testMultipleEdges() {
        graph.addEdge(0, 1, 2);
        graph.addEdge(0, 2, 3);
        graph.addEdge(1, 2, 4);
        assertEquals(2, graph.getWeight(0, 1));
        assertEquals(3, graph.getWeight(0, 2));
        assertEquals(4, graph.getWeight(1, 2));
    }

    @Test
    void testInvalidSourceIndex() {
        assertThrows(IllegalArgumentException.class, () -> {
            graph.addEdge(-1, 1, 5);
        });
    }

    @Test
    void testInvalidDestinationIndex() {
        assertThrows(IllegalArgumentException.class, () -> {
            graph.addEdge(0, 10, 5);
        });
    }

    @Test
    void testNegativeWeight() {
        assertThrows(IllegalArgumentException.class, () -> {
            graph.addEdge(0, 1, -5);
        });
    }

    @Test
    void testSelfLoopNotAllowed() {
        assertThrows(IllegalArgumentException.class, () -> {
            graph.addEdge(0, 0, 5);
        });
    }

    @Test
    void testDirectedEdges() {
        graph.addEdge(0, 1, 5);
        assertTrue(graph.hasEdge(0, 1));
        assertFalse(graph.hasEdge(1, 0));  // Should not be bidirectional
    }

    @Test
    void testUpdateEdgeWeight() {
        graph.addEdge(0, 1, 5);
        assertEquals(5, graph.getWeight(0, 1));
        graph.addEdge(0, 1, 10);  // Update weight
        assertEquals(10, graph.getWeight(0, 1));
    }

    @Test
    void testGetNeighbors() {
        graph.addEdge(0, 1, 5);
        graph.addEdge(0, 2, 3);
        graph.addEdge(0, 3, 7);

        var neighbors = graph.getNeighbors(0);
        assertEquals(3, neighbors.size());
    }

    @Test
    void testGetDegree() {
        graph.addEdge(0, 1, 5);
        graph.addEdge(0, 2, 3);
        graph.addEdge(0, 3, 7);

        assertEquals(3, graph.getDegree(0));
        assertEquals(0, graph.getDegree(1));
    }

    @Test
    void testLargeGraph() {
        AdjacencyList large = new AdjacencyList(100);
        large.addEdge(0, 99, 100);
        assertTrue(large.hasEdge(0, 99));
        assertEquals(100, large.getWeight(0, 99));
    }

    @Test
    void testToString() {
        graph.addEdge(0, 1, 5);
        String str = graph.toString();
        assertTrue(str.contains("5 nodes"));
        assertTrue(str.contains("1(5)"));
    }

    @Test
    void testInvalidSizeCreation() {
        assertThrows(IllegalArgumentException.class, () -> {
            new AdjacencyList(0);
        });
    }

    @Test
    void testGetWeightInvalidIndices() {
        assertEquals(Double.POSITIVE_INFINITY, graph.getWeight(-1, 1));
        assertEquals(Double.POSITIVE_INFINITY, graph.getWeight(0, 10));
    }
}
