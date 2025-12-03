package org.example;

/**
 * A custom implementation of a binary heap-based min priority queue.
 * Elements with smaller priorities are dequeued first.
 */
public class BinaryHeapPriorityQueue<T extends Comparable<T>> {
    private static final int DEFAULT_CAPACITY = 16;
    private Object[] heap;
    private int size;

    /**
     * Creates a new binary heap priority queue with default capacity.
     */
    public BinaryHeapPriorityQueue() {
        this.heap = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    /**
     * Inserts an element into the priority queue.
     *
     * @param element the element to insert
     */
    public void offer(T element) {
        if (element == null) {
            throw new IllegalArgumentException("Cannot insert null element");
        }
        
        // Resize if necessary
        if (size == heap.length) {
            resize();
        }

        // Add element at end
        heap[size] = element;
        siftUp(size);
        size++;
    }

    /**
     * Retrieves and removes the element with minimum priority.
     *
     * @return the element with minimum priority, or null if empty
     */
    public T poll() {
        if (size == 0) {
            return null;
        }

        @SuppressWarnings("unchecked")
        T min = (T) heap[0];

        // Move last element to root
        heap[0] = heap[size - 1];
        heap[size - 1] = null;
        size--;

        // Restore heap property
        if (size > 0) {
            siftDown(0);
        }

        return min;
    }

    /**
     * Retrieves but does not remove the element with minimum priority.
     *
     * @return the element with minimum priority, or null if empty
     */
    public T peek() {
        if (size == 0) {
            return null;
        }
        @SuppressWarnings("unchecked")
        T min = (T) heap[0];
        return min;
    }

    /**
     * Checks if the priority queue is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of elements in the priority queue.
     *
     * @return the size of the queue
     */
    public int size() {
        return size;
    }

    /**
     * Clears all elements from the priority queue.
     */
    public void clear() {
        for (int i = 0; i < size; i++) {
            heap[i] = null;
        }
        size = 0;
    }

    /**
     * Moves an element up the heap to maintain heap property.
     * Used after insertion.
     *
     * @param index the index of the element to sift up
     */
    private void siftUp(int index) {
        Object element = heap[index];
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            Object parent = heap[parentIndex];

            @SuppressWarnings("unchecked")
            T elem = (T) element;
            @SuppressWarnings("unchecked")
            T par = (T) parent;

            if (elem.compareTo(par) >= 0) {
                break;
            }

            heap[index] = parent;
            index = parentIndex;
        }
        heap[index] = element;
    }

    /**
     * Moves an element down the heap to maintain heap property.
     * Used after removing the root.
     *
     * @param index the index of the element to sift down
     */
    private void siftDown(int index) {
        Object element = heap[index];
        int halfSize = size / 2;

        while (index < halfSize) {
            int leftChildIndex = 2 * index + 1;
            int rightChildIndex = leftChildIndex + 1;
            int smallestIndex = leftChildIndex;

            Object rightChild = rightChildIndex < size ? heap[rightChildIndex] : null;
            Object leftChild = heap[leftChildIndex];

            @SuppressWarnings("unchecked")
            T left = (T) leftChild;
            @SuppressWarnings("unchecked")
            T right = (T) rightChild;
            @SuppressWarnings("unchecked")
            T elem = (T) element;

            if (right != null && right.compareTo(left) < 0) {
                smallestIndex = rightChildIndex;
            }

            Object smallest = heap[smallestIndex];
            @SuppressWarnings("unchecked")
            T small = (T) smallest;

            if (elem.compareTo(small) <= 0) {
                break;
            }

            heap[index] = smallest;
            index = smallestIndex;
        }
        heap[index] = element;
    }

    /**
     * Doubles the capacity of the heap array when full.
     */
    private void resize() {
        Object[] newHeap = new Object[heap.length * 2];
        System.arraycopy(heap, 0, newHeap, 0, heap.length);
        heap = newHeap;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(heap[i]);
        }
        sb.append("]");
        return sb.toString();
    }
}
