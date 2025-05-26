package hex_game;

import static hex_game.PuzzleUtils.block;
import static hex_game.PuzzleUtils.puzzlePiece;

public class PuzzlePieces {

	public static final int GRID_SIZE = 5;
	
	public static final PuzzlePiece PIECE0  = puzzlePiece(new Coordinate[] {
			block(0, -1),
			block(0, 0),
			block(-1, 1),
			block(-1, 2), 
		}, 0);//orange
	
	public static final PuzzlePiece NORMPIECE0  = puzzlePiece(new Coordinate[] {
			block(0, 0),
			block(-1, -1),
			block(-1, -2),
			block(-2, -3), 
		}, 0);//orange
	
	public static final PuzzlePiece PIECE1  = puzzlePiece(new Coordinate[] {
			block(0, -1),
			block(0, 0),
			block(1, 0),
			block(-1, 1),
		}, 1);//green
	
	public static final PuzzlePiece NORMPIECE1  = puzzlePiece(new Coordinate[] {
			block(0, 0),
			block(-1, -1),
			block(-1, -2),
			block(-2, -1),
		}, 1);//green

	public static final PuzzlePiece PIECE2  = puzzlePiece(new Coordinate[] {
			block(-1, -1),
			block(0, -1),
			block(0, 0),
			block(-1,1),
		}, 2);//yellow
	
	public static final PuzzlePiece NORMPIECE2  = puzzlePiece(new Coordinate[] {
			block(0, 0),
			block(-1, 0),
			block(-2, -1),
			block(-2,-2),
		}, 2);//yellow

	public static final PuzzlePiece PIECE3  = puzzlePiece(new Coordinate[] {
			block(0, -1),
			block(0, 0),
			block(-1,1),
		}, 3);//white
	
	public static final PuzzlePiece NORMPIECE3  = puzzlePiece(new Coordinate[] {
			block(0, 0),
			block(-1, -1),
			block(-1,-2),
		}, 3);//white

	public static final PuzzlePiece PIECE4  = puzzlePiece(new Coordinate[] {
			block(-1, -1),
			block(0, -1),
			block(0, 0),
			block(-1, 0),
		}, 4);//black
	
	public static final PuzzlePiece NORMPIECE4  = puzzlePiece(new Coordinate[] {
			block(0, 0),
			block(-1, -1),
			block(-1, 0),
			block(-2, -1),
		}, 4);//black

	public static final PuzzlePiece PIECE5  = puzzlePiece(new Coordinate[] {
			block(0, 0),
			block(-1, 1),
			block(0, 1),
			block(1, 1),
		}, 5);//red
	
	public static final PuzzlePiece NORMPIECE5  = puzzlePiece(new Coordinate[] {
			block(0, 0),
			block(-1, -1),
			block(-2, -2),
			block(-1, -2),
		}, 5);//red

	public static final PuzzlePiece PIECE6  = puzzlePiece(new Coordinate[] {
			block(0, 0),
			block(0, 1),
			block(-1, 1),
			block(-2, 1),
		}, 6);//blue
	
	public static final PuzzlePiece NORMPIECE6  = puzzlePiece(new Coordinate[] {
			block(0, 0),
			block(-1, -1),
			block(-2, -2),
			block(-2, -1),
		}, 6);//blue
	
	public static final PuzzlePiece[] ALL_PIECES = new PuzzlePiece[] {
		PIECE0, PIECE1, PIECE2, PIECE3, PIECE4, PIECE5, PIECE6
	};
	public static final PuzzlePiece[] ALL_NORMAL_PIECES = new PuzzlePiece[] {
		NORMPIECE0, NORMPIECE1, NORMPIECE2, NORMPIECE3, NORMPIECE4, NORMPIECE5, NORMPIECE6
	};
	
}
