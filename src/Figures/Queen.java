package Figures;
import java.io.File;
import java.io.FileInputStream;

import Game.Board;
import Game.Field;
import Game.Figure;
import javafx.scene.image.Image;

public class Queen implements Figure{
	private int col;
	private int row;
	private boolean isWhite;
	private int type;
	private Image icon;
	/**
	 * Constructor for Queen, loads image from file
	 * @param col column of current position 
	 * @param row row of current position
	 * @param isWhite color
	 * @return void
	 */
	public Queen(int col, int row, boolean isWhite)
	{
		this.col =col;
		this.row =row;
		this.isWhite = isWhite;
		this.type = 5;
		try{this.icon = new Image(new FileInputStream("lib"+File.separator+this.isWhite+File.separator+"Queen.png"));
		}
		catch(Exception e) {
		};
	}
	/**
	 * Method for checking if the piece can move on a certain field
	 * 
	 * @param field field that the piece is moving on
	 * @param board board of the game
	 * @return true if it can move there, false if not
	 */
	@Override
	public boolean canmove(Field field, Board board) {
		return (new Rook(this.col,this.row,isWhite).canmove(field, board) || new Bishop(this.col,this.row,isWhite).canmove(field, board));
	}
	/**
	 * getter for column
	 * @return value of column
	 */
	@Override
	public int getCol() {
		return this.col;
	}
	/**
	 * getter for row
	 * @return value of row
	 */
	@Override
	public int getRow() {
		return this.row;
	}
	/**
	 * getter for color of the piece
	 * @return value of color
	 */
	@Override
	public boolean getColor() {
		return this.isWhite;
	}
	/**
	 * getter for type
	 * @return number representation of the piece, 1 = pawn, 2 = rook, 3 = knight, 4 = bishop, 5 = queen, 6 = king 
	 */
	@Override
	public int getType() {
		return this.type;
	}
	/**
	 * updates the row and column of the piece
	 * @param field the field that the piece is on, the piece gets the column and row from it
	 */
	@Override
	public void updateRC(Field field) {
		this.row = field.getRow();
		this.col = field.getCol();
	}
	/**
	 * getter for image of the piece
	 * @return image of the piece
	 */
	@Override
	public Image getIcon() {
		return this.icon;
	}
}
