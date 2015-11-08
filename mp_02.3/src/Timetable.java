/**
 * Timetable is responsible for creating the schedule according to only a number
 * of competitors
 * 
 * @author Emilia
 *
 */
public class Timetable {

	/**
	 * number of competitors
	 */
	private int competitors;

	/**
	 * <p>
	 * Set of <code>ArrayLists</code> containing <code>Pairs</code> of
	 * competitors
	 * </p>
	 * <p>
	 * It holds all the possible combinations of competitors, i.e. C*(C-1)/2
	 * </p>
	 * <p>
	 * Consists of C/2 Buckets
	 * </p>
	 */
	private Bucket[] unordered_pairs;

	/**
	 * It holds ordered Pairs on the daily schedule
	 */
	private Bucket[] days;

	public Timetable(int competitors, boolean naive) {
		this.competitors = competitors;
		setPairs();

		long start;
		long stop;

		if (!naive) {
			start = System.nanoTime();
			orderDays();
			stop = System.nanoTime();
			System.out.println("Time fast: \t" + (stop - start) + " nanoseconds");
		} else {
			start = System.nanoTime();
			orderDaysNaive();
			stop = System.nanoTime();
			System.out.println("Time naive: \t" + (stop - start) + " nanoseconds");
		}

	}

	/**
	 * <p>
	 * Generates all possible <code>Pairs</code> and puts it into <b>Bucket[]
	 * unordered_pairs</b>
	 * </p>
	 * <p>
	 * there are C/2 buckets - it is used to implement <b><u>@DEVIDE-AND-CONQUER
	 * strategy</u></b>
	 * </p>
	 * 
	 * <p>
	 * When Buckets are filled, each (beside the last one) contains 2 unsorted
	 * sets of data i.e. has the information about 2 days of competitions
	 * </p>
	 * 
	 * <p>
	 * Last bucket already consists of a correct set of Pairs
	 * </p>
	 */
	private void setPairs() {

		int groups = competitors / 2;
		unordered_pairs = new Bucket[groups];

		for (int i = 0; i < groups; i++)
			unordered_pairs[i] = new Bucket();

		int currentGroup = 0;
		int dir = 1;

		for (int i = 1; i < competitors; i++) {
			dir = 1;
			currentGroup = 0;
			for (int j = i + 1; j <= competitors; j++) {
				unordered_pairs[currentGroup].add(i, j);
				if (currentGroup == groups - 1)
					dir = -1;
				currentGroup += dir;

			}
		}
	}

	/**
	 * <p>
	 * Uses <b>Bucket[] unordered_pairs</b> as a data set to create correct
	 * daily schedules it takes each Bucket and divides it properly into 2 days
	 * of competitions the last Bucket is being rewritten
	 * </p>
	 * 
	 * <p>
	 * It fills in the <b>Bucket[] days</b> array
	 * </p>
	 */
	private void orderDays() {
		days = new Bucket[competitors - 1];
		for (int i = 0; i < days.length; i++) {
			days[i] = new Bucket();
		}

		int currentDay = 0;
		for (Bucket bucket : unordered_pairs) {

			divideIntoTwo(bucket);

			for (int i = 0; i < competitors / 2; i++)
				days[currentDay].add(bucket.bucket_pairs.get(i));
			if (currentDay != competitors - 2) {
				for (int i = competitors / 2; i < competitors; i++)
					days[currentDay + 1].add(bucket.bucket_pairs.get(i));
			}

			currentDay += 2;

		}

	}

	/**
	 * <p>
	 * Sorts Pairs in the given Bucket in such a way, the first half will create
	 * 1 correct day, and so as the second half
	 * </p>
	 * 
	 * @param b
	 */
	private void divideIntoTwo(Bucket b) {

		Bucket b1 = new Bucket();
		Bucket b2 = new Bucket();

		Pair temp;

		b1.add(b.bucket_pairs.get(0));
		boolean added = false;
		do {
			// System.out.println(this); //tu fajnie widaæ. do wykasowania przy
			// pomiarach

			for (Pair pair : b1.bucket_pairs) {
				temp = b.findPairByX(pair);
				if (!b2.containsCompetitor(temp)) {
					b2.add(temp);
					added = true;
				}

				temp = b.findPairByY(pair);
				if (!b2.containsCompetitor(temp)) {
					b2.add(temp);
					added = true;
				}
			}
			for (Pair pair : b2.bucket_pairs) {
				temp = b.findPairByX(pair);
				if (!b1.containsCompetitor(temp)) {
					b1.add(temp);
					added = true;
				}

				temp = b.findPairByY(pair);
				if (!b1.containsCompetitor(temp)) {
					b1.add(temp);
					added = true;
				}
			}
			while (!added) {
				for (Pair pair : b.bucket_pairs) {
					if (!b1.containsCompetitor(pair)) {
						b1.add(pair);
						added = true;
						break;
					}
				}
			}

			added = false;

		} while (b1.bucket_pairs.size() < competitors / 2 || b2.bucket_pairs.size() < competitors / 2);

		b.bucket_pairs.clear();
		b.bucket_pairs.addAll(b1.bucket_pairs);
		b.bucket_pairs.addAll(b2.bucket_pairs);

	}

	private void orderDaysNaive() {
		Bucket allPairs = new Bucket();
		for (int i = 0; i < unordered_pairs.length; i++)
			allPairs.bucket_pairs.addAll(unordered_pairs[i].bucket_pairs);

		days = new Bucket[competitors - 1];
		for (int i = 0; i < days.length; i++) {
			days[i] = new Bucket();
		}
		int day = 0;
		
		if (!orderDaysNaive(day, allPairs, days))
			System.out.println("ERROR. answer not found");

	}

	
	private boolean orderDaysNaive(int i, Bucket all, Bucket[] days) {

		// System.out.println(this); //tu fajnie widaæ. do wykasowania podczas
		// pomiarów

		if (i >= all.bucket_pairs.size())
			return true;
		boolean notSorted = true;
		Pair tmp = all.bucket_pairs.get(i);
		int j = 0;

		while (notSorted && j < days.length) {
			if (!days[j].containsCompetitor(tmp)) {
				days[j].add(tmp);
				notSorted = !orderDaysNaive(i + 1, all, days);
				if (notSorted)
					days[j].bucket_pairs.remove(days[j].bucket_pairs.size() - 1);
			}
			j++;
		}
		if (notSorted)
			return false;
		return true;
	}

	@Override
	public String toString() {
		String r = "";
		int i = 0;
		for (Bucket bucket : days) {
			System.out.println("Day " + (++i) + ":\t" + bucket);
		}
		return r;
	}

}
