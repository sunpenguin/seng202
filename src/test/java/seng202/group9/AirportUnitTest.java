package seng202.group9;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.rules.ExpectedException;

import seng202.group9.Controller.DataException;
import seng202.group9.Core.Airport;
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

		assertEquals(heathrow.getArrivalRoutes().get(0).arrivesAt(), "LHR1");
		assertEquals(heathrow.getArrivalRoutes().get(1).arrivesAt(), "LHR1");
		assertEquals(heathrow.getArrivalRoutes().get(2).arrivesAt(), "LHR2");
		assertEquals(heathrow.getArrivalRoutes().get(3).arrivesAt(), "LHR3");

		//check add departrue routes;
		heathrow.addDepartureRoutes(route1);
		heathrow.addDepartureRoutes(routes);

		assertEquals(heathrow.getDepartureRoutes().get(0).departsFrom(), "SIN1");
		assertEquals(heathrow.getDepartureRoutes().get(1).departsFrom(), "SIN1");
		assertEquals(heathrow.getDepartureRoutes().get(2).departsFrom(), "SIN2");
		assertEquals(heathrow.getDepartureRoutes().get(3).departsFrom(), "SIN3");

		//check set
		heathrow.setArrivalRoutes(routes);
		heathrow.setDepartureRoutes(routes);
		//Arrival check
		assertEquals(heathrow.getArrivalRoutes().get(0).arrivesAt(), "LHR1");
		assertEquals(heathrow.getArrivalRoutes().get(1).arrivesAt(), "LHR2");
		assertEquals(heathrow.getArrivalRoutes().get(2).arrivesAt(), "LHR3");
		//departure check
		assertEquals(heathrow.getDepartureRoutes().get(0).departsFrom(), "SIN1");
		assertEquals(heathrow.getDepartureRoutes().get(1).departsFrom(), "SIN2");
		assertEquals(heathrow.getDepartureRoutes().get(2).departsFrom(), "SIN3");
	}

	@Test
	public void checkIDException(){
		//507,"Heathrow","London","United Kingdom","LHR","EGLL",51.4775,-0.461389,83,0,"E","Europe/London"
		//ID, Name, City, Country, IATA/FFA, ICAO, Latitude, Longitude, Altitude, Timezone, DST, Tz Data
		Airport heathrow = new Airport("Heathrow", "London", "United Kingdom", "LHR", "EGLL", 51.4775, -0.41389, 83);
		thrown.expect(DataException.class);
		thrown.expectMessage("ID not set.");
		try {
			assertEquals(heathrow.getID(), 544);//check ID no id should be thrown
		} catch (DataException e) {
			
		}
	}

}
