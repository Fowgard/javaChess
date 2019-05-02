package Game;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

import Figures.Bishop;
import Figures.King;
import Figures.Knight;
import Figures.Pawn;
import Figures.Queen;
import Figures.Rook;

public class ChessGame {
	public Board board;
	private ArrayList<Figure[][]> history = new ArrayList<Figure[][]>();
	private int histIndex;
	public ChessGame()
	{
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
	
	public void addHistory() {
		Figure[][] figures = new Figure[8][8];
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
            	if(!this.board.getField(x,y).isEmpty())
            	{
            		switch(this.board.getField(x,y).getFigure().getType())
            		{
            			case 1:
            				figures[x][y] = new Pawn(x,y,this.board.getField(x,y).getFigure().getColor());
            				break;
            			case 2:
            				figures[x][y] = new Rook(x,y,this.board.getField(x,y).getFigure().getColor());
            				break;
            			case 3:
            				figures[x][y] = new Knight(x,y,this.board.getField(x,y).getFigure().getColor());
            				break;
            			case 4:
            				figures[x][y] = new Bishop(x,y,this.board.getField(x,y).getFigure().getColor());
            				break;
            			case 5:
            				figures[x][y] = new Queen(x,y,this.board.getField(x,y).getFigure().getColor());
            				break;
            			case 6:
            				figures[x][y] = new King(x,y,this.board.getField(x,y).getFigure().getColor());
            				break;
            		}
            	}
            }
         }
        
		this.history.add(figures);
		this.histIndex ++;
	}
	
	public boolean move(Figure figure, Field field) {
		if(figure == null)
			return false;
		if(!figure.canmove(field,board))
			return false;
		
		try
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter("text.txt", true));
			switch(figure.getType())
			{
				case 1:
					writer.append("p");
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
			writer.append(" ");
			writer.append((char)(97+field.getCol()));
			writer.append(Integer.toString(field.getRow()+1));
			if(!field.isEmpty())
			{
				//TODO special znaky
			}
			writer.append("\n");
			writer.close();
		}
		catch (Exception ex)
		{
			System.out.println("fuck");
		}
		
		
		
		field.setFigure(figure);
		this.board.field[figure.getCol()][figure.getRow()].removeFigure();
		figure.updateRC(field);
		
		addHistory();
		
		return true;
	}

	public void undo() {
        this.histIndex--;
		Figure[][] figures = history.get(this.histIndex);
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
            	this.board.getField(x,y).setFigure(figures[x][y]);
            }
         }
	}
	
	public void redo(){
        this.histIndex++;
		Figure[][] figures = history.get(this.histIndex);
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
            	this.board.getField(x,y).setFigure(figures[x][y]);
            }
         }		
	}
	
}
