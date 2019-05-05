package application;
	


import java.io.IOException;

import Game.ChessGame;
import Game.Field;
import Game.Figure;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;




import javafx.scene.layout.Priority;





public class Main extends Application  implements EventHandler<ActionEvent>{
	Button undoButton;
	Button redoButton;
	ChessGame game;
	boolean figurePicked;//choosing figure or moving figure
	boolean whitesMove;
	Figure figureOnMove;
	@Override
	public void start(Stage primaryStage) throws IOException {
		init();
		
		//use fxml
		Parent root = FXMLLoader.load(getClass().getResource("Chess.fxml"));
		//primaryStage.setTitle("CHEESE");
		//primaryStage.setScene(new Scene(root,400,400));
		//primaryStage.show();
		
		
		
		
		
		try {
			
			primaryStage.setMinHeight(700);
			primaryStage.setMinWidth(700);
			
			//main border pane
			//BorderPane borderpane = new BorderPane();
			
			//pane with chess
			GridPane chesspane = new GridPane();
			chesspane.setPadding(new Insets(10,20,20,20));
			
			//pane with controls
			HBox controls = new HBox();
			controls.setPadding(new Insets(20,20,10,20));
			undoButton = new Button("Undo");
			undoButton.setOnAction(this);
			
			redoButton = new Button("Redo");
			redoButton.setOnAction(this);
			
			
			controls.getChildren().addAll(undoButton, redoButton);
			
			
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
	                this.game.board.getField(col,row).setOnAction(this);
	            }
	        }

	        //TODO okraje
	       
	        
	        

            //borderpane.setRight((new Button()));
	        
            //put it together
            ((BorderPane) root).setCenter(chesspane);
            //borderpane.setCenter(chesspane);
            ((BorderPane) root).setTop(controls);
            
            
            
            Scene scene = new Scene(root, 700, 700);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
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
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void handle(ActionEvent event) {
		
		if(event.getSource() == undoButton) {
			System.out.println(this.game.board.getField(0, 1).isEmpty());
			this.game.undo();
			System.out.println(this.game.board.getField(0, 1).isEmpty());
		}
		else if(event.getSource() == redoButton) {
			System.out.println(this.game.board.getField(0, 1).isEmpty());
			this.game.redo();
			System.out.println(this.game.board.getField(0, 1).isEmpty());			
		}
		else {
			for (int row = 0; row < 8; row++) {
	            for (int col = 0; col < 8; col ++) {
	            	if(event.getSource() == this.game.board.getField(col,row)) {
	            		
	            		if(figurePicked)//move figure
	            		{
	            			if(this.game.move(figureOnMove, this.game.board.getField(col,row)))
	            			{
	            				if(whitesMove)
	            					whitesMove = false;
	            				else
	            					whitesMove = true;
	            			}

	    					this.figurePicked = false;
	    					this.figureOnMove = null;
	    					
	    					
	    					//remove highlights
	    					for (int x = 0; x < 8; x++) 
	    					{
	    						for (int y = 0; y < 8; y ++) 
	    						{
	    		            	         		
	    		            		
	    							this.game.board.getField(x,y).getStyleClass().clear();
	    							this.game.board.getField(x,y).getStyleClass().add("remove");
	    							//this.game.board.getField(x,y).setStyle(null);
	    		            		System.out.println("remove");
	    							
	    		            	}
	    					}
	    					return;
	            		}
	            		else//pick figure
	            		{
	            			if(!this.game.board.getField(col,row).isEmpty() && this.game.board.getField(col,row).getFigure().getColor() == whitesMove)
		    				{
		    					this.figurePicked = true;
		    					this.figureOnMove = this.game.board.getField(col,row).getFigure();
		    					
		    					
		    					
		    					
		    					for (int x = 0; x < 8; x++) {
		    						for (int y = 0; y < 8; y ++) {
		    		            		if(this.game.board.getField(col,row).getFigure().canmove(this.game.board.getField(x,y), this.game.board))
		    		            		{
		    		            			this.game.board.getField(x,y).getStyleClass().clear();
		    		            			this.game.board.getField(x,y).getStyleClass().add("test");
			    		            		System.out.println("test");
		    		            		}
		    		            	}
		    					}
		    				}
	            		}
	    				
	    			}
	            }
			} 
			
		}
		
	}
	
	public void init() {
		
		game = new ChessGame();
		this.game.addHistory();
		Figure figure = game.board.getField(0, 1).getFigure();
		Field field = game.board.getField(0, 2);
		game.move(figure, field);
		figurePicked = false;
		whitesMove = true;
	}


}
