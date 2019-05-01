package Figures;
import Game.Board;
import Game.Field;
import Game.Figure;

public class Queen implements Figure{
	private int col;
	private int row;
	private boolean isWhite;
	private int type;
	public Queen(int col, int row, boolean isWhite)
	{
		this.col =col;
		this.row =row;
		this.isWhite = isWhite;
		this.type = 5;
	}
	@Override
	public boolean canmove(Field field, Board board) {
		return (new Rook(this.col,this.row,isWhite).canmove(field, board) || new Bishop(this.col,this.row,isWhite).canmove(field, board));
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
