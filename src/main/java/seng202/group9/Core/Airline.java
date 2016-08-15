package seng202.group9.Core;

import java.util.ArrayList;

/**
 * Airline Class Stores all Airline information.
 * @author Fan-Wu Yang
 *
 */
public class Airline {
	private int ID;
	private String IATA;
	private String ICAO;
	private String name;
	private String alias;
	private String callSign;
	private String active;
	private String country;
	private ArrayList<Route> routes;
	
	/**
	 * Constructor
	 * 
	 * @param ID
	 * @param name
	 * @param alias
	 * @param IATA
	 * @param ICAO
	 * @param callSign
	 * @param country
	 * @param active
	 */
	public Airline(int ID, String name, String alias, String IATA, String ICAO, String callSign, String country, String active){
		this.ID = ID;
		this.IATA = IATA;
		this.ICAO = ICAO;
		this.name = name;
		this.alias = alias;
		this.callSign = callSign;
		this.active = active;
		this.country = country;
		routes = new ArrayList<Route>();
	}
	
	/**
	 * Changes the ID of the Airline this is correlated to the database.
	 * @param iD
	 */
	public void setID(int iD) {
		ID = iD;
	}

	/**
	 * Changes IATA code.
	 * @param iATA
	 */
	
	public void setIATA(String iATA) {
		IATA = iATA;
	}
	/**
	 * Changes ICAO code
	 * @param iCAO
	 */
	public void setICAO(String iCAO) {
		ICAO = iCAO;
	}
	/**
	 * Changes name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * CHanges Alias
	 * @param alias
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}
	/**
	 * Changes Call Sign
	 * @param callSign
	 */
	public void setCallSign(String callSign) {
		this.callSign = callSign;
	}
	/**
	 * Changes active state
	 * @param active
	 */
	public void setActive(String active) {
		this.active = active;
	}
	/**
	 * Changes Country the Airline belongs to
	 * @param country
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * Sets routes to the airline
	 * @param routes
	 */
	public void setRoutes(ArrayList<Route> routes) {
		this.routes = routes;
	}
	/**
	 * get the ID of relative to the database
	 * @return
	 */
	public int getID(){
		return ID;
	}
	/**
	 * get the IATA code
	 * @return
	 */
	public String getIATA(){
		return IATA;
	}
	/**
	 * get the ICAO code
	 * @return
	 */
	public String getICAO(){
		return ICAO;
	}
	/**
	 * get the name of the airline
	 * @return
	 */
	public String getName(){
		return name;
	}
	/**
	 * get the Alias of the airline
	 * @return
	 */
	public String getAlias(){
		return alias;
	}
	/**
	 * get the call sign of the airline
	 * @return
	 */
	public String getCallSign(){
		return callSign;
	}
	/**
	 * get the state of the airline (Y/N)
	 * @return
	 */
	public String getActive(){
		return active;
	}
	/**
	 * get the country the airline belongs to
	 * @return
	 */
	public String getCountry(){
		return country;
	}
	/**
	 * get routes the airline flies
	 * @return
	 */
	public ArrayList<Route> getRoutes(){
		return routes;
	}
	/**
	 * add route to the airline
	 * @param route
	 */
	public void addRoutes(Route route){
		routes.add(route);
	}
	/**
	 * returns the name of the airline when concatenated to a string.
	 */
	@Override
	public String toString(){
		return name;
	}
	
}
