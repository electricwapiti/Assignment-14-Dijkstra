/*
 * Unit tests for BinaryHeapPriorityQueue class
 */
package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class BinaryHeapPriorityQueueTest {
    private BinaryHeapPriorityQueue<Integer> pq;

    @BeforeEach
    void setUp() {
        pq = new BinaryHeapPriorityQueue<>();
    }

    @Test
    void testEmptyQueue() {
        assertTrue(pq.isEmpty());
        assertEquals(0, pq.size());
        assertNull(pq.poll());
        assertNull(pq.peek());
    }

    @Test
    void testSingleElement() {
        pq.offer(5);
        assertFalse(pq.isEmpty());
        assertEquals(1, pq.size());
        assertEquals(5, pq.peek());
        assertEquals(5, pq.poll());
        assertTrue(pq.isEmpty());
    }

    @Test
    void testMinHeapOrder() {
        pq.offer(5);
        pq.offer(3);
        pq.offer(7);
        pq.offer(1);
        pq.offer(9);

        assertEquals(1, pq.poll());
        assertEquals(3, pq.poll());
        assertEquals(5, pq.poll());
        assertEquals(7, pq.poll());
        assertEquals(9, pq.poll());
    }

    @Test
    void testPeekDoesNotRemove() {
        pq.offer(10);
        assertEquals(10, pq.peek());
        assertEquals(1, pq.size());
        assertEquals(10, pq.peek());
        assertEquals(1, pq.size());
    }

    @Test
    void testAddAfterRemove() {
        pq.offer(5);
        pq.offer(3);
        pq.poll();  // Remove 3
        assertEquals(1, pq.size());
        pq.offer(1);
        assertEquals(1, pq.poll());
        assertEquals(5, pq.poll());
    }

    @Test
    void testDuplicateElements() {
        pq.offer(5);
        pq.offer(5);
        pq.offer(3);
        pq.offer(3);

        assertEquals(3, pq.poll());
        assertEquals(3, pq.poll());
        assertEquals(5, pq.poll());
        assertEquals(5, pq.poll());
    }

    @Test
    void testLargeQuantities() {
        // Add 1000 elements in random order
        for (int i = 1000; i > 0; i--) {
            pq.offer(i);
        }

        assertEquals(1000, pq.size());

        // Poll all and verify they come out in sorted order
        for (int i = 1; i <= 1000; i++) {
            assertEquals(i, pq.poll());
        }
    }

    @Test
    void testNegativeNumbers() {
        pq.offer(-5);
        pq.offer(-10);
        pq.offer(0);
        pq.offer(5);

        assertEquals(-10, pq.poll());
        assertEquals(-5, pq.poll());
        assertEquals(0, pq.poll());
        assertEquals(5, pq.poll());
    }

    @Test
    void testClear() {
        pq.offer(1);
        pq.offer(2);
        pq.offer(3);
        assertEquals(3, pq.size());

        pq.clear();
        assertEquals(0, pq.size());
        assertTrue(pq.isEmpty());
    }

    @Test
    void testNullElementRejected() {
        assertThrows(IllegalArgumentException.class, () -> {
            pq.offer(null);
        });
    }

    @Test
    void testResize() {
        // Add more elements than default capacity (16)
        for (int i = 0; i < 100; i++) {
            pq.offer(i);
        }

        assertEquals(100, pq.size());

        // Verify heap property is maintained
        for (int i = 0; i < 100; i++) {
            assertEquals(i, pq.poll());
        }
    }

    @Test
    void testComparableElements() {
        BinaryHeapPriorityQueue<String> stringPq = new BinaryHeapPriorityQueue<>();
        stringPq.offer("zebra");
        stringPq.offer("apple");
        stringPq.offer("banana");

        assertEquals("apple", stringPq.poll());
        assertEquals("banana", stringPq.poll());
        assertEquals("zebra", stringPq.poll());
    }

    @Test
    void testDoubleValues() {
        BinaryHeapPriorityQueue<Double> doublePq = new BinaryHeapPriorityQueue<>();
        doublePq.offer(3.5);
        doublePq.offer(1.1);
        doublePq.offer(2.2);
        doublePq.offer(1.1);

        assertEquals(1.1, doublePq.poll());
        assertEquals(1.1, doublePq.poll());
        assertEquals(2.2, doublePq.poll());
        assertEquals(3.5, doublePq.poll());
    }
}
