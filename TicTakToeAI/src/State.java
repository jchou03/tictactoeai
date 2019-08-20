
//data that is passed into the tree
public class State {
	private Board board;
	private Player turn;
	//set to values of 1, -1, or 0 based on who wins or if it is a draw
	private int priority;
	//number of computer losses that are below this node
	private int losts;
	private int row;
	private int column;
	private int level;
	private Node<State> node;
	public State(Board board, Player turn, int row, int column, int level) {
		this.board = new Board(board);
		this.turn = turn;
		this.row = row;
		this.column = column;
		priority = 0;
		losts = 0;
		this.level = level;
		node = null;
	}
	
	//Constructor for first empty board (the one with no moves on it)
	public State(Board board) {
		this.board = new Board(board);
		turn = null;
		row = 0;
		column = 0;
		priority = 0;
		losts = 0;
		level = 0;
		node = null;
	}
	
	public Board getBoard() {
		return board;
	}
	public Player getTurn() {
		return turn;
	}
	public int getPriority() {
		return priority;
	}
	public int getLosts() {
		return losts;
	}
	public int getRow() {
		return row;
	}
	public int getColumn() {
		return column;
	}
	public int getLevel() {
		return level;
	}
	public Node<State> getNode(){
		return node;
	}
	public void setBoard(Board board) {
		this.board = board;
	}
	public void setTurn(Player turn) {
		this.turn = turn;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	//Overloading setPriority to run calculations
	public void setPriority() {
		priority = board.check(turn, row, column);
		if(turn.getYourPiece() == 'X') {
			priority = priority * -1;
		}
	}
	public void setLosts(int losts) {
		this.losts = losts;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public void setNode(Node<State> node) {
		this.node = node;
	}
	
}
