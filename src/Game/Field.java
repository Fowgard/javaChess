package Game;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class Field extends Button{
	int col;
	int row;
	private Figure figure;
	
	public Field(int col, int row)
	{
		super();
		this.col = col;
		this.row = row;
	}
	
	public boolean isEmpty() {
		return figure == null; 
		
	}
	public void setFigure(Figure figure)
	{
		this.figure = figure;
		
		 if (this.figure != null)
			 this.setGraphic( new ImageView ( figure.getIcon() ) );
	     else
	         this.setGraphic( new ImageView() );
	}
	
	public Figure getFigure() {
		return this.figure;
	}

	
	public int getRow() {
		return this.row;
	}

	
	public int getCol() {
		return this.col;
	}

	
	public void removeFigure() {
		this.figure = null;
		
	}
	
	public void updateImg() {
		 if (this.figure != null)
			 this.setGraphic( new ImageView ( figure.getIcon() ) );
	     else
	         this.setGraphic( new ImageView() );
	}
}
