/*Authors: Daniel Bily(xbilyd01), Jakub Gajdosik(xgajdo24)
 * 
 * Contains the class for Field 
 */

package Game;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
/**
 * Field extends Button so its clickable, used for controlling movement of pieces
 * 
 * 
 *
 */
public class Field extends Button{
	int col;
	int row;
	private Figure figure;
	/**
	 * Constructor for field
	 * @param col column of the field
	 * @param row row of the field
	 */
	public Field(int col, int row)
	{
		super();
		this.col = col;
		this.row = row;
	}
	/**
	 * Checks if the field is empty(doesent have any figure on it)
	 * @return true if empty, false if not
	 */
	public boolean isEmpty() {
		return figure == null; 
		
	}
	/**
	 * sets a figure on the field
	 * @param figure figure to be put on the field
	 */
	public void setFigure(Figure figure)
	{
		this.figure = figure;
		
		 if (this.figure != null)
			 this.setGraphic( new ImageView ( figure.getIcon() ) );
	     else
	         this.setGraphic( new ImageView() );
	}
	/**
	 * getter for figure
	 * @return figure that has been set on the field or null
	 */
	public Figure getFigure() {
		return this.figure;
	}

	/**
	 * getter for row
	 * @return row of the field
	 */
	public int getRow() {
		return this.row;
	}

	/**
	 * getter for column
	 * @return column of the field
	 */
	public int getCol() {
		return this.col;
	}

	/**
	 * removes figure set on the field, sets the value to null
	 */
	public void removeFigure() {
		this.figure = null;
		
	}
	/**
	 * updates the image of field, used when figure changed position
	 */
	public void updateImg() {
		 if (this.figure != null)
			 this.setGraphic( new ImageView ( figure.getIcon() ) );
	     else
	         this.setGraphic( new ImageView() );
	}
}
