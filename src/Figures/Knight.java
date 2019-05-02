package Figures;
import java.io.FileInputStream;

import Game.Board;
import Game.Field;
import Game.Figure;
import javafx.scene.image.Image;

public class Knight implements Figure{
	private int col;
	private int row;
	private boolean isWhite;
	private int type;
	private Image icon;
	
	public Knight(int col, int row, boolean isWhite)
	{
		this.col =col;
		this.row =row;
		this.isWhite = isWhite;
		this.type = 3;
		try{this.icon = new Image(new FileInputStream("lib/"+this.isWhite+"/Knight.png"));
		}
		catch(Exception e) {
			try{this.icon = new Image(new FileInputStream("lib\\"+this.isWhite+"\\Knight.png"));}catch(Exception ex) {System.out.println("Error");};
		};
	}
	@Override
	public boolean canmove(Field field, Board board) {
		if(!field.isEmpty() && field.getFigure().getColor() == this.isWhite)
			return false;

		if((this.row + 2) == field.getRow() && (this.col - 1) == field.getCol())
			return true;
		else if ((this.row + 2) == field.getRow() && (this.col + 1) == field.getCol())
			return true;
		else if ((this.row - 2) == field.getRow() && (this.col + 1) == field.getCol())
			return true;
		else if ((this.row - 2) == field.getRow() && (this.col - 1) == field.getCol())
			return true;
		else if ((this.row + 1) == field.getRow() && (this.col - 2) == field.getCol())
			return true;
		else if ((this.row + 1) == field.getRow() && (this.col + 2) == field.getCol())
			return true;
		else if ((this.row - 1) == field.getRow() && (this.col - 2) == field.getCol())
			return true;
		else if ((this.row - 1) == field.getRow() && (this.col + 2) == field.getCol())
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
	@Override
	public Image getIcon() {
		return this.icon;
	}
}
