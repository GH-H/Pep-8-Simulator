package model;

/**
 * This class was create as a memory unit
 * storing memory in an array
 *
 */
public class MemoryUnit {

    private final int DEFAULT_MEMORY_LOCATION_AMOUNT = 65536;

    //array of database
    private String[] myMemory = new String[DEFAULT_MEMORY_LOCATION_AMOUNT];

    public MemoryUnit() {
    }

    public int getTotalMemoryLocations() { return DEFAULT_MEMORY_LOCATION_AMOUNT; }

    //getting data inside memory
    public String getDataAt(int index) {
        return myMemory[index];
    }

    //storing data at a specific index
    public void storeAt(int index, String data) {
        myMemory[index] = data;
    }

    //return the entire memory
    public String[] getMyMemory() {
        return myMemory;
    }

    // Clear the memory
    public void clearMyMemory() { myMemory = new String[DEFAULT_MEMORY_LOCATION_AMOUNT]; }

}