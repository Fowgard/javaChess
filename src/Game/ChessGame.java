/*Authors: Daniel Bily(xbilyd01), Jakub Gajdosik(xgajdo24)
 * 
 * Contains class for game 
 */

package Game;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import Figures.Bishop;
import Figures.King;
import Figures.Knight;
import Figures.Pawn;
import Figures.Queen;
import Figures.Rook;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ChessGame {
	public Board board;
	private ArrayList<Figure[][]> history = new ArrayList<Figure[][]>();
	private int histIndex;
	private Board tmpBoard;
	private boolean checkMate;
	private boolean check;
	private boolean loadingFile;
	private ArrayList<Field> escape = new ArrayList<Field>();
	private ArrayList<Field> escapeKing = new ArrayList<Field>();
	private File file;
	/**
	 * Constructor, sets the figures on board, initializes variables
	 */
	public ChessGame()
	{
		this.loadingFile = false;
		this.histIndex = -1;
		this.board = new Board();
        for (int x = 0; x < 8; x++) {
           this.board.field[x][1].setFigure(new Pawn(x,1,true));
           this.board.field[x][6].setFigure(new Pawn(x,6,false));
        }
        this.board.field[0][0].setFigure(new Rook(0,0,true));
        this.board.field[7][0].setFigure(new Rook(7,0,true));
        this.board.field[0][7].setFigure(new Rook(0,7,false));
        this.board.field[7][7].setFigure(new Rook(7,7,false));
        this.board.field[1][0].setFigure(new Knight(1,0,true));
        this.board.field[6][0].setFigure(new Knight(6,0,true));
        this.board.field[1][7].setFigure(new Knight(1,7,false));
        this.board.field[6][7].setFigure(new Knight(6,7,false));
        this.board.field[2][0].setFigure(new Bishop(2,0,true));
        this.board.field[5][0].setFigure(new Bishop(5,0,true));
        this.board.field[2][7].setFigure(new Bishop(2,7,false));
        this.board.field[5][7].setFigure(new Bishop(5,7,false));
        this.board.field[3][0].setFigure(new Queen(3,0,true));
        this.board.field[3][7].setFigure(new Queen(3,7,false));
        this.board.field[4][0].setFigure(new King(4,0,true));
        this.board.field[4][7].setFigure(new King(4,7,false));        
	}
	/**
	 * saves current positions of figures in stack
	 */
	public void addHistory() {
		Figure[][] figures = new Figure[8][8];
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
            	figures[x][y] = copyFigure(this.board.getField(x,y).getFigure());
            }
         }
		this.history.add(figures);
		this.histIndex ++;
	}
	/**
	 * Creates a deep copy of a figure
	 * @param figure figure to be copied
	 * @return copied figure
	 */
	public Figure copyFigure(Figure figure) {
		Figure newFig = null;
		if(figure !=null)
			switch(figure.getType())
			{
				case 1:
					newFig = new Pawn(figure.getCol(),figure.getRow(),figure.getColor());
					break;
				case 2:
					newFig = new Rook(figure.getCol(),figure.getRow(),figure.getColor());
					break;
				case 3:
					newFig = new Knight(figure.getCol(),figure.getRow(),figure.getColor());
					break;
				case 4:
					newFig = new Bishop(figure.getCol(),figure.getRow(),figure.getColor());
					break;
				case 5:
					newFig = new Queen(figure.getCol(),figure.getRow(),figure.getColor());
					break;
				case 6:
					newFig = new King(figure.getCol(),figure.getRow(),figure.getColor());
					break;
			}
		return newFig;
	}
	/**
	 * performs required move, saves the move into a file
	 * @param figure figure to be moves
	 * @param field field to move to
	 * @param whitesMove whose move it is
	 * @return true if successfully moves
	 */
	public boolean move(Figure figure, Field field, boolean whitesMove) {
		if(figure == null)
			return false;
		if(!figure.canmove(field,board))
			return false;
		changeHistory();
		if(!this.loadingFile)
		{
			try
			{
				BufferedWriter writer = new BufferedWriter(new FileWriter(this.file, true));
				switch(figure.getType())
				{
					case 1:
						break;
					case 2:
						writer.append("V");
						break;
					case 3:
						writer.append("J");
						break;
					case 4:
						writer.append("S");
						break;
					case 5:
						writer.append("D");
						break;
					case 6:
						writer.append("K");
						break;
				}
				writer.append((char)(97+figure.getCol()));
				writer.append(Integer.toString(figure.getRow()+1));
				if(!field.isEmpty())
					writer.append("x");
				writer.append((char)(97+field.getCol()));
				writer.append(Integer.toString(field.getRow()+1));
				writer.close();
			}
			catch (Exception ex)
			{
				this.showError("File error");
			}
		}
		
		field.setFigure(figure);
		this.board.field[figure.getCol()][figure.getRow()].removeFigure();
		this.board.field[figure.getCol()][figure.getRow()].updateImg();
		figure.updateRC(field);
		if(isCheck(whitesMove, this.board))
		{
			this.check = true; 
			if(isCheckMate(whitesMove))
			{
				this.checkMate = true;
			}
			else
				this.checkMate = false;
		}
		else 
			this.check = false;
		
		addHistory();
		
		return true;
	}
	/**
	 * takes a move back, takes the last record from stack and puts in on board
	 */
	public void undo() {
        this.histIndex--;
		Figure[][] figures = history.get(this.histIndex);
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
            	this.board.getField(x,y).setFigure(copyFigure(figures[x][y]));
            	this.board.field[x][y].updateImg();
            }
        }
	}
	/**
	 * takes a move forward
	 */
	public void redo(){
        this.histIndex++;
		Figure[][] figures = history.get(this.histIndex);
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
            	this.board.getField(x,y).setFigure(copyFigure(figures[x][y]));
            	this.board.field[x][y].updateImg();
            }
        }
	}
	/**
	 * changes records in file if they were changed by using undo and moving a piece manually
	 */
	public void changeHistory() {
		int keepLines = Math.floorDiv(histIndex,2);
		boolean keepOneMore = Math.floorMod(histIndex,2) == 1;
	    try {
    	BufferedReader reader = new BufferedReader(new FileReader(this.file));
    	String currentLine;
    	StringBuffer fileSave=new StringBuffer("");
    	
    	while (keepLines > 0) 
        {
    		currentLine = reader.readLine();
    		if(currentLine == null)
    			return;
    		fileSave.append(currentLine+"\n");
    		keepLines--;
    	}
    	if(keepOneMore)
    	{
    		currentLine = reader.readLine();
    		if(currentLine == null)
    			return;
    		String[] oneMove = currentLine.split(" ");
    		fileSave.append(oneMove[0]+" ");
    	}
    	reader.close();
    	
    	BufferedWriter writer = new BufferedWriter(new FileWriter(this.file, false));
		writer.write(fileSave.toString());
		writer.close();
		
    }catch(Exception Ex){
    	this.showError("File error");
    }
	    
	while(histIndex < history.size()-1){
    	history.remove(histIndex+1);
    }
	}
	/**
	 * getter for "pointer" into array that stores all moves
	 * @return int value of the index
	 */
	public int getHistIndex() {
		return this.histIndex;
	}
	/**
	 * getter for size of an array that stores all moves
	 * @return int value
	 */
	public int getHistSize() {
		return this.history.size();
		}
	/**
	 * checks if a king is checked
	 * @param whitesMove who moved the last piece
	 * @param board board of the game
	 * @return true if the king is checked, false if not
	 */
	public boolean isCheck(boolean whitesMove, Board board) {
		Figure king = null;
        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 8; row++) {
            	if(!board.getField(col,row).isEmpty())
            	{
    				if(board.getField(col,row).getFigure().getType()==6 && board.getField(col,row).getFigure().getColor() == !whitesMove)
                		king = copyFigure(board.getField(col,row).getFigure());
            	}

            }
         }
        if(king == null)
        	this.showError("King not found");
        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 8; row++) {
            	if(!board.getField(col,row).isEmpty())
            	{
                	if(board.getField(col,row).getFigure().getColor() == whitesMove)
                		if(king != null)
                			if(board.getField(col,row).getFigure().canmove(board.getField(king.getCol(),king.getRow()), board))
                			{
                				return true;
                			}
            	}
            }
         }
		return false;
	}
	/**
	 * Checks for check mate
	 * @param whitesMove who did the last move
	 * @return true if check mate false if not
	 */
	public boolean isCheckMate(boolean whitesMove) {
		Boolean retval = true;
        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 8; row++) {
            	if(!board.getField(col,row).isEmpty())
            	{
                	if(this.board.getField(col,row).getFigure().getColor() == !whitesMove)
                	{
                        for (int x = 0; x < 8; x++) {
                            for (int y = 0; y < 8; y++) {
                            	if(this.board.getField(col,row).getFigure().canmove(this.board.getField(x,y), this.board))
                            	{	
                            		makeTmpBoard(this.board.getField(col,row),this.board.getField(x,y));
                            		if(!isCheck(whitesMove,this.tmpBoard))
                            		{
                            			if(this.board.getField(col,row).getFigure().getType() == 6)
                            				this.escapeKing.add(new Field(this.board.getField(x,y).getCol(),this.board.getField(x,y).getRow()));
                            			else	
                            				this.escape.add(new Field(this.board.getField(x,y).getCol(),this.board.getField(x,y).getRow()));
                            			retval = false;
                            		}
                            	}
                            }
                         }
                	}
            	}
            }
         }
		return retval;
	}
	/**
	 * Checks if a player is check mating himself(by moving a piece that protects his king)
	 * @param figure figure that the player wants to move
	 * @param field  destination of the figure
	 * @param whitesMove who did the last move
	 * @return true if check mate, false otherwise
	 */
	public boolean selfCheckMate(Figure figure, Field field,boolean whitesMove)
	{
		makeTmpBoard(this.board.getField(figure.getCol(),figure.getRow()),field);
		if(isCheck(!whitesMove,this.tmpBoard))
			return true;
				
		return false;
	}
	/**
	 * creates a temporary board where check mate is tested(can the king be saved)
	 * 
	 * @param from source field
	 * @param to destination field
	 */
	public void makeTmpBoard(Field from, Field to) {
		this.tmpBoard = new Board();
        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 8; row++) {
            	this.tmpBoard.getField(col,row).setFigure(copyFigure(this.board.getField(col, row).getFigure()));
            }
         }
        this.tmpBoard.getField(to.getCol(),to.getRow()).setFigure(copyFigure(from.getFigure()));
        this.tmpBoard.getField(to.getCol(),to.getRow()).getFigure().updateRC(to);
        this.tmpBoard.getField(from.getCol(),from.getRow()).removeFigure();
	}
	/**
	 * getter for check flag
	 * @return boolean value
	 */
	public boolean getCheck(){
		return this.check;
	}
	/**
	 * getter for check mate flag
	 * @return boolean value
	 */
	public boolean getCheckMate(){
		return this.checkMate;
	}
	/**
	 * setter for check
	 * @param check true or false
	 */
	public void setCheck(boolean check){
		this.check = check;
	}
	/**
	 * setter for check mate
	 * @param checkMate true or false
	 */
	public void setCheckMate(boolean checkMate){
		this.checkMate = checkMate;
	}
	/**
	 * checks if a piece can save the king
	 * @param field field on which the piece stands
	 * @return true if the king can be saves, false otherwise
	 */
	public boolean isEscape(Field field) {
		for(int i = 0; i <this.escape.size(); i++)
		{
			if(field.getCol() == this.escape.get(i).getCol() &&field.getRow() == this.escape.get(i).getRow())
				return true;
		}
		return false;
	}
	/**
	 * checks if the king has a way to escape
	 * @param field field on which the king stands
	 * @return true if the king can escape false otherwise
	 */
	public boolean isKingEscape(Field field) {
		for(int i = 0; i <this.escapeKing.size(); i++)
		{
			if(field.getCol() == this.escapeKing.get(i).getCol() &&field.getRow() == this.escapeKing.get(i).getRow())
				return true;
		}
		return false;
	}
	/**
	 * setter for a flag that says if moves are being loaded from file or performed manually
	 * @param loadingFile true or false
	 */
	public void setLoadingFile(boolean loadingFile) {
		this.loadingFile = loadingFile;
	}
	/**
	 * setter for file that contains moves
	 * @param gameCounter id of a game, every game has its file
	 * @throws Exception if the file can not be accessed
	 */
	public void setFile(int gameCounter) throws Exception {
		this.file = new File("lib"+File.separator+"gameSaves"+File.separator+"game"+gameCounter+".txt");
		if(file.createNewFile())
		{}
		else
		{
			file.delete();
			file.createNewFile();
		}
	}
	/**
	 * setter for file
	 * @return file that the game uses for moves
	 */
	public File getFile() {
		return this.file;
	}
	
	public boolean getLoadingFile() {
		return this.loadingFile;
	}
	/**
	 * shows an alert window with an error
	 * @param message specific error message
	 */
	public void showError(String message)
	{
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("Error");
		alert.setContentText(message);
		alert.showAndWait();
		System.exit(1);
	}
}
