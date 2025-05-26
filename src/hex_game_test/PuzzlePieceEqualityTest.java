package hex_game_test;

import static hex_game.PuzzleUtils.block;
import static hex_game.PuzzleUtils.puzzlePiece;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import org.junit.Test;

import hex_game.Coordinate;
import hex_game.PuzzlePiece;

/**
 * Test puzzle piece equality
 */
public class PuzzlePieceEqualityTest {

	@Test
	public void equals() {
		PuzzlePiece piece1  = puzzlePiece(new Coordinate[] {
				block(0, -1),
				block(0, 0),
				block(-1, 1),
				block(-1, 2),
			}, 1);
		
		PuzzlePiece piece2  = puzzlePiece(new Coordinate[] {
				block(0, -1),
				block(0, 0),
				block(1, 0),
				block(-1, 1),
				block(0,0),
			}, 2);

		assertNotSame(piece1, piece2);

		assertEquals(piece1, piece1);
		assertEquals(piece2, piece2);
	}
	
}
