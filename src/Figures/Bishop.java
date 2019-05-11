/*Authors: Daniel Bily(xbilyd01), Jakub Gajdosik(xgajdo24)
 * 
 * Contains the class for bishop, all his methods and image
 * 
 */

package Figures;
import java.io.File;
import java.io.FileInputStream;

import Game.Board;
import Game.Field;
import Game.Figure;
import javafx.scene.image.Image;
/**
 * class of bishop
 * 
 */
public class Bishop implements Figure{
	private int col;
	private int row;
	private boolean isWhite;
	private int type;
	private Image icon;
	/**
	 * Constructor for  Bishop, loads image from file
	 * @param col column of current position 
	 * @param row row of current position
	 * @param isWhite color
	 * @return void
	 */
	public Bishop(int col, int row, boolean isWhite)
	{
		this.col =col;
		this.row =row;
		this.isWhite = isWhite;
		this.type = 4;
		try{this.icon = new Image(new FileInputStream("lib"+File.separator+this.isWhite+File.separator+"Bishop.png"));
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
		if(Math.abs(this.col - field.getCol()) != Math.abs(this.row - field.getRow()))
			return false;
		if(!field.isEmpty() && field.getFigure().getColor() == this.isWhite)
			return false;
	
		int dx = 0;
		int dy = 0;
		
		int x = this.col;
		int y = this.row;
		
		if(this.col < field.getCol())
			dx = 1;
		else
			dx = -1;
	
		if(this.row < field.getRow())
			dy = 1;
		else
			dy = -1;
	
		while(x != field.getCol()-dx && y != field.getRow()-dy)
		{
			x += dx;
			y += dy;
			if(x>= 8 || x<=-1 || y>=8 ||y<=-1)
				return false;
			if(!board.getField(x,y).isEmpty())
				return false;
		}
		return true;
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
