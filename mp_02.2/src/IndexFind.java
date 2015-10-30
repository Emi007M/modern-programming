import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * <h3>Problem 2.</h3> 
 * Let T[0,...,n-1] be a sorted array of integers, some of
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
		long[] times = new long[trials];

		
		do {
			trials--;
			T = doArray(size);
			long start;
			long stop;
			
			Arrays.sort(T);
			// for (Integer i : T) {
			// System.out.print(i + " ");
			// }

	
			start = System.nanoTime();
			try {
				find(T);
				System.out.println("\nIndex is " + index + "");
				
			} catch (Exception e) {
				System.out.println("\nIndex not found");
			} 
			stop = System.nanoTime();
			

			times[trials] = stop - start;
			System.out.println("Time: " + times[trials] + " nanoseconds");

		} while (0 != trials);

		Arrays.sort(times);
		long meanTime = times[times.length / 2];
		System.out.println("\n---\nMean time: " + meanTime + " nanoseconds");
		
	}

	/**
	 * Finding an index with use of recurrence to implement 
	 * <b><u>DEVIDE-AND-CONQUER strategy</u></b>
	 * 
	 * @param T input array
	 * @throws Exception if no index was found
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
	 * @param L left boundary of a searched field in the array
	 * @param R right boundary of a searched field in the array
	 * @param T input array
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
