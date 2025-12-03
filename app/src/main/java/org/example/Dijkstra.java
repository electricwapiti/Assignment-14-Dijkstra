package org.example;

import java.util.*;

/**
 * Implementation of Dijkstra's shortest path algorithm using adjacency list and binary heap.
 * Finds the shortest path between two nodes in a weighted directed graph.
 *
 * Time Complexity: O((V + E) log V) where V is vertices and E is edges
 * Space Complexity: O(V + E) for the adjacency list representation
 */
public class Dijkstra {
    private static final double INFINITY = Double.POSITIVE_INFINITY;

    /**
     * Finds the shortest path distance between start and end nodes using Dijkstra's algorithm
     * with an adjacency list representation and binary heap priority queue.
     *
     * @param graph an AdjacencyList representing the weighted graph
     * @param start the starting node index
     * @param end the ending node index
     * @return the shortest path distance, or -1 if no path exists
     * @throws IllegalArgumentException if start or end nodes are out of bounds
     */
    public static double shortestPath(AdjacencyList graph, int start, int end) {
        int nodeCount = graph.getSize();

        // Validate input
        if (start < 0 || start >= nodeCount || end < 0 || end >= nodeCount) {
            throw new IllegalArgumentException("Node indices must be between 0 and " + (nodeCount - 1));
        }

        // Initialize distances array
        double[] distances = new double[nodeCount];
        Arrays.fill(distances, INFINITY);
        distances[start] = 0;

        // Track visited nodes
        boolean[] visited = new boolean[nodeCount];

        // Use a custom binary heap priority queue to always process the unvisited node with smallest distance
        BinaryHeapPriorityQueue<NodeDistance> pq = new BinaryHeapPriorityQueue<>();
        pq.offer(new NodeDistance(start, 0));

        while (!pq.isEmpty()) {
            NodeDistance current = pq.poll();
            int currentNode = current.node;
            double currentDistance = current.distance;

            // Skip if already visited
            if (visited[currentNode]) {
                continue;
            }

            visited[currentNode] = true;

            // If we reached the end node with this distance, we found the shortest path
            if (currentNode == end) {
                return currentDistance;
            }

            // Check all neighbors using adjacency list
            for (AdjacencyList.Edge edge : graph.getNeighbors(currentNode)) {
                int neighbor = edge.destination;
                if (!visited[neighbor]) {
                    double edgeWeight = edge.weight;
                    double newDistance = currentDistance + edgeWeight;

                    // If we found a shorter path, update it
                    if (newDistance < distances[neighbor]) {
                        distances[neighbor] = newDistance;
                        pq.offer(new NodeDistance(neighbor, newDistance));
                    }
                }
            }
        }

        // No path found
        return distances[end] == INFINITY ? -1 : distances[end];
    }

    /**
     * Helper class to store a node and its distance for priority queue ordering.
     * Implements Comparable to work with the binary heap.
     */
    private static class NodeDistance implements Comparable<NodeDistance> {
        final int node;
        final double distance;

        NodeDistance(int node, double distance) {
            this.node = node;
            this.distance = distance;
        }

        @Override
        public int compareTo(NodeDistance other) {
            return Double.compare(this.distance, other.distance);
        }

        @Override
        public String toString() {
            return "Node(" + node + ", dist=" + (int) distance + ")";
        }
    }
}
