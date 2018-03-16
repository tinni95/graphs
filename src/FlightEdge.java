import org.jgrapht.graph.DefaultWeightedEdge;
/* *****************************************************
* Part C : Additional Flight Information               *
****************************************************** */
/**
 * implementing class flightEdge for part C,
 * to let the edges contain all the information we need
 * @author tinni
 */
public class FlightEdge extends DefaultWeightedEdge{
/**
 * adding 4 new fields to our class which extends DefaultWeightedEdges, that has just weight as field.
 * but in our case we need also flight number, departure time, arrival time and flight duration
 */
private static final long serialVersionUID = 1L;
private String flightNum;
private String DepartureTm;
private String ArrivalTm;
private String FlightDur;
/**
 * we inherit all the property of the super class
 */
public FlightEdge(){
	super();
}

/**
 * get flight number
 * @return
 */
public String getFlightNum(){
	return flightNum;
}
/**
 * set flight number
 * @param flightNum
 */
public void setFlightNum(String flightNum){
	this.flightNum=flightNum;
}
/**
 * get Departure Time
 * @return
 */
public String DepTm(){
	return DepartureTm;
}
/**
 * set Departure Time
 * @param DepTm
 */
public void setDepTm(String DepTm){
	this.DepartureTm=DepTm;
}
/**
 * get Arrival Time
 * @return
 */
public String ArrivalTm(){
	return ArrivalTm;
}
/**
 * set Arrival Time
 * @param ArrivalTm
 */
public void setArrivalTm(String ArrivalTm){
	this.ArrivalTm=ArrivalTm;
}
/**
 * get Flight duration
 * @return
 */
public String FlightDur(){
	return FlightDur;
}
/**
 * set Flight Duration
 * @param FlightDur
 */
public void setFlightDur(String FlightDur){
	this.FlightDur=FlightDur;
}
/**
 * methods which return the protected 
 * double getWeight from the superclass
 * @return
 */
public double price(){
	return this.getWeight();
}
}
