package Game;

public interface Figure {
	public boolean canmove(Field field, Board board);

	public int getCol();

	public int getRow();
	
	public void updateRC(Field field);

	public boolean getColor();
	
	//1 pawn, 2 rook, 3 knight, 4 bishop, 5queen, 6 king 
	public int getType();

}
