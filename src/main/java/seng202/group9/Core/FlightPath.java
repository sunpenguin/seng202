package seng202.group9.Core;

import java.util.ArrayList;

public class FlightPath {
	private int ID;
	private ArrayList<FlightPoint> flightPoints;
	private Airport departureAirport;
	private Airport arrivalAirport;
	
	public FlightPath(int ID, Airport departureAirport, Airport arrivalAirport){
		this.ID = ID;
		this.departureAirport = departureAirport;
		this.arrivalAirport = arrivalAirport;
		this.flightPoints = new ArrayList<FlightPoint>();
	}
	
	public int getID(){
		return ID;
	}
	
	public Airport departsFrom(){
		return departureAirport;
	}
	
	public Airport arrivesAt(){
		return arrivalAirport;
	}
	
	public ArrayList<FlightPoint> getFlight(){
		return flightPoints;
	}
	
	public void addFlightPoint(FlightPoint flightPoint){
		flightPoints.add(flightPoint);
	}
}
