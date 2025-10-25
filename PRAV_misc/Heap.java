package fr.istic.prg3;

/**
 * @version 1.0
 *
 */
public interface Heap {
	
	/**
	 * Adds a new value to the heap.
	 * This methods does nothing is the value is already present.
	 * 
	 * @param newValue	the value to add
	 */
	public void addValue(int newValue);
	
	
	public int extractMax();
	
	
	public int getMax();
	
	
	public static int[] heapsort(int[] unsortedValues) {
		// TODO
		return null;
	}
	
	
	public void siftDown();
	
	
	public void siftUp();

}
