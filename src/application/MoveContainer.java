package application;

import javafx.beans.property.SimpleStringProperty;

public class MoveContainer {
	SimpleStringProperty id;
	SimpleStringProperty whiteMove;
	SimpleStringProperty blackMove;
	
	
	
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
