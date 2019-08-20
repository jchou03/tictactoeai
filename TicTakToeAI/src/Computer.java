import java.util.ArrayList;

// Hey, to make something a child of another class use EXTENDS then name of the class
public class Computer extends Player{
	
	private Tree<State> tree;
	private Player opponent;
	
	public Computer(char yourPiece,char enemyPiece, String name, Player opponent) {
		super(yourPiece,enemyPiece,name);
		tree = new Tree<State>(9);
		this.opponent = opponent;
	}
	
	public Tree<State> getTree(){
		return tree;
	}
	public Player getOpponent() {
		return opponent;
	}
	public void setOpponent(Player opponent) {
		this.opponent = opponent;
	}
	//start calling after player moves so no empty board
	//Build out the entire board
	public Node<State> createPossibilities(Board board, Node<State> root, Player player, int level) {
		//Build the 5 levels
		if(level < 5) {
			//Loop through setting the coordinates of the empty spaces to the state and putting the states into nodes into the tree
			ArrayList<Coordinate> coordinates = board.getCoordinates();
			
			//This for each loop adds each empty states to the second level in the tree (first level is the player's first move)
			for(Coordinate coordinate: coordinates) {
				State state = new State(board, player, coordinate.getRow(), coordinate.getColumn(),level+1);
				Node<State> n = null;
				//Add the new piece to the board that we are storing in the tree
				state.getBoard().setPiece(coordinate.getRow(), coordinate.getColumn(), player.getYourPiece());
				state.setPriority();
				Node<State> node = new Node<State>(state);
				
				tree.add(node, root, 0, level);
				
				//Uses recursion to create the next nodes all the way down to level 5 and switch 
				if(player == this) {
					n = createPossibilities(state.getBoard(),node,opponent, level + 1);
				}else if(player == opponent) {
					n = createPossibilities(state.getBoard(),node,this, level + 1);
					//actually can just be an else since the player is either going to be the human player or the computer, just for readability
				}	
			}
		}
		return root.getParent();
	}

	public void createTree(Board board) {
		State rootState = new State(board);
		Node<State> root = new Node(rootState);
		
		//Build an empty tree with the number of nodes branching off of it based on the number of empty spaces
		tree = new Tree<State>(root,board.emtpySpaces());
		
		//create the tree as well as set all of the loss values
		createPossibilities(board,root,this,0);
//		findLosts(root);
	}
	
	//check if this node is the root and if it is then search root pointers for best move
	public Node<State> checkRoot(Node<State> node) {
		Node<State> best = null;
		if(node == tree.getRoot()) {
//			System.out.println("Entered into the root search");
			for(Node<State> n : node.getPointers()) {
//				System.out.println("Priority:" + n.getData().getPriority());
//				System.out.println("Level: " + n.getData().getLevel());
//				n.getData().getBoard().printTicTacToeBoard();
				if(shouldReplace(best,n) && n.getData().getPriority() != 0) {
					best = n;
				}
			}
		}
		return best;
	}
	
	public Node<State> searchPossibilities(Node<State> node){
		Node<State> bestNode = null;
		
		if(node.getData().getBoard().placeFree(1, 1)) {
			Board<Character> board = new Board(node.getData().getBoard());
			board.setPiece(1, 1, getYourPiece());
//			State state = new State (board, this, 1, 1, 0); 
//			a new state can be created in either of the two ways, outside or directly in it
			return new Node<State>(new State(board, this, 1, 1, 0));
		}
	
		Node<State> best = checkRoot(node);
		if(best != null) {
			return best;
		}
		
		// check if the priority is 0 then use the recursion
		if (node.getData().getPriority() == 0) {
			for(Node<State> n: node.getPointers()) {
				bestNode = searchPossibilities(n);
			}
		}
		
		// don't make this an else if because if the if statements up there fail then it executes the rest of the code
		// If the node that is put in is a win, then return the current one (the base case for recursion to start returning)
		if(node.getData().getPriority() == 1) {
			// Set the target node in the parent to itself if it is a win
			if(shouldReplace(node.getParent(),node)) {
//				node.getData().getPriority();
				node.getParent().getData().setNode(node);
				node.getParent().getData().setLevel(node.getData().getLevel());
				node.getParent().getData().setPriority(node.getData().getPriority());
			}
			if(node == tree.getRoot()) return checkRoot(node);
//			checkRoot(node) and node.getData().getNode() is the same because the best node of the root is already set beforehand
			return node.getParent();
		}
		// if the node is a block then check shouldReplace()
		else if(node.getData().getPriority() == -1) {
			
			if(shouldReplace(node.getParent(),node)) {
				node.getParent().getData().setNode(node);
				node.getParent().getData().setLevel(node.getData().getLevel());
				node.getParent().getData().setPriority(node.getData().getPriority());
			}
			if(node == tree.getRoot()) return checkRoot(node);
//			checkRoot(node) and node.getData().getNode() is the same because the best node of the root is already set beforehand
			return node.getParent();
		}
		// if this is the end of the tree
		else if(node.getPointers().size() == 0) {
			if(shouldReplace(node.getParent(),node)) {
				node.getParent().getData().setNode(node);
				node.getParent().getData().setLevel(node.getData().getLevel());
				node.getParent().getData().setPriority(node.getData().getPriority());
			}
			return node.getParent();
		}
//		if priority is 0, set best to the original node
		node.getData().setNode(bestNode);
		best = checkRoot(node);
		if (best != null) {
			return best;
		}else {
			return node;
		}
	}
	
	// find the number of losses that are below this node
	private int findLosts(Node<State> node) {
		int losts = node.getData().getLosts();
		// for each loop to search for losses
		for(Node<State> n: node.getPointers()) {
			if(n.getData().getPriority() == -1) {
				losts++;
			}
			losts += findLosts(n) + n.getData().getLosts();
		}
		node.getData().setLosts(losts);
		return losts;
	}
	
//	Check if n1 should be replaced by n2 in the searchPossibilities
	private boolean shouldReplace(Node<State> n1, Node<State> n2) {
		/*
		 * n1 = previous best
		 * n2 = possible new best
		 * check for null
		 * check if n2.bestLevel is < n1.bestLevel
		 * check n2.priority is better than n1.priority
		 */
//		System.out.println(n1 == tree.getRoot());
		
		if(n2 != tree.getRoot() && n1 == null) {
			return true;
		}else if(n1 == null) {
			return false;
		}else if(n1.getData().getLevel() == 1 && n2.getData().getLevel() == 1) {
//			System.out.println("n1 priority: " + n1.getData().getPriority());
//			System.out.println("n2 priority: " + n2.getData().getPriority());
			if(n1.getData().getPriority() == 0 || (n1.getData().getPriority() == -1 && n2.getData().getPriority() == 1)) {
				return true;
			}else if(n1.getData().getPriority() == -1 || n1.getData().getPriority() == 1) {
				return false;
			}else if(n1.getData().getLosts() >= n2.getData().getLosts()) {
				return true;
			}
			return false;
		}
		
		if(n1.getData().getNode() == null || n1.getData().getNode().getData().getPriority() == 0) {
			return true;
		}
		//if the previous one is lower than the new one
		else if(n1.getData().getLevel() > n2.getData().getLevel()) {
			if(n2.getData().getPriority() != 0) {
				return true;
			}
		}
		//Check if the two best levels are equal to each other then check priority
		else if(n1.getData().getLevel() == n2.getData().getLevel()) {
			if(n1.getData().getPriority() == -1 && n2.getData().getPriority() == 1) {
				return true;
			}else if(n1.getData().getLosts() >= n2.getData().getLosts()) {
				return true;
			}
		}
		//If not then return false
		return false;
	}
}


