/*Authors: Daniel Bily(xbilyd01), Jakub Gajdosik(xgajdo24)
 * 
 * Contains the class for board 
 */

package Game;
public class Board  {
	public Field[][] field;
	/**
	 * Constructor, creates two dimensional array of fields and initializes them
	 */
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
	/**
	 * getter for a certain field specified by the parameters
	 * @param col value of column, values from 0 to 7
	 * @param row value of row, values from 0 to 7
	 * @return requested field
	 */
	public Field getField(int col, int row)
	{
		return this.field[col][row];
	}
	
}
