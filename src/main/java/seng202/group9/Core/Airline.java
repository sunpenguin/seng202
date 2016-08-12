package seng202.group9.Core;

import java.util.ArrayList;

public class Airline {
	private int ID;
	private String IATA;
	private String ICAO;
	private String name;
	private String alias;
	private String callSign;
	private String active;
	private Country country;
	private ArrayList<Route> routes;
	
	public Airline(int ID, String IATA, String ICAO, String name, String alias, String callSign, String active, Country country){
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
	
	public int getID(){
		return ID;
	}
	
	public String getIATA(){
		return IATA;
	}
	
	public String getICAO(){
		return ICAO;
	}
	
	public String getName(){
		return name;
	}
	
	public String getAlias(){
		return alias;
	}
	
	public String getCallSign(){
		return callSign;
	}
	
	public String getActive(){
		return active;
	}
	
	public Country getCountry(){
		return country;
	}
	
	public ArrayList<Route> getRoutes(){
		return routes;
	}
	
	public void addRoutes(Route route){
		routes.add(route);
	}
	
}
