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

	public FlightPoint(String type, String name, double altitude, double latitude, double longitude){
		//extra calculations will have to be used to find heading, legdistance and total distance. If necessary
		//Type 1 file the file the lecturers gave us
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

	public int getIndexID() throws DataException {
		if (this.ID == -1){
			throw new DataException("ID not set.");
		}else{
			return ID;
		}
	}

	public void setIndexID(int indexID) {
		this.indexID = indexID;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setVia(String via) {
		this.via = via;
	}

	public void setHeading(int heading) {
		this.heading = heading;
	}

	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	public void setLegDistance(double legDistance) {
		this.legDistance = legDistance;
	}

	public void setTotalDistance(double totalDistance) {
		this.totalDistance = totalDistance;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getName(){
		return name;
	}

	public int getID() throws DataException {
		if (this.ID == -1){
			throw new DataException("ID not set.");
		}else{
			return ID;
		}
	}
	
	public int getIndex(){
		return indexID;
	}
	
	public String getType(){
		return type;
	}
	
	public String getVia(){
		return via;
	}
	
	public int getHeading(){
		return heading;
	}
	
	public double getAltitude(){
		return altitude;
	}
	
	public double getLegDistance(){
		return legDistance;
	}
	
	public double getTotalDistance(){
		return totalDistance;
	}
	
	public double getLongitude(){
		return longitude;
	}
	
	public double getLatitude(){
		return latitude;
	}
	
}
