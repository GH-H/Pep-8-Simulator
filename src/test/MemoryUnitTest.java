package test;

import model.MemoryUnit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests that the methods of the MemoryUnit class work as intended.
 *
 * @version 22 November 2020
 */
public class MemoryUnitTest {

    /**
     * Tests to see if the constructor creates a default memory unit with empty array element locations.
     */
    @Test
    public void memoryUnitConstructorTest() {
        MemoryUnit mem = new MemoryUnit();
        assertEquals(mem.getDataAt(10), null);
    }

    /**
     * Tests if the method returns the correct amount of total memory locations available in the MemoryUnit class.
     */
    @Test
    public void getTotalMemoryLocationsTest() {
        MemoryUnit mem = new MemoryUnit();

        assertEquals(mem.getTotalMemoryLocations(), 65536);
    }

    /**
     * Tests that data can be retrieved at Memory locations.
     */
    @Test
    public void getDataAtTest() {
        MemoryUnit mem = new MemoryUnit();
        assertEquals(mem.getDataAt(10), null);
    }

    /**
     * Tests that data can be set at Memory locations.
     */
    @Test
    public void storeAtTest() {
        MemoryUnit mem = new MemoryUnit();
        mem.storeAt(10, "11000000");
        assertEquals(mem.getDataAt(10), "11000000");
    }

    /**
     * Tests that a copy of the Memory array with its contents can be returned from this method.
     */
    @Test
    public void getMyMemoryTest() {
        MemoryUnit mem = new MemoryUnit();

        // Set a value in the mem array.
        mem.storeAt(10, "11001010");
        // Get a copy of the mem array and pass it to memoryArrayCopy.
        String[] memoryArrayCopy = mem.getMyMemory();

        // Use memoryArrayCopy to see if the value set in mem carried over.
        assertEquals(mem.getDataAt(10), "11001010");
        // Check that both String arrays are the same length, implying the full array was copied.
        assertEquals(memoryArrayCopy.length, mem.getTotalMemoryLocations());
    }

    /**
     * Tests that the memory contents can all be cleared with a method call.
     */
    @Test
    public void clearMyMemoryTest() {
        MemoryUnit mem = new MemoryUnit();

        // Set a value in the mem array.
        mem.storeAt(10, "11001010");

        // Clear the memory
        mem.clearMyMemory();

        // Now try to get the data at memory location 10, and see that it is gone.
        assertEquals(mem.getDataAt(10), null);
    }
}
