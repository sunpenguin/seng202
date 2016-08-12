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
