package Figures;
import Game.Board;
import Game.Field;
import Game.Figure;

public class King implements Figure{
	private int col;
	private int row;
	private boolean isWhite;
	private int type;
	public King(int col, int row, boolean isWhite)
	{
		this.col =col;
		this.row =row;
		this.isWhite = isWhite;
		this.type = 6;
	}
	@Override
	public boolean canmove(Field field, Board board) {
		if(!field.isEmpty() && field.getFigure().getColor() == this.isWhite)
			return false;

		if((this.row + 1) == field.getRow() && (this.col) == field.getCol())
			return true;
		else if ((this.row + 1) == field.getRow() && (this.col - 1) == field.getCol())
			return true;
		else if ((this.row + 1) == field.getRow() && (this.col + 1) == field.getCol())
			return true;
		else if ((this.row) == field.getRow() && (this.col - 1) == field.getCol())
			return true;
		else if ((this.row) == field.getRow() && (this.col + 1) == field.getCol())
			return true;
		else if ((this.row - 1) == field.getRow() && (this.col) == field.getCol())
			return true;
		else if ((this.row - 1) == field.getRow() && (this.col - 1) == field.getCol())
			return true;
		else if ((this.row - 1) == field.getRow() && (this.col + 1) == field.getCol())
			return true;
		else 
			return false;
	
	}

	@Override
	public int getCol() {
		return this.col;
	}

	@Override
	public int getRow() {
		return this.row;
	}

	@Override
	public boolean getColor() {
		return this.isWhite;
	}

	@Override
	public int getType() {
		return this.type;
	}
	
	@Override
	public void updateRC(Field field) {
		this.row = field.getRow();
		this.col = field.getCol();
	}
}
