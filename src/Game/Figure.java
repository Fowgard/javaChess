/*Authors: Daniel Bily(xbilyd01), Jakub Gajdosik(xgajdo24)
 * 
 * Contains interface for figure 
 */

package Game;

import javafx.scene.image.Image;

public interface Figure {
	public boolean canmove(Field field, Board board);

	public int getCol();

	public int getRow();
	
	public void updateRC(Field field);

	public boolean getColor();
	
	public int getType();

	public Image getIcon();
}
