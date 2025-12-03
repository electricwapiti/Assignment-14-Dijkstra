/*
 * Unit tests for Dijkstra's algorithm implementation
 */
package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class DijkstraTest {
    private AdjacencyList graph;

    @BeforeEach
    void setUp() {
        // Create a simple test graph
        graph = new AdjacencyList(6);
        // 0 -> 1: weight 4
        graph.addEdge(0, 1, 4);
        // 0 -> 2: weight 2
        graph.addEdge(0, 2, 2);
        // 1 -> 2: weight 1
        graph.addEdge(1, 2, 1);
        // 1 -> 3: weight 5
        graph.addEdge(1, 3, 5);
        // 2 -> 3: weight 8
        graph.addEdge(2, 3, 8);
        // 2 -> 4: weight 10
        graph.addEdge(2, 4, 10);
        // 3 -> 4: weight 2
        graph.addEdge(3, 4, 2);
        // 3 -> 5: weight 6
        graph.addEdge(3, 5, 6);
        // 4 -> 5: weight 3
        graph.addEdge(4, 5, 3);
    }

    @Test
    void testShortestPathSimple() {
        // 0 -> 2 has direct edge with weight 2
        double distance = Dijkstra.shortestPath(graph, 0, 2);
        assertEquals(2, distance);
    }

    @Test
    void testShortestPathMultipleHops() {
        // 0 -> 1 -> 3 -> 4 -> 5: 4 + 5 + 2 + 3 = 14 (shortest)
        double distance = Dijkstra.shortestPath(graph, 0, 5);
        assertEquals(14, distance);
    }

    @Test
    void testNoPath() {
        AdjacencyList disconnected = new AdjacencyList(3);
        disconnected.addEdge(0, 1, 1);
        // No path from 0 to 2
        double distance = Dijkstra.shortestPath(disconnected, 0, 2);
        assertEquals(-1, distance);
    }

    @Test
    void testSingleNode() {
        AdjacencyList single = new AdjacencyList(1);
        double distance = Dijkstra.shortestPath(single, 0, 0);
        assertEquals(0, distance);
    }

    @Test
    void testSingleEdge() {
        AdjacencyList simple = new AdjacencyList(2);
        simple.addEdge(0, 1, 5);
        double distance = Dijkstra.shortestPath(simple, 0, 1);
        assertEquals(5, distance);
    }

    @Test
    void testUnweightedEdges() {
        AdjacencyList unweighted = new AdjacencyList(4);
        unweighted.addEdge(0, 1);  // weight 1
        unweighted.addEdge(1, 2);  // weight 1
        unweighted.addEdge(2, 3);  // weight 1
        double distance = Dijkstra.shortestPath(unweighted, 0, 3);
        assertEquals(3, distance);
    }

    @Test
    void testMultipleShortestPaths() {
        AdjacencyList multi = new AdjacencyList(4);
        // Path 1: 0 -> 1 -> 3: 1 + 1 = 2
        multi.addEdge(0, 1, 1);
        multi.addEdge(1, 3, 1);
        // Path 2: 0 -> 2 -> 3: 1 + 1 = 2
        multi.addEdge(0, 2, 1);
        multi.addEdge(2, 3, 1);
        double distance = Dijkstra.shortestPath(multi, 0, 3);
        // Both paths have the same length
        assertEquals(2, distance);
    }

    @Test
    void testInvalidStartNode() {
        assertThrows(IllegalArgumentException.class, () -> {
            Dijkstra.shortestPath(graph, -1, 5);
        });
    }

    @Test
    void testInvalidEndNode() {
        assertThrows(IllegalArgumentException.class, () -> {
            Dijkstra.shortestPath(graph, 0, 10);
        });
    }

    @Test
    void testComplexGraph() {
        AdjacencyList complex = new AdjacencyList(7);
        complex.addEdge(0, 1, 7);
        complex.addEdge(0, 2, 9);
        complex.addEdge(0, 5, 14);
        complex.addEdge(1, 2, 10);
        complex.addEdge(1, 3, 15);
        complex.addEdge(2, 3, 11);
        complex.addEdge(2, 5, 2);
        complex.addEdge(3, 4, 6);
        complex.addEdge(4, 5, 9);
        complex.addEdge(5, 4, 9);
        double distance = Dijkstra.shortestPath(complex, 0, 4);
        assertEquals(20, distance);  // 0 -> 2 -> 5 -> 4: 9 + 2 + 9 = 20
    }

    @Test
    void testLargeWeights() {
        AdjacencyList largeWeights = new AdjacencyList(3);
        largeWeights.addEdge(0, 1, 1000000);
        largeWeights.addEdge(0, 2, 500000);
        largeWeights.addEdge(2, 1, 400000);
        double distance = Dijkstra.shortestPath(largeWeights, 0, 1);
        assertEquals(900000, distance);  // 0 -> 2 -> 1 is shorter
    }

    @Test
    void testLinearGraph() {
        AdjacencyList linear = new AdjacencyList(5);
        linear.addEdge(0, 1, 1);
        linear.addEdge(1, 2, 2);
        linear.addEdge(2, 3, 3);
        linear.addEdge(3, 4, 4);
        double distance = Dijkstra.shortestPath(linear, 0, 4);
        assertEquals(10, distance);
    }

    @Test
    void testDenseGraph() {
        AdjacencyList dense = new AdjacencyList(4);
        // Complete graph with weights
        dense.addEdge(0, 1, 1);
        dense.addEdge(0, 2, 4);
        dense.addEdge(0, 3, 6);
        dense.addEdge(1, 2, 2);
        dense.addEdge(1, 3, 5);
        dense.addEdge(2, 3, 1);
        double distance = Dijkstra.shortestPath(dense, 0, 3);
        // 0 -> 1 -> 2 -> 3: 1 + 2 + 1 = 4
        assertEquals(4, distance);
    }
}
