package hex_game;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class PuzzlePiece implements Cloneable {
	
	Set<Coordinate> coordinates = new HashSet<>();
	int number;
	public PuzzlePiece(Iterable<Coordinate> coordinates, int pieceNum) {
		
		for (Coordinate coordinate : coordinates) {
			this.coordinates.add(coordinate);
		}
		this.number = pieceNum;

	}
	
	/**
	 * Rotate piece, i.e. rotate 60 degrees clockwise
	 */
	public void rotate(int step, int mode) {
		Set<Coordinate> modifiedCoords = new HashSet<>();
		int y = 0;
		int x = 0;
		// Rotate (60 degrees clockwise)
		switch(mode) {
		case(0):
			for (Coordinate coordinate : this.coordinates) {
				Coordinate toAdd = new Coordinate(-1,-1);
				y = coordinate.getY();
				x = coordinate.getX();
				toAdd.setX(-y);
				toAdd.setY(y+x);
				modifiedCoords.add(toAdd);
			}
			break;
		default:
			switch(step % 6) {
			case(0):
				modifiedCoords.addAll(this.coordinates);
				break;
			case(1):
				for (Coordinate coordinate : this.coordinates) {
					Coordinate toAdd = new Coordinate(-1,-1);
					y = coordinate.getY();
					x = coordinate.getX();
					toAdd.setX(x-y);
					toAdd.setY(x);
					modifiedCoords.add(toAdd);
				}
				break;
			case(2):
				for (Coordinate coordinate : this.coordinates) {
					Coordinate toAdd = new Coordinate(-1,-1);
					y = coordinate.getY();
					x = coordinate.getX();
					toAdd.setX(-y);
					toAdd.setY(x-y);
					modifiedCoords.add(toAdd);
				}
				break;
			case(3):
				for (Coordinate coordinate : this.coordinates) {
					Coordinate toAdd = new Coordinate(-1,-1);
					y = coordinate.getY();
					x = coordinate.getX();
					toAdd.setX(-x);
					toAdd.setY(-y);
					modifiedCoords.add(toAdd);
				}
				break;
			case(4):
				for (Coordinate coordinate : this.coordinates) {
					Coordinate toAdd = new Coordinate(-1,-1);
					y = coordinate.getY();
					x = coordinate.getX();
					toAdd.setX(y-x);
					toAdd.setY(-x);
					modifiedCoords.add(toAdd);
				}
				break;
			case(5):
				for (Coordinate coordinate : this.coordinates) {
					Coordinate toAdd = new Coordinate(-1,-1);
					y = coordinate.getY();
					x = coordinate.getX();
					toAdd.setX(y);
					toAdd.setY(y-x);
					modifiedCoords.add(toAdd);
				}
				break;
			}
			break;
	}
		this.coordinates = modifiedCoords;
	}
	
	public boolean translate(int deltaX, int deltaY, int mode, int pieceNum, int normMode, int normIter) {
		Set<Coordinate> modifiedCoords = new HashSet<>();
		for (Coordinate coord : coordinates) {
			coord.setAll(coord.getX() + deltaX, coord.getY() + deltaY);
			coord = PuzzleUtils.validPlacement(coord, mode, pieceNum, normMode, normIter, 0);
			if(coord==PuzzleUtils.INVALID_COORD){
				return false;
			}
			modifiedCoords.add(coord);
		}
		coordinates = modifiedCoords;
		return true;
	}
	public void setCoordinates(Set<Coordinate> replacement) {
		this.coordinates = replacement;
	}
	public Collection<Coordinate> getCoordinates() {
		return Collections.unmodifiableCollection(coordinates);
	}
	
	public int getNumber() {
		return this.number;
	}
	@Override
	public PuzzlePiece clone() {
		Set<Coordinate> cloneCoordinates = new HashSet<>();
		for (Coordinate coord : coordinates) {
			cloneCoordinates.add(coord.clone());
		}
		return new PuzzlePiece(cloneCoordinates, this.number);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (!obj.getClass().equals(getClass()))
			return false;
		
		PuzzlePiece otherPiece = (PuzzlePiece)obj;
		return (otherPiece.coordinates.equals(this.coordinates));
	}
	
	@Override
	public int hashCode() {
		int hash = 17;
		for (Coordinate coord : coordinates) {
			hash += coord.hashCode();
		}
		return hash;
	}
	
	public int[] dimensions() {
		
		int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;
		
		int maxX = Integer.MIN_VALUE;
		int maxY = Integer.MIN_VALUE;
		
		for (Coordinate coordinate : coordinates) {
			if (coordinate.getX() < minX) 
				minX = coordinate.getX();
			if (coordinate.getY() < minY) 
				minY = coordinate.getY();

			if (coordinate.getX() > maxX) 
				maxX = coordinate.getX();
			if (coordinate.getY() > maxY) 
				maxY = coordinate.getY();

		}
		
		return new int[] { 
				Math.abs(maxX - minX + 1),
				Math.abs(maxY - minY + 1),};
	}
	
	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append("{");
		for (Coordinate coord : coordinates) {
			buf.append(coord.toString());
			buf.append(", ");
		}
		buf.append("}");
		return buf.toString();
	}
		
}
