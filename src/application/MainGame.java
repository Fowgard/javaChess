package application;

import Game.ChessGame;
import Game.Figure;

public class MainGame {
	public boolean figurePicked;
	public boolean whitesMove;
	public Figure figureOnMove;
	public ChessGame game;
	public int gameCounter;
	
	public MainGame(int gameCounter) throws Exception {
		this.game = game;
		figurePicked = false;
		whitesMove = true;
		this.gameCounter = gameCounter;
		this.init();
	}
	
	public void init() throws Exception {
		game = new ChessGame();
		this.game.addHistory();
		this.game.setFile(this.gameCounter);

	}
}
