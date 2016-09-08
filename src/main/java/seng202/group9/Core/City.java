package seng202.group9.Core;

import java.util.ArrayList;

public class City {
	private String name;
	private String country;
	private double timezone;
	private String timeOlson;
	private ArrayList<Airport> airports;
	
	public City(String name, String country, double timezone, String timeOlson){
		this.name = name;
		this.country = country;
		this.timezone = timezone;
		this.timeOlson = timeOlson;
		this.airports = new ArrayList<Airport>();
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setCountry(String country){
		this.country = country;
	}

	public void setTimezone(double timezone) {
		this.timezone = timezone;
	}

	public void setTimeOlson(String timeOlson) {
		this.timeOlson = timeOlson;
	}

	public void setAirports(ArrayList<Airport> airports) {
		this.airports = new ArrayList<Airport>();
		for (int i = 0; i < airports.size(); i ++) {
			this.airports.add(airports.get(i));
		}
	}

	public String getName(){
		return name;
	}

	public String getCountry(){
		return country;
	}


	public double getTimezone(){
		return timezone;
	}
	
	public String getTimeOlson(){
		return timeOlson;
	}
	
	public ArrayList<Airport> getAirports(){
		return airports;
	}
	
	public void addAirport(Airport airport){
		airports.add(airport);
	}

	public void addAirport(ArrayList<Airport> airports){
		for (int i = 0; i < airports.size(); i++){
			addAirport(airports.get(i));
		}
	}

	public void delAirport(Airport airport){
		airports.remove(airport);
	}

	public void delAirport(int index) {
		airports.remove(index);
	}
	@Override
	public String toString(){
		return this.name;
	}
}
