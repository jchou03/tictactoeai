
public class Player {
	// First things always: data, constructor, getters and setters
	private char yourPiece;
	private char enemyPiece;
	
	public Player(char yourPiece,char enemyPiece,String name) {
		this.yourPiece = yourPiece;
		this.enemyPiece = enemyPiece;
	}
	
	public char getYourPiece() {
		return yourPiece;
	}
	public char getEnemyPiece() {
		return enemyPiece;
	}

	public void setYourPiece(char yourPiece) {
		this.yourPiece = yourPiece;
	}
	public void setEnemyPiece(char enemyPiece) {
		this.enemyPiece = enemyPiece;
	}

	
}
