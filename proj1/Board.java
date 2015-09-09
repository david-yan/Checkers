import java.awt.Point;
import java.util.ArrayList;

/*Board.java*/

/**
 * Represents a Board configuration of a game of Checkers61bl
 * 
 * @author
 */

public class Board
{

	/**
	 * Define any variables associated with a Board object here. These variables
	 * MUST be private.
	 */
	/**
	 * pieces is a 2D array representing the board. Each pair of coordinates
	 * represents the location piece. null represents an empty space.
	 * [row][column]
	 */
	private Piece[][]			pieces;
	private boolean				fireTurn;
	private Point				selected;
	private boolean				canEndTurn;
	private ArrayList<Point>	canMoveTo;
	private int					firePieces, waterPieces;
	private String winner;

	/**
	 * Constructs a new Board
	 * 
	 * @param shouldBeEmpty
	 *            if true, add no pieces
	 */
	public Board(boolean shouldBeEmpty)
	{
		fireTurn = true;
		canEndTurn = false;
		pieces = new Piece[8][8];
		firePieces = 12;
		waterPieces = 12;
		winner = "Game in progress";
		if (shouldBeEmpty)
			return;
		pieces[0][0] = new Piece(1, this);
		pieces[2][0] = new Piece(1, this);
		pieces[4][0] = new Piece(1, this);
		pieces[6][0] = new Piece(1, this);
		pieces[1][1] = new ShieldPiece(1, this);
		pieces[3][1] = new ShieldPiece(1, this);
		pieces[5][1] = new ShieldPiece(1, this);
		pieces[7][1] = new ShieldPiece(1, this);
		pieces[0][2] = new BombPiece(1, this);
		pieces[2][2] = new BombPiece(1, this);
		pieces[4][2] = new BombPiece(1, this);
		pieces[6][2] = new BombPiece(1, this);
		pieces[1][5] = new BombPiece(0, this);
		pieces[3][5] = new BombPiece(0, this);
		pieces[5][5] = new BombPiece(0, this);
		pieces[7][5] = new BombPiece(0, this);
		pieces[0][6] = new ShieldPiece(0, this);
		pieces[2][6] = new ShieldPiece(0, this);
		pieces[4][6] = new ShieldPiece(0, this);
		pieces[6][6] = new ShieldPiece(0, this);
		pieces[1][7] = new Piece(0, this);
		pieces[3][7] = new Piece(0, this);
		pieces[5][7] = new Piece(0, this);
		pieces[7][7] = new Piece(0, this);
	}

	private void drawBoard()
	{
		for (int i = 0; i < pieces.length; i++)
		{
			for (int j = 0; j < pieces[0].length; j++)
			{
				if (selected != null && i == selected.x && j == selected.y)
					StdDrawPlus.setPenColor(StdDrawPlus.WHITE);
				else if ((i + j) % 2 == 0)
					StdDrawPlus.setPenColor(StdDrawPlus.GRAY);
				else
					StdDrawPlus.setPenColor(StdDrawPlus.RED);
				if (canMoveTo != null)
					for (Point p : canMoveTo)
						if (p.x == i && p.y == j)
							StdDrawPlus.setPenColor(StdDrawPlus.BLUE);
				StdDrawPlus.filledSquare(i + .5, j + .5, .5);
				Piece p = pieces[i][j];
				if (p != null)
					StdDrawPlus.picture(i + .5, j + .5, p.image(), 1, 1);
			}
		}
	}

	/**
	 * gets the Piece at coordinates (x, y)
	 * 
	 * @param x
	 *            X-coordinate of Piece to get
	 * @param y
	 *            Y-coordinate of Piece to get
	 * @return the Piece at (x, y)
	 */
	public Piece pieceAt(int x, int y)
	{
		return pieces[x][y];
	}

	/**
	 * Precondition: A piece is not already at coordinate (x, y) Places a Piece
	 * at coordinate (x, y)
	 * 
	 * @param p
	 *            Piece to place
	 * @param x
	 *            X coordinate of Piece to place
	 * @param y
	 *            Y coordinate of Piece to place
	 */
	public void place(Piece p, int x, int y)
	{
		pieces[x][y] = p;
	}

	/**
	 * Removes a Piece at coordinate (x, y)
	 * 
	 * @param x
	 *            X coordinate of Piece to remove
	 * @param y
	 *            Y coordinate of Piece to remove
	 * @return Piece that was removed
	 */
	public Piece remove(int x, int y)
	{
		Piece p = pieces[x][y];
		pieces[x][y] = null;
		if (p.side() == 0)
			waterPieces--;
		else
			firePieces--;
		return p;
	}

	/**
	 * Determines if a Piece can be selected
	 * 
	 * @param x
	 *            X coordinate of Piece
	 * @param y
	 *            Y coordinate of Piece to select
	 * @return true if the Piece can be selected
	 */
	public boolean canSelect(int x, int y)
	{
		if (canEndTurn)
			return false;
		if ((x + y) % 2 != 0)
			return false;
		Piece p = pieces[x][y];
		if (p == null)
			return false;
		if (p.side() == 0 && fireTurn)
			return false;
		if (p.side() == 1 && !fireTurn)
			return false;
		return true;
	}

	/**
	 * Selects a square. If no Piece is active, selects the Piece and makes it
	 * active. If a Piece is active, performs a move if an empty place is
	 * selected. Else, allows you to reselect Pieces
	 * 
	 * @param x
	 *            X coordinate of place to select
	 * @param y
	 *            Y coordinate of place to select
	 */
	public void select(int x, int y)
	{
		canMoveTo = new ArrayList<Point>();
		selected = new Point(x, y);
		if (this.pieceAt(x, y).isKing())
		{
			if (inBoard(x - 1, y - 1) && this.pieceAt(x - 1, y - 1) == null && !this.pieceAt(x, y).hasCaptured())
				this.canMoveTo.add(new Point(x - 1, y - 1));
			else if (inBoard(x - 2, y - 2) && this.pieceAt(x - 2, y - 2) == null && pieceAt(x - 1, y - 1) != null && (this.pieceAt(x - 1, y - 1).side() == 1 && this.pieceAt(x, y).side() == 0 || this.pieceAt(x - 1, y - 1).side() == 0 && this.pieceAt(x, y).side() == 1))
				this.canMoveTo.add(new Point(x - 2, y - 2));
			if (inBoard(x + 1, y - 1) && this.pieceAt(x + 1, y - 1) == null && !this.pieceAt(x, y).hasCaptured())
				this.canMoveTo.add(new Point(x + 1, y - 1));
			else if (inBoard(x + 2, y - 2) && this.pieceAt(x + 2, y - 2) == null && pieceAt(x + 1, y - 1) != null && (this.pieceAt(x + 1, y - 1).side() == 1 && this.pieceAt(x, y).side() == 0 || this.pieceAt(x + 1, y - 1).side() == 0 && this.pieceAt(x, y).side() == 1))
				this.canMoveTo.add(new Point(x + 2, y - 2));
			if (inBoard(x + 1, y + 1) && this.pieceAt(x + 1, y + 1) == null && !this.pieceAt(x, y).hasCaptured())
				this.canMoveTo.add(new Point(x + 1, y + 1));
			else if (inBoard(x + 2, y + 2) && this.pieceAt(x + 2, y + 2) == null && pieceAt(x + 1, y + 1) != null && (this.pieceAt(x + 1, y + 1).side() == 1 && this.pieceAt(x, y).side() == 0 || this.pieceAt(x + 1, y + 1).side() == 0 && this.pieceAt(x, y).side() == 1))
				this.canMoveTo.add(new Point(x + 2, y + 2));
			if (inBoard(x - 1, y + 1) && this.pieceAt(x - 1, y + 1) == null && !this.pieceAt(x, y).hasCaptured())
				this.canMoveTo.add(new Point(x - 1, y + 1));
			else if (inBoard(x - 2, y + 2) && this.pieceAt(x - 2, y + 2) == null && pieceAt(x - 1, y + 1) != null && (this.pieceAt(x - 1, y + 1).side() == 1 && this.pieceAt(x, y).side() == 0 || this.pieceAt(x - 1, y + 1).side() == 0 && this.pieceAt(x, y).side() == 1))
				this.canMoveTo.add(new Point(x - 2, y + 2));
			return;
		}
		if (this.pieceAt(x, y).side() == 0)
		{
			if (inBoard(x - 1, y - 1) && this.pieceAt(x - 1, y - 1) == null && !this.pieceAt(x, y).hasCaptured())
				this.canMoveTo.add(new Point(x - 1, y - 1));
			else if (inBoard(x - 2, y - 2) && this.pieceAt(x - 2, y - 2) == null && pieceAt(x - 1, y - 1) != null && this.pieceAt(x - 1, y - 1).side() == 1)
				this.canMoveTo.add(new Point(x - 2, y - 2));
			if (inBoard(x + 1, y - 1) && this.pieceAt(x + 1, y - 1) == null && !this.pieceAt(x, y).hasCaptured())
				this.canMoveTo.add(new Point(x + 1, y - 1));
			else if (inBoard(x + 2, y - 2) && this.pieceAt(x + 2, y - 2) == null && pieceAt(x + 1, y - 1) != null && pieceAt(x + 1, y - 1).side() == 1)
				this.canMoveTo.add(new Point(x + 2, y - 2));
		}
		if (this.pieceAt(x, y).side() == 1)
		{
			if (inBoard(x + 1, y + 1) && this.pieceAt(x + 1, y + 1) == null && !this.pieceAt(x, y).hasCaptured())
				this.canMoveTo.add(new Point(x + 1, y + 1));
			else if (inBoard(x + 2, y + 2) && this.pieceAt(x + 2, y + 2) == null && pieceAt(x + 1, y + 1) != null && this.pieceAt(x + 1, y + 1).side() == 0)
				this.canMoveTo.add(new Point(x + 2, y + 2));
			if (inBoard(x - 1, y + 1) && this.pieceAt(x - 1, y + 1) == null && !this.pieceAt(x, y).hasCaptured())
				this.canMoveTo.add(new Point(x - 1, y + 1));
			else if (inBoard(x - 2, y + 2) && this.pieceAt(x - 2, y + 2) == null && pieceAt(x - 1, y + 1) != null && pieceAt(x - 1, y + 1).side() == 0)
				this.canMoveTo.add(new Point(x - 2, y + 2));
		}
	}

	public boolean inBoard(int x, int y)
	{
		return !(x > 7 || x < 0 || y > 7 || y < 0);
	}

	public boolean canMoveTo(int x, int y)
	{
		if (canMoveTo == null)
			return false;
		for (Point p : canMoveTo)
			if (p.x == x && p.y == y)
				return true;
		return false;
	}

	/**
	 * Moves the active piece to coordinate (x, y)
	 * 
	 * @param p
	 *            Piece to move
	 * @param x1
	 *            Original X coordinate of p
	 * @param y1
	 *            Origin Y coordinate of p
	 * @param x
	 *            X coordinate to move to
	 * @param y
	 *            Y coordinate to move to
	 */
	public void move(Piece p, int x1, int y1, int x2, int y2)
	{
		int x = selected.x;
		int y = selected.y;
		pieces[x2][y2] = pieces[x][y];
		if (pieces[x2][y2].side() == 0 && y2 == 0)
			pieces[x2][y2].kingMe();
		if (pieces[x2][y2].side() == 1 && y2 == 7)
			pieces[x2][y2].kingMe();
		pieces[x][y] = null;
		canMoveTo = null;
		selected = new Point(x2, y2);
		if (Math.abs(x - x2) == 2)
		{
			pieces[x2][y2].startCapturing();
			remove((x + x2) / 2, (y + y2) / 2);
			pieces[x2][y2].explode(x2, y2);
			selected = null;
		}
		if (pieces[x2][y2] != null && pieces[x2][y2].hasCaptured())
			select(x2, y2);
		canEndTurn = true;
	}

	/**
	 * Determines if the turn can end
	 * 
	 * @return true if the turn can end
	 */
	public boolean canEndTurn()
	{
		return canEndTurn;
	}

	/**
	 * Ends the current turn. Changes the player.
	 */
	public void endTurn()
	{
		fireTurn = !fireTurn;
		if (!(selected == null))
			pieceAt(selected.x, selected.y).finishCapturing();
		selected = null;
		canMoveTo = null;
		canEndTurn = false;
		if (waterPieces == 0)
		{
			winner = "Fire";
			System.out.println(winner + " wins!");
		}
		if (firePieces == 0)
		{
			winner = "water";
			System.out.println(winner + " wins!");
		}
	}

	/**
	 * Returns the winner of the game
	 * 
	 * @return The winner of this game
	 */
	public String winner()
	{
		return winner;
	}

	/**
	 * Starts a game
	 */
	public static void main(String[] args)
	{
		Board b = new Board(false);
		StdDrawPlus.setScale(0, 8);
		while (true)
		{
			b.drawBoard();
			if (StdDrawPlus.mousePressed())
			{
				int x = (int) StdDrawPlus.mouseX();
				int y = (int) StdDrawPlus.mouseY();
				if (b.canSelect(x, y))
					b.select(x, y);
				if (b.canMoveTo(x, y))
					b.move(null, 0, 0, x, y);
			}
			if (StdDrawPlus.isSpacePressed())
				if (b.canEndTurn())
					b.endTurn();
//			if (StdDrawPlus.isNPressed())
//				b = new Board(false);
			StdDrawPlus.show(10);
		}
	}
}