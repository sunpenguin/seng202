package seng202.group9.Core;

import java.util.ArrayList;

import seng202.group9.Controller.DataException;
/**
 * Airport Class
 * @author Fan-Wu Yang
 *
 */
public class Airport {
	private int ID;
	private String ICAO;
	private String IATA_FFA;
	private float altitude;
	private float longitude;
	private float latitude;
	private String location;
	private ArrayList<Route> departureRoutes = new ArrayList<Route>();
	private ArrayList<Route> arrivalRoutes = new ArrayList<Route>();
	
	/**
	 * Constructor
	 * @param ID from the database
	 * @param ICAO
	 * @param IATA_FFA
	 * @param altitude
	 * @param longitude
	 * @param latitude
	 * @param location
	 */
	public Airport(int ID, String ICAO, String IATA_FFA, float altitude, float longitude,
			float latitude, String location){
		this.ID = ID;
		this.ICAO = ICAO;
		this.IATA_FFA = IATA_FFA;
		this.altitude = altitude;
		this.longitude = longitude;
		this.latitude = latitude;
		this.location = location;
	}
	/**
	 * Secondary Constructor the ID needs to be set after.
	 * @param ICAO
	 * @param IATA_FFA
	 * @param altitude
	 * @param longitude
	 * @param latitude
	 * @param location
	 */
	public Airport(String ICAO, String IATA_FFA, float altitude, float longitude,
			float latitude, String location){
		this.ID = -1;
		this.ICAO = ICAO;
		this.IATA_FFA = IATA_FFA;
		this.altitude = altitude;
		this.longitude = longitude;
		this.latitude = latitude;
		this.location = location;
	}
	/**
	 * returns the IATA/FFA code
	 * @return
	 */
	public String getIATA_FFA() {
		return IATA_FFA;
	}
	/**
	 * sets the IATA/FFA code
	 * @param iATA_FFA
	 */
	public void setIATA_FFA(String iATA_FFA) {
		IATA_FFA = iATA_FFA;
	}
	/**
	 * sets the database ID (Autoincremented)
	 * @param iD
	 */
	public void setID(int iD) {
		ID = iD;
	}
	/**
	 * sets the ICAO Code
	 * @param iCAO
	 */
	public void setICAO(String iCAO) {
		ICAO = iCAO;
	}
	/**
	 * sets the altitude of the airport
	 * @param altitude
	 */
	public void setAltitude(float altitude) {
		this.altitude = altitude;
	}
	/**
	 * set longitude of the airport
	 * @param longitude
	 */
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	/**
	 * set latitude of the airport
	 * @param latitude
	 */
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	/**
	 * sets the coordinates of the place
	 * @param latitude
	 * @param longitude
	 */
	public void setCoordinates(float latitude, float longitude){
		this.latitude = latitude;
		this.longitude = longitude;
	}
	/**
	 * sets the location of the airport (which city it is in)
	 * @param location
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	/**
	 * set the routes that depart from it
	 * @param departureRoutes
	 */
	public void setDepartureRoutes(ArrayList<Route> departureRoutes) {
		//the array list must be clones else future errors may occur
		this.departureRoutes = new ArrayList<Route>();
		for (int i = 0; i < departureRoutes.size(); i ++){
			this.departureRoutes.add(departureRoutes.get(i));
		}
	}
	/**
	 * sets the routes that arrive at this airport
	 * @param arrivalRoutes
	 */
	public void setArrivalRoutes(ArrayList<Route> arrivalRoutes) {
		//the array list must be clones else future errors may occur
		this.arrivalRoutes = new ArrayList<Route>();
		for (int i = 0; i < departureRoutes.size(); i ++){
			this.arrivalRoutes.add(arrivalRoutes.get(i));
		}
	}
	/**
	 * gets the data auto-incremented database ID of the airport.
	 * if it is -1 it throws an not set data error
	 * @return
	 * @throws DataException
	 */
	public int getID() throws DataException{
		if (this.ID == -1){
			throw new DataException("ID not set.");
		}else{
			return ID;
		}
	}
	/**
	 * get the ICAO of the airport
	 * @return
	 */
	public String getICAO(){
		return ICAO;
	}
	/**
	 * gets the IATA/FFA of the airport
	 * @return
	 */
	public String IATA_FFA(){
		return IATA_FFA;
	}
	/**
	 * gets the altitude of the airport
	 * @return
	 */
	public float getAltitude(){
		return altitude;
	}
	/**
	 * gets the longitude of the airport
	 * @return
	 */
	public float getLongitude(){
		return longitude;
	}
	/**
	 * gets the latitude of the airport
	 * @return
	 */
	public float getLatitude(){
		return latitude;
	}
	/**
	 * gets the city the airport is located in
	 * @return
	 */
	public String getLocation(){
		return location;
	}
	/**
	 * gets the routes that depart from this airport
	 * @return
	 */
	public ArrayList<Route> getDepartureRoutes(){
		return departureRoutes;
	}
	/**
	 * gets the routes that arrive at this airport
	 * @return
	 */
	public ArrayList<Route> getArrivalRoutes(){
		return arrivalRoutes;
	}
	/**
	 * adds departing routes to this airport
	 * @param route
	 */
	public void addDepartureRoutes(Route route){
		departureRoutes.add(route);
	}
	/**
	 * add arriving routes to this airport
	 * @param route
	 */
	public void addArrivalRoutes(Route route){
		arrivalRoutes.add(route);
	}
	/**
	 * Information of the airport returned in String format.
	 */
	@Override
	public String toString(){
		return this.location+" Airport has ICAO: "+this.ICAO+", IATA/FFA: "+this.IATA_FFA+" and is located at ("+this.latitude+", "+this.longitude
				+ ").\n It has "+this.departureRoutes.size()+" departing routes and "+this.arrivalRoutes+" arriving routes.";
	}
}
