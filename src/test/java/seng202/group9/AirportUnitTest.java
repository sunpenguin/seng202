package seng202.group9;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.rules.ExpectedException;

import seng202.group9.Controller.DataException;
import seng202.group9.Core.Airport;
import seng202.group9.Core.City;
import seng202.group9.Core.Country;
import seng202.group9.Core.Route;

import java.util.ArrayList;

/**
 * Unit test for Airport
 * @author Fan-Wu Yang
 *
 */
public class AirportUnitTest {
	//exception checker
	public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void airportGetterAndSetterTest() {
		//507,"Heathrow","London","United Kingdom","LHR","EGLL",51.4775,-0.461389,83,0,"E","Europe/London"
		//ID, Name, City, Country, IATA/FFA, ICAO, Latitude, Longitude, Altitude, Timezone, DST, Tz Data
		Airport heathrow = new Airport(507, "Heathrow", "London", "United Kingdom", "LHR", "EGLL", 51.4775, -0.41389, 83);
		/////////////////
		//check getters//
		/////////////////
		try {
			assertEquals(heathrow.getID(), 507);//check ID no id should be thrown
		} catch (DataException e) {
			fail("No Exception should be thrown by the constructor where the ID is already set bye trying to get the ID.");
		}
		assertEquals(heathrow.getName(), "Heathrow");
		assertEquals(heathrow.getCityName(), "London");//check city
		assertEquals(heathrow.getCountryName(), "United Kingdom");
		assertEquals(heathrow.getIATA_FFA(), "LHR");//check IATA/FFA
		assertEquals(heathrow.getICAO(), "EGLL");//check ICAO
		assertTrue(heathrow.getLatitude() == 51.4775);//check latitude
		assertTrue(heathrow.getLongitude() == -0.41389);//check longitude
		assertTrue(heathrow.getAltitude() == 83);//check altitude.
		/////////////////
		//check setters//
		/////////////////
		heathrow.setID(544);
		try {
			assertEquals(heathrow.getID(), 544);//check ID no id should be thrown
		} catch (DataException e) {
			fail("No Exception should be thrown by the constructor where the ID is already set bye trying to get the ID.");
		}
		heathrow.setName("Hearthstone");//check name
		assertEquals(heathrow.getName(), "Hearthstone");
		heathrow.setCityName("Blizzard Servers");//check city
		assertEquals(heathrow.getCityName(), "Blizzard Servers");
		heathrow.setCountryName("America");
		assertEquals(heathrow.getCountryName(), "America");
		heathrow.setIATA_FFA("HTS");//test set IATA/FFA
		assertEquals(heathrow.getIATA_FFA(), "HTS");
		heathrow.setICAO("BLIZ");// test set ICAO
		assertEquals(heathrow.getICAO(), "BLIZ");
		heathrow.setLatitude(50.1);//test set latitude
		assertTrue(heathrow.getLatitude() == 50.1);
		heathrow.setLongitude(0.006);//test set longitude
		assertTrue(heathrow.getLongitude() == 0.006);
		heathrow.setCoordinates(51.4775, -0.41389);//test set coordinates
		assertTrue(heathrow.getLatitude() == 51.4775);
		assertTrue(heathrow.getLongitude() == -0.41389);
		heathrow.setAltitude(0);//test set altitude
		assertTrue(heathrow.getAltitude() == 0);
	}

	@Test
	public void checkRoutesFnc(){
		//507,"Heathrow","London","United Kingdom","LHR","EGLL",51.4775,-0.461389,83,0,"E","Europe/London"
		//ID, Name, City, Country, IATA/FFA, ICAO, Latitude, Longitude, Altitude, Timezone, DST, Tz Data
		Airport heathrow = new Airport(507, "Heathrow", "London", "United Kingdom", "LHR", "EGLL", 51.4775, -0.41389, 83);
		//BA,1355,SIN,3316,LHR,507,,0,744 777
		//airline, airline ID (This is not parsed as this will be different
		//from our database id for this route), source Aiport, source Airport ID (not parsed),
		//destination airport, destination ID (not parsed), Codeshare, stops, Equipment
		Route route1 = new Route("BA1", "SIN1", "LHR1", "", 0, "744 777");
		Route route2 = new Route("BA2", "SIN2", "LHR2", "", 0, "744 777");
		Route route3 = new Route("BA3", "SIN3", "LHR3", "", 0, "744 777");

		ArrayList<Route> routes = new ArrayList<Route>();
		routes.add(route1);
		routes.add(route2);
		routes.add(route3);

		//first check addArrivalRoutes;
		heathrow.addArrivalRoutes(route1);
		heathrow.addArrivalRoutes(routes);

		assertEquals(heathrow.getArrivalRoutes().get(0).getArrivalAirport(), "LHR1");
		assertEquals(heathrow.getArrivalRoutes().get(1).getArrivalAirport(), "LHR1");
		assertEquals(heathrow.getArrivalRoutes().get(2).getArrivalAirport(), "LHR2");
		assertEquals(heathrow.getArrivalRoutes().get(3).getArrivalAirport(), "LHR3");

		//check add departrue routes;
		heathrow.addDepartureRoutes(route1);
		heathrow.addDepartureRoutes(routes);

		assertEquals(heathrow.getDepartureRoutes().get(0).getDepartureAirport(), "SIN1");
		assertEquals(heathrow.getDepartureRoutes().get(1).getDepartureAirport(), "SIN1");
		assertEquals(heathrow.getDepartureRoutes().get(2).getDepartureAirport(), "SIN2");
		assertEquals(heathrow.getDepartureRoutes().get(3).getDepartureAirport(), "SIN3");

		//check set
		heathrow.setArrivalRoutes(routes);
		heathrow.setDepartureRoutes(routes);
		//Arrival check
		assertEquals(heathrow.getArrivalRoutes().get(0).getArrivalAirport(), "LHR1");
		assertEquals(heathrow.getArrivalRoutes().get(1).getArrivalAirport(), "LHR2");
		assertEquals(heathrow.getArrivalRoutes().get(2).getArrivalAirport(), "LHR3");
		//departure check
		assertEquals(heathrow.getDepartureRoutes().get(0).getDepartureAirport(), "SIN1");
		assertEquals(heathrow.getDepartureRoutes().get(1).getDepartureAirport(), "SIN2");
		assertEquals(heathrow.getDepartureRoutes().get(2).getDepartureAirport(), "SIN3");
	}

	@Test
	public void testCountryAndCity(){
		Airport heathrow = new Airport(507, "Heathrow", "London", "United Kingdom", "LHR", "EGLL", 51.4775, -0.41389, 83);
		Country UnitedKing = new Country("E", "United Kingdom");
		heathrow.setCountry(UnitedKing);
		City london = new City("London", "United Kingdom", 0, "Europe/London");
		UnitedKing.addCities(london);
		heathrow.setCity(london);
		assertEquals(heathrow.getCity().getName(), "London");
		assertEquals(heathrow.getCity().getCountry(), "United Kingdom");
		assertEquals(heathrow.getCity().getTimeOlson(), "Europe/London");
		assertTrue(heathrow.getCity().getTimezone() == 0);
		//country tests
		assertEquals(heathrow.getCountry().getDST(), "E");
		assertEquals(heathrow.getCountry().getName(), "United Kingdom");
		//check if the country has city london
		assertEquals(heathrow.getCountry().getCities().get(0), london);
		//check the get commands from the airport city 
		assertEquals(heathrow.getDST(), "E");
		assertEquals(heathrow.getDST(), heathrow.getCountry().getDST());
		assertEquals(heathrow.getCityName(), heathrow.getCity().getName());
		assertEquals(heathrow.getCity().getCountry(), heathrow.getCountryName());
		assertEquals(heathrow.getCity().getCountry(), "United Kingdom");
		assertEquals(heathrow.getTz(), "Europe/London");
		assertEquals(heathrow.getTz(), heathrow.getCity().getTimeOlson());
		assertTrue(heathrow.getTimezone() == 0);
		assertTrue(heathrow.getTimezone() == heathrow.getCity().getTimezone());
		//check the get commands for airport countries
		assertEquals(heathrow.getCountryName(), heathrow.getCountry().getName());
		assertEquals(heathrow.getCountry().getDST(), heathrow.getDST());
		assertEquals(heathrow.getCountry().getDST(), "E");
	}
	
	@Test
	public void checkCalculateDistance(){
		//check whether the airport distance between teh same airport is 0
		Airport heathrow = new Airport(507, "Heathrow", "London", "United Kingdom", "LHR", "EGLL", 51.4775, -0.41389, 83);
		Airport heathrow2 = new Airport(507, "Heathrow", "London", "United Kingdom", "LHR", "EGLL", 51.4775, -0.41389, 83);
		assertTrue(heathrow.calculateDistance(heathrow2) == 0);
		//check the correct distance between this and Edmonton
		Airport edmonton = new Airport(26,"Kugaaruk","Pelly Bay","Canada","YBB","CYBB",68.534444,-89.808056,56);
		//must be correct with a 0.3% margin of error calculations from http://www.movable-type.co.uk/scripts/latlong.html
		assertTrue(heathrow.calculateDistance(edmonton) <= 4789 * 1.003);
		assertTrue(heathrow.calculateDistance(edmonton) >= 4789 * 0.997);
	}
	
	@Test(expected = DataException.class)
	public void checkNameDuplicate() throws DataException{
		Airport heathrow = new Airport(507, "Heathrow", "London", "United Kingdom", "LHR", "EGLL", 51.4775, -0.41389, 83);
		Airport heathrow2 = new Airport(507, "Heathrow", "Londons", "United Ksingdom", "LRR", "EPLL", 51.4775, -0.41389, 83);
		heathrow.hasDuplicate(heathrow2);
	}
	
	@Test(expected = DataException.class)
	public void checkEmptyNameDuplicate() throws DataException{
		Airport heathrow = new Airport(507, "Heathrow", "London", "United Kingdom", "LHR", "EGLL", 51.4775, -0.41389, 83);
		Airport heathrow2 = new Airport(507, "", "Londons", "United Ksingdom", "LRR", "EPLL", 51.4775, -0.41389, 83);
		heathrow.hasDuplicate(heathrow2);
	}

	@Test(expected = DataException.class)
	public void checkIATADuplicate() throws DataException{
		Airport heathrow = new Airport(507, "Heathrow", "London", "United Kingdom", "LHR", "EGLL", 51.4775, -0.41389, 83);
		Airport heathrow2 = new Airport(507, "Heathrsow", "Lossndon", "United Kisssngdom", "LHR", "EKLL", 51.4775, -0.41389, 83);
		heathrow.hasDuplicate(heathrow2);
	}

	@Test(expected = DataException.class)
	public void checkICAODuplicate() throws DataException{
		Airport heathrow = new Airport(507, "Heathrow", "London", "United Kingdom", "LHR", "EGLL", 51.4775, -0.41389, 83);
		Airport heathrow2 = new Airport(507, "Heathrows", "Lonsdon", "United Kisngdom", "LJR", "EGLL", 51.4775, -0.41389, 83);
		heathrow.hasDuplicate(heathrow2);
	}
	
	@Test(expected = DataException.class)
	public void checkIDException() throws DataException{
		//507,"Heathrow","London","United Kingdom","LHR","EGLL",51.4775,-0.461389,83,0,"E","Europe/London"
		//ID, NaWme, City, Country, IATA/FFA, ICAO, Latitude, Longitude, Altitude, Timezone, DST, Tz Data
		Airport heathrow = new Airport("Heathrow", "London", "United Kingdom", "LHR", "EGLL", 51.4775, -0.41389, 83);
		assertEquals(heathrow.getID(), 544);//check ID no id should be thrown
	}

}
