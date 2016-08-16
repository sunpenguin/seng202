package seng202.group9.Core;

/**
 * Route Class
 * @author Fan-Wu Yang
 *
 */
public class Route {
	private int ID;
	private int stops;
	private String codeShare;
	private String equipment;
	private String airline;
	private String departureAirport;
	private String arrivalAirport;
	/**
	 * Constructor
	 * @param airline
	 * @param ID
	 * @param departureAirport
	 * @param arrivalAirport
	 * @param codeShare
	 * @param stops
	 * @param equipment
	 */
	public Route(String airline, int ID, String departureAirport, String arrivalAirport,
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
	 * Returns code share of the route
	 * @return
	 */
	public String getCodeShare() {
		return codeShare;
	}
	/**
	 * sets code share of the route
	 * @param codeShare
	 */
	public void setCodeShare(String codeShare) {
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
	public int getID(){
		return ID;
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
	
	
}
