package seng202.group9.Core;

import java.util.ArrayList;

public class FlightPath {
	private int ID;
	private ArrayList<FlightPoint> flightPoints;
	private String departureAirport;
	private String arrivalAirport;
	final private RoutePath routePath = new RoutePath();

	/**
	 *
	 * @param ID id of the the flight path in the database
	 * @param departureAirport Iata/FFA of the airport
	 * @param arrivalAirport IATA/FFA of the airport
	 */
	public FlightPath(int ID, String departureAirport, String arrivalAirport){
		this.ID = ID;
		this.departureAirport = departureAirport;
		this.arrivalAirport = arrivalAirport;
		this.flightPoints = new ArrayList<FlightPoint>();
	}

	public FlightPath(String departureAirport, String arrivalAirport){
		this.ID = -1;
		this.departureAirport = departureAirport;
		this.arrivalAirport = arrivalAirport;
		this.flightPoints = new ArrayList<FlightPoint>();
	}
	
	public ArrayList<FlightPoint> getFlightPoints() {
		return flightPoints;
	}

	public void setFlightPoints(ArrayList<FlightPoint> flightPoints) {
		this.flightPoints = new ArrayList<FlightPoint>();
		for (int i = 0; i < flightPoints.size(); i ++) {
			this.flightPoints = flightPoints;
		}
	}

	public void setDepartureAirport(String departureAirport) {
		this.departureAirport = departureAirport;
	}

	public void setArrivalAirport(String arrivalAirport) {
		this.arrivalAirport = arrivalAirport;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getID(){
		return ID;
	}
	
	public String departsFrom(){
		return departureAirport;
	}
	
	public String arrivesAt(){
		return arrivalAirport;
	}
	
	public ArrayList<FlightPoint> getFlight(){
		return flightPoints;
	}
	
	public void addFlightPoint(FlightPoint flightPoint){
		flightPoints.add(flightPoint);
	}

	public void addFlightPoint(FlightPoint flightPoint, int index){
		flightPoints.add(index, flightPoint);
	}

	public void delFlightPoint(FlightPoint flightPoint){
		flightPoints.remove(flightPoint);
	}

	public void delFlightPoint(int index){
		flightPoints.remove(index);
	}

	public void addFlightPoint(ArrayList<FlightPoint> flightPoints){
		for (int i = 0; i < flightPoints.size(); i ++){
			this.flightPoints.add(flightPoints.get(i));
		}
	}

	public RoutePath getRoutePath(){
		if (routePath.getRoute().size() == 0){
			for (FlightPoint point: flightPoints){
				routePath.addPosition(new Position(point.getLatitude(), point.getLongitude()));
			}
		}
		return routePath;
	}
}
