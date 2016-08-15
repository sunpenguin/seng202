package seng202.group9.Core;

public class FlightPoint {
	private String name;
	private int ID;
	private int indexID;
	private String type;
	private String via;
	private String heading;
	private float altitude;//note float has a max value so they may try to break this
	private float legDistance;
	private float totalDistance;
	private float latitude;
	private float longitude;
	
	public FlightPoint(String name, int ID, int indexID, String type, String via,
			String heading, float altitude, float legDistance, float totalDistance,
			float latitude, float longitude){
		this.name = name;
		this.ID = ID;
		this.indexID = indexID;
		this.type = type;
		this.via = via;
		this.heading = heading;
		this.altitude = altitude;
		this.legDistance = legDistance;
		this.totalDistance = totalDistance;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public int getIndexID() {
		return indexID;
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

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public void setAltitude(float altitude) {
		this.altitude = altitude;
	}

	public void setLegDistance(float legDistance) {
		this.legDistance = legDistance;
	}

	public void setTotalDistance(float totalDistance) {
		this.totalDistance = totalDistance;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public String getName(){
		return name;
	}
	
	public int getID(){
		return ID;
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
	
	public String getHeading(){
		return heading;
	}
	
	public float getAltitude(){
		return altitude;
	}
	
	public float getLegDistance(){
		return legDistance;
	}
	
	public float getTotalDistance(){
		return totalDistance;
	}
	
	public float getLongitude(){
		return longitude;
	}
	
	public float getLatitude(){
		return latitude;
	}
	
}
