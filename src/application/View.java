package application;

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
	ChessGame game;
	ChessHandler handler;
	
	public View()
	{
		
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(View.class.getResource("tabWindow.fxml"));
		TabPane root = loader.load();
		
		loader = new FXMLLoader(View.class.getResource("Chess.fxml"));
		BorderPane Game = loader.load();
		
		
		this.handler = loader.getController();
		this.game = handler.getGame();
		
		TabPane window = new TabPane();
		Tab Game1 = new Tab("Game1");
		
		

		try {
			

			//pane with chess
			GridPane chesspane = new GridPane();
			chesspane.setPadding(new Insets(10,20,20,20));
			
	        int size = 8 ;
	        for (int row = 0; row < size; row++) {
	            for (int col = 0; col < size; col ++) {
	                String color ;
	                if ((row + col) % 2 == 0) {
	                    color = "white";
	                } else {
	                    color = "#008040";
	                }
	                this.game.board.getField(col,row).setStyle("-fx-background-color: "+color+";");
	                chesspane.add(this.game.board.getField(col,Math.abs(row-7)), col, row);
	                this.game.board.getField(col,row).setOnAction(handler);
	            }
	        }

	        //TODO okraje
	        

	        chesspane.setPrefSize(600, 600);
	        chesspane.setMinSize(500, 500);
            //put it together
            ((BorderPane)Game).setCenter(chesspane);
            ((BorderPane)Game).setMinSize(600, 600);
	        Game1.setContent(Game);
	        root.getTabs().addAll(Game1);


            Scene scene = new Scene(root, 700, 700);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            
        	primaryStage.setHeight(1200);
    		primaryStage.setWidth(1200);
            
	        primaryStage.setScene(scene);
	        primaryStage.show();
	        
	        
	        chesspane.heightProperty().addListener(new ChangeListener() {

				@Override
				public void changed(ObservableValue arg0, Object arg1, Object arg2) {
					double height = (double) arg2;
					for (int row = 0; row < size; row++) {
			            for (int col = 0; col < size; col ++) {
			            	game.board.getField(col,Math.abs(row-7)).setPrefHeight(height/8);
			            }
					}
				}
	        	
	        });
	        
	        chesspane.widthProperty().addListener(new ChangeListener() {

				@Override
				public void changed(ObservableValue arg0, Object arg1, Object arg2) {
					double width = (double) arg2;
					for (int row = 0; row < size; row++) {
			            for (int col = 0; col < size; col ++) {
			            	game.board.getField(col,Math.abs(row-7)).setPrefWidth(width/8);
			            }
					}
				}
	        	
	        });
	        
	        
		}catch(Exception e) {
				e.printStackTrace();
			}
	        
		
	}
	
	public void startView(String [] args)
	{
		launch(args);
	}
	
	

}
