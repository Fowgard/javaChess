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

public class moveParser {
	MainGame mainGame;
	File file;				//   	C:\Users\Mefisto\Desktop\IJA\javaChess\text.txt

	private static final Pattern pType = Pattern.compile(".*([K|D|V|S|J|p]).*");
	private static final Pattern pCol = Pattern.compile(".*([a-h]).*([a-h]).*");
	private static final Pattern pRow = Pattern.compile(".*([1-8]).*([1-8]).*");
	private static final Pattern pTypeS = Pattern.compile(".*([K|D|V|S|J|p]).*");
	private static final Pattern pColS = Pattern.compile(".*([a-h]).*");
	private static final Pattern pRowS = Pattern.compile(".*([1-8]).*");
	
	public moveParser(File file, MainGame mainGame) throws Exception
	{
		this.file = file;
		this.mainGame = mainGame;
		fileParse();
	}
	
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
					longSimulation();
					this.mainGame.game.setLoadingFile(false);
				}	
				else
					System.out.println("file is not ok");
			}
			else if( splited[0].matches("[K|D|V|S|J|p]?[a-h]([a-h]|[0-8])?[0-8]"))
			{
				if(shortParseOk())
				{
					System.out.println("file if ok");
					this.mainGame.game.setLoadingFile(true);
					shortSimulation();
					this.mainGame.game.setLoadingFile(false);
				}	
				else
					System.out.println("file is not ok");
			}
			else
			{
				System.out.println("Error in File parser bad format");			
			}
		}
		else {
			System.out.println("Error in File parser no line");
		}
	}
	
	public boolean longParseOk() throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = br.readLine();
		if(line != null)
		{
			String[] splited = line.split(" ");
			
			while(splited.length ==2 && (line = br.readLine())!= null)
			{
				if(!splited[0].matches("(K|D|V|S|J|p)?[a-h][0-8](x)?[a-h][0-8](\\+|#)?") ||!splited[1].matches("(K|D|V|S|J|p)?[a-h][0-8](x)?[a-h][0-8](\\+|#)?"))
					return false;
				splited = line.split(" ");
			}
			if(splited.length == 1)
			{
				if(!splited[0].matches("(K|D|V|S|J|p)?[a-h][0-8](x)?[a-h][0-8](\\+|#)?"))
					return false;
			}
		}		
		return true;
	}
	public boolean shortParseOk() throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = br.readLine();
		int x = 0;
		if(line != null)
		{
			String[] splited = line.split(" ");
			
			while(splited.length ==2 && (line = br.readLine())!= null)
			{
				if(!splited[0].matches("[K|D|V|S|J|p]?(x)?[a-h]([a-h]|[0-8])?[0-8](\\+|#)?") ||!splited[1].matches("[K|D|V|S|J|p]?(x)?[a-h]([a-h]|[0-8])?[0-8](\\+|#)?"))
					return false;
				splited = line.split(" ");
			}
			if(splited.length == 1)
			{
				if(!splited[0].matches("[K|D|V|S|J|p]?(x)?[a-h]([a-h]|[0-8])?[0-8](\\+|#)?"))
					return false;
			}
		}		
		return true;
	}
	
	
	public void longSimulation() throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = br.readLine();
		if(line != null)
		{
			String[] splited = line.split(" ");

			while(splited.length ==2 && (line != null))
			{
				if(!longDoMoves(splited[0]))
					return; //  TODO chyba
				
				if(!longDoMoves(splited[1]))
					return; //  TODO chyba
				line = br.readLine();
				if(line != null)
					splited = line.split(" ");
			}
			if(splited.length == 1)
			{
				if(!longDoMoves(splited[0]))
					return; //  TODO chyba
			}
		}	
	}
	
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
		
		//is figure type correct 1.
		/*if(move.indexOf(typeT) == 0) 
		{
			if(!checkFigureType(fieldFrom,typeT))
			{
				System.out.println("1");
				return false;
			}
		}
		else
			if(!checkFigureType(fieldFrom,"")) //situation when pawn gets promoted
			{
				System.out.println("99");
				return false;
			}
		*/
		if(!checkFigureType(fieldFrom,typeT))
		{
			System.out.println("1");
			return false;
		}
		
		
		fieldFrom.fire();
		
		if(move.contains("x"))
			if(fieldTo.isEmpty())
			{
				System.out.println("2");
				return false;
			}
		
		fieldTo.fire();
		
		if(this.mainGame.whitesMove == whitesTurn)
		{
			System.out.println("3");
			return false;
		}
		whitesTurn = Boolean.valueOf(this.mainGame.whitesMove);
		/*
		if(move.indexOf(typeT) != 0)
		{
			if(!checkFigureType(fieldTo,typeT))
			{
				System.out.println("4");
				return false;
			}
		}
		else if(type.groupCount() == 2)
			if(!checkFigureType(fieldTo,typeT))
			{
				System.out.println("6");
				return false;
			}
		*/
		if(typeUp != "")
			if(!checkFigureType(fieldTo,typeUp))
			{
				System.out.println("20");
				return false;
			}
		
		if(this.mainGame.game.getCheck() != check && this.mainGame.game.getCheckMate() != checkMate)
		{
			System.out.println("8");
			return false;
		}
	
		if(this.mainGame.game.getCheckMate() != checkMate)
		{
			System.out.println("9");
			return false;
		}
		
		
		return true;
	}
	
	public void shortSimulation() throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = br.readLine();
		if(line != null)
		{
			String[] splited = line.split(" ");

			while(splited.length ==2 && (line != null))
			{
				if(!shortDoMoves(splited[0]))
					return; //  TODO chyba
				
				if(!shortDoMoves(splited[1]))
					return; //  TODO chyba
				line = br.readLine();
				if(line != null)
					splited = line.split(" ");
			}
			if(splited.length == 1)
			{
				if(!shortDoMoves(splited[0]))
					return; //  TODO chyba
			}
		}	
	}
	
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
			System.out.println(col.group(0));
			System.out.println(col.group(1));
			System.out.println(col.group(2));
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
		{
			System.out.println("0");
			return false;
		}

		
		//is figure type correct 
		if(!checkFigureType(fieldFrom,typeT))
		{
			System.out.println("1");
			return false;
		}
		
		fieldFrom.fire();
		
		if(move.contains("x"))
			if(fieldTo.isEmpty())
			{
				System.out.println("2");
				return false;
			}
		
		fieldTo.fire();
		
		if(this.mainGame.whitesMove == whitesTurn)
		{
			System.out.println("3");
			return false;
		}
		whitesTurn = Boolean.valueOf(this.mainGame.whitesMove);
		
		if(typeUp != "")
			if(!checkFigureType(fieldTo,typeUp))
			{
				System.out.println("20");
				return false;
			}
		
		
		if(this.mainGame.game.getCheck() != check && this.mainGame.game.getCheckMate() != checkMate)
		{
			System.out.println("8");
			return false;
		}
	
		if(this.mainGame.game.getCheckMate() != checkMate)
		{
			System.out.println("9");
			return false;
		}
		
		
		return true;
	}
	
	
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
