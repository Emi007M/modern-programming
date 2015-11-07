import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * <h3>Problem 2.</h3> Let T[0,...,n-1] be a sorted array of integers, some of
 * which may be negative but all of which are different. Give an algorithm that
 * is able to find an index i such that 0≤i≤n-1 and T[i]=i, provided such an
 * index exists. Your algorithm should take a time in O(log n) in the worst
 * case.
 * 
 * @author Emilia
 *
 */
public class IndexFind {

	/**
	 * array of random distinct integers
	 */
	private static int T[];
	/**
	 * place holder for the answer
	 */
	private static int index;
	/**
	 * assistant variable
	 */
	private static boolean indexFound;

	public static void main(String[] args) {

		int size = 50000;
		int trials = 50;
		long[] timesNaive = new long[trials];
		long[] timesFast = new long[trials];
		
		do {
			trials--;
			T = doArray(size);
			long start;
			long stop;

			Arrays.sort(T);
			// for (Integer i : T) {
			// System.out.print(i + " ");
			// }

			
			// start naive method //
			start = System.nanoTime();
			indexFound = false;
			int i = 0;
			while (i < size && !indexFound) {
				if (T[i] == i)
					indexFound = true;
				i++;
			}
			stop = System.nanoTime();

			timesNaive[trials] = stop - start;
			if (indexFound)
				System.out.println("Time: " + timesNaive[trials] + " nanoseconds");
			else
				System.out.println("\nIndex not found");

			// start fast method //
			start = System.nanoTime();
			try {
				find(T);
				System.out.println("\nIndex is " + index + "");

			} catch (Exception e) {
				System.out.println("\nIndex not found");
			}
			stop = System.nanoTime();

			timesFast[trials] = stop - start;
			System.out.println("Time: " + timesFast[trials] + " nanoseconds");

		} while (0 != trials);

		
		//compiling results
		Arrays.sort(timesNaive);
		long meanTime2 = timesNaive[timesNaive.length / 2];
		System.out.println("\n---\nMean time for naive: \t" + meanTime2 + " nanoseconds");
		Arrays.sort(timesFast);
		long meanTime = timesFast[timesFast.length / 2];
		System.out.println("\nMean time for fast: \t" + meanTime + " nanoseconds");

	}

	/**
	 * Finding an index with use of recurrence to implement
	 * <b><u>DEVIDE-AND-CONQUER strategy</u></b>
	 * 
	 * @param T
	 *            input array
	 * @throws Exception
	 *             if no index was found
	 */
	public static void find(int[] T) throws Exception {
		indexFound = false;

		if (T[0] > T.length || T[T.length - 1] < 0)
			throw new Exception();

		search(0, T.length - 1, T);
		if (!indexFound)
			throw new Exception();

	}

	/**
	 * Recursive searching
	 * 
	 * @param L
	 *            left boundary of a searched field in the array
	 * @param R
	 *            right boundary of a searched field in the array
	 * @param T
	 *            input array
	 */
	public static void search(int L, int R, int[] T) {
		if (L >= R) {
			if (T[L] == L) {
				index = L;
				indexFound = true;
			}
			return;
		}

		int mid = (R - L) / 2 + L;

		if (T[mid] == mid) {
			index = mid;
			indexFound = true;
		} else if (T[mid] > mid)
			search(L, mid - 1, T);
		else
			search(mid + 1, R, T);

	}

	/**
	 * Creates the input array
	 * 
	 * @param size
	 * @return
	 */
	public static int[] doArray(int size) {
		int T[] = new int[size];
		int shift = size / 2;

		ArrayList<Integer> arrayT = new ArrayList<>();
		for (int i = 0; i < size * 2; i++) {
			arrayT.add(i - shift);
		}

		Collections.shuffle(arrayT);

		for (int i = 0; i < size; i++) {
			T[i] = arrayT.get(i);
		}

		return T;
	}

}
