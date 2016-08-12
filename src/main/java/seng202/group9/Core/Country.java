package seng202.group9.Core;

import java.util.ArrayList;

public class Country {
	private String DST, name;
	private ArrayList<Airline> airlines = new ArrayList<Airline>();
	
	public Country(String DST, String name){
		this.DST = DST;
		this.name = name;
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
}
