import junit.framework.TestCase;

public class BoardTest extends TestCase
{
	public void testSelect()
	{
		Board b = new Board(true);
		b.place(new Piece(0, b), 4, 4);
		b.select(4, 4);
		assertTrue(b.canMoveTo(3, 3));
		assertTrue(b.canMoveTo(5, 3));
		assertFalse(b.canMoveTo(5, 5));
		assertFalse(b.canMoveTo(3, 5));
		b.place(new Piece(1, b), 3, 3);
		b.place(new Piece(0, b), 5, 3);
		b.place(new Piece(1, b), 5, 5);
		b.place(new Piece(1, b), 3, 5);
		b.select(4, 4);
		assertTrue(b.canMoveTo(2, 2));
		assertFalse(b.canMoveTo(6, 2));
		assertFalse(b.canMoveTo(2, 6));
		assertFalse(b.canMoveTo(6, 6));
		assertFalse(b.canMoveTo(3, 3));
		assertFalse(b.canMoveTo(5, 3));
		assertFalse(b.canMoveTo(5, 5));
		assertFalse(b.canMoveTo(3, 5));
		b.place(new Piece(1, b), 4, 4);
		b.place(new Piece(0, b), 3, 3);
		b.place(new Piece(0, b), 5, 3);
		b.place(new Piece(0, b), 5, 5);
		b.place(new Piece(0, b), 3, 5);
		b.select(4, 4);
		assertTrue(b.canMoveTo(2, 6));
		assertTrue(b.canMoveTo(6, 6));
		assertFalse(b.canMoveTo(2, 2));
		assertFalse(b.canMoveTo(6, 2));
		assertFalse(b.canMoveTo(3, 3));
		assertFalse(b.canMoveTo(5, 3));
		assertFalse(b.canMoveTo(5, 5));
		assertFalse(b.canMoveTo(3, 5));

	}

	public void testKing()
	{
		Board b = new Board(true);
		b.place(new Piece(0, b), 4, 4);
		b.pieceAt(4, 4).kingMe();
		b.select(4, 4);
		assertTrue(b.canMoveTo(3, 3));
		assertTrue(b.canMoveTo(3, 5));
		assertTrue(b.canMoveTo(5, 3));
		assertTrue(b.canMoveTo(5, 5));
		b.place(new Piece(1, b), 3, 3);
		b.place(new Piece(1, b), 3, 5);
		b.place(new Piece(1, b), 5, 3);
		b.place(new Piece(1, b), 5, 5);
		b.select(4, 4);
		assertTrue(b.canMoveTo(2, 2));
		assertTrue(b.canMoveTo(2, 6));
		assertTrue(b.canMoveTo(6, 2));
		assertTrue(b.canMoveTo(6, 6));
		assertFalse(b.canMoveTo(3, 3));
		assertFalse(b.canMoveTo(3, 5));
		assertFalse(b.canMoveTo(5, 3));
		assertFalse(b.canMoveTo(5, 5));
		b.place(new Piece(0, b), 3, 3);
		b.place(new Piece(0, b), 3, 5);
		b.place(new Piece(0, b), 5, 3);
		b.place(new Piece(0, b), 5, 5);
		b.select(4, 4);
		assertFalse(b.canMoveTo(3, 3));
		assertFalse(b.canMoveTo(3, 5));
		assertFalse(b.canMoveTo(5, 3));
		assertFalse(b.canMoveTo(5, 5));
		assertFalse(b.canMoveTo(2, 2));
		assertFalse(b.canMoveTo(2, 6));
		assertFalse(b.canMoveTo(6, 2));
		assertFalse(b.canMoveTo(6, 6));
	}

	public void testExplode()
	{
		Board b = new Board(true);
		b.place(new Piece(0, b), 0, 0);
		b.place(new BombPiece(0, b), 2, 0);
		b.place(new Piece(1, b), 0, 2);
		b.place(new BombPiece(1, b), 2, 2);
		b.place(new BombPiece(0, b), 1, 1);
		b.pieceAt(1, 1).explode(1, 1);
		assertNull(b.pieceAt(0, 0));
		assertNull(b.pieceAt(2, 0));
		assertNull(b.pieceAt(0, 2));
		assertNull(b.pieceAt(2, 2));
		assertNull(b.pieceAt(1, 1));
		b.place(new BombPiece(1, b), 1, 1);
		b.place(new ShieldPiece(0, b), 0, 0);
		b.place(new ShieldPiece(1, b), 2, 2);
		b.pieceAt(1, 1).explode(1, 1);
		assertNotNull(b.pieceAt(0, 0));
		assertNotNull(b.pieceAt(2, 2));
		assertNull(b.pieceAt(1, 1));
	}
}
