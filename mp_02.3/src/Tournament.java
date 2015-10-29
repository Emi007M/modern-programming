/**
 * <h3>Problem 3.</h3>
 * You are to organise a tournament involving n competitors. Each competitor must play exactly once
 * against each of his opponents. Moreover each competitor must play exactly one match every day.
 * Assuming that n is a power of 2, give an algorithm to construct a timetable allowing the tournament
 * to be finished in n-1 days.
 *
 * @author Emilia
 *
 */
public class Tournament {
	
	/**
	 * number of competitors
	 */
	private static int competitors;

	public static void main(String[] args) {
		
		try {
			setCompetitors(8);
		} catch (Exception e) {
			System.out.println("Wrong number of cometitors. Must be a power of 2");
			return;
		}
		
		Timetable timetable = new Timetable(competitors);
		
		System.out.println("--Timetable for "+competitors+" competitors--\n");
		System.out.println(timetable);

	}
	
	/**
	 * 
	 * @param x how many competitors are
	 * @throws Exception when number is not a power of 2
	 */
	static void setCompetitors(int x) throws Exception{
		if(!((x & -x) == x)) throw new Exception();
		competitors = x;
	}

}
