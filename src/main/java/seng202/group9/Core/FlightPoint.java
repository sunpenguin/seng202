package seng202.group9.Core;

import seng202.group9.Controller.DataException;

public class FlightPoint {
	private String name;
	private int ID;
	private int indexID;
	private String type;
	private String via;
	private int heading;
	private double altitude;//note double has a max value so they may try to break this
	private double legDistance;
	private double totalDistance;
	private double latitude;
	private double longitude;

	/**
	 * Constructor for Flight Point when creating a new path
	 * @param name
	 * @param indexID
	 */
	public FlightPoint(String name, int indexID) {
		this.name = name;
		this.ID = -1;
		this.indexID = indexID;
		this.type = "";
		this.via = "";
		this.heading = 0;
		this.altitude = 0.0;
		this.legDistance = 0;
		this.totalDistance = 0;
		this.latitude = 0.0;
		this.longitude = 0.0;
	}

	/**
	 * Constructor for Flight Point before set by the database.
	 * @param type
	 * @param name
	 * @param altitude
	 * @param latitude
	 * @param longitude
	 */
	public FlightPoint(String type, String name, double altitude, double latitude, double longitude){
		//extra calculations will have to be used to find heading, legdistance and total distance. If necessary
		//Type 1 file the file the lecturers gave us
		//indexID = flight path ID
		//ID = unique Auto Increment value
		this.name = name;
		this.ID = -1;
		this.indexID = -1;
		this.type = type;
		this.via = "";
		this.heading = 0;
		this.altitude = altitude;
		this.legDistance = 0.0;
		this.totalDistance = 0.0;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public FlightPoint(String type, int id, int index, String name, double altitude, double latitude, double longitude){
		//extra calculations will have to be used to find heading, legdistance and total distance. If necessary
		//Type 1 file the file the lecturers gave us
		//indexID = flight path ID
		//ID = unique Auto Increment value
		this.name = name;
		this.ID = id;
		this.indexID = index;
		this.type = type;
		this.via = "";
		this.heading = 0;
		this.altitude = altitude;
		this.legDistance = 0.0;
		this.totalDistance = 0.0;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * Constructor when getting points from the database.
	 * @param name Name for the point.
	 * @param ID Unique ID from Database.
	 * @param indexID FOreighn key for {@link FlightPath}.
	 * @param type
	 * @param via
	 * @param heading
	 * @param altitude
	 * @param legDistance
	 * @param totalDistance
	 * @param latitude
	 * @param longitude
	 */
	public FlightPoint(String name, int ID, int indexID, String type, String via,
			int heading, double altitude, double legDistance, double totalDistance,
			double latitude, double longitude){
		this.name = name;
		this.ID = ID;
		this.indexID = indexID; //path
		this.type = type;
		this.via = via;
		this.heading = heading;
		this.altitude = altitude;
		this.legDistance = legDistance;
		this.totalDistance = totalDistance;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * get the Path ID
	 * @return
	 * @throws DataException
	 */
	public int getIndexID() throws DataException {
		if (this.ID == -1){
			throw new DataException("ID not set.");
		}else{
			return ID;
		}
	}

	/**
	 * sets the Path ID
	 * @param indexID
	 */
	public void setIndexID(int indexID) {
		this.indexID = indexID;
	}

	/**
	 * sets the name of the path.
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * sets the Unique Database ID of the Path
	 * @param iD
	 */
	public void setID(int iD) {
		ID = iD;
	}

	/**
	 * Sets the type of the Point.
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * set the VIA of the Point.
	 * @param via
	 */
	public void setVia(String via) {
		this.via = via;
	}

	/**
	 * Sets bearing the flight is heading.
	 * @param heading
	 */
	public void setHeading(int heading) {
		this.heading = heading;
	}

	/**
	 * sets the altitude of the flight at this point.
	 * @param altitude
	 */
	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	/**
	 * sets the distance this flight takes before the next point.
	 * @param legDistance
	 */
	public void setLegDistance(double legDistance) {
		this.legDistance = legDistance;
	}

	/**
	 * sets total distance travelled at this point.
	 * @param totalDistance
	 */
	public void setTotalDistance(double totalDistance) {
		this.totalDistance = totalDistance;
	}

	/**
	 * sets the latitude at this point.
	 * @param latitude
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * Sets the Longitude at this point.
	 * @param longitude
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * gets the name of this point.
	 * @return
	 */
	public String getName(){
		return name;
	}

	/**
	 * gets the UNIQUE ID at this point.
	 * @return
	 * @throws DataException
	 */
	public int getID() throws DataException {
		if (this.ID == -1){
			throw new DataException("ID not set.");
		}else{
			return ID;
		}
	}

	/**
	 * gets the Path Index ID at this point.
	 * @return
	 */
	public int getIndex() throws DataException{
		if (indexID == -1){
			throw new DataException("Index ID is not set.");
		}
		return indexID;
	}

	/**
	 * gets the type of this point.
	 * @return
	 */
	public String getType(){
		return type;
	}

	/**
	 * gets where the plane is via at this point.
	 * @return
	 */
	public String getVia(){
		return via;
	}

	/**
	 * gets the Heading bearing at this point
	 * @return
	 */
	public int getHeading(){
		return heading;
	}

	/**
	 * gets the altitude at this poitn.
	 * @return
	 */
	public double getAltitude(){
		return altitude;
	}

	/**
	 * gets the leg distance at this point.
	 * @return
	 */
	public double getLegDistance(){
		return legDistance;
	}

	/**
	 * gets total distance travelled by this flight so far.
	 * @return
	 */
	public double getTotalDistance(){
		return totalDistance;
	}

	/**
	 * gets longitude of this point.
	 * @return
	 */
	public double getLongitude(){
		return longitude;
	}

	/**
	 * gets the latitude of this point.
	 * @return
	 */
	public double getLatitude(){
		return latitude;
	}
	
}
