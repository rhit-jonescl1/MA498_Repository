package hex_game_test;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import hex_game.PuzzlePiece;
import hex_game.PuzzlePieces;

public class PuzzlePieceCountVariationsTest {

	@Test
	public void checkPuzzlePieceVariationCount() {
		checkPuzzlePiece(PuzzlePieces.PIECE0, 2);
		checkPuzzlePiece(PuzzlePieces.PIECE1, 2);
		checkPuzzlePiece(PuzzlePieces.PIECE2, 2);
		checkPuzzlePiece(PuzzlePieces.PIECE3, 2);
		checkPuzzlePiece(PuzzlePieces.PIECE4, 2);
		checkPuzzlePiece(PuzzlePieces.PIECE5, 2);
		checkPuzzlePiece(PuzzlePieces.PIECE6, 2);
	}

	private void checkPuzzlePiece(PuzzlePiece piece, int expectedCombos) {
		Set<PuzzlePiece> pieces = new HashSet<>();
		pieces.add(piece);
		
		PuzzlePiece rotated = piece.clone();
		rotated.rotate(1, 1);
		pieces.add(rotated);
		
		assertEquals(expectedCombos, pieces.size());
	}
}
