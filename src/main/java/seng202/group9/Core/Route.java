package seng202.group9.Core;

import seng202.group9.Controller.DataException;

/**
 * Route Class
 * @author Fan-Wu Yang
 *
 */
public class Route {
	private int ID;//this ID is the route id not the airline or airports.
	private int stops;
	private String codeShare;
	private String equipment;
	private String airline;
	private String departureAirport;
	private String arrivalAirport;
	private Airport sourceAirport;
	private Airport destinationAirport;

	/**
	 * Constructor for pulling from database
	 * @param airline
	 * @param ID
	 * @param departureAirport
	 * @param arrivalAirport
	 * @param codeShare
	 * @param stops
	 * @param equipment
	 */
	public Route(int ID, String airline, String departureAirport, String arrivalAirport,
			String codeShare, int stops, String equipment){
		this.ID = ID;
		this.stops = stops;
		this.codeShare = codeShare;
		this.equipment = equipment;
		this.airline = airline;
		this.departureAirport = departureAirport;
		this.arrivalAirport = arrivalAirport;
	}
	/**
	 * Constructor for adding
	 * @param airline
	 * @param departureAirport
	 * @param arrivalAirport
	 * @param codeShare
	 * @param stops
	 * @param equipment
	 */
	public Route(String airline, String departureAirport, String arrivalAirport,
			String codeShare, int stops, String equipment){
		//remember to set the id manually later after adding it to the database and grab
		//the new id value of the route.
		this.ID = -1;
		this.stops = stops;
		this.codeShare = codeShare;
		this.equipment = equipment;
		this.airline = airline;
		this.departureAirport = departureAirport;
		this.arrivalAirport = arrivalAirport;
	}
	/**
	 * sets code share of the route
	 * @param codeShare
	 */
	public void setCode(String codeShare) {
		this.codeShare = codeShare;
	}
	/**
	 * Sets departure Airport
	 * @param departureAirport
	 */
	public void setDepartureAirport(String  departureAirport) {
		this.departureAirport = departureAirport;
	}
	/**
	 * sets the arrival airport
	 * @param arrivalAirport
	 */
	public void setArrivalAirport(String arrivalAirport) {
		this.arrivalAirport = arrivalAirport;
	}
	/**
	 * sets id fo the route
	 * @param iD
	 */
	public void setID(int iD) {
		ID = iD;
	}
	/**
	 * sets number of stops
	 * @param stops
	 */
	public void setStops(int stops) {
		this.stops = stops;
	}
	/**
	 * sets equipment for the route
	 * @param equipment
	 */
	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}
	/**
	 * sets airline that flies this route
	 * @param airline
	 */
	public void setAirline(String airline) {
		this.airline = airline;
	}
	/**
	 * returns the id of this route
	 * @return
	 */
	public int getID() throws DataException{
		if (this.ID == -1){
			throw new DataException("ID not set.");
		}else{
			return ID;
		}
	}
	/**
	 * returns the number of stops the route stops.
	 * @return
	 */
	public int getStops(){
		return stops;
	}
	/**
	 * returns codeshare of the route
	 * @return
	 */
	public String getCode(){
		return codeShare;
	}
	/**
	 * return equipment for the route
	 * @return
	 */
	public String getEquipment(){
		return equipment;
	}
	/**
	 * returns the airline that flies this route
	 * @return
	 */
	public String getAirline(){
		return airline;
	}
	/**
	 * returns the source airport of this route
	 * @return
	 */
	public String departsFrom(){
		return departureAirport;
	}
	/**
	 * returns the target airport of this route.
	 * @return
	 */
	public String arrivesAt(){
		return arrivalAirport;
	}

	/**
	 * get Airport class that is bound with the destination of the airport
	 * @return
     */
	public Airport getDestinationAirport() {
		return destinationAirport;
	}

	/**
	 * set Airport class that is bound with the destination of the airport
	 * @param destinationAirport
     */
	public void setDestinationAirport(Airport destinationAirport) {
		this.destinationAirport = destinationAirport;
	}

	/**
	 * get Airport class that is bound with the source airport
	 * @return
     */
	public Airport getSourceAirport() {
		return sourceAirport;
	}

	/**
	 * set Airport class that is bound with the source airport
	 * @param sourceAirport
     */
	public void setSourceAirport(Airport sourceAirport) {
		this.sourceAirport = sourceAirport;
	}

	/**
	 * Checks is passed route is a duplicate of the current one if so it throws an DataException
	 */
	public void hasDuplicate(Route route) throws DataException{
		//routeAirline + routeSourceAirport + routeArrvAirport + routeCodeShare + routeStops + routeEquip
		if (route.getAirline().equals(this.airline) && route.departsFrom().equals(this.departureAirport)
				&& route.arrivesAt().equals(this.arrivalAirport) && route.getCode().equals(this.codeShare)
				 && route.getStops() == this.stops && route.getEquipment().equals(this.equipment)){
			throw new DataException("This Route already exists.");
		}
	}
	@Override
	public String toString(){
		
		return airline+" flies from "+departureAirport+" to "+arrivalAirport+" on a "+equipment +" stopping "+stops+" amount of times";
	}
	
}
