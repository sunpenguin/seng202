package seng202.group9.Core;

import java.util.ArrayList;

public class Airport {
	private int ID;
	private String ICAO;
	private String IATA_FFA;
	private float altitude;
	private float longitude;
	private float latitude;
	private City location;
	private ArrayList<Route> departureRoutes = new ArrayList<Route>();
	private ArrayList<Route> arrivalRoutes = new ArrayList<Route>();
	
	public Airport(int ID, String ICAO, String IATA_FFA, float altitude, float longitude,
			float latitude, City location){
		this.ID = ID;
		this.ICAO = ICAO;
		this.IATA_FFA = IATA_FFA;
		this.altitude = altitude;
		this.longitude = longitude;
		this.latitude = latitude;
		this.location = location;
	}
	
	public int getID(){
		return ID;
	}
	
	public String getICAO(){
		return ICAO;
	}
	
	public String IATA_FFA(){
		return IATA_FFA;
	}
	
	public float getAltitude(){
		return altitude;
	}
	
	public float getLongitude(){
		return longitude;
	}
	
	public float getLatitude(){
		return latitude;
	}
	
	public City getLocation(){
		return location;
	}
	
	public ArrayList<Route> getDepartureRoutes(){
		return departureRoutes;
	}
	
	public ArrayList<Route> getArrivalRoutes(){
		return arrivalRoutes;
	}
	
	public void addDepartureRoutes(Route route){
		departureRoutes.add(route);
	}
	
	public void addArrivalRoutes(Route route){
		arrivalRoutes.add(route);
	}
	
}
