package seng202.group9.Core;

public class Route {
	private int ID;
	private int stops;
	private String codeShare;
	private String equipment;
	private Airline airline;
	private Airport departureAirport;
	private Airport arrivalAirport;
	
	public Route(int ID, int stops, String codeShare, String equipment, Airline airline,
			Airport departureAirport, Airport arrivalAirport){
		this.ID = ID;
		this.stops = stops;
		this.codeShare = codeShare;
		this.equipment = equipment;
		this.airline = airline;
		this.departureAirport = departureAirport;
		this.arrivalAirport = arrivalAirport;
	}
	
	public String getCodeShare() {
		return codeShare;
	}

	public void setCodeShare(String codeShare) {
		this.codeShare = codeShare;
	}

	public Airport getDepartureAirport() {
		return departureAirport;
	}

	public void setDepartureAirport(Airport departureAirport) {
		this.departureAirport = departureAirport;
	}

	public Airport getArrivalAirport() {
		return arrivalAirport;
	}

	public void setArrivalAirport(Airport arrivalAirport) {
		this.arrivalAirport = arrivalAirport;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public void setStops(int stops) {
		this.stops = stops;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	public void setAirline(Airline airline) {
		this.airline = airline;
	}

	public int getID(){
		return ID;
	}
	
	public int getStops(){
		return stops;
	}
	
	public String getCode(){
		return codeShare;
	}
	
	public String getEquipment(){
		return equipment;
	}
	
	public Airline getAirline(){
		return airline;
	}
	
	public Airport departsFrom(){
		return departureAirport;
	}
	
	public Airport arrivesAt(){
		return arrivalAirport;
	}
	
	
}
