package seng202.group9.Core;

import seng202.group9.Controller.DataException;

import java.util.ArrayList;
import java.util.HashMap;

public class Country {
	private String DST, name;
	private ArrayList<City> cities = new ArrayList<City>();
	private ArrayList<Airline> airlines = new ArrayList<Airline>();
	private HashMap<String, Airline> airlineDict = new HashMap<String, Airline>();
	private HashMap<String, City> cityDict = new HashMap<String, City>();
	private Position position;

	/**
	 * Contructor for Country.
	 * @param DST
	 * @param name
	 */
	public Country(String DST, String name){
		this.DST = DST;
		this.name = name;
	}

	/**
	 * Sets the DST of the country.
	 * @param dST
	 */
	public void setDST(String dST) {
		DST = dST;
	}

	/**
	 * Sets the name of the country.
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Set Airlines that are based in this country.
	 * @param airlines
	 */
	public void setAirlines(ArrayList<Airline> airlines) {
		this.airlines = new ArrayList<Airline>();
		this.airlineDict = new HashMap<String, Airline>();
		for (int i = 0; i < airlines.size(); i ++) {
			this.airlines.add(airlines.get(i));
			airlineDict.put(airlines.get(i).getName(), airlines.get(i));
		}
	}

	/**
	 * Gets the DST of the Country.
	 * @return
	 */
	public String getDST(){
		return this.DST;
	}

	/**
	 * Gets the Name of the Country.
	 * @return
	 */
	public String getName(){
		return this.name;
	}

	/**
	 * gets the Airlines that belong in this Country.
	 * @return
	 */
	public ArrayList<Airline> getAirlines(){
		return airlines;
	}

	/**
	 * Adds an Airline that is based in this country.
	 * @param airline
	 */
	public void addAirline(Airline airline){
		if (!airlineDict.containsKey(airline.getName())) {
			this.airlines.add(airline);
			airlineDict.put(airline.getName(), airline);
		}
	}

	/**
	 * Adds multiple Airlines to this Country.
	 * @param airlines
	 */
	public void addAirline(ArrayList<Airline> airlines){
		for (int i = 0; i < airlines.size(); i++){
			addAirline(airlines.get(i));
		}
	}

	/**
	 * deletes an Airline based in this country.
	 * @param airline
	 */
	public void delAirline(Airline airline){
		airlines.remove(airline);
		airlineDict.remove(airline.getName());
	}

	/**
	 * deletes an Airline in this country.
	 * @param index
	 */
	public void delAirline(int index) throws DataException{
		if (airlines.size() > index && index >= 0) {
			airlineDict.remove(airlines.get(index).getName());
			airlines.remove(index);
		}else{
			throw new DataException("Index " + index + " does not exist in list of Airlines in this Country.");
		}
	}

	/**
	 * sets the cities of this country
	 * @param cities
	 */
	public void setCities(ArrayList<City> cities){
		this.cities = new ArrayList<City>();
		this.cityDict = new HashMap<String, City>();
		for (int i = 0; i < cities.size(); i++){
			this.cities.add(cities.get(i));
			cityDict.put(cities.get(i).getName(), cities.get(i));
		}
	}

	/**
	 * adds a City to this country.
	 * @param city
	 */
	public void addCities(City city){
		if (!cityDict.containsKey(city.getName())) {
			this.cities.add(city);
			cityDict.put(city.getName(), city);
		}
	}

	/**
	 * Add multiple Cities to this Country.
	 * @param cities
	 */
	public void addCities(ArrayList<City> cities){
		for (int i = 0; i < cities.size(); i++){
			this.cities.add(cities.get(i));
		}
	}

	/**
	 * Deletes a city for this country.
	 * @param city
	 */
	public void delCities(City city){
		this.cities.remove(city);
	}

	/**
	 * Deletes Cities in this Country
	 * @param index
	 */
	public void delCities(int index) throws DataException{
		if (cities.size() > index && index >= 0) {
			this.cities.remove(index);
		}else{
			throw new DataException("City at Index "+ index + " does not exist.");
		}
	}

	/**
	 * Gets the CIties in this Country.
	 * @return
	 */
	public ArrayList<City> getCities() {
		return cities;
	}

	/**
	 * gets the {@link Position}(double Latitude, double Longitude) of this Country.
	 * @return
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * sets the {@link Position} of the Country.
	 * @param position
	 */
	public void setPosition(Position position) {
		this.position = position;
	}
}
