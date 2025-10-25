package fr.istic.prg3;
 
import java.util.Random;
 
/**
 * @version 1.0
 *
 */
public class HeapArray implements Heap {
 
	protected int[] values;
	protected int size;
 
	public HeapArray(int[] valuesArray) {
		values = valuesArray;
		size = valuesArray.length;
	}
 
 
	public void addValue(int newValue) {
		int[] temp = new int[size+1];
		for(int i = 0 ; i<values.length ; i++){
			temp[i] = values[i];
		}
		temp[size] = newValue;
		this.values = temp;
		size++;
	}
 
 
	public int getMax() {
		if(size == 0){
			return -1;			//si le tab est vide on renvoie -1 (-1  ne peut pas être dans le tableau)
		}
		int max = values[0];		//le max prend la valeur du 1er élément
		for(int i = 1; i< size ; i++){				//parcours de tab classique
			if(values[i] > max) max = values[i];				//et si on trouve un nouveau + grand élément, on update max
		}
 
		return max;
	}
 
 
	public int extractMax() {			//je suis pas sûr mais je crois que cette fonction retourne le max et supprime l'occurrence de max de values.
		int maxToRet = getMax();		//on prépare la valeur à return parce qu'on va modifier le tableau
 
		if(size == 0){
			return -1;
		}
		if(size == 1){
			int temp = values[0];
			values = new int[0];
			return temp;
		}
		int indiceMax = getIndexMax();				//on récup l'indice du max
		int[] newtab = new int[size-1];				//on crée un tab de taille size-1 (puisqu'on va enlever le max)
		int indiceNT = 0;
		for(int i = 0 ; i < values.length ; i++){	//on remet les valeurs présentes dans values dans le nouveau tableau
			if(i != indiceMax){
				newtab[indiceNT] = values[i];
				indiceNT++;
			}
		}
 
		this.values = newtab ; 
		return maxToRet;
 
	}
 
	public int getIndexMax(){			//renvoie l'indice de la première occurrence du max de values
		int max = getMax();
		int i = 0;
		while(values[i] != max){		//tant qu'on est pas sur l'indice du max
			i++;						//on avance
		}
		return i;						//et on renvoie l'index
	}
 
 
	protected void heapifyDown() {		
		for (int i = size / 2 - 1; i >= 0; i--) {
			siftDown(i); 
		}
	}
 
 
	public static int[] heapsort(int[] unsortedValues) {
    HeapArray heap = new HeapArray(unsortedValues);
    heap.heapifyDown(); 
 
    for (int i = heap.size - 1; i > 0; i--) {
        heap.swap(0, i);  // mettre le minimum en fin de tableau
        heap.size--;      // réduire la taille du tas
        heap.siftDown(0); 
    }
 
    return heap.values; 
	}
 
 
 
	protected int indexLeft(int position) {
		return 2*position+1;
	}
 
 
	protected int indexRight(int position) {
		return 2*position+2;
	}
 
 
	protected int indexUp(int position) {
		return (position-2)/2;
	}
 
 
	public void siftDown() {
		this.siftDown(0);
	}
 
 
	protected void siftDown(int position) {
		while (indexLeft(position) < size) { // pas d'enfant gauche
        int left = indexLeft(position);
        int right = indexRight(position);
        int smallest = position; 
 
        if (left < size && values[left] < values[smallest]) {
            smallest = left;
        }
        if (right < size && values[right] < values[smallest]) {
            smallest = right;
        }
 
        if (smallest == position) { 
            break;
        }
 
        swapIfLowerAndSiftDown(smallest, position);
        position = smallest; // Descendre à la nouvelle position
    }
	}
 
 
	public void siftUp() {
		this.siftUp(this.size - 1);
	}
 
 
	protected void siftUp(int position) {
		int parent = indexUp(position);
		while (position > 0 && values[position] > values[parent]) {
			swapIfGreaterAndSiftUp(position, parent);
			position = parent;
			parent = indexUp(position);
		}
	}
 
 
	protected void swap(int index1, int index2) {
		int temp = values[index1];
		values[index1] = values[index2];
		values[index2] = temp;
	}
 
 
	protected void swapIfGreaterAndSiftUp(int index1, int index2) {
		if (index1 >= 0 && index2 >= 0 && values[index1] > values[index2]) {
			swap(index1, index2);
			siftUp(index1);
		}
	}
 
 
	protected void swapIfLowerAndSiftDown(int index1, int index2) {
		if (index1 < size && index2 < size && values[index1] < values[index2]) {
			swap(index1, index2);
			siftDown(index1);
		}
	}
 
 
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i = 0; i < size; i++) {
			sb.append(values[i]);
			if (i < size - 1) {
				sb.append(", ");
			}
		}
		sb.append("]");
		return sb.toString();
	}
 
 
	public String toString(String offset) {
		StringBuilder sb = new StringBuilder();
		sb.append(offset).append("[\n");
		for (int i = 0; i < size; i++) {
			sb.append(offset).append("  ").append(values[i]);
			if (i < size - 1) {
				sb.append(",\n");
			}
		}
		sb.append("\n").append(offset).append("]");
		return sb.toString();
	}
 
 
	   public static int[] generateRandomArray(int size) {
        Random random = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(size); // Générer des entiers entre 0 et la taille du tableau
        }
        return array;
    }
 
	public static void mesurePerformance() {
        int[] sizes = {100, 1000, 10000, 100000, 1000000,10000000}; // 100 à 10 millions
        Random random = new Random();	//appel random
 
        for (int size : sizes) {
            int[] arrayForHeapArray = generateRandomArray(size); // Générer un tableau aléatoire
            int[] arrayForHeapTree = arrayForHeapArray.clone(); // Cloner le tableau pour HeapTree
 
            // Mesurer le temps pour HeapArray
            long startTimeHeapArray = System.nanoTime();
            HeapArray heapArray = new HeapArray(arrayForHeapArray);
            heapArray.heapifyDown(); // Construire le tas
            heapArray.heapsort(arrayForHeapArray); // Trier
            long endTimeHeapArray = System.nanoTime();
 
            // Mesurer le temps pour HeapTree
            long startTimeHeapTree = System.nanoTime();
            HeapTree heapTree = new HeapTree(arrayForHeapTree);
            HeapTree.heapsort(arrayForHeapTree); // Trier
            long endTimeHeapTree = System.nanoTime();
 
            // Afficher les résultats
            System.out.println("Taille: " + size);
            System.out.println("HeapArray: " + (endTimeHeapArray - startTimeHeapArray) / 1_000_000 + " ms");
            System.out.println("HeapTree: " + (endTimeHeapTree - startTimeHeapTree) / 1_000_000 + " ms");
            System.out.println("---------------------------------");
        }
    }
 
 
	public static void main(String[] args) {
		int[] values = {3, 1, 5, 8, 4, 2};
		HeapArray heap = new HeapArray(values);
 
		System.out.println("tas de base: " + heap);
 
		heap.heapifyDown();
		System.out.println("tas après heapify: " + heap);
 
		int[] sortedValues = HeapArray.heapsort(values);
		System.out.println("tas après heapsort: " + java.util.Arrays.toString(sortedValues));
 
 
        mesurePerformance(); // tests de performance
 
 
 
	}
 
}