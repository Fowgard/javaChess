package application;

import java.io.File;
import java.io.IOException;

import Game.ChessGame;
import Game.Figure;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ChessHandler  implements EventHandler<ActionEvent>{

	MainGame mainGame;
	Timeline timeLine;

	final FileChooser fileChooser = new FileChooser();
	moveParser moveParser;
	
	@FXML
    private BorderPane Game;

	@FXML
	private Stage stage;
    @FXML
    private Button ChooseGameFile;

    @FXML
    private TableView<?> MoveTable;

    @FXML
    private Button SaveGameFile;

    @FXML
    private Button Undo;

    @FXML
    private Button Reset;

    @FXML
    private Button Redo;

    @FXML
    private Button AutoBackwards;

    @FXML
    private Button Stop;

    @FXML
    private Button AutoForwards;

    @FXML
    private Slider SpeedSlider;

    @FXML
    private Tab newTab;
    
    @FXML
    private TabPane tabPane;

    
    
    
	public ChessHandler()
	{
		this.mainGame = new MainGame();

	}
	
	public MainGame getGame() {
		return this.mainGame;
	}

	
	@Override
	public void handle(ActionEvent event) {		
		for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col ++) {
            	if(event.getSource() == this.mainGame.game.board.getField(col,row)) {
            		
            		if(this.mainGame.figurePicked)//move figure
            		{
            			if(this.mainGame.game.move(this.mainGame.figureOnMove, this.mainGame.game.board.getField(col,row), this.mainGame.whitesMove) && this.mainGame.game.board.getField(col,row).getStyleClass().contains("highlight"))
            				this.mainGame.whitesMove = !this.mainGame.whitesMove;
            			
            			
    					this.mainGame.figurePicked = false;
    					this.mainGame.figureOnMove = null;
    					
    					
    					//remove highlights
    					for (int x = 0; x < 8; x++) 
    					{
    						for (int y = 0; y < 8; y ++) 
    						{
    							this.mainGame.game.board.getField(x,y).getStyleClass().remove("highlight");					
    		            	}
    					}
            		}
            		else//pick figure
            		{
            			if(!this.mainGame.game.board.getField(col,row).isEmpty() && this.mainGame.game.board.getField(col,row).getFigure().getColor() == this.mainGame.whitesMove)
	    				{
	    					this.mainGame.figurePicked = true;
	    					this.mainGame.figureOnMove = this.mainGame.game.board.getField(col,row).getFigure();
	    					
	    					
	    					
	    					
	    					for (int x = 0; x < 8; x++) {
	    						for (int y = 0; y < 8; y ++) {
	    		            		if(this.mainGame.game.board.getField(col,row).getFigure().canmove(this.mainGame.game.board.getField(x,y), this.mainGame.game.board))
	    		            		{
	    		            			if(this.mainGame.game.selfCheckMate(this.mainGame.game.board.getField(col,row).getFigure(),this.mainGame.game.board.getField(x,y),this.mainGame.whitesMove))
	    		            			{
	    		            				//self checkmate
	    		            			}
	    		            			else
	    		            			{
	    		            				if(this.mainGame.game.getCheck()) {
		    		            				if(this.mainGame.game.board.getField(col,row).getFigure().getType() == 6)
		    		            				{
		    		            					if(this.mainGame.game.isKingEscape(this.mainGame.game.board.getField(x,y)))
		    		            						this.mainGame.game.board.getField(x,y).getStyleClass().add("highlight");
		    		            				}
		    		            				else
		    		            					if(this.mainGame.game.isEscape(this.mainGame.game.board.getField(x,y)))
		    		            						this.mainGame.game.board.getField(x,y).getStyleClass().add("highlight");
		    		            			}
		    		            			else
		    		            				this.mainGame.game.board.getField(x,y).getStyleClass().add("highlight");
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
	@FXML
	public void undo() {
		if(this.mainGame.game.getHistIndex() > 0) {
			this.mainGame.whitesMove = !this.mainGame.whitesMove;
			this.mainGame.game.undo();
			this.mainGame.game.setCheck(this.mainGame.game.isCheck(this.mainGame.whitesMove,this.mainGame.game.board));
			for (int x = 0; x < 8; x++) 
			{
				for (int y = 0; y < 8; y ++) 
				{
					this.mainGame.game.board.getField(x,y).getStyleClass().remove("highlight");					
            	}
			}
			this.mainGame.figurePicked = false;
			this.mainGame.figureOnMove = null;
		}
	}
	@FXML
	public void redo()
	{
		if(this.mainGame.game.getHistIndex() < this.mainGame.game.getHistSize()-1) {
			this.mainGame.whitesMove = !this.mainGame.whitesMove;
			this.mainGame.game.redo();
			this.mainGame.game.setCheck(this.mainGame.game.isCheck(this.mainGame.whitesMove,this.mainGame.game.board));
			for (int x = 0; x < 8; x++) 
			{
				for (int y = 0; y < 8; y ++) 
				{
					this.mainGame.game.board.getField(x,y).getStyleClass().remove("highlight");					
	        	}
			}
			this.mainGame.figurePicked = false;
			this.mainGame.figureOnMove = null;
		}
	
	}
	

    @FXML
    void autoBackwards(ActionEvent event) {
    	timeLine = new Timeline(new KeyFrame(Duration.seconds(Math.round(SpeedSlider.getValue())), new EventHandler<ActionEvent>() {

    	    @Override
    	    public void handle(ActionEvent event) {
    	    	System.out.println("hi");
    	        undo();
    	    }
    	}));
    	timeLine.setCycleCount(mainGame.game.getHistIndex());
    	timeLine.play();

    }

    @FXML
    void autoForwards(ActionEvent event) throws InterruptedException {
    	timeLine = new Timeline(new KeyFrame(Duration.seconds(Math.round(SpeedSlider.getValue())), new EventHandler<ActionEvent>() {

    	    @Override
    	    public void handle(ActionEvent event) {
    	        redo();
    	    }
    	}));
    	timeLine.setCycleCount(mainGame.game.getHistSize()-mainGame.game.getHistIndex()-1);
    	timeLine.play();
    }

    @FXML
    void chooseFile(ActionEvent event) throws Exception {
    	File file = this.fileChooser.showOpenDialog(stage);
    	
    	moveParser= new moveParser(file, mainGame);
    }

    @FXML
    void reset(ActionEvent event) {
    	while (this.mainGame.game.getHistIndex() > 0)
    		this.undo();
    }

    @FXML
    void saveGame(ActionEvent event) {

    }

    @FXML
    void stop(ActionEvent event) {
	  	if(timeLine != null)
	  		timeLine.stop();
    }
    
    @FXML
    void addNewTab(Event event) throws Exception{
    	
    	
    	FXMLLoader loader = new FXMLLoader(View.class.getResource("Chess.fxml"));
		BorderPane Game = loader.load();
		ChessHandler chessHandler = loader.<ChessHandler>getController();
		//chessHandler.mainGame = this.mainGame;
		chessHandler.mainGame = new MainGame();
        
		Tab tab = new Tab("Game");
		tab.setClosable(true);
		tab.setContent(Game);
		
		//pane with chess
		GridPane chesspane = new GridPane();
		
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col ++) {
                String color ;
                if ((row + col) % 2 == 0) {
                    color = "white";
                } else {
                    color = "#008040";
                }
                chessHandler.mainGame.game.board.getField(col,row).setStyle("-fx-background-color: "+color+";");
                chesspane.add(chessHandler.mainGame.game.board.getField(col,Math.abs(row-7)), col, row);
                chessHandler.mainGame.game.board.getField(col,row).setOnAction(chessHandler);
            }
        }
        
        
        chesspane.setPrefSize(600, 600);
        chesspane.setMinSize(400, 400);
        //put it together
        ((BorderPane)Game).setCenter(chesspane);
        ((BorderPane)Game).setMinSize(600, 600);
        tab.setContent(Game);
        
        
        tabPane.getTabs().add( tabPane.getTabs().size()-1,tab);
        tabPane.getSelectionModel().clearAndSelect(tabPane.getTabs().size()-2);
        

        
        chesspane.heightProperty().addListener(new ChangeListener() {

			@Override
			public void changed(ObservableValue arg0, Object arg1, Object arg2) {
				double height = (double) arg2;
				for (int row = 0; row < 8; row++) {
		            for (int col = 0; col < 8; col ++) {
		            	chessHandler.mainGame.game.board.getField(col,Math.abs(row-7)).setMinHeight(height/8);
		            }
				}
			}
        	
        });
        
        chesspane.widthProperty().addListener(new ChangeListener() {

			@Override
			public void changed(ObservableValue arg0, Object arg1, Object arg2) {
				double width = (double) arg2;
				for (int row = 0; row < 8; row++) {
		            for (int col = 0; col < 8; col ++) {
		            	chessHandler.mainGame.game.board.getField(col,Math.abs(row-7)).setPrefWidth(width/8);
		            }
				}
			}
        	
        });
       
    }
    
	 

}
