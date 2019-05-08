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
import javafx.fxml.FXML;
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
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;




import javafx.scene.layout.Priority;


public class Main extends Application  implements EventHandler<ActionEvent>{
	//Button undoButton;
	//Button redoButton;
	ChessGame game;
	boolean figurePicked;//choosing figure or moving figure
	boolean whitesMove;
	Figure figureOnMove;
	@FXML
	private BorderPane Game;
	@FXML
	private Button Undo;
	@FXML
	private Button Redo = new Button();
	@Override
	public void start(Stage primaryStage) throws IOException {
		init();

		Parent Game = FXMLLoader.load(getClass().getResource("Chess.fxml"));
		TabPane window = new TabPane();
		Tab Game1 = new Tab("Game1");

		try {
			
			//primaryStage.setMinHeight(700);
			//primaryStage.setMinWidth(700);
			
			//pane with chess
			GridPane chesspane = new GridPane();
			chesspane.setPadding(new Insets(10,20,20,20));
			
			//pane with controls
			//HBox controls = new HBox();
			//controls.setPadding(new Insets(20,20,10,20));
			//undoButton = new Button("Undo");
			//undoButton.setOnAction(this);
			
			//redoButton = new Button("Redo");
			//redoButton.setOnAction(this);
			
			
			//controls.getChildren().addAll(undoButton, redoButton);
			
			//Undo.setOnAction(this);
			
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
	        

	        chesspane.setPrefSize(750, 750);
            //put it together
            ((BorderPane)Game).setCenter(chesspane);
	        Game1.setContent(Game);
	        window.getTabs().addAll(Game1);


            
            
            
            Scene scene = new Scene(window, 700, 700);
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
	    chesspane.setMinHeight(750);
	    chesspane.setMinWidth(750);
		primaryStage.setHeight(1200);
		primaryStage.setWidth(1200);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@FXML
	public void printhi() {
		System.out.println("hi");
	}

	@Override
	public void handle(ActionEvent event) {
		
		if(event.getSource() == Undo && this.game.getHistIndex() > 0) {
			this.whitesMove = !whitesMove;
			this.game.undo();
			this.game.setCheck(this.game.isCheck(this.whitesMove,this.game.board));
			for (int x = 0; x < 8; x++) 
			{
				for (int y = 0; y < 8; y ++) 
				{
					this.game.board.getField(x,y).getStyleClass().remove("highlight");					
            	}
			}
			this.figurePicked = false;
			this.figureOnMove = null;
		}
		else if(event.getSource() == Redo && this.game.getHistIndex() < this.game.getHistSize()-1) {
			this.whitesMove = !whitesMove;
			this.game.redo();
			this.game.setCheck(this.game.isCheck(this.whitesMove,this.game.board));
			for (int x = 0; x < 8; x++) 
			{
				for (int y = 0; y < 8; y ++) 
				{
					this.game.board.getField(x,y).getStyleClass().remove("highlight");					
            	}
			}
			this.figurePicked = false;
			this.figureOnMove = null;
		}
		else {
			//System.out.println(this.game.getCheck());
			//System.out.println(this.game.getCheckMate());
			//System.out.println();
			for (int row = 0; row < 8; row++) {
	            for (int col = 0; col < 8; col ++) {
	            	if(event.getSource() == this.game.board.getField(col,row)) {
	            		
	            		if(figurePicked)//move figure
	            		{
	            			if(this.game.move(figureOnMove, this.game.board.getField(col,row), this.whitesMove) && this.game.board.getField(col,row).getStyleClass().contains("highlight"))
	            				whitesMove = !whitesMove;
	            			
	            			
	    					this.figurePicked = false;
	    					this.figureOnMove = null;
	    					
	    					
	    					//remove highlights
	    					for (int x = 0; x < 8; x++) 
	    					{
	    						for (int y = 0; y < 8; y ++) 
	    						{
	    							this.game.board.getField(x,y).getStyleClass().remove("highlight");					
	    		            	}
	    					}
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
		    		            			//if(this.game.isCheckMate(!this.whitesMove))
		    		            				
		    		            			if(this.game.selfCheckMate(this.game.board.getField(col,row).getFigure(),this.game.board.getField(x,y),this.whitesMove))
		    		            			{
		    		            				//self checkmate
		    		            			}
		    		            			else
		    		            			{
		    		            				if(this.game.getCheck()) {
			    		            				if(this.game.board.getField(col,row).getFigure().getType() == 6)
			    		            				{
			    		            					if(this.game.isKingEscape(this.game.board.getField(x,y)))
			    		            						this.game.board.getField(x,y).getStyleClass().add("highlight");
			    		            				}
			    		            				else
			    		            					if(this.game.isEscape(this.game.board.getField(x,y)))
			    		            						this.game.board.getField(x,y).getStyleClass().add("highlight");
			    		            			}
			    		            			else
			    		            				this.game.board.getField(x,y).getStyleClass().add("highlight");
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
		
	}

	
	public void init() {
		
		game = new ChessGame();
		this.game.addHistory();
		figurePicked = false;
		whitesMove = true;
	}


}
