/*Authors: Daniel Bily(xbilyd01), Jakub Gajdosik(xgajdo24)
 * 
 * Contains class for moveContainer, used for storing information that are then put into a table view
 */
package application;

import javafx.beans.property.SimpleStringProperty;
/**
 * class for moveContainer, used for storing information that are then put into a table view
 * 
 *
 */
public class MoveContainer {
	SimpleStringProperty id;
	SimpleStringProperty whiteMove;
	SimpleStringProperty blackMove;
	
	
	/**
	 * constructor
	 * @param id which move is it
	 * @param whiteMove move that the white player performed
	 * @param blackMove move that the black player performed
	 */
	public MoveContainer(String id, String whiteMove, String blackMove)
	{
		this.id =new SimpleStringProperty (id);
		this.whiteMove = new SimpleStringProperty (whiteMove);
		this.blackMove = new SimpleStringProperty (blackMove);
	}
	
	public SimpleStringProperty getID()
	{
		return this.id;
	}
	public SimpleStringProperty getWhiteMove()
	{
		return this.whiteMove;
	}
	public SimpleStringProperty getBlackMove()
	{
		return this.blackMove;
	}
	
	
}
