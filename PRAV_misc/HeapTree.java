/**
 * 
 */
package fr.istic.prg3;



/**
 * @version 1.0
 *
 */
public class HeapTree extends BinaryTreeAlmostComplete implements Heap {
	
	public HeapTree(int value) {
        super(value);
    }

    public HeapTree(int value, HeapTree parent) {
        super(value, parent);
    }

    public HeapTree(int[] tab) {
        super(tab[0], null); 
        for (int i = 1; i < tab.length; i++) {
            this.addValue(tab[i]);
        }
    }
	
	
	@Override
	public void addValue(int value) {
		super.addValue(value);
        BinaryTreeAlmostComplete node = this.getRightmostLowestNode();
        node.siftUp(); 
    }
	
	
	public int extractMax() {
		int max = this.rootValue;

		BinaryTreeAlmostComplete min = this.getRightmostLowestNode();
		this.rootValue = min.rootValue;
		min.rootValue = max;
		BinaryTreeAlmostComplete parentMin = min.up;
		if(parentMin.right!=null){
			parentMin.right=null;
			
		}else{
			parentMin.left=null;
		}
		parentMin.updateNumberOfDescendants();
		this.siftDown();
		return max;
	}
	
	
	
	public int getMax() {
		return this.rootValue;
	}
	
	
	public static int[] heapsort(int[] unsortedValues) {
		int n = unsortedValues.length;
	
		// Construire un tas max
		for (int i = n / 2 - 1; i >= 0; i--) {
			heapsortAux(unsortedValues, n, i);
		}
	
		// Extraire les éléments du tas un par un
		for (int i = n - 1; i > 0; i--) {
			// Déplacer la racine (plus grand élément) à la fin
			int temp = unsortedValues[0];
			unsortedValues[0] = unsortedValues[i];
			unsortedValues[i] = temp;
	
			// Appeler heapsortAux sur le tas réduit
			heapsortAux(unsortedValues, i, 0);
		}
	
		return unsortedValues;
	}
	
	// Fonction auxiliaire pour transformer un sous-arbre en tas max
	private static void heapsortAux(int[] array, int size, int root) {
		int largest = root; // Racine est initialement le plus grand
		int leftChild = 2 * root + 1; // Indice du fils gauche
		int rightChild = 2 * root + 2; // Indice du fils droit
	
		// Vérifier si le fils gauche est plus grand que la racine
		if (leftChild < size && array[leftChild] > array[largest]) {
			largest = leftChild;
		}
	
		// Vérifier si le fils droit est plus grand que le plus grand actuel
		if (rightChild < size && array[rightChild] > array[largest]) {
			largest = rightChild;
		}
	
		// Si le plus grand n'est pas la racine
		if (largest != root) {
			// Échanger les éléments
			int swap = array[root];
			array[root] = array[largest];
			array[largest] = swap;
	
			// Appliquer récursivement heapsortAux sur le sous-arbre affecté
			heapsortAux(array, size, largest);
		}
	}
	
	
	
	
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//System.out.println("CONSTRUCTION");
		int[] treeValues = {109, 107, 111, 112, 104, 110, 101, 106, 102, 108, 105};
		for(int i = 0 ; i<treeValues.length ; i++){
			System.out.print(treeValues[i] + " |");
		}
		System.out.println();
		heapsort(treeValues);
		for(int i = 0 ; i<treeValues.length ; i++){
			System.out.print(treeValues[i] + " |");
		}
		System.out.println();
		HeapTree myTree = new HeapTree(treeValues);
		
		System.out.println("test add value");
		myTree.addValue(103);
		System.out.println(myTree.toString());


		System.out.println("test sort");
		HeapTree heapSort = new HeapTree(heapsort(treeValues));
		System.out.println(heapSort);

		System.out.println("test extractMax");
		System.out.println("\n");
		myTree.extractMax();
		System.out.println(myTree.toString());
		

		
	}

}
