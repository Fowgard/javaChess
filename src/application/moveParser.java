/*Authors: Daniel Bily(xbilyd01), Jakub Gajdosik(xgajdo24)
 * 
 * Contains the class which checks file validity 
 */

package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Figures.Bishop;
import Figures.King;
import Figures.Knight;
import Figures.Pawn;
import Figures.Queen;
import Figures.Rook;
import Game.Field;
import javafx.scene.control.TextField;

public class moveParser {
	MainGame mainGame;
	File file;
	TextField textField;
	
	private static final Pattern pType = Pattern.compile(".*([K|D|V|S|J|p]).*");
	private static final Pattern pCol = Pattern.compile(".*([a-h]).*([a-h]).*");
	private static final Pattern pRow = Pattern.compile(".*([1-8]).*([1-8]).*");
	private static final Pattern pTypeS = Pattern.compile(".*([K|D|V|S|J|p]).*");
	private static final Pattern pColS = Pattern.compile(".*([a-h]).*");
	private static final Pattern pRowS = Pattern.compile(".*([1-8]).*");
	/**
	 * initialize values and starts file checking
	 * @param file contains loaded file
	 * @param mainGame is game to which the file is loaded
	 * @param textField field which shows game status in GUI
	 * @throws Exception mostly file exception
	 */
	public moveParser(File file, MainGame mainGame, TextField textField) throws Exception
	{
		this.file = file;
		this.mainGame = mainGame;
		this.textField = textField;
		fileParse();
	}
	/***
	 * checks format of moves in file and calls correct whole file-checker
	 * @throws Exception file Exception
	 */
	public void fileParse() throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = br.readLine();
		String[] splited;
		if(line != null)
		{
			splited = line.split(" ");
			if(splited[0].matches("[K|D|V|S|J|p]?[a-h][0-8][a-h][0-8]"))
			{
				if(longParseOk())
				{
					this.mainGame.game.setLoadingFile(true);
					if(!longSimulation())
						this.mainGame.game.showError("Error in File parser bad format");
					this.mainGame.game.setLoadingFile(false);
				}	
				else
					this.mainGame.game.showError("Error in File parser bad format");
					
			}
			else if( splited[0].matches("[K|D|V|S|J|p]?[a-h]([a-h]|[0-8])?[0-8]"))
			{
				if(shortParseOk())
				{
					this.mainGame.game.setLoadingFile(true);
					shortSimulation();
					this.mainGame.game.setLoadingFile(false);
				}	
				else
					this.mainGame.game.showError("Error in File parser bad format");
			}
			else
			{
				this.mainGame.game.showError("Error in File parser bad format");			
			}
		}
		else {
			this.mainGame.game.showError("Error in File parser no line");
		}
	}
	/***
	 * checks the whole file with long move format
	 * @return if file has correct format
	 * @throws Exception file Exception
	 */
	public boolean longParseOk() throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = br.readLine();
		if(line != null)
		{
			String[] splited = line.split(" ");
			
			while(splited.length ==2 && (line = br.readLine())!= null)
			{
				if(!splited[0].matches("(K|D|V|S|J|p)?[a-h][0-8](x)?[a-h][0-8](K|D|V|S|J|p)?(\\+|#)?") ||!splited[1].matches("(K|D|V|S|J|p)?[a-h][0-8](x)?[a-h][0-8](K|D|V|S|J|p)?(\\+|#)?"))
					return false;
				splited = line.split(" ");
			}
			if(splited.length == 1)
			{
				if(!splited[0].matches("(K|D|V|S|J|p)?[a-h][0-8](x)?[a-h][0-8](K|D|V|S|J|p)?(\\+|#)?"))
					return false;
			}
		}		
		return true;
	}
	/***
	 * checks the whole file with short move format
	 * @return if file has correct format
	 * @throws Exception file Exception
	 */
	public boolean shortParseOk() throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = br.readLine();
		int x = 0;
		if(line != null)
		{
			String[] splited = line.split(" ");
			
			while(splited.length ==2 && (line = br.readLine())!= null)
			{
				if(!splited[0].matches("[K|D|V|S|J|p]?(x)?[a-h]([a-h]|[0-8])?[0-8](K|D|V|S|J|p)?(\\+|#)?") ||!splited[1].matches("[K|D|V|S|J|p]?(x)?[a-h]([a-h]|[0-8])?[0-8](K|D|V|S|J|p)?(\\+|#)?"))
					return false;
				splited = line.split(" ");
			}
			if(splited.length == 1)
			{
				if(!splited[0].matches("[K|D|V|S|J|p]?(x)?[a-h]([a-h]|[0-8])?[0-8](K|D|V|S|J|p)?(\\+|#)?"))
					return false;
			}
		}		
		return true;
	}
	
	/***
	 * loads and splits lines for simulation of long moves
	 * @return if move can't be done
	 * @throws Exception file Exception
	 */
	public boolean longSimulation() throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = br.readLine();
		if(line != null)
		{
			String[] splited = line.split(" ");

			while(splited.length ==2 && (line != null))
			{
				if(!longDoMoves(splited[0]))
					return false;
				
				if(!longDoMoves(splited[1]))
					return false;
				line = br.readLine();
				if(line != null)
					splited = line.split(" ");
			}
			if(splited.length == 1)
			{
				if(!longDoMoves(splited[0]))
					return false;
			}
		}
		return true;
	}
	/***
	 * simulates the moves in file for long move type
	 * @param move contains one move string from file
	 * @return if move can't be done
	 */
	public boolean longDoMoves(String move) {
		Matcher type = pType.matcher(move);
		Matcher col = pCol.matcher(move);
		Matcher row = pRow.matcher(move);

		col.find();
		row.find();
		
		String typeT = "";
		String typeUp = "";
		if(type.find())
		{
			if(move.indexOf(type.group(1)) == 0)
			{
				typeT = type.group(1);
				if(type.groupCount() == 2)
					typeUp = type.group(2);
			}
			else
			{
				typeT = "";
				typeUp = type.group(1);
			}
		}
		
		Field fieldFrom = this.mainGame.game.board.getField(col.group(1).charAt(0)-'a', Integer.valueOf(row.group(1))-1);
		Field fieldTo = this.mainGame.game.board.getField(col.group(2).charAt(0)-'a', Integer.valueOf(row.group(2))-1);
		boolean whitesTurn = this.mainGame.whitesMove;
		boolean check = move.contains("+");
		boolean checkMate = move.contains("#");

		if(!checkFigureType(fieldFrom,typeT))
			return false;
		
		
		fieldFrom.fire();
		
		if(move.contains("x"))
			if(fieldTo.isEmpty())
				return false;
		
		fieldTo.fire();
		if(typeUp != "")
		{	

			switch(typeUp)
			{
				case("D"): fieldTo.setFigure(new Queen(fieldTo.getCol(),fieldTo.getRow(),fieldTo.getFigure().getColor()));
				break;
				case("J"): fieldTo.setFigure(new Knight(fieldTo.getCol(),fieldTo.getRow(),fieldTo.getFigure().getColor()));
				break;
				case("V"): fieldTo.setFigure(new Rook(fieldTo.getCol(),fieldTo.getRow(),fieldTo.getFigure().getColor()));
				break;
				case("S"): fieldTo.setFigure(new Bishop(fieldTo.getCol(),fieldTo.getRow(),fieldTo.getFigure().getColor()));
				break;
				default: return false;
			}
			if(this.mainGame.game.isCheck(!this.mainGame.whitesMove, this.mainGame.game.board))
			{
				this.mainGame.game.setCheck(true); 
				if(this.mainGame.game.isCheckMate(!this.mainGame.whitesMove))
				{
					this.mainGame.game.setCheckMate(true);
				}
				else
					this.mainGame.game.setCheckMate(false);
			}
			else 
				this.mainGame.game.setCheck(false);
		}
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
		
		if(this.mainGame.whitesMove == whitesTurn)
			return false;
		
		whitesTurn = Boolean.valueOf(this.mainGame.whitesMove);

		
		if(this.mainGame.game.getCheck() != check && this.mainGame.game.getCheckMate() != checkMate)
			return false;
		
		if(this.mainGame.game.getCheckMate() != checkMate)
			return false;
		
		return true;
	}
	/***
	 * loads and splits lines for simulation of short moves
	 * @return if move can't be done
	 * @throws Exception file Exception
	 */
	public boolean shortSimulation() throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = br.readLine();
		if(line != null)
		{
			String[] splited = line.split(" ");

			while(splited.length ==2 && (line != null))
			{
				if(!shortDoMoves(splited[0]))
					return false;
				
				if(!shortDoMoves(splited[1]))
					return false;
				line = br.readLine();
				if(line != null)
					splited = line.split(" ");
			}
			if(splited.length == 1)
			{
				if(!shortDoMoves(splited[0]))
					return false;
			}
		}
		return true;
	}
	/***
	 * simulates the moves in file for short move type
	 * @param move contains one move string from file
	 * @return if move can't be done
	 */
	public boolean shortDoMoves(String move) {
		Matcher type = pTypeS.matcher(move);
		Matcher col = pColS.matcher(move);
		Matcher row = pRowS.matcher(move);

		col.find();
		row.find();
		
		String typeT = "";
		String typeUp = "";
		if(type.find())
		{
			if(move.indexOf(type.group(1)) == 0)
			{
				typeT = type.group(1);
				if(type.groupCount() == 2)
					typeUp = type.group(2);
			}
			else
			{
				typeT = "";
				typeUp = type.group(1);
			}
		}
		
		boolean whitesTurn = this.mainGame.whitesMove;
		boolean check = move.contains("+");
		boolean checkMate = move.contains("#");
		Field fieldFrom = null;
		Field fieldTo = null;
		
		if (col.groupCount()==2)
		{
			fieldTo = this.mainGame.game.board.getField(col.group(2).charAt(0)-'a', Integer.valueOf(row.group(1))-1);
			for(int y = 0; y<8;y++)
			{
				if (whitesTurn == this.mainGame.game.board.getField(col.group(1).charAt(0)-'a',y).getFigure().getColor() && checkFigureType(this.mainGame.game.board.getField(col.group(1).charAt(0)-'a',y),typeT))
				{
					fieldFrom = this.mainGame.game.board.getField(col.group(1).charAt(0)-'a',y);
					break;
				}
			}
		}
		else if (row.groupCount()==2)
		{
			fieldTo = this.mainGame.game.board.getField(col.group(1).charAt(0)-'a', Integer.valueOf(row.group(2))-1);
			for(int x = 0; x<8;x++)
			{
				if (whitesTurn == this.mainGame.game.board.getField(x,Integer.valueOf(row.group(1))-1).getFigure().getColor() && checkFigureType(this.mainGame.game.board.getField(x,Integer.valueOf(row.group(1))-1),typeT))
				{
					fieldFrom = this.mainGame.game.board.getField(x,Integer.valueOf(row.group(1))-1);
					break;
				}
			}
		}
		else
		{
			fieldTo = this.mainGame.game.board.getField(col.group(1).charAt(0)-'a', Integer.valueOf(row.group(1))-1);
			for(int x = 0; x<8;x++)
			{
				for(int y = 0; y<8;y++)
				{
					if(!this.mainGame.game.board.getField(x,y).isEmpty())
					{
						if (whitesTurn == this.mainGame.game.board.getField(x,y).getFigure().getColor() && checkFigureType(this.mainGame.game.board.getField(x,y),typeT) && this.mainGame.game.board.getField(x,y).getFigure().canmove(fieldTo, this.mainGame.game.board))
						{
							fieldFrom = this.mainGame.game.board.getField(x,y);
							break;
						}
					}
				}
			}
		}

		if (fieldFrom == null)
			return false;

		
		//is figure type correct 
		if(!checkFigureType(fieldFrom,typeT))
			return false;
		
		fieldFrom.fire();
		
		if(move.contains("x"))
			if(fieldTo.isEmpty())
				return false;
		
		fieldTo.fire();
		if(typeUp != "")
		{	
			switch(typeUp)
			{
				case("D"): fieldTo.setFigure(new Queen(fieldTo.getCol(),fieldTo.getRow(),fieldTo.getFigure().getColor()));
					break;
				case("J"): fieldTo.setFigure(new Knight(fieldTo.getCol(),fieldTo.getRow(),fieldTo.getFigure().getColor()));
				break;
				case("V"): fieldTo.setFigure(new Rook(fieldTo.getCol(),fieldTo.getRow(),fieldTo.getFigure().getColor()));
				break;
				case("S"): fieldTo.setFigure(new Bishop(fieldTo.getCol(),fieldTo.getRow(),fieldTo.getFigure().getColor()));
				break;
				default: return false;
			}
			if(this.mainGame.game.isCheck(!this.mainGame.whitesMove, this.mainGame.game.board))
			{
				this.mainGame.game.setCheck(true); 
				if(this.mainGame.game.isCheckMate(!this.mainGame.whitesMove))
				{
					this.mainGame.game.setCheckMate(true);
				}
				else
					this.mainGame.game.setCheckMate(false);
			}
			else 
				this.mainGame.game.setCheck(false);
		}
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
		
		if(this.mainGame.whitesMove == whitesTurn)
			return false;
		whitesTurn = Boolean.valueOf(this.mainGame.whitesMove);

		if(this.mainGame.game.getCheck() != check && this.mainGame.game.getCheckMate() != checkMate)
			return false;
	
		if(this.mainGame.game.getCheckMate() != checkMate)
			return false;
		
		
		return true;
	}
	
	/***
	 * translates figure type from file to game interpretation and checks if the one on board is the same
	 * @param field where the figure is located
	 * @param type type of figure from file
	 * @return if the figure from file is on the board
	 */
	public boolean checkFigureType(Field field,String type) {
		if(!field.isEmpty())
			switch(type)
			{
				case "":
					if(field.getFigure().getType() == 1)
						return true;
					break;
				case "p":
					if(field.getFigure().getType() == 1)
						return true;
					break;
				case "K":
					if(field.getFigure().getType() == 6)
						return true;
					break;
				case "D":
					if(field.getFigure().getType() == 5)
						return true;
					break;
				case "V":
					if(field.getFigure().getType() == 2)
						return true;
					break;
				case "S":
					if(field.getFigure().getType() == 4)
						return true;
					break;
				case "J":
					if(field.getFigure().getType() == 3)
						return true;
					break;
			}
		return false;
	}
	
}
