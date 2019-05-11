/*Authors: Daniel Bily(xbilyd01), Jakub Gajdosik(xgajdo24)
 * 
 * Contains class for MainGame, contains additional information about the game
 */
package application;

import Game.ChessGame;
import Game.Figure;
/**
 * 
 * class for MainGame, contains additional information about the game
 *
 */
public class MainGame {
	public boolean figurePicked;
	public boolean whitesMove;
	public Figure figureOnMove;
	public ChessGame game;
	public int gameCounter;
	/**
	 * constructor
	 * @param gameCounter id of the game, every game in every tab has its own id
	 * @throws Exception
	 */
	public MainGame(int gameCounter) throws Exception {
		this.game = game;
		figurePicked = false;
		whitesMove = true;
		this.gameCounter = gameCounter;
		this.init();
	}
	/**
	 * Initializes variables
	 * @throws Exception
	 */
	public void init() throws Exception {
		game = new ChessGame();
		this.game.addHistory();
		this.game.setFile(this.gameCounter);

	}
}
