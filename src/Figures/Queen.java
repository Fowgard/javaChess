package Figures;
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
	
	public Queen(int col, int row, boolean isWhite)
	{
		this.col =col;
		this.row =row;
		this.isWhite = isWhite;
		this.type = 5;
		try{this.icon = new Image(new FileInputStream("lib/"+this.isWhite+"/Queen.png"));
		}
		catch(Exception e) {
			try{this.icon = new Image(new FileInputStream("lib\\"+this.isWhite+"\\Queen.png"));}catch(Exception ex) {System.out.println("Error");};
		};
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
	@Override
	public Image getIcon() {
		return this.icon;
	}
}
