/*Authors: Daniel Bily(xbilyd01), Jakub Gajdosik(xgajdo24)
 * 
 * Contains class for moveContainer, used for storing information that are then put into a table view
 */
package application;

import java.io.File;

import Game.ChessGame;
import Game.Figure;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class View extends Application {
	ChessHandler handler;
	/**
	 * constructor, loads files
	 */
	public View()
	{
		File directory = new File("lib"+File.separator +"gameSaves");

		File[] files = directory.listFiles();
		for (File file : files)
		{
		   if (!file.delete())
		       System.out.println("Failed to delete "+file);
		}
	}
	/**
	 * creates window with a scene, loads css 
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(View.class.getResource("tabWindow.fxml"));
		TabPane root = loader.load();

        Scene scene = new Scene(root, 700, 700);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        
    	primaryStage.setMinHeight(700);
		primaryStage.setMinWidth(1000);
        
        primaryStage.setScene(scene);
        primaryStage.show();
	        
		
	}
	/**
	 * launches the start method
	 * @param args implicit argument that the start method needs
	 */
	public void startView(String [] args)
	{
		launch(args);
	}
	
	

}
