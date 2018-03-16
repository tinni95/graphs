import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Formatter;

import java.util.List;


import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.AsUnweightedDirectedGraph;

/**
 * 
 * @author tinni
 *
 */
public class Itinerary {
	private static Calendar local = Calendar.getInstance();
	private static SimpleDateFormat df = new SimpleDateFormat("HH:mm");
	
	/* *****************************************************
	* Part D: Itinerary                                    *
	****************************************************** */
	

	public Itinerary() {

	}

	/**
	 * this method computes the itinerary for a path asked by the user in the
	 * main program, the output is a string formatted with stringFormat
	 * I did not put any method identifier, because the method is just called in the same package
	 * @param From
	 * @param To
	 * @return
	 * @throws ParseException
	 */
	 String it(String From, String To, String pathType, FlightGraph g) throws ParseException {
		local.setTime(df.parse("00:00"));
		String toReturn = "Great, Your Itinerary for " + this.adjust(From) + " to " + this.adjust(To) + " is: " + "\n";
		Formatter f = new Formatter();

		DijkstraShortestPath<String, FlightEdge> path = new DijkstraShortestPath<String, FlightEdge>(g, From, To);

		/**
		 * if the user select the shortest path, the DijkstraShortestPath is
		 * calculated on an AsUnweightedDirectedGraph, in this way the
		 * weight(price) of the edges is ignored and the shortest path is
		 * returned instead this is for part F4
		 */
		if (pathType.equals("shortest")) {
			AsUnweightedDirectedGraph<String, FlightEdge> unweightedGraph = new AsUnweightedDirectedGraph<String, FlightEdge>(
					g);
			path = new DijkstraShortestPath<String, FlightEdge>(unweightedGraph, From, To);
		}
		/**
		 * if the user has selected the cheapest path, the DijkstraShortestPath
		 * is calculated on our weighted FlightGraph, as declared on the fifth
		 * line of the method
		 */

		if (path.getPath() != null) {
			toReturn = f.format("%5s %15s %7s %9s %15s %7s", "Leg", "Leave", "At", "On", "Arrive", "At" + "\n")
					.toString();
			List<String> it = path.getPath().getVertexList();
			List<FlightEdge> ft = path.getPathEdgeList();
			int l = 1;
			for (int i = 0; i < it.size() - 1; i++) {
				FlightEdge F = ft.get(i);
				toReturn = f.format("%5s %15s %7s %9s %15s %7s", l, adjust(it.get(i).toString()), F.DepTm(),
						F.getFlightNum(), adjust(it.get(i + 1).toString()), F.ArrivalTm() + "\n").toString();
				l++;

			}

			/**
			 * instead of using the pre-built method of DijkstraShortestPath
			 * getWeight(), I created my own which manually gets the weights of
			 * the edges in graph g, this was necessary to calculate the right
			 * price(weight) for the shortest path, for part F4 without part F4
			 * i would use path.getPath().getWeight();
			 */
			toReturn += "\n" + "  Total price is: £" + getWeight(path.getPath().getEdgeList(), g);
			
            /* *****************************************************
            * END OF PART D                                        *
            ****************************************************** */
			
            /* *****************************************************
            * Part E: Itinerary Duration                           *
            ****************************************************** */
		
			Calendar AirTime = calculateTimeInAir(ft);
			toReturn += "\n" + "  Total Time in air is: " + printTime(AirTime) + " hrs";
			
            /* *****************************************************
            * END OF PART E                                        *
            ****************************************************** */
			Calendar LandTime = calculateLandTime(ft);

			toReturn += "\n" + "  Total Time between Flights: " + printTime(LandTime) + " hrs";
			toReturn += "\n" + "  Total Journey Time: " + (printTime(TotalJourneyTime(AirTime, LandTime))) + " hrs";

		} else {
			toReturn = "Sorry, There are no route available for your flight";
		}

		f.close();
		return toReturn;
	}
	

	/* *****************************************************
	* Part E: Itinerary Duration                           *
	****************************************************** */
	/**
	 * A method to add the flight time of the flights in the path it gets the
	 * list of flight edges from the path as parameter It adds it using java
	 * SimpleDateFormat and Calendar classes
	 * 
	 * @param ft
	 * @return
	 * @throws ParseException
	 */
	private Calendar calculateTimeInAir(List<FlightEdge> ft) throws ParseException {

		Calendar cal = Calendar.getInstance();

		cal.setTime(df.parse("00:00"));

		for (FlightEdge f : ft) {

			String[] duration1 = f.FlightDur().split(":");
			addTime(cal, Integer.parseInt(duration1[0]), Integer.parseInt(duration1[1]));
		}

		return cal;

	}
	/* ******************************************************
	* Part F1: Total Journey Time                           *
	******************************************************* */
/**
 * A method which return a Calendar object consisting of the sum of two Calendars
 * a and b, which in this program are the time spent in air and the time between flights
 * returning as result the total journey time.
 * @param a
 * @param b
 * @return
 */
	private Calendar TotalJourneyTime(Calendar a, Calendar b) {
		int date1 = a.get(Calendar.DAY_OF_YEAR);
		int date2 = b.get(Calendar.DAY_OF_YEAR);
		//if date 2(calendar b) is bigger, we need to add the time to calendar b 
		//adding also the day offset calculated subtracting the local day_of_year
         if (date2 > date1) {
			addTime(b, a.get(Calendar.HOUR_OF_DAY), a.get(Calendar.MINUTE));
			b.add(Calendar.DAY_OF_YEAR, a.get(Calendar.DAY_OF_YEAR)-local.get(Calendar.DAY_OF_YEAR));
			return b;
		} else {
			addTime(a, b.get(Calendar.HOUR_OF_DAY), b.get(Calendar.MINUTE));
			a.add(Calendar.DAY_OF_YEAR, b.get(Calendar.DAY_OF_YEAR)-local.get(Calendar.DAY_OF_YEAR));
			return a;
		}
	}
/**
 * This method calculates the total time needed to be spent between more flights,
 * it does that subtracting to the departure time of the n+1 flight, the arrival time of n flight.
 * and then adding the result to a second Calendar that will be returned with the total land time.
 * @param ft
 * @param df
 * @return
 * @throws ParseException
 */
	private Calendar calculateLandTime(List<FlightEdge> ft) throws ParseException {

		Calendar toReturn = Calendar.getInstance();
		Calendar calculation = Calendar.getInstance();

		toReturn.setTime(df.parse("00:00"));

		if (ft.size() == 1)
			return toReturn;

		for (int i = 0; i < ft.size() - 1; i++) {

			String[] dep1 = ft.get(i).ArrivalTm().split(":");
			String[] dep2 = ft.get(i + 1).DepTm().split(":");

			calculation.setTime(df.parse("00:00"));

			addTime(calculation, Integer.parseInt(dep2[0]), Integer.parseInt(dep2[1]));

			addTime(calculation, -Integer.parseInt(dep1[0]), -Integer.parseInt(dep1[1]));

			addTime(toReturn, calculation.get(Calendar.HOUR_OF_DAY), calculation.get(Calendar.MINUTE));

		}
		return toReturn;

	}
   /* *****************************************************
   * END OF PART F1                                       *
   ****************************************************** */

   /* ******************************************************
   * Part F2: Journeys that span more than a day           *
   ******************************************************* */
  /**
   * this method calculates how many days required by a journey
   * comparing the Calendar object for the journey
   * @param df
   * @param cal1
   * @param cal2
   * @return
   */
	private String printTime(Calendar cal1) {
		
		int day = cal1.get(Calendar.DAY_OF_YEAR) - local.get(Calendar.DAY_OF_YEAR);
		if (day == 1)
			return day + " day and " + df.format(cal1.getTime());
		else if (day > 1 )
			return day + " days and " + df.format(cal1.getTime());
		else
	
			return df.format(cal1.getTime());
	}
   /* ******************************************************
   * End of Part F2                                        *
   ******************************************************* */
	
   /* **************************************************************************************
   * Part F4 :Extend your program to locate routes with the fewest number of changeovers   *
   *************************************************************************************** */
/**
 * I created this method to return the correct price for a shortest path
 * given that the shortest path is calculated on an unweighted graph I need to get 
 * the price manually and not by the DijkstraShortestPath, because that would return 
 * the DefaultWeightedEdge I.E. 1
 * @param edgeList
 * @param g
 * @return
 */
	private double getWeight(List<FlightEdge> edgeList, FlightGraph g) {
		int price=0;
		for(FlightEdge edge:edgeList){
         price+=edge.price();
		}
		return price;
	}
	
    /* *****************************************************
    * END OF PART F4                                       *
    ****************************************************** */
	
    /* *****************************************************************************
    * HELPER METHODS USED BY MORE THAN ONE SECTION OF THE PROGRAM                  *
    ****************************************************************************** */
	/**
	 * this is a helper method used for returning the airport name with capital
	 * first letter
	 * 
	 * @param s
	 * @return
	 */
	String adjust(String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}
	/**
	 * This method is used all over the program to facilitate the addition of hours and minutes 
	 * in calendar
	 * @param cal
	 * @param hours
	 * @param minutes
	 */
	private void addTime(Calendar cal, int hours, int minutes) {
		
		cal.add(Calendar.HOUR_OF_DAY, hours);
		cal.add(Calendar.MINUTE, minutes);

	}

}


