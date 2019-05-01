import javax.swing.JFrame;
import javax.swing.JTabbedPane;
public class Board  {
	
	private Field[][] fields;
	
	
	public Board()
	{
		fields = new Field[8][8];
		this.init();
	}
	
	private void init()
	{
		for(int i=0; i<8;i++)
		{
			for(int j=0; j<8;j++)
			{
				fields[i][j] = new Field(i+1,j+1); //TODO +1 or not
			}
		}
	}
	
}
