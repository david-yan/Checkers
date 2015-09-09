/*BombPiece.java*/

/**
 * Represents a BombPiece ins Checkers61bl
 * 
 * @author
 */

public class BombPiece extends Piece
{

	/**
	 * Define any variables associated with a BombPiece object here. These
	 * variables MUST be private or package private.
	 */

	/**
	 * Constructs a new BombPiece
	 * 
	 * @param side
	 *            what side this BombPiece is on
	 * @param b
	 *            Board that this BombPiece belongs to
	 */
	public BombPiece(int side, Board b)
	{
		super(side, b);
		if (side == 0)
			img = "/img/bomb-water.png";
		else
			img = "/img/bomb-fire.png";
		name = "bomb";
	}

	@Override
	public void kingMe()
	{
		super.kingMe();
		if (this.side() == 0)
			img = "/img/bomb-water-crowned.png";
		else
			img = "/img/bomb-fire-crowned.png";
	}
	
	@Override
	public void explode(int x, int y)
	{
		blowUp(x, y);
		blowUp(x - 1, y - 1);
		blowUp(x + 1, y + 1);
		blowUp(x - 1, y + 1);
		blowUp(x + 1, y - 1);
	}
}