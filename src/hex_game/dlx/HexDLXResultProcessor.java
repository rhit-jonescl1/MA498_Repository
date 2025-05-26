package hex_game.dlx;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import hex_game.Coordinate;
import hex_game.PuzzlePiece;
import hex_game.PuzzleSolution;

import dlx_solver.DLXResult;
import dlx_solver.DLXResultProcessor;

public class HexDLXResultProcessor implements DLXResultProcessor {

	Collection<PuzzleSolution> solutions = new ArrayList<>();
	int mode;
	public HexDLXResultProcessor(int board_mode) {
		this.mode = board_mode;
	}
	public boolean processResult(DLXResult result) {
		
		PuzzleSolution solution = new PuzzleSolution(mode);
//		System.out.println(result.toString());
		// For each row, process a single block
		Iterator<List<Object>> allRows = result.rows();
		while (allRows.hasNext()) {
			int pieceNumber = -1; // sentinel
			List<Object> row = allRows.next();
			
			// Scan for piece number first
			for (Object label : row) {
				if (label instanceof PieceLabel pieceLabel) {
					pieceNumber = pieceLabel.pieceNumber;
					break;
				}
			}
			assert(pieceNumber != -1);
			
			// Reconstruct block
			List<Coordinate> pieceCoordinates = new ArrayList<>();
			for (Object label : row) {
				if (label instanceof PositionLabel positionLabel) {
					Coordinate coord = new Coordinate(positionLabel.x, positionLabel.y);
					coord.setLabel(pieceNumber);
					pieceCoordinates.add(coord);
				}
			}
			assert(!pieceCoordinates.isEmpty());

			// Build solution, remember it
			PuzzlePiece piece = new PuzzlePiece(pieceCoordinates, pieceNumber);
			solution.addPieceToSolution(pieceNumber, piece);	
		}
		//System.out.println(solution.toString());
		
		solutions.add(solution);
		
		return true;
	}
	
	public Collection<PuzzleSolution> getSolutions() {
		return Collections.unmodifiableCollection(solutions);
	}

}
