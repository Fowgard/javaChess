/*Authors: Daniel Bily(xbilyd01), Jakub Gajdosik(xgajdo24)
 * 
 * Contains the class which handles GUI actions
 */

package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import Figures.Bishop;
import Figures.Knight;
import Figures.Pawn;
import Figures.Queen;
import Figures.Rook;
import Game.ChessGame;
import Game.Field;
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
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ChessHandler  implements EventHandler<ActionEvent>{

	MainGame mainGame;
	Timeline timeLine;
	int gameCounter=0;

	final FileChooser fileChooser = new FileChooser();

	moveParser moveParser;
	
	@FXML
    private BorderPane Game;

	@FXML
	private Stage stage;
    @FXML
    private Button ChooseGameFile;

    @FXML
    private TableView<MoveContainer> MoveTable;
    
    @FXML
    private TableColumn<MoveContainer, String> columnID;

    @FXML
    private TableColumn<MoveContainer, String> columnWhite;

    @FXML
    private TableColumn<MoveContainer, String> columnBlack;

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
    
    @FXML
    private TextField textField;

	/***
	 * handles the clicks on chess board and moving pieces 
	 * event click on field
	 */
	@Override
	public void handle(ActionEvent event) {		
		for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col ++) {
            	if(event.getSource() == this.mainGame.game.board.getField(col,row)) {
            		
            		if(this.mainGame.figurePicked)//move figure
            		{
            			if(this.mainGame.game.move(this.mainGame.figureOnMove, this.mainGame.game.board.getField(col,row), this.mainGame.whitesMove) && this.mainGame.game.board.getField(col,row).getStyleClass().contains("highlight"))
            			{
            				if(this.mainGame.figureOnMove.getType()== 1 && !this.mainGame.game.getLoadingFile())
            				{
            					
            					this.checkPromotion(this.mainGame.figureOnMove);

            					if (!this.mainGame.whitesMove && this.mainGame.figureOnMove.getRow()==0 || this.mainGame.whitesMove && this.mainGame.figureOnMove.getRow()==7)
            					{
            						try {
	        							BufferedWriter writer = new BufferedWriter(new FileWriter(this.mainGame.game.getFile(), true));
	        							switch(this.mainGame.game.board.getField(this.mainGame.figureOnMove.getCol(),this.mainGame.figureOnMove.getRow()).getFigure().getType())
	        							{
	        								case(2): writer.append("V");
	        									break;
	        								case(3): writer.append("J");
	        									break;
	        								case(4): writer.append("S");
	        									break;
	        								case(5): writer.append("D");
	        									break;
	        								default: this.mainGame.game.showError(("error wrong figure type"));
	        							}
	        							writer.close();
	        						}catch(Exception ex) {
	        							this.mainGame.game.showError("File error");
	        						}
            					}
            					if(this.mainGame.game.isCheck(this.mainGame.whitesMove, this.mainGame.game.board))
            					{
            						this.mainGame.game.setCheck(true); 
            						if(this.mainGame.game.isCheckMate(this.mainGame.whitesMove))
            						{
            							this.mainGame.game.setCheckMate(true);
            						}
            						else
            							this.mainGame.game.setCheckMate(false);
            					}
            					else 
            						this.mainGame.game.setCheck(false);
            					
            				}
            				
        					if(!this.mainGame.game.getLoadingFile())
        					{
        						try {
        							BufferedWriter writer = new BufferedWriter(new FileWriter(this.mainGame.game.getFile(), true));
        							if(this.mainGame.game.getCheck())
        								if(this.mainGame.game.getCheckMate())
        									writer.append("#");
        								else
        									writer.append("+");
        							if(!this.mainGame.whitesMove)
        								writer.append("\n");
        							else
        								writer.append(" ");
        							writer.close();
        						}catch(Exception ex) {
        							this.mainGame.game.showError("File error");
        						}
        					}
        					
            				this.mainGame.whitesMove = !this.mainGame.whitesMove;
            				if(this.mainGame.whitesMove)
            				{
            					if(this.mainGame.game.getCheck())
            					{
                					if(this.mainGame.game.getCheckMate())
                					{
                						this.textField.setText("Black Wins!");
                					}
                					else
                					{
                						this.textField.setText("White is checked");
                					}
            					}
            					else
            					{
            						this.textField.setText("White's turn");
            					}
            				}
            				else
            				{
            					if(this.mainGame.game.getCheck())
            					{
                					if(this.mainGame.game.getCheckMate())
                					{
                						this.textField.setText("White Wins!");
                					}
                					else
                					{
                						this.textField.setText("Black is checked");
                					}
            					}
            					else
            					{
            						this.textField.setText("Black's turn");
            					}
            				}
            				
            				this.updateTable();
            			}
            			
            			
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
	/***
	 * game goes one move back
	 */
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
			
			updateTable();
		}
	}
	/**
	 *  game goes one move forward
	 */
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
			
			updateTable();
		}
	
	}
	
	/***
	 * automatically plays the game backwards
	 * @param event click on autoBackwards button
	 */
    @FXML
    void autoBackwards(ActionEvent event) {
    	timeLine = new Timeline(new KeyFrame(Duration.seconds(Math.round(SpeedSlider.getValue())), new EventHandler<ActionEvent>() {

    	    @Override
    	    public void handle(ActionEvent event) {
    	        undo();
    	    }
    	}));
    	timeLine.setCycleCount(mainGame.game.getHistIndex());
    	timeLine.play();

    }
	/***
	 * automatically plays the game Forwards
	 * @param event click on autoForwards button
	 */
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
    /***
     * creates file-chooser window and loads the game
     * @param event click on Choose game file button
     * @throws Exception file Exception
     */
    @FXML
    void chooseFile(ActionEvent event) throws Exception {
    	FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
    	fileChooser.getExtensionFilters().add(extFilter);
    	File file = this.fileChooser.showOpenDialog(stage);
    	if(file == null)
    		return;
   
    	moveParser= new moveParser(file, mainGame, this.textField);
    	BufferedReader buf = new BufferedReader(new FileReader(file));
		String line = buf.readLine();
		StringBuilder sb = new StringBuilder();
		while(line != null){
			sb.append(line).append("\n");
			line = buf.readLine();
		} 
		String fileSave = sb.toString();
    	BufferedWriter writer = new BufferedWriter(new FileWriter(this.mainGame.game.getFile(), false));
		writer.write(fileSave.toString());
		writer.close();
		updateTable();
    }
    /***
     * resets game to starting positions
     * @param event click on Reset button
     */
    @FXML
    void reset(ActionEvent event) {
    	while (this.mainGame.game.getHistIndex() > 0)
    		this.undo();
    }
    /***
     * creates file-saver window and saves the game transcription
     * @param event click on game save button
     * @throws Exception file Exception
     */
    @FXML
    void saveGame(ActionEvent event) throws Exception {
    	FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
    	File file = this.fileChooser.showSaveDialog(stage);
    	if(file != null)
    	{
    		BufferedReader buf = new BufferedReader(new FileReader(this.mainGame.game.getFile()));
    		String line = buf.readLine();
    		StringBuilder sb = new StringBuilder();
    		while(line != null){
    			sb.append(line).append("\n");
    			line = buf.readLine();
    		} 
    		String fileSave = sb.toString();
        	BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
    		writer.write(fileSave.toString());
    		writer.close();
    	}
    }
    /***
     * stops auto-playing
     * @param event click on stop button
     */
    @FXML
    void stop(ActionEvent event) {
	  	if(timeLine != null)
	  		timeLine.stop();
    }
    /***
     * creates a new tab with a new chess game
     * @param event click on new tab button
     * @throws Exception file Exception
     */
    @FXML
    void addNewTab(Event event) throws Exception{
    	FXMLLoader loader = new FXMLLoader(View.class.getResource("Chess.fxml"));
		BorderPane Game = loader.load();
		ChessHandler chessHandler = loader.<ChessHandler>getController();
		chessHandler.mainGame = new MainGame(this.gameCounter++);
        
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
    
	/***
	 * handles clicking on the table(choosing a move)
	 * @param event
	 */
    @FXML
    void tableClicked(MouseEvent event) {
    	int fromLine;
    	int toLine;
    	int histIndex = this.mainGame.game.getHistIndex() + 1;
    	
    	
    	fromLine = (int) Math.floor( histIndex /2);
    	
    	toLine = Integer.parseInt(MoveTable.getSelectionModel().getSelectedItem().getID().get()) ;
    	int diff = Math.abs(toLine - fromLine);
    	
    	
    	diff *= 2;
    	diff += histIndex %2 -1;

    	for(int i = 0; i < diff; i++)
    	{
    		if(fromLine < toLine)
    			this.redo();
        	        	
        	else if(fromLine > toLine)
        		this.undo();
        	       	
    	}

    }
    
    /**
     * takes information from a file and puts them into the table
     */
    private void updateTable()
    {
    	MoveTable.getItems().clear();
    	
    	MoveContainer moveContainer;
    	int id = 0;
    	String  moves [] = new String[2];
    	moves[0] = " ";
    	moves[1] = " ";
    	String move0 = "";
    	String move1 = "";
    	 try {
         	BufferedReader reader = new BufferedReader(new FileReader(this.mainGame.game.getFile()));
         	String CurrentLine;
         	
         	columnID.setCellValueFactory(cellData -> cellData.getValue().getID());
            columnWhite.setCellValueFactory(cellData -> cellData.getValue().getWhiteMove());
            columnBlack.setCellValueFactory(cellData -> cellData.getValue().getBlackMove());
         	
         	while ((CurrentLine = reader.readLine()) != null) 
             {
         		id++;
  
      			moves = CurrentLine.split(" ");
         		
         		
         		if(moves.length == 1)
         		{
         			move0 = moves[0];
         			move1 = "";
         		}
         		else if(moves.length == 2)
         		{
         			move0 = moves[0];
         			move1 = moves[1];
         		}
         		moveContainer = new MoveContainer(Integer.toString(id),move0,move1);
         	
         		
         		MoveTable.getItems().add(moveContainer);
         	 }
         	reader.close();
         
 			
         }catch(Exception Ex){
        	 Ex.printStackTrace();
        	 this.mainGame.game.showError("File error");
         }
    	 
    	 MoveTable.setRowFactory(columnID -> new TableRow<MoveContainer>() {
    		    @Override
    		    public void updateItem(MoveContainer item, boolean empty) {
    		        super.updateItem(item, empty) ;
    		        if (item == null) {
    		            setStyle("");
    		        } else if (item.getID().get().equals(Integer.toString((mainGame.game.getHistIndex()/2)+Math.floorMod(mainGame.game.getHistIndex(), 2)) )) {
    		            setStyle("-fx-background-color: #6b9cf2b3;");
    		        } else {
    		            setStyle("");
    		        }
    		    }
    		});
    }
    
    //Integer.toString(mainGame.game.getHistIndex()/2)
    
    /**
     * checks if a pawn can be promoted
     * @param figure the pawn being checked
     */
    public void checkPromotion(Figure figure)
    {
    	
    	
    	
    	if(this.mainGame.whitesMove && this.mainGame.figureOnMove.getRow()==7)
    	{
    		Pawn promotedPawn = new Pawn(figure.getCol(),figure.getRow(),figure.getColor());
        	
        	GridPane gridPane = new GridPane();
        	
        	Button rook = new Field(-1,-1);
        	((Field)rook).setFigure(new Rook(-1,-1,true));
        	rook.setOnAction(event -> promotionRook(promotedPawn));
        	
        	Button bishop = new Field(-1,-1);
        	((Field)bishop).setFigure(new Bishop(-1,-1,true));
        	bishop.setOnAction(event -> promotionBishop(promotedPawn));
        	
        	Button knight = new Field(-1,-1);
        	((Field)knight).setFigure(new Knight(-1,-1,true));
        	knight.setOnAction(event -> promotionKnight(promotedPawn));
        	
        	Button queen = new Field(-1,-1);
        	((Field)queen).setFigure(new Queen(-1,-1,true));
        	queen.setOnAction(event -> promotionQueen(promotedPawn));
        	
        	
        	gridPane.add(rook,0,0);
        	gridPane.add(bishop,1,0);
        	gridPane.add(knight,2,0);
        	gridPane.add(queen,3,0);
        	gridPane.setVgap(10);
        	
        	Button ok = new Button("Confirm");
        	
        	Pane pain = new Pane();
        	
        	gridPane.add(ok,3,1);
        	gridPane.add(pain, 3, 2);
        	
        	
        	Scene scene = new Scene(gridPane);
        	Stage stage = new Stage();
        	stage.setTitle("PROMOTION");
        	stage.setScene(scene);

        	ok.setOnAction(event -> buttonConfirm(stage));
        	queen.fire();
        	stage.showAndWait();
        	

    	}	
    	else if (!this.mainGame.whitesMove && this.mainGame.figureOnMove.getRow()==0)
    	{
    		Pawn promotedPawn = new Pawn(figure.getCol(),figure.getRow(),figure.getColor());
        	
        	GridPane gridPane = new GridPane();
        	
        	Button rook = new Field(-1,-1);
        	((Field)rook).setFigure(new Rook(-1,-1,false));
        	rook.setOnAction(event -> promotionRook(promotedPawn));
        	
        	Button bishop = new Field(-1,-1);
        	((Field)bishop).setFigure(new Bishop(-1,-1,false));
        	bishop.setOnAction(event -> promotionBishop(promotedPawn));
        	
        	Button knight = new Field(-1,-1);
        	((Field)knight).setFigure(new Knight(-1,-1,false));
        	knight.setOnAction(event -> promotionKnight(promotedPawn));
        	
        	Button queen = new Field(-1,-1);
        	((Field)queen).setFigure(new Queen(-1,-1,false));
        	queen.setOnAction(event -> promotionQueen(promotedPawn));
        	
        	
        	gridPane.add(rook,0,0);
        	gridPane.add(bishop,1,0);
        	gridPane.add(knight,2,0);
        	gridPane.add(queen,3,0);
        	gridPane.setVgap(10);
        	
        	Button ok = new Button("Confirm");
        	
        	Pane pain = new Pane();
        	
        	gridPane.add(ok,3,1);
        	gridPane.add(pain, 3, 2);
        	
        	
        	Scene scene = new Scene(gridPane);
        	Stage stage = new Stage();
        	stage.setTitle("PROMOTION");
        	stage.setScene(scene);

        	ok.setOnAction(event -> buttonConfirm(stage));
        	queen.fire();
        	stage.showAndWait();

        	queen.fire();

    	}
    	
    }
    /**
     * handles clicking on confirm button
     * @param stage stage to be closed  after clicking the confirm button
     */
    public void buttonConfirm(Stage stage)
    {
    	stage.close();
    }
    
    /**
     * called if pawn is being promoted to a rook
     * @param promotedPawn pawn to be promoted
     */
    public void promotionRook(Pawn promotedPawn)
    {
    	Field field = this.mainGame.game.board.getField(promotedPawn.getCol(),promotedPawn.getRow());
    	field.setFigure(new Rook(promotedPawn.getCol(),promotedPawn.getRow(),promotedPawn.getColor()));
    	
    }
    /**
     * called if pawn is being promoted to a bishop
     * @param promotedPawn pawn to be promoted
     */
    public void promotionBishop(Pawn promotedPawn)
    {
    	Field field = this.mainGame.game.board.getField(promotedPawn.getCol(),promotedPawn.getRow());
    	field.setFigure(new Bishop(promotedPawn.getCol(),promotedPawn.getRow(),promotedPawn.getColor()));
    }
    /**
     * called if pawn is being promoted to a knight
     * @param promotedPawn pawn to be promoted
     */
    public void promotionKnight(Pawn promotedPawn)
    {
    	Field field = this.mainGame.game.board.getField(promotedPawn.getCol(),promotedPawn.getRow());
    	field.setFigure(new Knight(promotedPawn.getCol(),promotedPawn.getRow(),promotedPawn.getColor()));
    }
    /**
     * called if pawn is being promoted to a queen
     * @param promotedPawn pawn to be promoted
     */
    public void promotionQueen(Pawn promotedPawn)
    {
    	Field field = this.mainGame.game.board.getField(promotedPawn.getCol(),promotedPawn.getRow());
    	field.setFigure(new Queen(promotedPawn.getCol(),promotedPawn.getRow(),promotedPawn.getColor()));
    }
    
    
    
}
