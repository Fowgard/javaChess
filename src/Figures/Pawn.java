package Figures;
import java.io.File;
import java.io.FileInputStream;

import Game.Board;
import Game.Field;
import Game.Figure;
import javafx.scene.image.Image;

public class Pawn implements Figure{
	private int col;
	private int row;
	private boolean isWhite;
	private int type;
	private Image icon;
	/**
	 * Constructor for Pawn, loads image from file
	 * @param col column of current position 
	 * @param row row of current position
	 * @param isWhite color
	 * @return void
	 */
	public Pawn(int col, int row, boolean isWhite)
	{
		this.col =col;
		this.row =row;
		this.isWhite = isWhite;
		this.type = 1;
		try
		{
			this.icon = new Image(new FileInputStream("lib"+File.separator+this.isWhite+File.separator+"Pawn.png"));
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
		if(field.getCol() == this.col)
		{
			if(!field.isEmpty())
				return false;
			if(this.isWhite)
			{	
				if((this.row + 1) == field.getRow())
					return true;
				else if (this.row == 1 && (this.row + 2) == field.getRow() && board.getField(this.col, this.row+1).isEmpty())
					return true;
			}
			else
			{
				if((this.row - 1) == field.getRow())
					return true;
				else if (this.row == 6 && (this.row - 2) == field.getRow()&& board.getField(this.col, this.row-1).isEmpty())
					return true;
			}
		}
		else
		{
			if(field.isEmpty() || this.isWhite == field.getFigure().getColor())
				return false;
			if(this.isWhite)
			{	
				if((this.row + 1) == field.getRow() && (this.col + 1) == field.getCol())
					return true;
				else if ((this.row + 1) == field.getRow() && (this.col - 1) == field.getCol())
					return true;
			}
			else
			{
				if((this.row - 1) == field.getRow() && (this.col + 1) == field.getCol())
					return true;
				else if ((this.row - 1) == field.getRow() && (this.col - 1) == field.getCol())
					return true;
			}
		}
		return false;
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
