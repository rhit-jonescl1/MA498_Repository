package hex_game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Utility functions used for HEXGame solver.
 */
public class PuzzleUtils {
	public static final Coordinate INVALID_COORD = new Coordinate(Integer.MIN_VALUE,Integer.MIN_VALUE,Integer.MAX_VALUE);
	private static int placementFlag = -1;
	private static Coordinate[][] normalizedElbow = new Coordinate[][] {new Coordinate[] {
			new Coordinate(4,4),
			new Coordinate(3,4),
			new Coordinate(2,3),
			new Coordinate(2,2),
		}, new Coordinate[] {
			new Coordinate(4,4),
			new Coordinate(4,3),
			new Coordinate(3,4),
			new Coordinate(2,3),
		}, new Coordinate[] {
			new Coordinate(4,3),
			new Coordinate(4,4),
			new Coordinate(3,4),
			new Coordinate(2,3),
		},};
	private static Coordinate [] normalizedGreen = new Coordinate[] {
			new Coordinate(2,2),
			new Coordinate(2,3),
			new Coordinate(1,3),
			new Coordinate(3,4),
	};
	private static Coordinate[] normalizedOrange = new Coordinate[] {
			new Coordinate(1,1),
			new Coordinate(3,3),
			new Coordinate(2,3),
			new Coordinate(3,4),
	};
	private static Map<Coordinate,Coordinate> originalGameBoard = Map.ofEntries( 
			Map.entry(block(2,-2),block(2,-2)),
			Map.entry(block(2,-1),block(2,-1)),
			Map.entry(block(2,0),block(2,0)),
			Map.entry(block(2,1),block(2,1)),
			Map.entry(block(1,-2),block(1,-2)),
			Map.entry(block(1,-1),block(1,-1)),
			Map.entry(block(1,0),block(1,0)),
			Map.entry(block(1,1),block(1,1)),
			Map.entry(block(1,2),block(1,2)),
			Map.entry(block(0,-2),block(0,-2)),
			Map.entry(block(0,-1),block(0,-1)),
			Map.entry(block(0,0),block(0,0)),
			Map.entry(block(0,1),block(0,1)),
			Map.entry(block(0,2),block(0,2)),
			Map.entry(block(-1,-1),block(-1,-1)),
			Map.entry(block(-1,0),block(-1,0)),
			Map.entry(block(-1,1),block(-1,1)),
			Map.entry(block(-1,2),block(-1,2)),
			Map.entry(block(-2,-1),block(-2,-1)),
			Map.entry(block(-2,0),block(-2,0)),
			Map.entry(block(-2,1),block(-2,1)),
			Map.entry(block(-2,2),block(-2,2)),
			Map.entry(block(-2,3),block(-2,3)),
			Map.entry(block(-3,0),block(-3,0)),
			Map.entry(block(-3,1),block(-3,1)),
			Map.entry(block(-3,2),block(-3,2)),
			Map.entry(block(-3,3),block(-3,3))
			);

	private static Map<Coordinate,Coordinate> regularGameBoard = Map.ofEntries( 
			Map.entry(block(0,0),block(0,0)),
			Map.entry(block(0,1),block(0,1)),
			Map.entry(block(0,2),block(0,2)),
			Map.entry(block(0,3),block(0,3)),
			Map.entry(block(1,0),block(1,0)),
			Map.entry(block(1,1),block(1,1)),
			Map.entry(block(1,2),block(1,2)),
			Map.entry(block(1,3),block(1,3)),
			Map.entry(block(1,4),block(1,4)),
			Map.entry(block(2,1),block(2,1)),
			Map.entry(block(2,2),block(2,2)),
			Map.entry(block(2,3),block(2,3)),
			Map.entry(block(2,4),block(2,4)),
			Map.entry(block(3,1),block(3,1)),
			Map.entry(block(3,2),block(3,2)),
			Map.entry(block(3,3),block(3,3)),
			Map.entry(block(3,4),block(3,4)),
			Map.entry(block(3,5),block(3,5)),
			Map.entry(block(4,2),block(4,2)),
			Map.entry(block(4,3),block(4,3)),
			Map.entry(block(4,4),block(4,4)),
			Map.entry(block(4,5),block(4,5)),
			Map.entry(block(5,2),block(5,2)),
			Map.entry(block(5,3),block(5,3)),
			Map.entry(block(5,4),block(5,4)),
			Map.entry(block(5,5),block(5,5)),
			Map.entry(block(5,6),block(5,6))
			)
			;
	public static HashMap<Coordinate, Coordinate> copyBoardMap(int mode){
		HashMap<Coordinate, Coordinate> deepCopy = new HashMap<Coordinate, Coordinate>();
		switch(mode) {
		case(0)://non-tiled
			originalGameBoard.forEach((k,v) -> deepCopy.put(k.clone(), v.clone()));
			break;
		default:
			regularGameBoard.forEach((k,v) -> deepCopy.put(k.clone(), v.clone()));
			break;
		}
		return deepCopy;
	}
	
	public static Set<Coordinate> getGameBoard(int mode){
		switch(mode) {
		case(0)://non-tiled
			return originalGameBoard.keySet();
		default:
			return regularGameBoard.keySet();
		}	
	}
	
	private static Coordinate toroidalShift(Coordinate original, int mode) {
		int aXShift = -6;//hardcoded
		int aYShift = -3;
		int bXShift = -1;
		int bYShift = 4;
		
		Coordinate toroidalShifted = new Coordinate(original.getX(), original.getY());
		if(toroidalShifted.getX()-2*toroidalShifted.getY() < -7) {//off the "right edge"
			toroidalShifted.setAll(toroidalShifted.getX() - bXShift, toroidalShifted.getY() - bYShift);
		}
		if(toroidalShifted.getX()-2*toroidalShifted.getY() > 1) {//off the "left edge"
			toroidalShifted.setAll(toroidalShifted.getX() + bXShift, toroidalShifted.getY() + bYShift);
		}
		if(toroidalShifted.getX() < 0) {//"bottom edge"
			toroidalShifted.setAll(toroidalShifted.getX() - aXShift, toroidalShifted.getY() - aYShift);
		}
		if(toroidalShifted.getX() > 5) {//"top edge"
			toroidalShifted.setAll(toroidalShifted.getX() + aXShift, toroidalShifted.getY() + aYShift);
		}
		return toroidalShifted;
	}
	
	public static Coordinate validPlacement(Coordinate loc, int mode, int pieceNum, int normNum, int normIter, int placementFlag) {
		switch(mode){
		case(0)://non-tiled
			if(!checkValid(loc, mode)) {
				break;
			}else {
				return loc;
			}
		default://tiled
			int count = 0;
 			while(!checkValid(loc, mode)) {
				loc = toroidalShift(loc, mode);
				count++;
			}
 			ArrayList<Coordinate> normalizer = new ArrayList<>();
 			switch(normNum) {
 				case 0:
 					normalizer = new ArrayList<>(Arrays.asList(normalizedOrange));
 					break;
 				case 1:
 					normalizer = new ArrayList<>(Arrays.asList(normalizedGreen));
 					break;
 				case 2:
 					normalizer = new ArrayList<>(Arrays.asList(normalizedElbow[normIter]));
 					break;
 			}		
 			
			if(pieceNum != normNum && normalizer.contains(loc) && placementFlag != -1) {
				break;
			}
			return loc;
		}
		return PuzzleUtils.INVALID_COORD;
	}
	private static boolean checkValid(Coordinate coord, int mode) {
		switch(mode) {
		case(0)://non-tiled
			return originalGameBoard.containsKey(coord);
		default:
			return regularGameBoard.containsKey(coord);
		}
	}
	public static Coordinate block(int x, int y) {
		Coordinate result = new Coordinate(x,y);
		result.setAll(x, y);
		return result;
	}

	public static PuzzlePiece puzzlePiece(Coordinate[] blocks, int pieceNum) {
		return new PuzzlePiece(Arrays.asList(blocks), pieceNum);
	}
	
	public static Set<PuzzlePiece> generateAllPlacementsOfAllOrientations(
			PuzzlePiece piece, int mode, int normMode, int normIter) {
		
		// Generate all orientations of the basic pieces
		Set<PuzzlePiece> allOrientations = new HashSet<>();
		allOrientations.add(piece);
		PuzzlePiece lastRot = piece.clone();
		if(piece.getNumber() !=2) {
			for(int i = 0; i < 6; i++) {
				lastRot.rotate(i, mode);
				Set<Coordinate> possibleRotation = lastRot.coordinates;
				Set<Coordinate> possibleRotationPostNormalization = new HashSet<>();
				for(Coordinate coord : possibleRotation) {
					Coordinate toAdd = PuzzleUtils.validPlacement(coord, mode, lastRot.getNumber(), normMode, normIter, placementFlag);
					possibleRotationPostNormalization.add(toAdd);
				}
				lastRot.setCoordinates(possibleRotationPostNormalization);
				allOrientations.add(lastRot);
				lastRot = piece.clone(); //toFix: make rotation return a new piece
			}
		}
		// Generate all placements of all orientations
		Set<PuzzlePiece> allPlacements = new HashSet<>();
		for (PuzzlePiece orientation : allOrientations) {
			allPlacements.addAll(generateAllPlacements(orientation, mode, normMode, normIter));
		}
		
		return allPlacements;		
	}
		
	public static Set<PuzzlePiece> generateAllPlacements(
			PuzzlePiece orientation, int mode, int normMode, int normIter) {
		int[] dimensions = orientation.dimensions();
		int width = dimensions[0];
		int height = dimensions[1];
		int xMin, xMax;
		int yMin, yMax;
		switch(mode) {
		case(0):
			xMin = -3 + width / 2; xMax = 3 - width/2;
			yMin = -2 + height/2; yMax = 3 - height/2;
			break;
		default:
			xMin = 0; xMax = 5;
			yMin = 0; yMax = 5;
		}
		Set<PuzzlePiece> placements = new HashSet<>();
		int pieceNum = orientation.getNumber();
		PuzzlePiece rotation = orientation.clone();
		switch(mode) {
		case(0):
			for (int deltaX = xMin; deltaX <= xMax; ++deltaX) { //floor division
				for (int deltaY = yMin; deltaY <= yMax; ++deltaY) {//are these checks necessary after verif. in translate?
						PuzzlePiece placement = rotation.clone();
						if(placement.translate(deltaX, deltaY, mode, pieceNum, normMode, normIter)) {
							placements.add(placement);
						}
				}
			}
			break;
		default:
			switch(normMode) {
			case(2):
				if(rotation.getNumber()==2) {
					placements.add(puzzlePiece(normalizedElbow[normIter], 2));
				}else for (int deltaX = xMin; deltaX <= xMax; ++deltaX) { //floor division
					for (int deltaY = yMin; deltaY <= yMax; ++deltaY) {//are these checks necessary after verif. in translate?
							PuzzlePiece placement = rotation.clone();
							if(placement.translate(deltaX, deltaY, mode, pieceNum, normMode, normIter)) {
								placements.add(placement);
							}
					}
				}
				break;
			case(1):
				if(rotation.getNumber()==1) {
					placements.add(puzzlePiece(normalizedGreen, 1));
				}else for (int deltaX = xMin; deltaX <= xMax; ++deltaX) { //floor division
					for (int deltaY = yMin; deltaY <= yMax; ++deltaY) {//are these checks necessary after verif. in translate?
							PuzzlePiece placement = rotation.clone();
							if(placement.translate(deltaX, deltaY, mode, pieceNum, normMode, normIter)) {
								placements.add(placement);
							}
					}
				}
				break;
			case(0):
				if(rotation.getNumber()==0) {
					placements.add(puzzlePiece(normalizedOrange, 0));
				}else for (int deltaX = xMin; deltaX <= xMax; ++deltaX) { //floor division
					for (int deltaY = yMin; deltaY <= yMax; ++deltaY) {//are these checks necessary after verif. in translate?
							PuzzlePiece placement = rotation.clone();
							if(placement.translate(deltaX, deltaY, mode, pieceNum, normMode, normIter)) {
								placements.add(placement);
							}
					}
				}
				break;
			default:
				for (int deltaX = xMin; deltaX <= xMax; ++deltaX) { //floor division
					for (int deltaY = yMin; deltaY <= yMax; ++deltaY) {//are these checks necessary after verif. in translate?
							PuzzlePiece placement = rotation.clone();
							if(placement.translate(deltaX, deltaY, mode, pieceNum, normMode, normIter)) {
								placements.add(placement);
							}
					}
				}
				break;
			}
		}
		return placements;
	}

	public static String dumpPuzzlePiece(PuzzlePiece piece, int mode) {
		StringBuilder buf = new StringBuilder(40);
		buf.append("Piece: ");
		buf.append(piece.toString());
		buf.append("\n");
		Set<PuzzlePiece> allWaysAround = new HashSet<>();
		buf.append("Before normalization: ");
		buf.append(piece);
		buf.append("\n");
		
		
		// No rotation, normalized
		PuzzlePiece rot0 = piece.clone();
//		PuzzlePiece.normalize(rot0);
		buf.append("Normalized: ");
		buf.append(piece);
		buf.append("\n");
		
		allWaysAround.add(piece);
		
		// Rotated about Z axis
		PuzzlePiece rot1 = rot0.clone();
		rot1.rotate(3, mode);
		buf.append("Rotated: ");
		buf.append(rot1);
		buf.append("\n");
		
		allWaysAround.add(rot1);

		// Dump out everything in set of all possible rotations
		buf.append("Now, showing all pieces after putting in set:");
		buf.append("\n");
		for (PuzzlePiece aPiece : allWaysAround) {
			buf.append("   ");
			buf.append(aPiece.toString());
			buf.append("\n");
		}
		
		return buf.toString();
	}
	
}
