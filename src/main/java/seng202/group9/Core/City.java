package seng202.group9.Core;

import java.util.ArrayList;

public class City {
	private String name;
	private String timezone;
	private String timeOlson;
	private ArrayList<Airport> airports;
	
	public City(String name, String timezone, String timeOlson){
		this.name = name;
		this.timezone = timezone;
		this.timeOlson = timeOlson;
		this.airports = new ArrayList<Airport>();
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public void setTimeOlson(String timeOlson) {
		this.timeOlson = timeOlson;
	}

	public void setAirports(ArrayList<Airport> airports) {
		this.airports = airports;
	}

	public String getName(){
		return name;
	}
	
	public String getTimezone(){
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
}
