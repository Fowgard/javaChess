package application;

import Game.ChessGame;
import Game.Figure;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class ChessHandler  implements EventHandler<ActionEvent>{

	boolean figurePicked;//choosing figure or moving figure
	boolean whitesMove;
	Figure figureOnMove;
	ChessGame game;
	
	
	@FXML
	private BorderPane Game;
	@FXML
	private Button Undo;
	@FXML
	private Button Redo;
	

	
	public ChessHandler()
	{
		this.game = game;
		figurePicked = false;
		whitesMove = true;
		this.init();
	}
	
	public ChessGame getGame() {
		return this.game;
	}
	
	public void init() {
		game = new ChessGame();
		this.game.addHistory();
		//handler = new ChessHandler(game);
		
	}
	
	
	@Override
	public void handle(ActionEvent event) {		
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
	@FXML
	public void undo() {
		if(this.game.getHistIndex() > 0) {
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
	}
	
	public void redo()
	{
		if(this.game.getHistIndex() < this.game.getHistSize()-1) {
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
	
	}
	
	
	

}
