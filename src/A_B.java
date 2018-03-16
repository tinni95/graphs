import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;


import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.*;

public class A_B {

	/* ***************************************************************
	* Part A / B: Necessary for part A and part B                    *
	**************************************************************** */
	final static String[] f = { "Edinburgh", "Heathrow", "Dubai", "Sydney", "Kuala Lumpur", "Frankfurt", "Auckland",
			"New York", "Rio de Janeiro", "Santiago" };
/**
 * main program for part A_B, here is were the user interaction is going on, and the information of the flights
 * are collected, and then processed in the same java class.
 * @param args
 */
	public static void main(String[] args) {
		SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> flightGraph = createFlightsGraph();

		Scanner s = new Scanner(System.in);

		System.out.println("<<<<<<< Welcome to JD Airline >>>>>>>" + "\n" + "PART A:" + "\n"
				+ "The following connections are available:" + "\n" + DirectFlights(flightGraph) + "\n");
		System.out.println("The Following Airports are used:" + "\n" + AllAirports(flightGraph));
		System.out.println("\n" + "Please State The Airport Where You Come From");
		while (s.hasNext()) {

			String From = s.nextLine().toLowerCase();
			while (!flightGraph.containsVertex(From)) {
				System.out.println("The Airport Does not match one of Our Airports Partners, Please Try Again");
				From = s.nextLine().toLowerCase();
			}
			System.out.println("Perfect, Where would you Like To go Today?" + "\n");
			String To = s.nextLine().toLowerCase();
			while (!flightGraph.containsVertex(To)) {
				System.out.println("The Airport Does not match one of Our Airports Partners, Please Try Again");
				To = s.nextLine().toLowerCase();
			}
			DijkstraShortestPath<String, DefaultWeightedEdge> path = new DijkstraShortestPath<String, DefaultWeightedEdge>(
					flightGraph, From, To);
			CalculatePath(path, s, From, To);
			break;
		}
		s.close();
	}

	/**
	 * Populating the graph with the connections 
	 * @return
	 */
	private static SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> createFlightsGraph() {
		SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> g = new SimpleDirectedWeightedGraph<>(
				DefaultWeightedEdge.class);

		TwoWayEdge(g, f[0], f[1], 80);

		TwoWayEdge(g, f[1], f[2], 130);

		TwoWayEdge(g, f[1], f[3], 570);

		TwoWayEdge(g, f[2], f[4], 170);

		TwoWayEdge(g, f[2], f[0], 190);

		TwoWayEdge(g, f[4], f[3], 150);

		TwoWayEdge(g, f[0], f[5], 90);

		TwoWayEdge(g, f[3], f[6], 120);

		TwoWayEdge(g, f[8], f[7], 430);

		TwoWayEdge(g, f[7], f[9], 320);

		return g;

	}
/**
 * helper method to populate the graph with two way edges connections
 * @param g
 * @param f1
 * @param f2
 * @param weight
 */
	private static void TwoWayEdge(SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> g, String f1, String f2,
			double weight) {
		
		f1 = f1.toLowerCase();
		f2 = f2.toLowerCase();
		
		g.addVertex(f1);
		g.addVertex(f2);
		g.addEdge(f1, f2);
		g.addEdge(f2, f1);

		g.setEdgeWeight(g.getEdge(f1, f2), weight);
		g.setEdgeWeight(g.getEdge(f2, f1), weight);
		
	}
	/* *****************************************************
	 * END OF PART A / B                                   *
	 ***************************************************** */
	
	/* *****************************************************
	* Part A : Representing Direct Flights                 *
	****************************************************** */
	/**
	 * This method returns a list of all the connection available, as requested for Part A
	 * The method iterates through the set of edges in the graph and uses replace to display the double connection
	 * @param flightGraph
	 * @return
	 */
	private static String DirectFlights(SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> flightGraph) {
		Set<DefaultWeightedEdge> s=flightGraph.edgeSet();
	Iterator<DefaultWeightedEdge> it=s.iterator();
	String toReturn="";
	while(it.hasNext()){
		toReturn+=it.next().toString().replace("(", "").replace(":", "<->").replace(")", "")+"\n";
		it.next();
	}
		return toReturn;
	}
	/* *****************************************************
	 * END OF PART A                                       *
	 ***************************************************** */
	
	/* *****************************************************
	* Part B : Least Cost Connections                      *
	****************************************************** */
	/**
	 * prints all the airports in the program
	 * @param flightGraph
	 * @return
	 */
	private static String AllAirports(SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> flightGraph) {
		Set<String> s=flightGraph.vertexSet();
	Iterator<String> it=s.iterator();
	String toReturn="";
	while(it.hasNext()){
		toReturn+=it.next()+"\n";
	
	}
		return toReturn;
	}
	/**
	 * Calculates the cheapest path
	 * @param path
	 * @param s
	 * @param from
	 * @param to
	 */
	private static void CalculatePath(DijkstraShortestPath<String,DefaultWeightedEdge> path, Scanner s, String from, String to) {

		if (path.getPath() != null) {
			System.out.println("Your Itinerary is:");
			List<String> it = path.getPath().getVertexList();
			System.out.println(generateRoute(it));
			System.out.println("Cost of shortest (i.e Cheapest) path: £"+path.getPath().getWeight());
			
		} else {
			System.out.println("Sorry, There are no route available for your flight");
		}

	}
    /**
     * return a string with the full route needed to reach the destination
     * @param it
     * @return
     */
	private static String generateRoute(List<String> it) {
		String toReturn = "";
		int l=1;
		for (int i = 0; i < it.size()-1; i++) {

				toReturn += l+". "+it.get(i).toString() + " -> "+it.get(i+1).toString()+"\n";l++;

		}
		return toReturn;
	}

	/* *****************************************************
	 * END OF PART B                                       *
	 ***************************************************** */


}
