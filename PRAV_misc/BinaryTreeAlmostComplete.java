/**
 * 
 */
package fr.istic.prg3;

import java.util.Objects;



/**
 * @version 1.0
 *
 */
public class BinaryTreeAlmostComplete {
	
	protected int rootValue;
	protected BinaryTreeAlmostComplete left;
	protected BinaryTreeAlmostComplete right;
	protected BinaryTreeAlmostComplete up;
	protected int nbDescendants;
	
	
	public BinaryTreeAlmostComplete(int value) {
		this(value, null);
	}
	
	
	public BinaryTreeAlmostComplete(int[] values) {
		this(values[0],null);
		for(int i=1; i<values.length; i++){
			this.addValue(values[i]);
		}
	}
	
	
	public BinaryTreeAlmostComplete(int value, BinaryTreeAlmostComplete parent) {
		this.rootValue = value;
		this.left = null;
		this.right = null;
		this.up = parent;
		this.updateNumberOfDescendants();
	}
	
	
	
	
	
	
	public void addValue(int value) {
		if (Objects.isNull(this.left)) {
			this.left = new BinaryTreeAlmostComplete(value, this);
			this.updateNumberOfDescendants();
		}
		else {
			if (Objects.isNull(this.right)) {
				this.right = new BinaryTreeAlmostComplete(value, this);
				this.updateNumberOfDescendants();
			}
			else {
				// both left and right exist
				int nbDescLeft = this.left.nbDescendants;
				if (getLevels(nbDescLeft) == getLevels(nbDescLeft + 1)) {
					// the lowest level of left child is not full
					this.left.addValue(value);
				}
				else {
					// the lowest level of left child is full
					int nbDescRight = this.right.nbDescendants;
					if (nbDescLeft > nbDescRight) {
						// the lowest level of left child is full, AND the lowest level of right child is not full
						this.right.addValue(value);
					}
					else {
						// both left and right child are full and have the same level
						this.left.addValue(value);
					}
				}
			}
		}
	}
	
	
	
	protected static int getLevels(int n) {
		return (int)(Math.log(n + 1) / Math.log(2));
	}
	
	
	protected BinaryTreeAlmostComplete getRightmostLowestNode() {
		return getRightmostLowestNodeAux(this, this.nbDescendants);
		
		
	}
	
	private BinaryTreeAlmostComplete getRightmostLowestNodeAux(BinaryTreeAlmostComplete bt, int nbDescendants){
		if(bt.nbDescendants==0){
			return bt;		//cas où bt est une feuille  
		}
		
		if(bt.right==null ) return getRightmostLowestNodeAux(bt.left, nbDescendants-1);
		else if (bt.left==null ) return getRightmostLowestNodeAux(bt.right, nbDescendants-1);

		
		if((getLevels(bt.right.nbDescendants)>=getLevels(bt.left.nbDescendants))){
			return getRightmostLowestNodeAux(bt.right, nbDescendants - bt.left.nbDescendants - 2 );
		}else{
			return getRightmostLowestNodeAux(bt.left, nbDescendants - bt.right.nbDescendants - 2 );
		}
	}

	
	
	
	public void siftDown() {
		if( (this.left == null && this.right == null) || (this.rootValue > this.left.rootValue && this.rootValue > this.right.rootValue) ){
			return ; 				//on s'arrête si jamais t
		}
		
		//cas 1 : le fils droit est seul et est + grand que le root courant
		if(this.left == null && this.right.rootValue > this.rootValue){
			int tmp = this.right.rootValue;
			this.right.rootValue = this.rootValue;
			this.rootValue = tmp;
			this.right.siftDown(); 
		}

		//cas 2 : le fils gauche est seul et est + grand que le root courant
		if(this.right == null && this.left.rootValue > this.rootValue){
			int tmp = this.left.rootValue;
			this.left.rootValue = this.rootValue;
			this.rootValue = tmp;
			this.left.siftDown(); 
		}

		//cas 3 : el ultimo, si y'a deux fils de valeur supérieure, on prend celui à la plus grande valeur 
		if(this.left.rootValue > this.right.rootValue){				//fils gauche + grand
			int tmp = this.left.rootValue;
			this.left.rootValue = this.rootValue;
			this.rootValue = tmp;
			this.left.siftDown();
		}
		else {														//fils droite + grand
			int tmp = this.right.rootValue;
			this.right.rootValue = this.rootValue;
			this.rootValue = tmp;
			this.right.siftDown();
		}

	}
	
	
	public void siftUp() {
		if(this.up == null || this.up.rootValue > this.rootValue){
			return ; 				//on s'arrête
		}
		int tmp = this.up.rootValue;
		this.up.rootValue = this.rootValue;
		this.rootValue = tmp;
		this.up.siftUp(); 
	}	

	
	
	
	
	public String toString() {
		return this.toString("");
	}
	
	
	public String toString(String offset) {
		StringBuilder sb = new StringBuilder();
		sb.append(offset).append(rootValue).append(" (").append(nbDescendants).append(" descendants)\n");
		if (left != null) {
			sb.append(left.toString(offset + "  "));
		}
		if (right != null) {
			sb.append(right.toString(offset + "  "));

		}
		return sb.toString();
	}
	
	
	
	protected void updateNumberOfDescendants() {
		this.nbDescendants = 0;
		if (Objects.nonNull(this.left)) {
			this.nbDescendants += 1 + this.left.nbDescendants;
		}
		if (Objects.nonNull(this.right)) {
			this.nbDescendants += 1 + this.right.nbDescendants;
		}
		if (Objects.nonNull(this.up)) {
			up.updateNumberOfDescendants();
		}
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
			int[] tab = {107,111,112,103,104,110,101,106,102,108,105};
			BinaryTreeAlmostComplete btc2 = new BinaryTreeAlmostComplete(tab);
			
			System.out.println(btc2.getRightmostLowestNode());
			btc2.siftDown();
			System.out.println(btc2.getRightmostLowestNode());
			btc2.addValue(100);
			System.out.println(btc2.toString());
			System.out.println();
		}

		
	}


