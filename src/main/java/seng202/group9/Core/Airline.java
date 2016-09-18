package seng202.group9.Core;

import seng202.group9.Controller.DataException;

import javax.xml.crypto.Data;
import java.util.ArrayList;

/**
 * Airline Class Stores all Airline information.
 * @author Fan-Wu Yang
 *
 */
public class Airline{
	private int ID;
	private String IATA;
	private String ICAO;
	private String name;
	private String alias;
	private String callSign;
	private String active;
	private String countryName;
	private ArrayList<Route> routes;
	private Country country = null;

	/**
	 * Constructor for Airline when pulled from the database.
	 * 
	 * @param ID
	 * @param name
	 * @param alias
	 * @param IATA
	 * @param ICAO
	 * @param callSign
	 * @param countryName
	 * @param active
	 */
	public Airline(int ID, String name, String alias, String IATA, String ICAO, String callSign, String countryName, String active){
		this.ID = ID;
		this.IATA = IATA;
		this.ICAO = ICAO;
		this.name = name;
		this.alias = alias;
		this.callSign = callSign;
		this.active = active;
		this.countryName = countryName;
		this.routes = new ArrayList<Route>();
	}

	/**
	 * Constructor for Airline without ID this will be set later by the dataset from the dataset.
	 * @param name
	 * @param alias
	 * @param IATA
	 * @param ICAO
	 * @param callSign
	 * @param countryName
	 * @param active
	 */
	public Airline(String name, String alias, String IATA, String ICAO, String callSign, String countryName, String active){
		this.ID = -1;
		this.IATA = IATA;
		this.ICAO = ICAO;
		this.name = name;
		this.alias = alias;
		this.callSign = callSign;
		this.active = active;
		this.countryName = countryName;
		this.routes = new ArrayList<Route>();
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
	 * @param countryName
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	/**
	 * Sets routes to the airline
	 * @param routes
	 */
	public void setRoutes(ArrayList<Route> routes) {
		//this is necessary so that the object routes arraylist does not
		//point the the other list which will result to data errors. THus
		//a clone must be made.
		this.routes = new ArrayList<Route>();
		for(int i = 0; i < routes.size();i ++){
			this.routes.add(routes.get(i));
		}
	}
	/**
	 * get the ID of relative to the database
	 * @return
	 */
	public int getID() throws DataException {
		if (this.ID == -1){
			throw new DataException("ID not set.");
		}else{
			return ID;
		}
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
	 * get the countryName the airline belongs to
	 * @return
	 */
	public String getCountryName(){
		return countryName;
	}

	/**
	 * gets the Country class that this airline belongs to
	 * @return
     */
	public Country getCountry() {
		return country;
	}

	/**
	 * sets the country class this airline belongs to
	 * @param country
     */
	public void setCountry(Country country) {
		this.country = country;
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
		ArrayList<Route> routesToAdd = new ArrayList<Route>();
		routesToAdd.add(route);
		addRoutes(routesToAdd);
	}
	/**
	 * Function Overload of addRoutes this allow the adding of a list to route
	 * @param routesToAdd array list of routes to add routes
	 */
	public void addRoutes(ArrayList<Route> routesToAdd){
		for (int i = 0; i < routesToAdd.size(); i ++){
			routes.add(routesToAdd.get(i));
		}
	}

	/**
	 * deletes a route by matching the route class/pointer in routes array keep in mind that the array position will change for all indexs after this.
	 * @param route
	 */
	public void delRoutes(Route route){
		routes.remove(route);
	}

	/**
	 * deletes a route by matching index keep in mind that the array position will change for all indexs after this.
	 * @param index
	 */
	public void delRoutes(int index) throws DataException{
		if (routes.size() > index && index >= 0) {
			routes.remove(index);
		}else{
			throw new DataException("The Route at index "+index+ "does not exist to be deleted.");
		}
	}

	/**
	 * checks if the airline has a duplicate unique entry to another. Used for validating
	 * @param airline
	 * @return
     */
	public void hasDuplicate(Airline airline) throws DataException{
		if (this.name.equals(airline.getName())){
			throw new DataException("This Airline Name already Exists, Please Choose Another.");
		}
		if (airline.getName().equals("")){
			throw new DataException("This Airline Name cannot be Empty");
		}
		if (!this.IATA.equals("") && this.IATA.equals(airline.getIATA())){
			throw new DataException("This IATA Code already Exists, Please Choose Another.");
		}
		if (!this.ICAO.equals("") && this.ICAO.equals(airline.getICAO())){
			throw new DataException("This ICAO Code already Exists, Please Choose Another.");
		}
		if (!this.alias.equals("") && this.alias.equals(airline.getAlias())){
			throw new DataException("This Alias already Exists, Please Choose Another.");
		}
		if (!this.callSign.equals("") && this.callSign.equals(airline.getCallSign())){
			throw new DataException("This Callsign already Exists, Please Choose Another.");
		}
	}
	/**
	 * returns the name of the airline when concatenated to a string.
	 */
	@Override
	public String toString(){
		return name + ", IATA: " + IATA + ", ICAO: " + ICAO;
	}
	
}
