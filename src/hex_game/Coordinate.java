package hex_game;

/**
 * Three-dimension coordinate of a puzzle piece
 */
public class Coordinate implements Cloneable, Comparable<Coordinate> {
	
	private int x, y, label;
	public Coordinate(int x, int y, int label) {
		this.x = x;
		this.y = y;
		this.label = label;
	}
	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setAll(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public void setLabel(int toLabel) {
		this.label = toLabel;
	}
	public int getLabel() {
		return this.label;
	}
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (!obj.getClass().equals(this.getClass()))
			return false;
		
		Coordinate other = (Coordinate)obj;
		return ((x == other.x) && (y == other.y));
	}
	
	@Override
	public int hashCode() {
		int val = 13;
		val += (17 * x);
		val += (17 * y);
		return val;
	}
	
	@Override
	protected Coordinate clone() {
		Coordinate result = new Coordinate(x,y);
		return result;
	}
	
	@Override
	public String toString() {
		return String.format("(%d, %d), %d", x, y, label);
	}

	public int compareTo(Coordinate other) {
		if (y != other.y)
			return y - other.y;
		if (x != other.x)
			return x - other.x;
		return 0;
	}
	
}
