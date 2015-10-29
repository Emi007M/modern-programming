/**
 * A primary structure consisting of 2 competitor numbers and describing a
 * single match
 * 
 * @author Emilia
 *
 */
public class Pair {

	Integer x;
	Integer y;

	public Pair(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Integer getX() {
		return x;
	}

	public Integer getY() {
		return y;
	}

	/**
	 * @param x what number are we looking for
	 * @return true if it matches
	 */
	public boolean contains(int x) {
		return x == getX() || x == getY();
	}

	@Override
	public String toString() {
		return getX() + "-" + getY();
	}
}
