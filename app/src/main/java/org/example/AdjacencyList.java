package org.example;

import java.util.*;

/**
 * A customizable adjacency list representation of a weighted directed graph.
 * Supports both weighted and unweighted graphs (unweighted edges default to weight 1).
 */
public class AdjacencyList {
    private static final double INFINITY = Double.POSITIVE_INFINITY;
    private final Map<Integer, Map<Integer, Double>> adjacencyList;
    private final int size;

    /**
     * Represents an edge in the graph
     */
    public static class Edge {
        public final int destination;
        public final double weight;

        public Edge(int destination, double weight) {
            this.destination = destination;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return destination + "(" + (int) weight + ")";
        }
    }

    /**
     * Creates an adjacency list with the given number of nodes.
     * All nodes are initialized with empty adjacency lists.
     *
     * @param size the number of nodes in the graph
     */
    public AdjacencyList(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Number of nodes must be positive");
        }
        this.size = size;
        this.adjacencyList = new HashMap<>();
        // Initialize adjacency list for each node
        for (int i = 0; i < size; i++) {
            adjacencyList.put(i, new HashMap<>());
        }
    }

    /**
     * Adds a weighted edge from source to destination node.
     *
     * @param source the source node index
     * @param destination the destination node index
     * @param weight the weight of the edge
     * @throws IllegalArgumentException if indices are out of bounds or invalid
     */
    public void addEdge(int source, int destination, double weight) {
        if (source < 0 || source >= size || destination < 0 || destination >= size) {
            throw new IllegalArgumentException("Node indices must be between 0 and " + (size - 1));
        }
        if (source == destination) {
            throw new IllegalArgumentException("Cannot add self-loop");
        }
        if (weight < 0) {
            throw new IllegalArgumentException("Weight cannot be negative");
        }
        adjacencyList.get(source).put(destination, weight);
    }

    /**
     * Adds an unweighted edge (weight = 1) from source to destination.
     *
     * @param source the source node index
     * @param destination the destination node index
     */
    public void addEdge(int source, int destination) {
        addEdge(source, destination, 1.0);
    }

    /**
     * Gets the weight of the edge from source to destination.
     * Returns infinity if no edge exists.
     *
     * @param source the source node index
     * @param destination the destination node index
     * @return the weight of the edge, or infinity if no edge exists
     */
    public double getWeight(int source, int destination) {
        if (source < 0 || source >= size || destination < 0 || destination >= size) {
            return INFINITY;
        }
        Double weight = adjacencyList.get(source).get(destination);
        return weight != null ? weight : INFINITY;
    }

    /**
     * Checks if an edge exists from source to destination.
     *
     * @param source the source node index
     * @param destination the destination node index
     * @return true if an edge exists, false otherwise
     */
    public boolean hasEdge(int source, int destination) {
        if (source < 0 || source >= size || destination < 0 || destination >= size) {
            return false;
        }
        return adjacencyList.get(source).containsKey(destination);
    }

    /**
     * Gets the number of nodes in the graph.
     *
     * @return the number of nodes
     */
    public int getSize() {
        return size;
    }

    /**
     * Gets all neighbors of a given node as a collection of edges.
     *
     * @param node the node index
     * @return a collection of edges from the given node
     */
    public Collection<Edge> getNeighbors(int node) {
        if (node < 0 || node >= size) {
            return Collections.emptyList();
        }
        List<Edge> neighbors = new ArrayList<>();
        for (Map.Entry<Integer, Double> entry : adjacencyList.get(node).entrySet()) {
            neighbors.add(new Edge(entry.getKey(), entry.getValue()));
        }
        return neighbors;
    }

    /**
     * Gets the degree of a node (number of outgoing edges).
     *
     * @param node the node index
     * @return the number of outgoing edges from this node
     */
    public int getDegree(int node) {
        if (node < 0 || node >= size) {
            return 0;
        }
        return adjacencyList.get(node).size();
    }

    /**
     * Removes an edge from source to destination.
     *
     * @param source the source node index
     * @param destination the destination node index
     */
    public void removeEdge(int source, int destination) {
        if (source >= 0 && source < size && destination >= 0 && destination < size) {
            adjacencyList.get(source).remove(destination);
        }
    }

    /**
     * Returns a string representation of the adjacency list.
     *
     * @return a string representation of the list
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AdjacencyList (").append(size).append(" nodes):\n");
        for (int i = 0; i < size; i++) {
            sb.append("Node ").append(i).append(": ");
            Map<Integer, Double> neighbors = adjacencyList.get(i);
            if (neighbors.isEmpty()) {
                sb.append("(no outgoing edges)");
            } else {
                List<String> edgeStrings = new ArrayList<>();
                for (Map.Entry<Integer, Double> entry : neighbors.entrySet()) {
                    edgeStrings.add(entry.getKey() + "(" + (int) entry.getValue().doubleValue() + ")");
                }
                sb.append(String.join(", ", edgeStrings));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
