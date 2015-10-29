import java.util.ArrayList;

/**
 * structure containing ArrayList of Pairs; it shows e.g. list of Pairs of a
 * single day
 * 
 * @author Emilia
 *
 */
public class Bucket {

	/**
	 * list of Pairs
	 */
	public ArrayList<Pair> bucket_pairs = new ArrayList<>();;

	public Bucket() {
	}

	public void add(int i, int j) {
		bucket_pairs.add(new Pair(i, j));
	}

	public void add(Pair pair) {
		bucket_pairs.add(pair);
	}

	/**
	 * checks if in the Bucket already exists any of the numbers from the given
	 * Pair
	 * 
	 * @param _pair
	 * @return
	 */
	public boolean containsCompetitor(Pair _pair) {
		if (bucket_pairs.isEmpty())
			return false;

		int _x = _pair.getX();
		int _y = _pair.getY();

		for (Pair pair : bucket_pairs) {
			if (pair.contains(_x) || pair.contains(_y))
				return true;
		}

		return false;

	}

	/**
	 * Find the Pair in the Bucket which contains number equal to the X from the
	 * given Pair but the other number is different
	 * 
	 * @param p
	 * @return
	 */
	public Pair findPairByX(Pair p) {
		int x = p.getX();
		for (Pair pair : bucket_pairs) {
			if (pair.contains(x) && !pair.equals(p))
				return pair;
		}
		return p;
	}

	/**
	 * Find the Pair in the Bucket which contains number equal to the Y from the
	 * given Pair but the other number is different
	 * 
	 * @param p
	 * @return
	 */
	public Pair findPairByY(Pair p) {
		int x = p.getY();
		for (Pair pair : bucket_pairs) {
			if (pair.contains(x) && !pair.equals(p))
				return pair;
		}
		return p;
	}

	@Override
	public String toString() {
		String ret = "";
		for (Pair pair : bucket_pairs) {
			ret += pair + "\t";
		}
		return ret;
	}
}
