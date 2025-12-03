package org.example;

/**
 * A customizable adjacency matrix representation of a weighted directed graph.
 * Supports both weighted and unweighted graphs (unweighted edges default to weight 1).
 */
public class AdjacencyMatrix {
    private static final double INFINITY = Double.POSITIVE_INFINITY;
    private final double[][] matrix;
    private final int size;

    /**
     * Creates an adjacency matrix with the given number of nodes.
     * All edges are initially set to infinity (no connection).
     *
     * @param size the number of nodes in the graph
     */
    public AdjacencyMatrix(int size) {
        this.size = size;
        this.matrix = new double[size][size];
        // Initialize all edges to infinity (no connection)
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i != j) {
                    matrix[i][j] = INFINITY;
                }
            }
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
        matrix[source][destination] = weight;
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
        return matrix[source][destination];
    }

    /**
     * Checks if an edge exists from source to destination.
     *
     * @param source the source node index
     * @param destination the destination node index
     * @return true if an edge exists, false otherwise
     */
    public boolean hasEdge(int source, int destination) {
        return matrix[source][destination] != INFINITY;
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
     * Removes an edge from source to destination.
     *
     * @param source the source node index
     * @param destination the destination node index
     */
    public void removeEdge(int source, int destination) {
        if (source >= 0 && source < size && destination >= 0 && destination < size) {
            matrix[source][destination] = INFINITY;
        }
    }

    /**
     * Returns a string representation of the adjacency matrix.
     *
     * @return a string representation of the matrix
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AdjacencyMatrix (").append(size).append(" nodes):\n");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (matrix[i][j] == INFINITY) {
                    sb.append("∞ ");
                } else {
                    sb.append((int) matrix[i][j]).append(" ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
