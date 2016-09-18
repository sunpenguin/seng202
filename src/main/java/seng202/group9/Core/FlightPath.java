package seng202.group9.Core;

import seng202.group9.Controller.DataException;

import java.util.ArrayList;

/**
 * Created By Fan-Wu Yang
 */
public class FlightPath {
	private int ID;
	private ArrayList<FlightPoint> flightPoints;
	private String departureAirport;
	private String arrivalAirport;
	final private RoutePath routePath = new RoutePath();

	/**
	 * Constructor for this FLight Path from database
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

	/**
	 * COnstructor for FlightPath from dataset add later the ID needs to be set from database.
	 * @param departureAirport
	 * @param arrivalAirport
	 */
	public FlightPath(String departureAirport, String arrivalAirport){
		this.ID = -1;
		this.departureAirport = departureAirport;
		this.arrivalAirport = arrivalAirport;
		this.flightPoints = new ArrayList<FlightPoint>();
	}

	/**
	 * Gets the {@link FlightPoint} of this flight Path.
	 * @return
	 */
	public ArrayList<FlightPoint> getFlightPoints() {
		return flightPoints;
	}

	/**
	 * Sets the {@link FlightPoint} of this Flight Path.
	 * @param flightPoints
	 */
	public void setFlightPoints(ArrayList<FlightPoint> flightPoints) {
		this.flightPoints = new ArrayList<FlightPoint>();
		for (int i = 0; i < flightPoints.size(); i ++) {
			this.flightPoints.add(flightPoints.get(i));
		}
	}

	/**
	 * Sets the {@link Airport} that the Flight Path leaves from.
	 * @param departureAirport
	 */
	public void setDepartureAirport(String departureAirport) {
		this.departureAirport = departureAirport;
	}

	/**
	 * Sets the {@link Airport} that the Flight Path arrives at.
	 * @param arrivalAirport
	 */
	public void setArrivalAirport(String arrivalAirport) {
		this.arrivalAirport = arrivalAirport;
	}

	/**
	 * Sets the ID that corresponds to the database for this flight path.
	 * Also the ID that corresponds to {@see FlightPoint} IndexID
	 * @param iD
	 */
	public void setID(int iD) {
		ID = iD;
	}

	/**
	 * gets the ID of the Flight Path.
	 * @return
	 */
	public int getID() throws DataException{
		if (ID == -1){
			throw new DataException("This Flight Path has no ID set yet");
		}
		return ID;
	}

	/**
	 * gets the {@link Airport} that the FLight Departs from.
	 * @return
	 */
	public String departsFrom(){
		return departureAirport;
	}

	/**
	 * gets the {@link Airport} that the flight arrives at.
	 * @return
	 */
	public String arrivesAt(){
		return arrivalAirport;
	}

	/**
	 * Gets all the Points that the FLight passes
	 * {@link FlightPoint}
	 * @return
	 */
	public ArrayList<FlightPoint> getFlight(){
		return flightPoints;
	}

	/**
	 * Adds a {@link FlightPoint} to the Flight Path.
	 * @param flightPoint
	 */
	public void addFlightPoint(FlightPoint flightPoint){
		flightPoints.add(flightPoint);
	}

	/**
	 * Adds a {@link FlightPoint} to the Flight Path at a specific point of the flight.
	 * @param flightPoint
	 * @param index
	 */
	public void addFlightPoint(FlightPoint flightPoint, int index){
		if (index >= flightPoints.size() || index < 0){
			flightPoints.add(flightPoint);
		}else {
			flightPoints.add(index, flightPoint);
		}
	}

	/**
	 * deletes a point from the flight.
	 * @param flightPoint
	 */
	public void delFlightPoint(FlightPoint flightPoint){
		flightPoints.remove(flightPoint);
	}

	/**
	 * delets a point from the flight at a specific index.
	 * @param index
	 */
	public void delFlightPoint(int index) throws DataException{
		if (flightPoints.size() > index && index >= 0) {
			flightPoints.remove(index);
		}else{
			throw new DataException("Flight Point at Index " + index + " does not exist for this flight Path.");
		}
	}

	/**
	 * Adds multiple {@link FlightPoint} to the FlightPath.
	 * @param flightPoints
	 */
	public void addFlightPoint(ArrayList<FlightPoint> flightPoints){
		for (int i = 0; i < flightPoints.size(); i ++){
			this.flightPoints.add(flightPoints.get(i));
		}
	}

	/**
	 * Gets the {@link RoutePath} that the FlightPath traverses.
	 * Also see {@see seng202.group9.Map.Map}
	 * @return
	 */
	public RoutePath getRoutePath(){
		if (routePath.getRoute().size() == 0){
			for (FlightPoint point: flightPoints){
				routePath.addPosition(new Position(point.getLatitude(), point.getLongitude()));
			}
		}
		return routePath;
	}
}
