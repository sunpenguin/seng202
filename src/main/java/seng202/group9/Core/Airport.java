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
	private String name;
	private String ICAO;
	private String IATA_FFA;
	private double altitude;
	private double longitude;
	private double latitude;
	private String city;
	private ArrayList<Route> departureRoutes = new ArrayList<Route>();
	private ArrayList<Route> arrivalRoutes = new ArrayList<Route>();
	
	/**
	 * Constructor
	 * @param ID from the database
	 * @param name Name of the airport
	 * @param city city of the airport
	 * @param IATA_FFA
	 * @param ICAO
	 * @param altitude
	 * @param longitude
	 * @param latitude
	 */
	public Airport(int ID, String name, String city, String IATA_FFA, String ICAO, double latitude, double longitude
			, double altitude){
		this.ID = ID;
		this.name = name;
		this.ICAO = ICAO;
		this.IATA_FFA = IATA_FFA;
		this.altitude = altitude;
		this.longitude = longitude;
		this.latitude = latitude;
		this.city = city;
	}
	/**
	 * Secondary Constructor the ID needs to be set after.
	 * @param city City of airport
	 * @param IATA_FFA
	 * @param ICAO
	 * @param altitude
	 * @param longitude
	 * @param latitude
	 */
	public Airport(String name, String city, String IATA_FFA, String ICAO, double latitude, double longitude
			, double altitude){
		this.ID = -1;
		this.name = name;
		this.ICAO = ICAO;
		this.IATA_FFA = IATA_FFA;
		this.altitude = altitude;
		this.longitude = longitude;
		this.latitude = latitude;
		this.city = city;
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
		this.ID = iD;
	}
	public void setName(String name){
		this.name = name;
	}
	/**
	 * sets the ICAO Code
	 * @param iCAO
	 */
	public void setICAO(String iCAO) {
		this.ICAO = iCAO;
	}
	/**
	 * sets the altitude of the airport
	 * @param altitude
	 */
	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}
	/**
	 * set longitude of the airport
	 * @param longitude
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	/**
	 * set latitude of the airport
	 * @param latitude
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	/**
	 * sets the coordinates of the place
	 * @param latitude
	 * @param longitude
	 */
	public void setCoordinates(double latitude, double longitude){
		this.latitude = latitude;
		this.longitude = longitude;
	}
	/**
	 * sets the city of the airport (which city it is in)
	 * @param city
	 */
	public void setCity(String city) {
		this.city = city;
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
	 * get the name of the airport
	 * @return Airport Name
	 */
	public String getName(){
		return name;
	}
	/**
	 * get the ICAO of the airport
	 * @return ICAO Code
	 */
	public String getICAO(){
		return ICAO;
	}
	/**
	 * gets the IATA/FFA of the airport
	 * @return IATA/FFA Code
	 */
	public String IATA_FFA(){
		return IATA_FFA;
	}
	/**
	 * gets the altitude of the airport
	 * @return Altitude of Airport
	 */
	public double getAltitude(){
		return altitude;
	}
	/**
	 * gets the longitude of the airport
	 * @return Longitude of Airport
	 */
	public double getLongitude(){
		return longitude;
	}
	/**
	 * gets the latitude of the airport
	 * @return Latitude of Airport
	 */
	public double getLatitude(){
		return latitude;
	}
	/**
	 * gets the city the airport is located in
	 * @return City of Airport
	 */
	public String getCity(){
		return city;
	}
	/**
	 * gets the routes that depart from this airport
	 * @return Routes Departing from Airport
	 */
	public ArrayList<Route> getDepartureRoutes(){
		return departureRoutes;
	}
	/**
	 * gets the routes that arrive at this airport
	 * @return Routes Arriving at Airport
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
		return this.city+" Airport has ICAO: "+this.ICAO+", IATA/FFA: "+this.IATA_FFA+" and is located at ("+this.latitude+", "+this.longitude
				+ ").\n It has "+this.departureRoutes.size()+" departing routes and "+this.arrivalRoutes+" arriving routes.";
	}
}
