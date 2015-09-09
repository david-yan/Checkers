/*Piece.java*/

/**
 * Represents a Normal Piece in Checkers61bl
 * 
 * @author
 */

public class Piece
{

	/**
	 * Define any variables associated with a Piece object here. These variables
	 * MUST be private or package private.
	 */
	private int			side;			// side 0 is water, side 1 is fire
	private Board		b;
	private boolean		isKing;
	private boolean		hasCaptured;
	protected String	img;
	protected String name;

	public Piece()
	{
		side = 0;
		isKing = false;
		hasCaptured = false;
		img = "/img/pawn-water.png";
		name = "pawn";
	}

	/**
	 * Returns the side that the piece is on
	 * 
	 * @return 0 if the piece is fire and 1 if the piece is water
	 */
	public int side()
	{
		return side;
	}

	public boolean isKing()
	{
		return isKing;
	}

	/**
	 * Initializes a Piece
	 * 
	 * @param side
	 *            The side of the Piece
	 * @param b
	 *            The Board the Piece is on
	 */
	Piece(int side, Board b)
	{
		this.side = side;
		this.b = b;
		this.isKing = false;
		this.hasCaptured = false;
		if (this.side == 0)
			img = "/img/pawn-water.png";
		else
			this.img = "/img/pawn-fire.png";
		name = "pawn";
	}

	/**
	 * Destroys the piece at x, y. ShieldPieces do not blow up
	 * 
	 * @param x
	 *            The x position of Piece to destroy
	 * @param y
	 *            The y position of Piece to destroy
	 */
	void blowUp(int x, int y)
	{
		if (b.inBoard(x, y) && b.pieceAt(x, y) != null && !b.pieceAt(x, y).getName().equals("shield"))
			b.remove(x, y);
	}

	/**
	 * Does nothing. For bombs, destroys pieces adjacent to it
	 * 
	 * @param x
	 *            The x position of the Piece that will explode
	 * @param y
	 *            The y position of the Piece that will explode
	 */
	void explode(int x, int y)
	{
		return;
	}

	/**
	 * Signals that this Piece has begun to capture (as in it captured a Piece)
	 */
	void startCapturing()
	{
		this.hasCaptured = true;
	}

	/**
	 * Returns whether or not this piece has captured this turn
	 * 
	 * @return true if the Piece has captured
	 */
	public boolean hasCaptured()
	{
		return this.hasCaptured;
	}

	/**
	 * Resets the Piece for future turns
	 */
	public void finishCapturing()
	{
		this.hasCaptured = false;
	}

	public String image()
	{
		return img;
	}

	public void kingMe()
	{
		this.isKing = true;
		if (side == 0)
			img = "/img/pawn-water-crowned.png";
		else
			img = "/img/pawn-fire-crowned.png";
	}
	
	public String getName()
	{
		return name;
	}
}