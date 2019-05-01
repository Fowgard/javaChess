package Game;
public class Board  {
	public Field[][] field;
	public Board()
	{



		field = new Field[8][8];
		this.init();
	}
	
	private void init()
	{
		for(int i=0; i<8;i++)
		{
			for(int j=0; j<8;j++)
			{
				field[i][j] = new Field(i,j);
			}
		}
	}
	
	public Field getField(int col, int row)
	{
		return this.field[col][row];
	}
	
}
