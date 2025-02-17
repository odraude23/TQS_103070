/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tqs.sets;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import tqs.sets.BoundedSetOfNaturals;

/**
 * @author ico0
 */
class BoundedSetOfNaturalsTest {
    private BoundedSetOfNaturals setA;
    private BoundedSetOfNaturals setB;
    private BoundedSetOfNaturals setC;


    @BeforeEach
    public void setUp() {
        setA = new BoundedSetOfNaturals(1);
        setB = BoundedSetOfNaturals.fromArray(new int[]{10, 20, 30, 40, 50, 60});
        setC = BoundedSetOfNaturals.fromArray(new int[]{50, 60});
    }

    @AfterEach
    public void tearDown() {
        setA = setB = setC = null;
    }

    @Test
    public void testAddElement() {

        setA.add(99);
        assertTrue(setA.contains(99), "add: added element not found in set.");
        assertEquals(1, setA.size());

        assertThrows(IllegalArgumentException.class, () -> setB.add(11));

        assertFalse(setB.contains(11), "add: added element not found in set.");
        assertEquals(6, setB.size(), "add: elements count not as expected.");

        assertThrows(IllegalArgumentException.class, () -> setA.add(-1));
        assertThrows(IllegalArgumentException.class, () -> setA.add(99));

        BoundedSetOfNaturals setD = new BoundedSetOfNaturals(2);
        setD.add(1);

        assertThrows(IllegalArgumentException.class, () -> setD.add(1));
        assertThrows(IllegalArgumentException.class, () -> setD.add(0));
    }

    @Test
    public void testAddFromBadArray() {
        int[] elems = new int[]{10, -20, -30};

        // must fail with exception
        assertThrows(IllegalArgumentException.class, () -> setA.add(elems));

        int[] elems2 = new int[]{4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
        assertThrows(IllegalArgumentException.class, () -> setA.add(elems2));
    }

    @Test
    public void testIntersect() {
        assertTrue(setC.intersects(setB), "intersect: sets should intersect.");
        assertFalse(setA.intersects(setB), "intersects: empty set intersects with non-empty set.");
    }
}
