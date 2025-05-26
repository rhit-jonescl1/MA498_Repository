package hex_game.dlx;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import hex_game.Coordinate;
import hex_game.PuzzlePiece;
import hex_game.PuzzlePieces;
import hex_game.PuzzleSolution;
import hex_game.PuzzleUtils;

import dlx_solver.DLX;
import dlx_solver.data.ColumnObject;

/**
 * <p>
 * Columns in matrix are mapped as follows:
 * <ul>
 * <li>Piece n placed</li>
 * <li>Position (x,y) occupied</li>
 * </ul>
 * <p>
 * Rows correspond to a piece placed in a certain position in a certain
 * orientation.
 */
public class DLXHexSolver {
	
	public static void main(String[] args) {
		Scanner keyboardInput = new Scanner(System.in);
		System.out.println("Please select board type (0 -> Original; 1 -> Regular)");
		int mode = keyboardInput.nextInt();
		System.out.println("Please select normalization piece (0 -> O; 1 -> G; 2 -> Y)");
		int normMode = keyboardInput.nextInt();
		byte[][] input = generateInput(mode, normMode);
		Object[] labels = generateLabels(mode);
		ColumnObject sparseMatrix = DLX.buildSparseMatrix(input, labels);
		HexDLXResultProcessor processor = new HexDLXResultProcessor(mode);
		DLX.solve(sparseMatrix, true, processor);
		dumpSolutions(processor.getSolutions(), mode);
		keyboardInput.close();
	}

	private static Object[] generateLabels(int mode) {
		int numPieces = PuzzlePieces.ALL_PIECES.length;
		ColumnLabel[] labels = new ColumnLabel[numPieces + 27];
		int xMin, xMax;
		int yMin, yMax;
		switch(mode) {
		case(0):
			xMin = -3; xMax = 3;
			yMin = -2; yMax = 3;
			break;
		default:
			xMin = 0; xMax = 5;
			yMin = 0; yMax = 6;
		}
		int offset;
		for (int i=0; i < numPieces; ++i) {
			labels[i] = new PieceLabel(i);
				for (int x=xMin; x<=xMax; ++x) {//hardcoded
					for (int y=yMin; y<=yMax; ++y) {
						if(PuzzleUtils.getGameBoard(mode).contains(new Coordinate(x,y))){
							offset = columnOffset(numPieces, x, y, mode);
							labels[offset] = new PositionLabel(x,y);
						}
					}
				}
		}
		return labels;
	}

	private static int columnOffset(int numPieces, int x, int y, int mode) {
		int rowOff = 0, colOff = 0;
		switch(mode) {
		case(0):
			switch(x) {
			case(-3): rowOff = 0; colOff =  y; break;
			case(-2): rowOff = 4; colOff =   1 + y;break;
			case(-1): rowOff = 4 + 5; colOff =  1 + y;break;
			case(0): rowOff = 4 + 5 + 4; colOff =  2 + y;break;
			case(1): rowOff = 4 + 5 + 4 + 5; colOff =  2 + y;break;
			case(2): rowOff = 4 + 5 + 4 + 5 + 5; colOff =  2 + y;break;
			}
			break;
		default:
			switch(x) {
				case(0): rowOff = 0; colOff =  y; break;
				case(1): rowOff = 4; colOff =   y;break;
				case(2): rowOff = 4 + 5; colOff =  y - 1;break;
				case(3): rowOff = 4 + 5 + 4; colOff =  y - 1;break;
				case(4): rowOff = 4 + 5 + 4 + 5; colOff =  y - 2;break;
				case(5): rowOff = 4 + 5 + 4 + 5 + 4; colOff =  y - 2;break;
			}
			break;
		}
		int offset = numPieces + rowOff + colOff;
		return offset;
	}

	private static byte[][] generateInput(int mode, int normMode) {
		List<byte[]> rows = new LinkedList<>();
		int numPieces = PuzzlePieces.ALL_PIECES.length;
		int pieceNum = 0;
		PuzzlePiece[] original_set = mode == 0? PuzzlePieces.ALL_PIECES : PuzzlePieces.ALL_NORMAL_PIECES;
		for (PuzzlePiece piece : original_set) {
			Set<PuzzlePiece> allPlacementsForPiece = new HashSet<PuzzlePiece>();
			for(int i = 0; i < 3; i++) {
				allPlacementsForPiece.addAll(PuzzleUtils.generateAllPlacementsOfAllOrientations(piece, mode, normMode, i));
			}
			for (PuzzlePiece placedPiece : allPlacementsForPiece) {
				byte[] row = new byte[numPieces + 27];
				row[pieceNum] = 1;
				for (Coordinate coord : placedPiece.getCoordinates()) {
					row[columnOffset(numPieces, coord.getX(), coord.getY(), mode)] = 1;
				}
				rows.add(row);
			}
			pieceNum++;
		}
		byte[][] input = new byte[rows.size()][];
		for (int i=0; i < rows.size(); ++i) {
			input[i] = rows.get(i);
		}
//		Prints the sparse matrix
//		for(int j = 0; j < rows.size(); j++) {
//			for(int i = 0; i < rows.get(j).length; i++){
//				System.out.print(rows.get(j)[i]);
//			}
//		System.out.println();
//		}
		return input;
	}
	
	private static void dumpSolutions(Collection<PuzzleSolution> solutions, int mode) {
		int solutionNum = 1;
		System.out.println("Dumping out solutions.  Some may be mirror images of others.");
		for (PuzzleSolution solution : solutions) {
			System.out.println("Solution#" + solutionNum);
			if(mode == 0) {
				System.out.println(solution.toString());
			}else {
				System.out.println(solution.normToString());
			}
			System.out.println();
			solutionNum++;
		}
	}
}
abstract class ColumnLabel {
	
}

class PieceLabel extends ColumnLabel {
	final int pieceNumber;
	public PieceLabel(int pieceNumber) { this.pieceNumber = pieceNumber; }
	public String toString() { return "Piece=" + pieceNumber; }
}

class PositionLabel extends ColumnLabel {
	final int x;
	final int y;
	public PositionLabel(int x, int y) {
		this.x = x;
		this.y = y;
	}
	@Override
	public String toString() {
		return "[" + x + ", " + y + "]";
	}
}
