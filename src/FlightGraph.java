
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

/* *****************************************************
* PART C : Additional Flight Information               *
****************************************************** */

/**
 * class FlightGraph for handling flightEdge nodes 
 * 
 * @author tinni
 */
public class FlightGraph extends SimpleDirectedWeightedGraph<String, FlightEdge> {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for the class MyGraph
	 * we state here that we want to use flightEdges as the edges for the graph
	 * @param class1
	 */
	public FlightGraph(Class<FlightEdge> flightEdges) {
		super(flightEdges);

	}

	/**
	 * setting the flight number to the edge
	 * 
	 * @param e
	 * @param num
	 */
	public void setEdgeFlightNum(FlightEdge e, String num) {
		e.setFlightNum(num);
	}

	/**
	 * setting Departure Time
	 * 
	 * @param e
	 * @param tm
	 */
	public void setEdgeDepTm(FlightEdge e, String tm) {
		e.setDepTm(tm);
	}

	/**
	 * setting arrival Time
	 * 
	 * @param e
	 * @param tm
	 */
	public void setEdgeArrTm(FlightEdge e, String tm) {
		e.setArrivalTm(tm);
	}

	/**
	 * set Flight Duration
	 * 
	 * @param e
	 * @param tm
	 */
	public void setEdgeFlightDur(FlightEdge e, String tm) {
		e.setFlightDur(tm);
	}
}

	
