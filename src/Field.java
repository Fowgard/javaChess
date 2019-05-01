
public class Field {

	int col;
	int row;
	private Figure figure;
	
	public Field(int col, int row)
	{
		this.col = col;
		this.row = row;
	}
	
	public boolean isEmpty() {
		return figure == null; 
		
	}
	public void setFigure(Figure figure)
	{
		this.figure = figure;
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
}
