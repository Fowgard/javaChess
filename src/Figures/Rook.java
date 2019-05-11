package Figures;
import java.io.File;
import java.io.FileInputStream;

import Game.Board;
import Game.Field;
import Game.Figure;
import javafx.scene.image.Image;

public class Rook implements Figure{
	private int col;
	private int row;
	private boolean isWhite;
	private int type;
	private Image icon;
	
	public Rook(int col, int row, boolean isWhite)
	{
		this.col =col;
		this.row =row;
		this.isWhite = isWhite;
		this.type = 2;
		try{this.icon = new Image(new FileInputStream("lib"+File.separator+this.isWhite+File.separator+"Rook.png"));
		}
		catch(Exception e) {
		};
	}
	@Override
	public boolean canmove(Field field, Board board) {
		if(this.col != field.getCol() && this.row != field.getRow())
			return false;
		if(!field.isEmpty() && field.getFigure().getColor() == this.isWhite)
			return false;

		int dx = 0;
		int dy = 0;
		
		int x = this.col;
		int y = this.row;
		
		if(this.col == field.getCol())
		{
			if(this.row < field.getRow())
				dy = 1;
			else
				dy = -1;
			for(int i = 1; i< Math.abs(field.getRow()-this.row);i++)
			{
				y += dy;
				if(!board.getField(x, y).isEmpty())
					return false;
			}
		}
		else
		{
			if(this.col < field.getCol())
				dx = 1;
			else
				dx = -1;
			for(int i = 1; i< Math.abs(field.getCol()-this.col);i++)
			{
				x += dx;
				if(!board.getField(x, y).isEmpty())
					return false;
			}
		}
		return true;
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
	@Override
	public Image getIcon() {
		return this.icon;
	}
}
