package hex_game_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import org.junit.Test;

import hex_game.PuzzlePiece;
import hex_game.PuzzlePieces;

/**
 * Test puzzle piece rotation
 */
public class PuzzlePieceRotateTest {

	@Test
	public void rotation() {
		for (PuzzlePiece piece : PuzzlePieces.ALL_PIECES) {
			testRotationForPiece(piece);
		}
	}

	private void testRotationForPiece(PuzzlePiece piece) {
		PuzzlePiece rotatedPiece = piece.clone();
		rotatedPiece.rotate(6, 1);
		assertEquals(rotatedPiece, piece);
	}

}
