package Game;

public class Main{
	public static void main(String[]args)
	{
		ChessGame game = new ChessGame();
		
		Figure figure = game.board.getField(0, 1).getFigure();
		Field field = game.board.getField(0, 2);
		game.move(figure, field);
		System.out.println("fagg");
	}
}
