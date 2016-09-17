package seng202.group9.Core;

import java.util.ArrayList;

public class Country {
	private String DST, name;
	private ArrayList<City> cities = new ArrayList<City>();
	private ArrayList<Airline> airlines = new ArrayList<Airline>();
	private Position position;
	
	public Country(String DST, String name){
		this.DST = DST;
		this.name = name;
	}
	
	public void setDST(String dST) {
		DST = dST;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAirlines(ArrayList<Airline> airlines) {
		this.airlines = new ArrayList<Airline>();
		for (int i = 0; i < airlines.size(); i ++) {
			this.airlines.add(airlines.get(i));
		}
	}

	public String getDST(){
		return this.DST;
	}
	
	public String getName(){
		return this.name;
	}
	
	public ArrayList<Airline> getAirlines(){
		return airlines;
	}
	
	public void addAirline(Airline airline){
		this.airlines.add(airline);
	}

	public void addAirline(ArrayList<Airline> airlines){
		for (int i = 0; i < airlines.size(); i++){
			addAirline(airlines.get(i));
		}
	}

	public void delAirline(Airline airline){
		airlines.remove(airline);
	}

	public void delAirline(int index){
		airlines.remove(index);
	}

	public void setCities(ArrayList<City> cities){
		this.cities = new ArrayList<City>();
		for (int i = 0; i < cities.size(); i++){
			this.cities.add(cities.get(i));
		}
	}

	public void addCities(City city){
		this.cities.add(city);
	}

	public void addCities(ArrayList<City> cities){
		for (int i = 0; i < cities.size(); i++){
			this.cities.add(cities.get(i));
		}
	}

	public void delCities(City city){
		this.cities.remove(city);
	}

	public void delCities(int index){
		this.cities.remove(index);
	}

	public ArrayList<City> getCities() {
		return cities;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
}
