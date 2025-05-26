package hex_game;


import java.util.Arrays;
import java.util.HashMap;
/**
 * Models a solution to the HEXGame problem.
 */
public class PuzzleSolution {

	final PuzzlePiece[] pieces = new PuzzlePiece[PuzzlePieces.ALL_PIECES.length];
	private HashMap<Coordinate,Coordinate> gameBoard;
	public PuzzleSolution(int boardMode) {
		this.gameBoard = PuzzleUtils.copyBoardMap(boardMode);
	}
	public void addPieceToSolution(int pieceNum, PuzzlePiece piece) {
		
		if (pieces[pieceNum] != null) {
			throw new IllegalArgumentException("Piece already added");
		}
 		pieces[pieceNum] = piece;
 		
 		for (Coordinate coord : piece.getCoordinates()) {
 			this.gameBoard.get(coord).setLabel(pieceNum);
 		}
	}
	
	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append("Coordinate representation of the Gameboard as filled\n");
		for(Coordinate v : gameBoard.values()) {
			buf.append(v.toString());
			buf.append("\n");
		}

		return buf.toString();
	}

	public String normToString() {
		int out[][] = new int[6][5];
		for(int i =0; i < 6; i++) {
			for(int j = 0; j< 5; j++) {
				out[i][j] = -1;
			}
		}
		StringBuilder buf = new StringBuilder();
		buf.append("Coordinate representation of the Gameboard as filled\n");
		for(Coordinate v : gameBoard.values()) {
			int x_mat = v.getX();
			int y_mat;
			switch(x_mat) {
			case(2):
				y_mat = v.getY() - 1;
				break;
			case(3):
				y_mat = v.getY() - 1;
				break;
			case(4):
				y_mat = v.getY() - 2;
				break;
			case(5):
				y_mat = v.getY() - 2;
				break;
			default:
				y_mat = v.getY();
			}
			out[x_mat][y_mat] = v.getLabel();
		}
		for(int[] row : out) {
			buf.append(Arrays.toString(row));
			buf.append("\n");
		}
		return buf.toString();
	}
	
	
}
