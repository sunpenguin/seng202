package seng202.group9.Core;

import seng202.group9.Controller.DataException;

import java.util.ArrayList;

/**
 * Created By Fan-Wu Yang.
 */
public class City {
	private String name;
	private String country;
	private double timezone;
	private String timeOlson;
	private ArrayList<Airport> airports;

	/**
	 * City Constructor
	 * @param name
	 * @param country
	 * @param timezone
	 * @param timeOlson
	 */
	public City(String name, String country, double timezone, String timeOlson){
		this.name = name;
		this.country = country;
		this.timezone = timezone;
		this.timeOlson = timeOlson;
		this.airports = new ArrayList<Airport>();
	}

	/**
	 * Sets Name of the City
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets Country that the city is in.
	 * @param country
	 */
	public void setCountry(String country){
		this.country = country;
	}

	/**
	 * Set Timezone that the City is in.
	 * @param timezone
	 */
	public void setTimezone(double timezone) {
		this.timezone = timezone;
	}

	/**
	 * Sets the time olson the city is in.
	 * @param timeOlson
	 */
	public void setTimeOlson(String timeOlson) {
		this.timeOlson = timeOlson;
	}

	/**
	 * Sets the airports the are in the city
	 * @param airports
	 */
	public void setAirports(ArrayList<Airport> airports) {
		this.airports = new ArrayList<Airport>();
		for (int i = 0; i < airports.size(); i ++) {
			this.airports.add(airports.get(i));
		}
	}

	/**
	 * Gets the name of the city.
	 * @return
	 */
	public String getName(){
		return name;
	}

	/**
	 * Gets the Country that the city is in.
	 * @return
	 */
	public String getCountry(){
		return country;
	}

	/**
	 * gets the Timezone that the City is in.
	 * @return
	 */
	public double getTimezone(){
		return timezone;
	}

	/**
	 * Gets the Timezone in Olson format the City is in.
	 * @return
	 */
	public String getTimeOlson(){
		return timeOlson;
	}

	/**
	 * gets the Airports that are in this city.
	 * @return
	 */
	public ArrayList<Airport> getAirports(){
		return airports;
	}

	/**
	 * adds an airport that is in this city.
	 * @param airport
	 */
	public void addAirport(Airport airport){
		airports.add(airport);
	}

	/**
	 * adds multiple airports to this city.
	 * @param airports
	 */
	public void addAirport(ArrayList<Airport> airports){
		for (int i = 0; i < airports.size(); i++){
			addAirport(airports.get(i));
		}
	}

	/**
	 * Deletes an Airport from this City.
	 * @param airport
	 */
	public void delAirport(Airport airport){
		airports.remove(airport);
	}

	/**
	 * Deletes an Airport by Index from this City.
	 * @param index
	 */
	public void delAirport(int index) throws DataException{
		if (airports.size() > index && index >= 0) {
			airports.remove(index);
		}else{
			throw new DataException("Index "+index+" is out of number of City Airports array size.");
		}
	}
	@Override
	public String toString(){
		return this.name + " has " + airports.size() + " Airports and is in "+timeOlson;
	}
}
