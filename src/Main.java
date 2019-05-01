import java.awt.Color;

import javax.swing.JFrame;
public class Main {
	public static void main(String[]args)
	{
		GUI gui = new GUI();
		gui.setVisible(true);
		
		Board board = new Board();
		
		
		
		
		
		int i = 0;
		while(true)
		{	
			if(i == 0)
			{
				gui.getContentPane().setBackground(Color.red);
				i = 1;
			}
			else
			{
				gui.getContentPane().setBackground(Color.green);
				i = 0;
			}
		}
		
		
	}
}
