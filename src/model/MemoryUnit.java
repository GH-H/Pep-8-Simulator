package model;

/**
 * This class was create as a memory unit
 * storing memory in an array
 *
 */
public class MemoryUnit {

    //array of database
    private String memory[] = new String[6500];

    public MemoryUnit() {
    }

    //getting data inside memory
    public String getDataAt(int index) {
        return memory[index];
    }

    //storing data at a specific index
    public void storeAt(int index, String data) {
        memory[index] = data;
    }

    //return the entire memory
    public String[] getMemory() {
        return memory;
    }

}