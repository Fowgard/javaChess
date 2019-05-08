package application;

import Game.ChessGame;
import Game.Figure;

public class MainGame {
	public boolean figurePicked;
	public boolean whitesMove;
	public Figure figureOnMove;
	public ChessGame game;
	
	public MainGame() {
		this.game = game;
		figurePicked = false;
		whitesMove = true;
		this.init();
	}
	
	public void init() {
		game = new ChessGame();
		this.game.addHistory();

	}
}
