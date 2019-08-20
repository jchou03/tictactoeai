import java.util.Scanner;
import java.awt.Graphics;

public class Main {

	public static void main(String[] args) {
		Board<Character> board = new GraphicsBoard<Character>(3,3,"banana");		
//		draw the starting board, uses two because when just using one, 
//		it doesn't draw properly and has gaps in the lines, but with two paints, it overlaps to cover it
		((GraphicsBoard) board).paint(((GraphicsBoard) board).getFrame().getGraphics());
		((GraphicsBoard) board).paint(((GraphicsBoard) board).getFrame().getGraphics());

	}

}

