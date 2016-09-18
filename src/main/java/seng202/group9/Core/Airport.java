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
	private String cityName;
	private City city;
	private String countryName;
	private Country country;
	private ArrayList<Route> departureRoutes = new ArrayList<Route>();
	private ArrayList<Route> arrivalRoutes = new ArrayList<Route>();
	
	/**
	 * Constructor
	 * @param ID from the database
	 * @param name Name of the airport
	 * @param cityName cityName of the airport
	 * @param countryName name of Country the airport belongs to
	 * @param IATA_FFA
	 * @param ICAO
	 * @param altitude
	 * @param longitude
	 * @param latitude
	 */
	public Airport(int ID, String name, String cityName, String countryName, String IATA_FFA, String ICAO, double latitude, double longitude
			, double altitude){
		this.ID = ID;
		this.name = name;
		this.ICAO = ICAO;
		this.IATA_FFA = IATA_FFA;
		this.altitude = altitude;
		this.longitude = longitude;
		this.latitude = latitude;
		this.cityName = cityName;
		this.countryName = countryName;
	}
	/**
	 * Secondary Constructor the ID needs to be set after.
	 * @param cityName City of airport
	 * @param IATA_FFA
	 * @param ICAO
	 * @param altitude
	 * @param longitude
	 * @param latitude
	 */
	public Airport(String name, String cityName, String countryName, String IATA_FFA, String ICAO, double latitude, double longitude
			, double altitude){
		this.ID = -1;
		this.name = name;
		this.ICAO = ICAO;
		this.IATA_FFA = IATA_FFA;
		this.altitude = altitude;
		this.longitude = longitude;
		this.latitude = latitude;
		this.cityName = cityName;
		this.countryName = countryName;
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

	/**
	 * Sets the Name of the Airport.
	 * @param name
	 */
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
	 * sets the cityName of the airport (which cityName it is in)
	 * @param cityName
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
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
	 * gets the country name
	 * @return
	 */
	public String getCountryName() {
		return countryName;
	}

	/**
	 * sets the country name
	 * @param countryName
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	/**
	 * sets the routes that arrive at this airport
	 * @param arrivalRoutes
	 */
	public void setArrivalRoutes(ArrayList<Route> arrivalRoutes) {
		//the array list must be clones else future errors may occur
		this.arrivalRoutes = new ArrayList<Route>();
		for (int i = 0; i < arrivalRoutes.size(); i ++){
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
	 * gets the cityName the airport is located in
	 * @return City of Airport
	 */
	public String getCityName(){
		return cityName;
	}

	/**
	 * gets the city class asssociated wit hthis airport
	 * @return
     */
	public City getCity() {
		return city;
	}

	/**
	 * sets the city class associated with this airport
	 * @param city
     */
	public void setCity(City city) {
		this.city = city;
	}

	/**
	 * get country class associated with this airport
	 * @return
     */
	public Country getCountry() {
		return country;
	}

	/**
	 * gets the timezone of the Airport
	 * @return
	 */
	public Double getTimezone() {
		if (this.city != null) {
			return this.city.getTimezone();
		}else{
			return 0.0;
		}
	}

	/**
	 * gets the DST of the Country the Airport is in.
	 * @return
	 */
	public String getDST() {
		if (this.country != null) {
			return this.country.getDST();
		}else{
			return "";
		}
	}

	/**
	 * gets the timezone in Olson format of the country the airport is in
	 * @return
	 */
	public String getTz() {
		if (this.city != null) {
			return this.city.getTimeOlson();
		}else{
			return "";
		}
	}

	/**
	 * set country class associated with this airport
	 * @param country
     */
	public void setCountry(Country country) {
		this.country = country;
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
	 * Adds each member of routes to array list;
	 * @param routes
	 */
	public void addDepartureRoutes(ArrayList<Route> routes){
		for (int i = 0; i < routes.size(); i ++){
			addDepartureRoutes(routes.get(i));
		}
	}
	/**
	 * add arriving routes to this airport
	 * @param route
	 */
	public void addArrivalRoutes(Route route){
		arrivalRoutes.add(route);
	}

	/**
	 * Adds each member of routes to array list;
	 * @param routes
	 */
	public void addArrivalRoutes(ArrayList<Route> routes){
		for (int i = 0; i < routes.size(); i ++){
			addArrivalRoutes(routes.get(i));
		}
	}

	/**
	 * deletes a member of arrival routes by matching route pointer
	 * @param route
	 */
	public void delArrivalRoutes(Route route){
		arrivalRoutes.remove(route);
	}

	/**
	 * deletes a member of arrival routes by index
	 * @param index
	 */
	public void delArrivalRoutes(int index){
		arrivalRoutes.remove(index);
	}

	/**
	 * Calculates the distance between this airport and another airport in kilometers.
	 * @param airport
	 * @return
     */
	public double calculateDistance(Airport airport){
		double distance = 0;
		double dLong = this.longitude - airport.getLongitude();
		double dLat = this.latitude - airport.getLatitude();
		dLong = Math.toRadians(dLong);
		dLat = Math.toRadians(dLat);
		double a = Math.pow((Math.sin(dLat/2)), 2) + Math.cos(Math.toRadians(this.latitude)) * Math.cos(Math.toRadians(airport.getLatitude())) * Math.pow(Math.sin(dLong/2), 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		distance = 6371 * c;
		return distance;
	}

	/**
	 * Checks if the airport is a semi duplicate of this class. Used to see if it passes to enter into the Database.
	 * @param airport
	 * @throws DataException
	 */
	public void hasDuplicate(Airport airport) throws DataException{
		if (airport.getName().equals(this.name)){
			throw new DataException("Airport Name already Exists, Please Choose Another.");
		}
		if (airport.getName().equals("")){
			throw new DataException("Airport name cannot be Empty. Please Choose Another.");
		}
		if (!airport.getIATA_FFA().equals("") && airport.getIATA_FFA().equals(this.IATA_FFA)){
			throw new DataException("Airport IATA/FFA already Exists, Please Choose Another.");
		}
		if (!airport.getICAO().equals("") && airport.getICAO().equals(this.ICAO)){
			throw new DataException("Airport ICAO already Exists, Please Choose Another.");
		}
	}
	/**
	 * Information of the airport returned in String format.
	 */
	@Override
	public String toString(){
		return this.cityName +" Airport has ICAO: "+this.ICAO+", IATA/FFA: "+this.IATA_FFA+" and is located at ("+this.latitude+", "+this.longitude
				+ ").\n It has "+this.departureRoutes.size()+" departing routes and "+this.arrivalRoutes.size()+" arriving routes.";
	}
}
