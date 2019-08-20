
public class Tree<E> {
	private Node<E> root;
	// depth
	// The number of nodes in the second layer that represent the amount of starting moves on a blank board
	private int firstMoves;
	
	//Constructor for creating the first node
	public Tree (int firstMoves) {
		root = null;
		this.firstMoves = firstMoves;
	}
	//Constructor for creating a new tree based on node 
	public Tree(Node<E> node, int firstMoves) {
		root = node;
		//Firstmoves is going to be taken in after counting the number of empty spaces on the board
		this.firstMoves = firstMoves;
	}
	
	
	public Node<E> getRoot(){
		return root;
	}
	public int getFirstMoves() {
		return firstMoves;
	}
	public void setRoot(Node<E> root) {
		this.root = root;
	}
	public void setFirstMoves(int firstMoves) {
		this.firstMoves = firstMoves;
	}
	
	//adds ONE node to the tree, uses recursion to find spot to add node
	public void add(Node<E> node,Node<E> n,int index, int level) {
		//data is thing put in node, n is the node creating from, and index is the placement of the node in the current level
		if(root == null) {
			node.setParent(root);
			this.root = node;
		}else {
			Node<E> temp = n;
			// if it is the first node in the level and the parent is first node on the level above
			if(temp.getPointers().isEmpty() && index == 0) {
				temp.getPointers().add(node);
				node.setParent(temp);
			}
			//Creating node at the same level, but not the first one
			else if(temp.getPointers().size() <= (firstMoves - level + 1)){
				temp.getPointers().add(node);
				node.setParent(temp);
			}
		}
	}
	
}
