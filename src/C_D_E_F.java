
import java.text.ParseException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * Program for part C,D,E,F
 * @author tinni
 *
 */

public class C_D_E_F {
	
	final static private String[] f = { "Edinburgh", "Heathrow", "Dubai", "Sydney", "Kuala Lumpur", "Frankfurt", "Auckland",
			"New York", "Rio de Janeiro", "Santiago" };

	static private FlightGraph flightGraph = createFlightsGraph();
	static private Itinerary it=new Itinerary();

/**
 * Main program of part C_D_E_F , here is where the scanner gets all of the information 
 * we need, to print the itinerary in class Itinerary
 * @param args
 * @throws ParseException
 */
	public static void main(String[] args) throws ParseException {
		printWelcome();
		Scanner s = new Scanner(System.in);
		System.out.println("The Following Airports are used:" + "\n" + AllAirports(flightGraph));
		System.out.println("Which Airport Are You Leaving From?"+"\n");
		while(s.hasNext()){
	    
		String From=s.nextLine().toLowerCase();
		while(!flightGraph.containsVertex(From)){
			System.out.println("The Airport Does not match one of Our Airports Partners, Please Try Again");
		    From=s.nextLine().toLowerCase();
			}
		System.out.println("Perfect, Where would you Like To go Today?"+"\n");
		String To=s.nextLine().toLowerCase();
		while(!flightGraph.containsVertex(To)||From.equals(To)){
			System.out.println("Invalid Destination, Please Try Again");
		    To=s.nextLine().toLowerCase();
			}
		
		System.out.println("Type 'shortest' for shortest path or 'cheapest' for cheapest path ");
		String pathType = s.nextLine().toLowerCase();
		while (!pathType.toLowerCase().equals("shortest") && !pathType.toLowerCase().equals("cheapest")) {
		    System.out.println(pathType+" is not valid input,"+"\n"+"Type 'shortest' for shortest path or 'cheapest' for cheapest path ");
		  pathType = s.nextLine().toLowerCase();
		}
		System.out.println(it.it(From, To,pathType, flightGraph));
		break;
		}
		s.close();

	}

	private static void printWelcome() {
		System.out.println("<<<<<<< Welcome to JD Airline >>>>>>>" + "\n" + "The following airports are used:" + "\n");
		
	}


	/**
	 * populating the graph creating the edges, containing the Price(weight),FlightNum,DepartureTm,ArrivalTm,FlightDur
	 */
	private static FlightGraph createFlightsGraph() {
		FlightGraph g = new FlightGraph(
				FlightEdge.class);	
		
		TwoWayEdge(g, f[0], f[1], 89,91,"EDHT22","HTED23","16:30","14:30","17:30","15:30","01:00");

		TwoWayEdge(g, f[1], f[2], 130,119,"HTDB11","DBHT12","12:00","18:00","14:00","20:00","04:00");

		TwoWayEdge(g, f[1], f[3], 570,570,"HTSD32","SDHT33","06:40","19:30","09:30","22:00","08:00");
		
		TwoWayEdge(g, f[2], f[4], 170,149,"DBKL09","KLDB01","12:30","17:00","19:10","23:40","06:40");
		
		TwoWayEdge(g, f[2], f[0], 190,132,"DBED41","EDDB49","20:00","16:30","00:20","01:00","05:20");
		
		TwoWayEdge(g, f[4], f[3], 150,169,"KLSD35","SDKL39","12:30","04:10","19:05","12:15","09:30");

		TwoWayEdge(g, f[0], f[5], 90,109,"EDFK13","FKED09","08:20","17:00","13:00","19:40","03:15");
		
		TwoWayEdge(g, f[3], f[6], 180,168,"SDAU02","AUSD20","9:20","12:00","11:20","14:00","02:00");
		
		TwoWayEdge(g, f[8], f[7], 209,198,"RJNY32","NYRJ35","23:00","07:00","05:30","12:30","03:30");
		
		TwoWayEdge(g, f[7], f[9], 159,138,"NYST51","STNY55","08:20","17:00","13:00","20:40","05:40");

  
		return g;

	}

	/**
	 * helper method to create two way connection, for the vertexes that are connected.
	 * @param g
	 * @param f1
	 * @param f2
	 * @param price
	 * @param price2
	 * @param flightNum1
	 * @param flightNum2
	 * @param DepTm1
	 * @param DepTm2
	 * @param ArrTm1
	 * @param ArrTm2
	 * @param duration
	 */
	private static void TwoWayEdge(FlightGraph g, String f1, String f2,
			double price,double price2,String flightNum1,String flightNum2,String DepTm1,String DepTm2,
			String ArrTm1,String ArrTm2,String duration) {
		f1 = f1.toLowerCase();
		f2 = f2.toLowerCase();
	   
		g.addVertex(f1);
		g.addVertex(f2);
		g.addEdge(f1, f2);
		g.addEdge(f2, f1);
		
        FlightEdge onGoing=g.getEdge(f1, f2);
        FlightEdge onReturn=g.getEdge(f2, f1);
        
		g.setEdgeWeight(onGoing, price);
		g.setEdgeFlightNum(onGoing, flightNum1);
		g.setEdgeDepTm(onGoing, DepTm1);
		g.setEdgeArrTm(onGoing, ArrTm1);
		g.setEdgeFlightDur(onGoing, duration);
		
		g.setEdgeWeight(onReturn, price2);
		g.setEdgeFlightNum(onReturn, flightNum2);
		g.setEdgeDepTm(onReturn, DepTm2);
		g.setEdgeArrTm(onReturn, ArrTm2);
		g.setEdgeFlightDur(onReturn, duration);
		
		
		
	}
	
	private static String AllAirports(FlightGraph flightGraph2) {
		Set<String> s=flightGraph2.vertexSet();
	Iterator<String> it=s.iterator();
	String toReturn="";
	while(it.hasNext()){
		toReturn+=it.next()+"\n";
	
	}
		return toReturn;
	}
}
