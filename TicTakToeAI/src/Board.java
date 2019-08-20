import java.util.ArrayList;

public class Board<E> {
	private E[][] board;
	private int rows;
	private int columns;
	
	public Board(int rows, int columns){
		this.rows = rows;
		this.columns = columns;
		//Since all classes inherit Object, we can create a object matrix then cast it (or change type) to type E
		board = (E[][]) new Object[rows][columns];
		//Set all of the data points to null at the beginning
		for(int r = 0; r < board.length; r++) {
			for(int c = 0; c < board[r].length; c++) {
				board[r][c] = null;
			}
		}
	}
	
	public Board(Board newBoard) {
		this.rows = newBoard.getRows();
		this.columns = newBoard.getColumns();
		this.board = (E[][]) new Object[rows][columns];
		for(int r = 0; r < board.length; r++) {
			for(int c = 0; c < board[r].length; c++) {
				board[r][c] = (E)newBoard.getBoard()[r][c];
			}
		}
		
	}
	
	public int getRows() {
		return rows;
	}
	public int getColumns() {
		return columns;
	}
	public E[][] getBoard(){
		return board;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public void setColumns(int columns) {
		this.columns = columns;
	}
	public void setBoard(E[][] board) {
		this.board = board;
	}
	
	private void printHorizontalLine(int col) {
		for(int i = 1; i<col;i++) {
			System.out.print("--");
			if(i+1 == col) {
				System.out.print("-");
			}
		}
		System.out.println();
	}
	
	public void printTicTacToeBoard() {
		for(int r = 0; r < rows;r+=1) {
			for(int c = 0; c < columns; c+=1) {
				if(c == columns - 1) {
					if(board[r][c] != null) {
						System.out.println(board[r][c]);
					}else{
						System.out.println(" ");
					}
					
				}else {
					if(board[r][c] != null) {
						System.out.print(board[r][c] + "|");
					}else {
						System.out.print(" |");
					}
					
				}
			}
			if(r != rows - 1) {
				printHorizontalLine(columns);
				
			}
		}
	}
	public void printBoard() {
		printHorizontalLine(columns+1);
		for(int r = 0; r < rows;r++) {
			System.out.print("|");
			for(int c = 0; c < columns; c++) {
				if(board[r][c] != null) {
					System.out.print(board[r][c] + "|");
				}else {
					System.out.print(" |");
				}
				
			}
			System.out.println();
			printHorizontalLine(columns+1);
		}
	}
	
	public void setPiece(int r, int c, E piece) {
		if(board[r][c] == null) {
			board[r][c] = piece;
		}else {
			
		}
	}
	
	public boolean placeFree(int r, int c) {
		if(board[r][c] == null) {
			return true;
		}else {
			return false;
		}
	}
	
	//Methods to calculate win conditions
	//Check row if there is a win condition or it is blocked
	public int checkRow(Player player, int row) {
		if(board[row][0] != null && (char)board[row][0] == player.getYourPiece() && board[row][0] == board[row][1] && board[row][1] == board[row][2]) {
			return 1;
		}
		int countPlayer = 0;
		int countOpponent = 0;
		for(int i = 0; i < board[row].length; i++) {
			if(board[row][i] != null && (char)board[row][i] == player.getYourPiece()) {
				countPlayer++;
			}else if(board[row][i] != null && (char)board[row][i] == player.getEnemyPiece()) {
				countOpponent++;
			}
		}
		//Check if we block an opponent's row
		if(countOpponent == 2 && countPlayer == 1) {
			return -1;
		}else {
			return 0;
		}
	}
	
	//Check column if there is win condition or it is blocked
	public int checkColumn(Player player, int column) {
		if(board[0][column] != null && (char)board[0][column] == player.getYourPiece() && board[0][column] == board[1][column] && board[1][column] == board[2][column]) {
			return 1;
		}
		int countPlayer = 0;
		int countOpponent = 0;
		for(int i = 0; i < board.length; i++) {
			if(board[i][column] != null && (char)board[i][column] == player.getYourPiece()) {
				countPlayer++;
			}else if(board[i][column] != null && (char)board[i][column] == player.getEnemyPiece()) {
				countOpponent++;
			}
		}
		//Check if we block an opponent's row
		if(countOpponent == 2 && countPlayer == 1) {
			return -1;
		}else {
			return 0;
		}
	}
	
	//Check diagonal if there is win condition or it is blocked
	public int checkDiagonal(Player player, int row, int col) {
		if((row == 0 && col == 1) || (row == 1 && col == 0) || (row == 1 && col == 2) || (row == 2 && col == 1)) {
			return 0;
		}else if(board[0][0] != null && (char)board[0][0] == player.getYourPiece() && board[0][0] == board[1][1] && board[2][2] == board[1][1]) {
			return 1;
		}else if(board[0][2] != null && (char)board[0][2] == player.getYourPiece() && board[0][2] == board[1][1] && board[2][0] == board[1][1]) {
			return 1;
		}
		int countPlayer = 0;
		int countOpponent = 0;
		if(row == col) {
			for(int i = 0; i < board.length;i++) {
				if(board[i][i] != null && (char)board[i][i] == player.getYourPiece()) {
					countPlayer++;
				}else if(board[i][i] != null && (char)board[i][i] == player.getEnemyPiece()) {
					countOpponent++;
				}
			}
			if(countOpponent == 2 && countPlayer == 1) {
				return -1;
			}
		}
		countPlayer = 0;
		countOpponent = 0;
		if(2-row == col) {
			for(int i = 0; i < board.length;i++) {
				if(board[i][2-i] != null && (char)board[i][2-i] == player.getYourPiece()) {
					countPlayer++;
				}else if(board[i][2-i] != null && (char)board[i][2-i] == player.getEnemyPiece()) {
					countOpponent++;
				}
			}
			if(countOpponent == 2 && countPlayer == 1) {
				return -1;
			}
		}
		return 0;
	}
	//Check the current position if it is a win, block, or nothing, computer uses
	public int check(Player player, int row, int column) {
		if(checkRow(player, row) == 1 || checkColumn(player,column) == 1 || checkDiagonal(player,row,column) == 1) {
			return 1;
		}else if(checkRow(player, row) == -1 || checkColumn(player,column) == -1  || checkDiagonal(player, row, column) == -1) {
			return -1;
		}
		return 0;
	}
	
	//Check if it is a draw
	public boolean isDraw() {
		for(int r = 0; r < board.length; r ++) {
			for(int c = 0; c < board[r].length; c++) {
				if(board[r][c] == null) {
					return false;
				}
			}
		}
		return true;
	}
	
	//Check how many empty spaces
	public int emtpySpaces() {
		int counter = 0;
		for(int r = 0; r < rows; r ++) {
			for(int c = 0;c < columns;c++) {
				if(board[r][c] == null) {
					counter++;
				}
			}
		}
		return counter;
	}
	
	//Return the coordinates of each of the empty spaces
	public ArrayList getCoordinates() {
		//ArrayList of coordinates that are going to be returned together
		ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
		for(int r = 0; r < rows; r ++) {
			for(int c = 0; c < columns; c++) {
				if(board[r][c] == null) {
					Coordinate coordinate = new Coordinate(r,c);
					coordinates.add(coordinate);
					
				}
			}
		}
		return coordinates;
	}
	
}
