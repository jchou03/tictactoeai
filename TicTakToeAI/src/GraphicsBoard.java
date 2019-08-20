import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GraphicsBoard<E> extends Board implements MouseListener{
	
	private JFrame frame;
	private final int height = 600;
	private final int width = 600;
	private final Player player = new Player('X','O',"Player");
	private final Player computer = new Computer('O','X',"Computer",player);	private boolean run;
	
	// creating a graphics board from a pre-existing board
	public GraphicsBoard(Board board, String title) {
		super(board);
		frame = new JFrame(title);
		frame.setSize(600, 600);
		frame.setVisible(true);
		frame.addMouseListener(this);
		run = true;
	}

	// creating an empty graphics board 
	public GraphicsBoard(int rows, int columns, String title) {
		super(rows, columns);
		frame = new JFrame(title);
		frame.setSize(600, 600);
		frame.setVisible(true);
		frame.addMouseListener(this);
		run = true;
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	public void setJFrame(JFrame frame) {
		this.frame = frame;
	}
	
	public boolean getRun() {
		return run;
	}
	
	public void setRun(boolean run) {
		this.run = run;
	}
	
	// draw the board through graphics
	public void drawBoard(Graphics g) {
		// draw the vertical lines
		for(int i = 1; i <= 2; i++) {
			int x = 200 * i;
			g.drawLine(x, 0, x, 600);
		}
		// draw the horizontal lines
		for(int i = 1; i <= 2; i++) {
			int y = 200 * i;
			g.drawLine(0, y, 600, y);
		}
		/*
		 * for(int i = 1; i < 3; i++){
		 * 	g.drawLine(0, 200 * i, width, 200 * i);
		 *  g.drawLine(200 * i, 0, 200 * i, height);
		 * }
		 */
	}
	
	// draw the x symbols
	private void drawX(Graphics g, int row, int column) {
		int startX = column * 200 + 25;
		int startY = row * 200 + 25;
		g.drawLine(startX, startY, startX + 150, startY + 150);
		g.drawLine(startX, startY + 150, startX + 150, startY);
	}
	
	// draw the o symbols
	private void drawO(Graphics g, int row, int column) {
		int startX = column * 200 + 25;
		int startY = row * 200 + 25;
		g.drawOval(startX, startY, 150, 150);
	}
	
	// paint to draw just an empty board
	public void paint(Graphics g) {
		drawBoard(g);
	}
	
	// call all three draw methods to draw the board, 
	// since graphics doesn't clear the board each time, we just have to draw the newly placed piece that goes onto the board
	public void paint(Graphics g, int row, int column, Player p) {
		drawBoard(g);
		if(p != null) {
			if(p.getYourPiece() == 'X') {
				drawX(g, row, column);
			}else if(p.getYourPiece() == 'O') {
				drawO(g, row, column);
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(run) {
			boolean cont = false;
			
			int col = e.getX()/200;
			int row = e.getY()/200;
			
	//		console.log("row: " + row + "col: " + col);
			
			if(placeFree(row, col)) {
				setPiece(row, col, player.getYourPiece());
				paint(frame.getGraphics(), row, col, player);
				cont = true;
			}else {
				System.out.println("That place is taken, please try again");
			}
//			System.out.println("Your move:");
//			printTicTacToeBoard();
			
			if(check(player, row, col) == 1) {
				run = false;
				cont = false;
				System.out.println("You won");
			}else if(isDraw()) {
				run = false;
				System.out.println("The game is a draw");
				cont = false;
			}
			
			if(cont){
				//Computer's move
				//computer rebuilds the tree after every move
				((Computer)computer).createTree(this);
				Node<State> bestMove = ((Computer)computer).searchPossibilities(((Computer)computer).getTree().getRoot());
				setPiece(bestMove.getData().getRow(), bestMove.getData( ).getColumn(), computer.getYourPiece());
				row = bestMove.getData().getRow();
				col = bestMove.getData().getColumn();
//				System.out.println("Computer's move:");
//				printTicTacToeBoard();
				paint(getFrame().getGraphics(), row, col, computer);
				if(check(computer, row, col) == 1) {
					run = false;
					System.out.println("The computer won");
				}else if(isDraw()) {
					run = false;
					System.out.println("The game is a draw");
				}
			}
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}

// continue working on drawing the board using graphics